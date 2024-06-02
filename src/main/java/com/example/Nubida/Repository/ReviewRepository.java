package com.example.Nubida.Repository;

import com.example.Nubida.Entity.Review;
import com.example.Nubida.Entity.Traveler;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByAuthor(Traveler traveler);
}
