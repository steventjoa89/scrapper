package com.pt.steven.scrapper.service;

import com.pt.steven.scrapper.model.Product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.util.ArrayList;

@Service
public class ScrapService {

  @Autowired
  CsvService csvService;

  ArrayList<Product> productList = new ArrayList<Product>();

  public String getTokopedia(){
    for(int i=1;i<=10;i++){
      scrapMasterTokopedia(i);
    }
    csvService.writeIntoCsvFile(productList);
    System.out.println("done");
    return "Done";
  }


  private String getSellerNameFromMetaDataUrl(String metaString){
    String revMetaString = new StringBuilder(metaString).reverse().toString();
    int posPipe = revMetaString.indexOf("|");
    int posDi = revMetaString.indexOf(" id ", posPipe+1);

    if(posPipe == -1 || posDi == -1){
      return metaString;
    }
    revMetaString = revMetaString.substring(posPipe+2, posDi);
    return new StringBuilder(revMetaString).reverse().toString();
  }

  private void scrapMasterTokopedia(int page){
    try{
      Document doc = Jsoup.connect("https://www.tokopedia.com/search?q=handphone"+((page>1)?"&page="+page:"")).timeout(6000).get();
      Elements body = doc.select("div.css-rjanld");

      for(Element e: body.select("a.pcv3__info-content.css-1qnnuob")){
        if(removeWebProtocol(e.attr("href")).startsWith("tokopedia")){
          System.out.println("Scrapping details link: "+e.attr("href"));
          scrapDetailTokopediaProducts(e.attr("href"));
        }
      }

    }catch(Exception e){
      System.err.println(e.getMessage());
    }
  }

  private void scrapDetailTokopediaProducts(String url){
    try{
      System.out.println("masuk scrap details...");

      Document doc = Jsoup.connect(url).timeout(6000).get();
      Elements body = doc.select("div.css-3lpl5n");
//      System.out.println("Product Name: "+body.select("h1.css-1wtrxts").text());         // PRODUT NAME
//      System.out.println("Product Price: "+body.select("div.price").text());         // PRODUT PRICE
//      System.out.println("Product Description: " + body.select("span.css-168ydy0.e1iszlzh1").text());         // PRODUCT Description
//      System.out.println("Product Image" + body.select("div.css-19i5z4j img").attr("src"));   // Product Image
//      System.out.println("Product Rating: "+doc.select("meta[itemprop=ratingValue]").attr("content"));   // Product Rating
//      System.out.println("Seller Name: "+getSellerNameFromMetaDataUrl(doc.select("meta[property=og:title]").attr("content")));       // Seller
      productList.add(
          new Product(
              body.select("h1.css-1wtrxts").text(),
              body.select("span.css-168ydy0.e1iszlzh1").text(),
              body.select("div.css-19i5z4j img").attr("src"),
              body.select("div.price").text(),
              doc.select("meta[itemprop=ratingValue]").attr("content"),
              getSellerNameFromMetaDataUrl(doc.select("meta[property=og:title]").attr("content"))));

    }catch(Exception e){
      System.err.println(e.getMessage());
    }
  }

  private String removeWebProtocol(String url){
    return url.replaceFirst("^(http[s]?://www\\.|http[s]?://|www\\.)","");
  }


}
