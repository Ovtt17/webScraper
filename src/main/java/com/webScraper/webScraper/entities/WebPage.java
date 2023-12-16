package com.webScraper.webScraper.entities;

import lombok.*;

import jakarta.persistence.*;

import javax.management.ConstructorParameters;

@Entity
@Table(name = "webpage")
@Getter @Setter
@ToString @EqualsAndHashCode
public class WebPage {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "url")
  private String url;

  @Column(name = "title")
  private String title;

  @Column(name = "description")
  private String description;

  public WebPage() {
  }
  public WebPage(String url) {
    this.url = url;
  }

}