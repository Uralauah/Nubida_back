package com.example.Nubida.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDTO {
    private Long id;
    private String subject;
    private String content;
    private double rate;
}
