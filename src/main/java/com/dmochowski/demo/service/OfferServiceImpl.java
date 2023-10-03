package com.dmochowski.demo.service;

import com.dmochowski.demo.dao.OfferRepo;
import com.dmochowski.demo.entity.Offer;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class OfferServiceImpl implements OfferService{
    private final OfferRepo offerRepo;
    private static final int codeLen = 8;

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

    //TODO add delete if proper code
    //TODO add update if proper code

    @Override
    public Offer add(Offer offer) {
        offer.setId(0);
        offer.setPostedOn(new Timestamp(System.currentTimeMillis()));
        offer.setCode(SpecialCodeGenerator.codeGenerator());
        if(offer.getCode().length()!=codeLen){
            throw new RuntimeException("Code length error");
        }
        return offerRepo.save(offer);
    }
    @Scheduled(cron = "55 59 23 * * ?")
    public void clearTable(){
        //TODO add archiving/history feature
        offerRepo.deleteAll();
    }


    private static class SpecialCodeGenerator{
        static char[] chars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'q', 'w', 'e', 'r', 't', 'z', 'u', 'i', 'o', 'p', 'a', 's',
                'd', 'f', 'g', 'h', 'j', 'k', 'l', 'y', 'x', 'c', 'v', 'b', 'n', 'm', 'Q', 'W', 'E', 'R', 'T', 'Z', 'U', 'I', 'O', 'P', 'A',
                'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'Y', 'X', 'C', 'V', 'B', 'N', 'M' };

        private static String codeGenerator(){
            StringBuilder stringBuilder = new StringBuilder();
            for(int i = 0; i < codeLen; i++){
                stringBuilder.append(chars[new Random().nextInt(chars.length)]);
            }
            return stringBuilder.toString();

        }
    }
}
