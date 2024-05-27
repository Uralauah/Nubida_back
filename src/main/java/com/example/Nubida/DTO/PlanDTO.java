package com.example.Nubida.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class PlanDTO {
    private Long id;
    private String plan_name;
    private Long plan_cost;
    private LocalDateTime start_date;
    private LocalDateTime finish_date;
}
