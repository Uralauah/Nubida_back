package com.example.Nubida.DTO;

import com.example.Nubida.Entity.Traveler;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TravelDTO {
    private String name;
    private long budget_won;
    private Traveler leader;
    private LocalDate startDate;
    private LocalDate returnDate;
    private String country;
    private int id;
}
