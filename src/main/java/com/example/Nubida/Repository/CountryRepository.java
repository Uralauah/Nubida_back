package com.example.Nubida.Repository;

import com.example.Nubida.Entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country,Long> {
    Optional<Country> findByName(String name);

    @Query(value = "SELECT c FROM Country c ORDER BY c.rate DESC")
    List<Country> findRecommendCountry(Pageable pageable);
}
