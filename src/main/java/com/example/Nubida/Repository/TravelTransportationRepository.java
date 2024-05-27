package com.example.Nubida.Repository;

import com.example.Nubida.Entity.TravelTransportation;
import com.example.Nubida.Entity.Travel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TravelTransportationRepository extends JpaRepository<TravelTransportation,Long> {
    List<TravelTransportation> findAllByTravel(Travel travel);
}
