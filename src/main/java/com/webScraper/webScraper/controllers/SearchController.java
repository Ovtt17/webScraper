package com.webScraper.webScraper.controllers;

import com.webScraper.webScraper.entities.WebPage;
import com.webScraper.webScraper.services.SearchService;
import com.webScraper.webScraper.services.SpiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class SearchController {
  @Autowired
  private SearchService searchService;
  @Autowired
  private SpiderService spiderService;

  @RequestMapping(value = "api/search", method = RequestMethod.GET)
  public List<WebPage> search (@RequestParam Map<String, String> params) {
    String query = params.get("query");
    return searchService.search(query);
  }
  @RequestMapping(value = "api/test", method = RequestMethod.GET)
  public void search () {
    spiderService.indexWebPages();
  }
}
