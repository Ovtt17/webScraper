package com.webScraper.webScraper.services;

import com.webScraper.webScraper.entities.WebPage;
import com.webScraper.webScraper.repositories.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {
  @Autowired
  private SearchRepository repository;
  public List<WebPage> search(String textToSearch) {
    return repository.search(textToSearch);
  }

  public void save(WebPage webPage) {
    repository.save(webPage);
  }

  public boolean exist(String link) {
    return repository.exist(link);
  }
  List<WebPage> getLinksToIndex(){
    return repository.getLinksToIndex();
  }
}
