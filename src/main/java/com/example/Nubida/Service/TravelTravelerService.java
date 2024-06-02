package com.example.Nubida.Service;

import com.example.Nubida.Entity.Travel;
import com.example.Nubida.Entity.TravelTraveler;
import com.example.Nubida.Entity.Traveler;
import com.example.Nubida.Repository.TravelRepository;
import com.example.Nubida.Repository.TravelTravelerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TravelTravelerService {
    private final TravelTravelerRepository travelTravelerRepository;
    private final TravelService travelService;
    private final TravelRepository travelRepository;

    public void delete(Traveler traveler){
        List<TravelTraveler> travelTravelers = travelTravelerRepository.findAllByTraveler(traveler);
        for(TravelTraveler travelTraveler : travelTravelers){
            Travel travel = travelTraveler.getTravel();
            if(Objects.equals(travel.getLeader(), traveler.getId())){
                travelService.deleteTravel(travel.getId());
            }
            else{
                travel.setNum_traveler(travel.getNum_traveler()-1);
                travelRepository.save(travel);
            }
            travelTravelerRepository.delete(travelTraveler);
        }
    }
}
