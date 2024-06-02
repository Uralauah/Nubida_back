package com.example.Nubida.Repository;

import com.example.Nubida.Entity.Travel;
import com.example.Nubida.Entity.TravelTraveler;
import com.example.Nubida.Entity.Traveler;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TravelTravelerRepository extends JpaRepository<TravelTraveler,Long> {
    List<TravelTraveler> findAllByTravel(Travel travel);
    List<TravelTraveler> findAllByTravelerId(Long id);
    List<TravelTraveler> findAllByTravelId(Long id);
    List<TravelTraveler> findAllByTraveler(Traveler traveler);
}
