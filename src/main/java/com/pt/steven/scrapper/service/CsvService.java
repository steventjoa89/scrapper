package com.pt.steven.scrapper.service;

import com.pt.steven.scrapper.model.Product;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.IOError;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

@Service
public class CsvService {
  @Value("${csv.file.path}")
  private String SAMPLE_CSV_FILE;

  public void writeIntoCsvFile(ArrayList<Product> productList){
    try{
      BufferedWriter writer = Files.newBufferedWriter(Paths.get(SAMPLE_CSV_FILE));
      CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("No", "Name", "Description", "Image Url", "Price", "Rating", "Seller"));{
        for(int i=0;i<productList.size();i++){
          csvPrinter.printRecord((i+1),
              productList.get(i).getName(),
              productList.get(i).getDescription(),
              productList.get(i).getImageUrl(),
              productList.get(i).getPrice(),
              productList.get(i).getRating(),
              productList.get(i).getSellerName());
        }
        csvPrinter.flush();
        csvPrinter.close();
        writer.close();
      }

    }catch (IOException ex){
      System.err.println(ex.toString());
    }
  }
}
