package com.dmochowski.demo.dao;

import com.dmochowski.demo.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OfferRepo extends JpaRepository<Offer, Integer> {
    List<Offer> findByCategory(String category);
    
    @Query(value = "SELECT * from offers where IS_VISIBLE = 1 order by POSTED_ON desc ", nativeQuery = true)
    List<Offer> findAllVisible();

    boolean existsOfferByContact(String contact);

    @Modifying
    @Transactional
    @Query(value = "TRUNCATE TABLE offers", nativeQuery = true)
    void truncateTable();

}
