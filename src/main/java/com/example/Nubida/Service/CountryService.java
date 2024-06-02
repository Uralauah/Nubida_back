package com.example.Nubida.Service;

import com.example.Nubida.DTO.CountryDTO;
import com.example.Nubida.DTO.RecommendCountryDTO;
import com.example.Nubida.DTO.ReviewDTO;
import com.example.Nubida.Entity.*;
import com.example.Nubida.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CountryService {
    private final CountryRepository countryRepository;
    private final CountryReviewRepository countryReviewRepository;
    private final ReviewService reviewService;
    @Lazy
    private final TravelRepository travelRepository;
    private final ReviewRepository reviewRepository;
    private final TravelService travelService;
    public int createCountry(CountryDTO countryDTO){
        Optional<Country> optionalCountry = countryRepository.findByName(countryDTO.getName());
        if(optionalCountry.isPresent())
            return -1;
        Country country = new Country();
        country.setName(countryDTO.getName());
        country.setMoney_term(countryDTO.getMoney_term());
        country.setReview_cnt(0);
        countryRepository.save(country);
        return 200;
    }

    public List<Country> getAllCountry(){
        return countryRepository.findAll();
    }

    public Country getCountry(int id){
        Optional<Country> oc = countryRepository.findById((long)id);
        if(oc.isEmpty()){
            return null;
        }
        return oc.get();
    }



    public List<RecommendCountryDTO> getRecommendCountry(){
        Pageable pageable = PageRequest.of(0, 5);
        List<Country> countries =  countryRepository.findRecommendCountry(pageable);
        List<RecommendCountryDTO> recommendCountryDTOS = new ArrayList<>();
        for(Country country : countries){
            List<ReviewDTO> reviewDTOS = reviewService.getRecentReviews(country.getId());
            RecommendCountryDTO recommendCountryDTO = new RecommendCountryDTO();
            recommendCountryDTO.setName(country.getName());
            recommendCountryDTO.setReviewDTOS(reviewDTOS);
            recommendCountryDTO.setRate(country.getRate());
            recommendCountryDTO.setReview_cnt(country.getReview_cnt());
            recommendCountryDTOS.add(recommendCountryDTO);
        }
        return recommendCountryDTOS;
    }

    public int deleteCountry(CountryDTO countryDTO){
        Optional<Country> oc = countryRepository.findByName(countryDTO.getName());
        if(oc.isEmpty())
            return -1;
        Country country = oc.get();
        List<Travel> travels = travelRepository.findAllByDestination(country);
        for(Travel travel : travels){
            travelService.deleteTravel(travel.getId());
        }
        List<CountryReview> countryReviews = countryReviewRepository.findAllByCountry(country);
        for(CountryReview countryReview : countryReviews){
            Review review = countryReview.getReview();
            countryReviewRepository.delete(countryReview);
            reviewRepository.delete(review);
        }
        countryRepository.delete(country);
        return 200;
    }
}
