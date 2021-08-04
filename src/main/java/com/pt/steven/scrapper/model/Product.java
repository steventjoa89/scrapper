package com.pt.steven.scrapper.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {
  private String name;
  private String description;
  private String imageUrl;
  private String price;
  private String rating;
  private String sellerName;
}
