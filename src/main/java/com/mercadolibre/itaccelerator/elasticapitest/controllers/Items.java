package com.mercadolibre.itaccelerator.elasticapitest.controllers;

/*
{
  "title":"Item de test - No Ofertar",
  "category_id":"MLA5529",
  "price":10,
  "currency_id":"ARS",
  "available_quantity":1,
  "buying_mode":"buy_it_now",
  "listing_type_id":"bronze",
  "condition":"new",
  "description": "Item:,  Ray-Ban WAYFARER Gloss Black RB2140 901  Model: RB2140. Size: 50mm. Name: WAYFARER. Color: Gloss Black. Includes Ray-Ban Carrying Case and Cleaning Cloth. New in Box",
  "video_id": "YOUTUBE_ID_HERE",
  "warranty": "12 months by Ray Ban",
  "Pictures":[
    {"source":"http://upload.wikimedia.org/wikipedia/commons/f/fd/Ray_Ban_Original_Wayfarer.jpg"},
    {"source":"http://en.wikipedia.org/wiki/File:Teashades.gif"}
  ]
}

*/


import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.math.BigDecimal;

public class Items {

    private String title;
    private String category_id;
    private BigDecimal price;
    private String currency_id;
    private Integer available_quantity;
    private String buying_mode;
    private String listing_type_id;
    private String condition;
    private String description;
    private String video_id;
    private String warranty;
    private Pictures[] Pictures;


    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getCurrency_id() {
        return currency_id;
    }

    public void setCurrency_id(String currency_id) {
        this.currency_id = currency_id;
    }

    public Integer getAvailable_quantity() {
        return available_quantity;
    }

    public void setAvailable_quantity(Integer available_quantity) {
        this.available_quantity = available_quantity;
    }

    public String getBuying_mode() {
        return buying_mode;
    }

    public void setBuying_mode(String buying_mode) {
        this.buying_mode = buying_mode;
    }

    public String getListing_type_id() {
        return listing_type_id;
    }

    public void setListing_type_id(String listin_type_id) {
        this.listing_type_id = listin_type_id;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public String getTitle() {
        return title;

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) { this.warranty = warranty; }

    public Pictures[] getPictures() {
        return Pictures;
    }

    public void setPictures(Pictures[] Pictures) {
        this.Pictures = Pictures;
    }
}
