package com.example.Nubida.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CountryReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="country_id")
    private Country country;

    @OneToOne
    @JoinColumn(name="review_id")
    private Review review;
}
