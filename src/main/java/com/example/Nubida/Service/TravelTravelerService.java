package com.example.Nubida.Service;

import com.example.Nubida.Entity.Travel;
import com.example.Nubida.Entity.TravelTraveler;
import com.example.Nubida.Entity.Traveler;
import com.example.Nubida.Repository.TravelTravelerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TravelTravelerService {
    private final TravelTravelerRepository travelTravelerRepository;
    public void join(Travel travel, Traveler traveler){
        TravelTraveler tt = new TravelTraveler();
        tt.setTraveler(traveler);
        tt.setTravel(travel);
        travelTravelerRepository.save(tt);
    }
}
