package com.example.Nubida.Entity;

import com.example.Nubida.Entity.Plan;
import com.example.Nubida.Entity.Travel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class TravelPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="travel_id")
    private Travel travel;

    @OneToOne
    @JoinColumn(name="plan_id")
    private Plan plan;
}
