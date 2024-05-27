package com.example.Nubida.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TransportationDTO {
    private long transportation_id;
    private long cost;
    private LocalDateTime start_date;
    private LocalDateTime finish_date;
}
