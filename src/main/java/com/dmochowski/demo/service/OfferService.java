package com.dmochowski.demo.service;

import com.dmochowski.demo.entity.Offer;

import java.util.List;

public interface OfferService {
    List<Offer> findAll();
    List<Offer> findByCategory(String category);
    Offer findById(int id);
    void delete(Offer offer);
    Offer update(int id, Offer offer, String code);
    boolean delete(int id, String code);
    Offer add(Offer offer);
}
