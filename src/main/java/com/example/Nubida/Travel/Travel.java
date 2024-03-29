package com.example.Nubida.Travel;

import com.example.Nubida.Traveler.Traveler;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
    private double budget_local;

    @ManyToOne
    private Traveler leader;
}
