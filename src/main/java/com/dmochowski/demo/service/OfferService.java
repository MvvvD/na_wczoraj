package com.dmochowski.demo.service;

import com.dmochowski.demo.entity.Offer;
import com.dmochowski.demo.entity.OfferCodeless;

import java.util.List;

public interface OfferService {
    List<Offer> findAll();
    List<OfferCodeless> findAllCodeless();
    List<OfferCodeless> findByCategory(String category);
    Offer findById(int id);
    OfferCodeless findByIdCodeless(int id);
    void delete(Offer offer);
    OfferCodeless update(int id, Offer offer, String code);
    boolean delete(int id, String code);
    Offer add(Offer offer);
}
