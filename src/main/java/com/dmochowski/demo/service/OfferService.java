package com.dmochowski.demo.service;

import com.dmochowski.demo.entity.Offer;
import com.dmochowski.demo.entity.OfferCodeless;

import java.util.List;

public interface OfferService {
    List<Offer> findAll();

    List<OfferCodeless> findAllSortedCodeless();

    List<OfferCodeless> findByCategory(String category);
    Offer findById(int id);

    boolean existsOfferByContact(String contact);
    OfferCodeless findByIdCodeless(int id);
    OfferCodeless update(int id, Offer offer, String code);
    boolean delete(int id, String code);
    Offer add(Offer offer);

    boolean activation(int id, String code);
}
