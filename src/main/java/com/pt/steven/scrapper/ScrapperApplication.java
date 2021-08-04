package com.pt.steven.scrapper;

import com.pt.steven.scrapper.service.ScrapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScrapperApplication implements ApplicationRunner {
	@Autowired
	ScrapService scrapService;

	public static void main(String[] args) {
		SpringApplication.run(ScrapperApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments arg0) throws Exception {
//		scrapService.getTokopedia();
	}
}
