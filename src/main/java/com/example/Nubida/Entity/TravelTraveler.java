package com.example.Nubida.Entity;

import com.example.Nubida.Entity.Travel;
import com.example.Nubida.Entity.Traveler;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class TravelTraveler {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="traveler_id")
//    @JsonIgnore
    private Traveler traveler;

    @ManyToOne
    @JoinColumn(name="travel_id")
    private Travel travel;
}
