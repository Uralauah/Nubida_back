package com.example.Nubida.Entity;

import com.example.Nubida.Entity.Supply;
import com.example.Nubida.Entity.Travel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class TravelSupply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="travel_id")
    private Travel travel;

    @OneToOne
    @JoinColumn(name="supply_id")
    private Supply supply;
}
