package com.webScraper.webScraper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class WebScraperApplication {
	public static void main(String[] args) {
		SpringApplication.run(WebScraperApplication.class, args);
	}

}
