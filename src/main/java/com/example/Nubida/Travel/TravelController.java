package com.example.Nubida.Travel;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/travel")
public class TravelController {
    private final TravelService travelService;

    @PostMapping("/create")
    public void create(@RequestBody TravelDTO travelDTO){
        travelService.create(travelDTO);
    }
}
