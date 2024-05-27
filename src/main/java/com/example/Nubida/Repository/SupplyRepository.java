package com.example.Nubida.Repository;

import com.example.Nubida.Entity.Supply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SupplyRepository extends JpaRepository<Supply,Long> {
    Optional<Supply> findByName(String name);
}
