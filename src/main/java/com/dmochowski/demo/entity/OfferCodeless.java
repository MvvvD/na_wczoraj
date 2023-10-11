package com.dmochowski.demo.entity;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OfferCodeless {
    public OfferCodeless(Offer offer) {
       this.id = offer.getId();
        this.title = offer.getTitle();
        this.details = offer.getDetails();
        this.category = offer.getCategory();
        this.contact = offer.getContact();
        this.postedOn = offer.getPostedOn();
    }

    private int id;
    private String title;
    private String details;
    private String category;
    private int contact;
    private java.sql.Timestamp postedOn;
}
