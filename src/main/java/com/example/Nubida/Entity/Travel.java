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
    private Long id;

    @Column(length = 20)
    private String name;

    @Column(columnDefinition = "bigint default 0",nullable = false)
    private Long budget_won;

    @Column(columnDefinition = "double default 0",nullable = false)
    private Long remain_budget;

    private Long leader;

    private String code;

    @Column(columnDefinition = "int default 1",nullable = false)
    private Integer num_traveler;

    @ManyToOne
    @JoinColumn(name="country_id")
    private Country destination;

    private LocalDate start_date;

    private LocalDate return_date;

    @Column(name = "review", columnDefinition = "TINYINT(1)")
    private Boolean review;
}
