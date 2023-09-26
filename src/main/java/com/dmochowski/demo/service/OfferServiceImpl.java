package com.dmochowski.demo.service;

import com.dmochowski.demo.dao.OfferRepo;
import com.dmochowski.demo.entity.Offer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OfferServiceImpl implements OfferService{
    private final OfferRepo offerRepo;

    @Override
    public List<Offer> findAll() {
        return offerRepo.findAll();
    }

    @Override
    public List<Offer> findByCategory(String category) {
        return offerRepo.findByCategory(category);
    }

    @Override
    public Offer findById(int id) {
        Optional<Offer> offer = offerRepo.findById(id);
        if (offer.isEmpty()){
            throw new RuntimeException("Employee with ID "+ id + " not found");
        }
        else {
             return offer.get();
        }
    }

    @Override
    public void delete(Offer offer) {
        offerRepo.delete(offer);
    }

    @Override
    public void delete(int id) {
        offerRepo.delete(findById(id));
    }

    @Override
    public Offer add(Offer offer) {
        offer.setId(0);
        offer.setPostedOn(new Timestamp(System.currentTimeMillis()));
        return offerRepo.save(offer);
    }


}
