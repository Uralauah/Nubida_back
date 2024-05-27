package com.example.Nubida.Repository;

import com.example.Nubida.Entity.Travel;
import com.example.Nubida.Entity.Traveler;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TravelerRepository extends JpaRepository<Traveler,Long> {
//    Traveler findByUsername(String username);
//    Traveler findByNickname(String nickname);
    Optional<Traveler> findByUsername(String username);
    Optional<Traveler> findByNickname(String nickname);

    List<Travel> findAllTravelByUsername(String username);
}
