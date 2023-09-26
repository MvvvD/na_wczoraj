package com.dmochowski.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="offers")
@NoArgsConstructor
@Setter
@Getter
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private int id;

    @Column(name = "TITLE")
    private String title;
    @Column(name = "DETAILS")
    private String details;
    @Column(name = "CATEGORY")
    private String category;
    @Column(name = "POSTED_ON")
    private java.sql.Timestamp postedOn;

}