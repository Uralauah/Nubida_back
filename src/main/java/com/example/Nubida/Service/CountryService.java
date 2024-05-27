package com.example.Nubida.Service;

import com.example.Nubida.DTO.CountryDTO;
import com.example.Nubida.DTO.RecommendCountryDTO;
import com.example.Nubida.DTO.ReviewDTO;
import com.example.Nubida.Entity.Country;
import com.example.Nubida.Repository.CountryRepository;
import com.example.Nubida.Entity.CountryReview;
import com.example.Nubida.Repository.CountryReviewRepository;
import com.example.Nubida.Entity.Review;
import com.example.Nubida.Entity.Travel;
import com.example.Nubida.Repository.TravelRepository;
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
public class CountryService {
    private final CountryRepository countryRepository;
    private final CountryReviewRepository countryReviewRepository;
    private final ReviewService reviewService;
    private final TravelRepository travelRepository;
    public int create(CountryDTO countryDTO){
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

    public List<Country> allCountry(){
        return countryRepository.findAll();
    }

    public Country getCountry(int id){
        Optional<Country> oc = countryRepository.findById((long)id);
        if(oc.isEmpty()){
            return null;
        }
        return oc.get();
    }

    public int addReview(int id, ReviewDTO reviewDTO, Principal principal){
        Review review = reviewService.create(reviewDTO,principal,id);
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
}
