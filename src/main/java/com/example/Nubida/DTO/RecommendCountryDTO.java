package com.example.Nubida.DTO;

import com.example.Nubida.Entity.Country;
import com.example.Nubida.Entity.Review;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RecommendCountryDTO {
    private String name;
    private Double rate;
    private Long review_cnt;
    private List<ReviewDTO> reviewDTOS;
}
