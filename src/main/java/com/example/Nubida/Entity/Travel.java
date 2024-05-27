package com.example.Nubida.Entity;

import com.example.Nubida.Entity.Country;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter

public class Travel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 20)
    private String name;

    @Column(columnDefinition = "bigint default 0",nullable = false)
    private long budget_won;

    @Column(columnDefinition = "double default 0",nullable = false)
    private long remain_budget;

    private long leader;

    private String code;

    @Column(columnDefinition = "int default 1",nullable = false)
    private int num_traveler;

//    @OneToMany(mappedBy = "travel")
//    private List<TravelTraveler> travelerList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="country_id")
    private Country destination;

    private LocalDate start_date;

    private LocalDate return_date;

    @Column(name = "review", columnDefinition = "TINYINT(1)")
    private boolean review;
}
