package com.example.Nubida.Traveler;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelerRepository extends JpaRepository<Traveler,Integer> {
    Traveler findByUsername(String username);
}
