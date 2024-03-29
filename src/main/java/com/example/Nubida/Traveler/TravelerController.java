package com.example.Nubida.Traveler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/traveler")
public class TravelerController {
    private final TravelerService travelerService;

    @PostMapping("/register")
    public void create( TravelerDTO travelerDTO){
        travelerService.create(travelerDTO);
    }
}
