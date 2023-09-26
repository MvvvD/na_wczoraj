package com.dmochowski.demo.dao;

import com.dmochowski.demo.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferRepo extends JpaRepository<Offer, Integer> {
    List<Offer> findByCategory(String category);
}
