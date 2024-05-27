package com.example.Nubida.Repository;

import com.example.Nubida.Entity.Plan;
import com.example.Nubida.Entity.Travel;
import com.example.Nubida.Entity.TravelPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TravelPlanRepository extends JpaRepository<TravelPlan,Long> {
    List<TravelPlan> findAllByTravel(Travel travel);

    Optional<TravelPlan> findByPlan(Plan plan);
}
