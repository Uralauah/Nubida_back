package com.example.Nubida.Repository;

import com.example.Nubida.Entity.Transportation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransportationRepository extends JpaRepository<Transportation,Long> {
    Optional<Transportation> findById(long id);
}
