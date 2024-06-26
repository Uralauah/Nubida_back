package com.example.Nubida.Repository;

import com.example.Nubida.Entity.Country;
import com.example.Nubida.Entity.Travel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TravelRepository extends JpaRepository<Travel,Long> {
    List<Travel> findAllByLeader(long leader_id);
    Optional<Travel> findByCode(String code);
    Optional<Travel> findByName(String name);
    List<Travel> findAllByDestination(Country country);
}
