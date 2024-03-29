package com.example.Nubida.Travel;

import com.example.Nubida.Traveler.Traveler;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TravelDTO {
    private String name;
    private long budget_won;
    private double budget_local;
    private Traveler leader;
}
