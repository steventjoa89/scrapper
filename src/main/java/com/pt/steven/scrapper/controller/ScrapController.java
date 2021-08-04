package com.pt.steven.scrapper.controller;

import com.pt.steven.scrapper.service.ScrapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScrapController {
  @Autowired
  ScrapService scrapService;

  @GetMapping("/scrapper")
  public @ResponseBody String getTokopedia() {
    return scrapService.getTokopedia();
  }
}
