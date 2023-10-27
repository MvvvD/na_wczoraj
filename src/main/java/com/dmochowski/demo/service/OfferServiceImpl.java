package com.dmochowski.demo.service;

import com.dmochowski.demo.dao.OfferRepo;
import com.dmochowski.demo.entity.Offer;
import com.dmochowski.demo.entity.OfferCodeless;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
@AllArgsConstructor
public class OfferServiceImpl implements OfferService {
    private final OfferRepo offerRepo;
    private static final int codeLen = 8;
    private static final String[] categories = {"złota rączka", "korepetycje",
            "sprzątanie", "opieka nad dzieckiem", "zakupy", "opieka nad zwierzęciem"};

    @Override
    public List<Offer> findAll() {
        return offerRepo.findAllVisible();
    }

    @Override
    public List<OfferCodeless> findAllSortedCodeless() {
        List<OfferCodeless> codeless = new ArrayList<>();
        findAll().forEach(offer -> codeless.add(offerToCodeless(offer)));
        return codeless;
    }


    @Override
    public List<OfferCodeless> findByCategory(String category) {
        if (!Arrays.asList(categories).contains(category.toLowerCase())) {
            throw new RuntimeException("wrong category " + category);

        }
        category = category.toLowerCase();
        List<OfferCodeless> codeless = new ArrayList<>();
        offerRepo.findByCategory(category.toLowerCase()).forEach(offer -> codeless.add(offerToCodeless(offer)));
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
        offer.setCategory(offer.getCategory().toLowerCase());
        offerRepo.save(offer);
        return offerToCodeless(offer);
    }

    @Override
    public Offer add(Offer offer) {
        if (!Arrays.asList(categories).contains(offer.getCategory().toLowerCase())) {
            throw new RuntimeException("wrong category from offer titled " + offer.getTitle() + " id : " + offer.getId());
        }
        if (existsOfferByContact(offer.getContact())) {
            throw new RuntimeException("Phone number already in use");
        }
        offer.setId(0);
        offer.setPostedOn(new Timestamp(System.currentTimeMillis()));
        offer.setCode(SpecialCodeGenerator.codeGenerator());
        System.out.println(offer.getCode());
        //here I'd add some SMS service like twilio to provide special code to the user
        return offerRepo.save(offer);
    }

    @Override
    public boolean existsOfferByContact(String contact) {
        return offerRepo.existsOfferByContact(contact);
    }

    @Override
    public boolean activation(int id, String code) {
        Offer offer = findById(id);
        if (offer.getCode().equals(code)) {
            offer.setVisible(true);
            offerRepo.save(offer);
        }
        return offer.getCode().equals(code);
    }

    @Scheduled(cron = "55 59 23 * * ?")
    public void clearTable() {
        //TODO add archiving/history feature
        offerRepo.truncateTable();
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
