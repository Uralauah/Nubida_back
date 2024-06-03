package com.example.Nubida.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String name;

    private String money_term;

    @Column(columnDefinition = "double default 0",nullable = false)
    private Double rate;

    private Long review_cnt;
}
