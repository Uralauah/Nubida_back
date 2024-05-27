package com.example.Nubida.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class TravelTransportation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name="travel_id")
    private Travel travel;

    @ManyToOne
    @JoinColumn(name = "transportation_id")
    private Transportation transportation;

    private long cost;

    private LocalDateTime start_date;
    private LocalDateTime finish_date;
}
