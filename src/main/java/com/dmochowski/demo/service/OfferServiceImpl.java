package com.dmochowski.demo.service;

import com.dmochowski.demo.dao.OfferRepo;
import com.dmochowski.demo.entity.Offer;
import com.dmochowski.demo.entity.OfferCodeless;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class OfferServiceImpl implements OfferService {
    private final OfferRepo offerRepo;
    private static final int codeLen = 8;

    @Override
    public List<Offer> findAll() {
        return offerRepo.findAll();
    }
    @Override
    public List<OfferCodeless> findAllCodeless() {
        List<OfferCodeless> codeless = new ArrayList<>();
        findAll().forEach(offer -> codeless.add(offerToCodeless(offer)));
        return codeless;
    }

    @Override
    public List<OfferCodeless> findByCategory(String category) {
        List<OfferCodeless> codeless = new ArrayList<>();
        offerRepo.findByCategory(category).forEach(offer -> codeless.add(offerToCodeless(offer)));
        return codeless;
    }

    @Override
    public Offer findById(int id) {
        Optional<Offer> offer = offerRepo.findById(id);
        if (offer.isEmpty()) {
            throw new RuntimeException("Employee with ID " + id + " not found");
        } else {
            return offer.get();
        }
    }

    @Override
    public OfferCodeless findByIdCodeless(int id) {
        return offerToCodeless(findById(id));
    }

    @Override
    public boolean delete(int id, String code) {
        Offer offer = findById(id);
        if (offer.getCode().equals(code)) {
            offerRepo.delete(offer);
            return true;
        }
        return false;
    }

    @Override
    public OfferCodeless update(int id, Offer offer, String code) {
        Offer dbOffer = findById(id);
        if (!codeCheck(code, dbOffer)) {
            throw new RuntimeException("Wrong code");
        }
        offer.setId(dbOffer.getId());
        offer.setCode(dbOffer.getCode());
        offer.setPostedOn(dbOffer.getPostedOn());
        offerRepo.save(offer);
        return offerToCodeless(offer);
    }

    @Override
    public Offer add(Offer offer) {
        //here i'd add some SMS service like twilio to provide special code to the user
        //TODO add category verification
        offer.setId(0);
        offer.setPostedOn(new Timestamp(System.currentTimeMillis()));
        offer.setCode(SpecialCodeGenerator.codeGenerator());
        return offerRepo.save(offer);
    }

    @Scheduled(cron = "55 59 23 * * ?")
    public void clearTable() {
        //TODO add archiving/history feature
        offerRepo.deleteAll();
    }

    private boolean codeCheck(String code, Offer offer) {
        return code.toLowerCase().equals(offer.getCode());
    }

    private OfferCodeless offerToCodeless(Offer offer) {
        return new OfferCodeless(offer);
    }

    private static class SpecialCodeGenerator {
        static char[] chars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'q', 'w', 'e', 'r', 't', 'z', 'u', 'i', 'o', 'p', 'a', 's',
                'd', 'f', 'g', 'h', 'j', 'k', 'l', 'y', 'x', 'c', 'v', 'b', 'n', 'm'};

        private static String codeGenerator() {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < codeLen; i++) {
                stringBuilder.append(chars[new Random().nextInt(chars.length)]);
            }
            return stringBuilder.toString();

        }
    }
}
