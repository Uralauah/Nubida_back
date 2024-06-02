package com.example.Nubida.Service;

import com.example.Nubida.DTO.ReviewDTO;
import com.example.Nubida.Entity.*;
import com.example.Nubida.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final TravelerRepository travelerRepository;
    private final TravelRepository travelRepository;
    private final CountryReviewRepository countryReviewRepository;
    private final CountryRepository    countryRepository;

    public Review create(ReviewDTO reviewDTO, Principal principal, long travel_id){
        Optional<Traveler> ot = travelerRepository.findByUsername(principal.getName());
        if(ot.isEmpty())
            return null;
        Optional<Travel> ott = travelRepository.findById(travel_id);
        if(ott.isEmpty())
            return null;
        Travel travel = ott.get();
        Traveler traveler = ot.get();
        Review review = new Review();
        review.setSubject(reviewDTO.getSubject());
        review.setContent(reviewDTO.getContent());
        review.setRate(reviewDTO.getRate());
        review.setAuthor(traveler);
        review.setTravel(travel);
        reviewRepository.save(review);
        return review;
    }

    public int addReview(long id, ReviewDTO reviewDTO, Principal principal){
        Review review = create(reviewDTO,principal,id);
        Optional<Travel>ot = travelRepository.findById(id);
        if(ot.isEmpty())
            return -2;
        Travel travel = ot.get();
        long country_id = travel.getDestination().getId();
        Optional<Country> oc = countryRepository.findById(country_id);
        if(oc.isEmpty())
            return -1;
        Country country = oc.get();
        double rate = country.getRate()*country.getReview_cnt();
        rate = (rate + review.getRate())/(country.getReview_cnt()+1);
        country.setReview_cnt(country.getReview_cnt()+1);
        country.setRate(rate);

        CountryReview countryReview = new CountryReview();
        countryReview.setCountry(country);
        countryReview.setReview(review);

        travel.setReview(true);
        travelRepository.save(travel);
        countryReviewRepository.save(countryReview);
        return 200;
    }

    public List<ReviewDTO> getRecentReviews(long country_id){
        Pageable pageable = PageRequest.of(0,5);
        List<CountryReview> countryReviews = countryReviewRepository.findRecentReview(country_id,pageable);
        List<ReviewDTO> reviewDTOS = new ArrayList<>();
        for(CountryReview countryReview : countryReviews){
            Review review = countryReview.getReview();
            ReviewDTO reviewDTO = new ReviewDTO();
            reviewDTO.setContent(review.getContent());
            reviewDTO.setSubject(review.getSubject());
            reviewDTO.setRate(review.getRate());
            reviewDTOS.add(reviewDTO);
        }
        return reviewDTOS;
    }

    public List<ReviewDTO> getAll(Principal principal){
        Optional<Traveler> ot = travelerRepository.findByUsername(principal.getName());
        if(ot.isEmpty())
            return null;
        Traveler traveler = ot.get();
        List<ReviewDTO> reviewDTOS = new ArrayList<>();
        List<Review> reviews = reviewRepository.findAllByAuthor(traveler);
        for(Review review : reviews){
            ReviewDTO reviewDTO = new ReviewDTO();
            reviewDTO.setRate(review.getRate());
            reviewDTO.setId(review.getId());
            reviewDTO.setSubject(review.getSubject());
            reviewDTO.setContent(review.getContent());
            reviewDTOS.add(reviewDTO);
        }
        return reviewDTOS;
    }

    public int delete(Long review_id){
        Optional<Review> or = reviewRepository.findById(review_id);
        if(or.isEmpty())
            return -1;
        Review review = or.get();
        Travel travel = review.getTravel();
        travel.setReview(false);
        Country country = travel.getDestination();
        country.setRate(((country.getRate()*country.getReview_cnt())-review.getRate())/(country.getReview_cnt()-1));
        country.setReview_cnt(country.getReview_cnt() - 1);
        Optional<CountryReview> ocr = countryReviewRepository.findByReview(review);
        if(ocr.isEmpty())
            return -1;
        CountryReview countryReview = ocr.get();
        countryReviewRepository.delete(countryReview);
        reviewRepository.delete(review);
        return 200;
    }

    public int change(ReviewDTO reviewDTO){
        Optional<Review> or = reviewRepository.findById(reviewDTO.getId());
        if(or.isEmpty())
            return -1;
        Review review = or.get();
        Optional<CountryReview> ocr = countryReviewRepository.findByReview(review);
        if(ocr.isEmpty())
            return -2;
        CountryReview countryReview = ocr.get();
        Country country = countryReview.getCountry();
        country.setRate(((country.getRate()*country.getReview_cnt())-review.getRate()+ reviewDTO.getRate())/country.getReview_cnt());
        review.setSubject(reviewDTO.getSubject());
        review.setContent(reviewDTO.getContent());
        review.setRate(reviewDTO.getRate());
        reviewRepository.save(review);
        countryRepository.save(country);
        return 200;
    }



    public void deleteByAuthor(Traveler traveler){
        List<Review> reviews = reviewRepository.findAllByAuthor(traveler);
        for(Review review : reviews){
            delete(review.getId());
        }
    }
}
