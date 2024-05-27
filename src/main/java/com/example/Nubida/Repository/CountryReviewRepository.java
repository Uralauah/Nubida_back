package com.example.Nubida.Repository;

import com.example.Nubida.Entity.Country;
import com.example.Nubida.Entity.CountryReview;
import com.example.Nubida.Entity.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CountryReviewRepository extends JpaRepository<CountryReview,Long> {
    List<CountryReview> findAllByCountry(Country country);

    @Query("SELECT cr FROM CountryReview cr WHERE cr.country.id = :countryId ORDER BY cr.review.id DESC")
    List<CountryReview> findRecentReview(Long countryId, Pageable pageable);

    Optional<CountryReview> findByReview(Review review);
}
