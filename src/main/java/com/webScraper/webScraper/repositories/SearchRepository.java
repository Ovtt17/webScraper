package com.webScraper.webScraper.repositories;

import com.webScraper.webScraper.entities.WebPage;

import java.util.List;

public interface SearchRepository {
  WebPage getByUrl(String url);
  List<WebPage> getLinksToIndex();
  List<WebPage> search(String textToSearch);

  void save(WebPage webPage);

  boolean exist(String link);
}
