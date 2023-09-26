package com.dmochowski.demo.service;

import com.dmochowski.demo.entity.Offer;

import java.util.List;

public interface OfferService {
    List<Offer> findAll();
    List<Offer> findByCategory(String category);
    Offer findById(int id);
    void delete(Offer offer);
    void delete(int id);
    Offer add(Offer offer);
}
