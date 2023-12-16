package com.webScraper.webScraper.services;

import com.webScraper.webScraper.entities.WebPage;
import org.hibernate.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SpiderService {
  @Autowired
  private SearchService searchService;
  public void indexWebPages () {
    List<WebPage> linksToIndex = searchService.getLinksToIndex();
    linksToIndex.stream().parallel().forEach(webPage -> {
      try {
        indexWebPage(webPage);
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    });

  }

  private void indexWebPage(WebPage webPage) throws Exception {
    String url = webPage.getUrl();
    String content = getWebContent(url);
    if (StringHelper.isBlank(content)) {
      return;
    }
    indexAndsaveWebPage(webPage, content);
    String domain = getDomain(url);
    saveLinks(domain, content);
  }

  private String getDomain(String url) {
    String[] aux = url.split("/");
    return aux[0] + "//" + aux[2];
  }

  private void saveLinks(String domain, String content) {
    List<String> links = getLinks(domain, content);
    links.stream()
        .filter(link -> !searchService.exist(link))
        .map(link -> new WebPage(link))
        .forEach(webPage -> searchService.save(webPage));
  }


  public List<String> getLinks(String domain, String content) {
    List<String> links = new ArrayList<>();

    String[] splitHref = content.split("href=\"");

    List<String> listHref = new ArrayList<>(Arrays.asList(splitHref));

    listHref.forEach(strHref -> {
      String[] aux = splitHref[1].split("\"");
      links.add(aux[0]);
    });
    cleanLinks(domain, links);
    return links;
  }

  private List<String> cleanLinks(String domain, List<String> links) {
    String[] excludedExtensions = new String[] {"css", "js", "json", "jpg", "png", "woff2"};

    List<String> resultList = links.stream()
        .filter(link -> Arrays.stream(excludedExtensions)
        .noneMatch(link::endsWith))
        .map(link -> link.startsWith("/") ? domain + link : link)
        .filter(link -> link.startsWith("http"))
        .toList();

    List<String> uniqueLinks = new ArrayList<>();
    uniqueLinks.addAll(new HashSet<>(resultList));

    return uniqueLinks;
  }

  private void indexAndsaveWebPage(WebPage webPage, String content) {
    String title = getTitle(content);
    String description = getDescription(content);

    webPage.setTitle(title);
    webPage.setDescription(description);
    searchService.save(webPage);
  }

  public String getTitle(String content) {
    String[] titleSplit = content.split("<title>");
    if (titleSplit.length > 1) {
      String[] titleContentSplit = titleSplit[1].split("</title>");
      if (titleContentSplit.length > 0) {
        System.out.println(titleContentSplit[0]); // Imprimir el título
        return titleContentSplit[0];
      }
    }
    return "Title not found";
  }

  public String getDescription(String content) {
    String[] descriptionSplit = content.split("<meta name=\"description\" content=\"");
    if (descriptionSplit.length > 1) {
      String[] descriptionContentSplit = descriptionSplit[1].split("\">");
      if (descriptionContentSplit.length > 0) {
        return descriptionContentSplit[0];
      }
    }
    return "Description not found";
  }
  private String getWebContent(String link) {
    try {
      URL url = new URL(link);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      String encoding = conn.getContentEncoding();

      InputStream input = conn.getInputStream();
      Stream<String> lines = new BufferedReader(new InputStreamReader(input))
          .lines();
      //result
      return lines.collect(Collectors.joining());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
