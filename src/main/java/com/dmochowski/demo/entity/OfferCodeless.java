package com.dmochowski.demo.entity;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OfferCodeless extends Offer {
    public OfferCodeless(Offer offer) {
        this.setId(offer.getId());
        this.setTitle(offer.getTitle());
        this.setDetails(offer.getDetails());
        this.setContact(offer.getContact());
        this.setCategory(offer.getCategory());
        this.setPostedOn(offer.getPostedOn());
        this.setCode("secret code");
    }
}
