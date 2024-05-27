package com.example.Nubida.Repository;

import com.example.Nubida.Entity.Travel;
import com.example.Nubida.Entity.TravelSupply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TravelSupplyRepository extends JpaRepository<TravelSupply,Long> {
    List<TravelSupply> findAllByTravel(Travel travel);
}
