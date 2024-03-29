package com.example.Nubida.Travel;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Service
public class TravelService {
    private final TravelRepository travelRepository;

    public Travel create(TravelDTO travelDTO){
        Travel travel = new Travel();
        travel.setName(travelDTO.getName());
        travelRepository.save(travel);
        return travel;
    }
}
