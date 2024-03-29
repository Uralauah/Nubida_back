package com.example.Nubida.Travel;

import com.example.Nubida.Traveler.Traveler;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelRepository extends JpaRepository<Travel,Integer> {
}
