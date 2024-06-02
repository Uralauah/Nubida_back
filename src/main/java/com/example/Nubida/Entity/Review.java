package com.example.Nubida.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subject;

    private String content;

    private Double rate;

    @ManyToOne
    private Traveler author;

    @ManyToOne
    private Travel travel;
}
