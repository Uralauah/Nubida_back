package com.example.Nubida.Service;

import com.example.Nubida.DTO.*;
import com.example.Nubida.Entity.*;
import com.example.Nubida.Repository.*;
import com.example.Nubida.Entity.TravelPlan;
import com.example.Nubida.Repository.TravelPlanRepository;
import com.example.Nubida.Entity.TravelSupply;
import com.example.Nubida.Repository.TravelSupplyRepository;
import com.example.Nubida.Entity.TravelTraveler;
import com.example.Nubida.Repository.TravelTravelerRepository;
import com.example.Nubida.Entity.Traveler;
import com.example.Nubida.Repository.TravelerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.RandomStringUtils;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TravelService {
    private final TravelRepository travelRepository;
    private final TravelerRepository travelerRepository;
    private final CountryRepository countryRepository;
    private final TravelTravelerRepository travelTravelerRepository;
    private final SupplyService supplyService;
    private final TravelSupplyRepository travelSupplyRepository;
    private final SupplyRepository supplyRepository;
    private final TransportationRepository transportationRepository;
    private final TravelTransportationRepository travelTransportationRepository;
    private final TravelPlanRepository travelPlanRepository;
    private final ReviewRepository reviewRepository;

    public void createTravel(TravelDTO travelDTO, Principal principal) {
        Travel travel = new Travel();

        Optional<Traveler> ot = travelerRepository.findByUsername(principal.getName());
        Traveler traveler = ot.get();
        travel.setName(travelDTO.getName());
        travel.setLeader(traveler.getId());
        String code = RandomStringUtils.randomAlphanumeric(6);
        Optional<Travel> optionalTravel = travelRepository.findByCode(code);
        while (optionalTravel.isPresent()) {
            code = RandomStringUtils.randomAlphanumeric(6);
            optionalTravel = travelRepository.findByCode(code);
        }
        travel.setCode(code);
        travel.setNum_traveler(1);
        travel.setStart_date(travelDTO.getStartDate());
        travel.setReturn_date(travelDTO.getReturnDate());
        travel.setBudget_won(travelDTO.getBudget_won());
        travel.setRemain_budget(travelDTO.getBudget_won());
        travel.setIsReview(false);
        Optional<Country> oc = countryRepository.findByName(travelDTO.getCountry());
        if (oc.isPresent()) {
            Country country = oc.get();
            travel.setDestination(country);
        }
        travelRepository.save(travel);
        TravelTraveler travelTraveler = new TravelTraveler();
        travelTraveler.setTraveler(traveler);
        travelTraveler.setTravel(travel);
        travelTravelerRepository.save(travelTraveler);
    }

    public int deleteTravel(long id){
        Optional<Travel> ot = travelRepository.findById(id);
        if(ot.isEmpty())
            return -1;
        Travel travel = ot.get();
        List<TravelSupply> travelSupplies =  travelSupplyRepository.findAllByTravel(travel);
        travelSupplyRepository.deleteAll(travelSupplies);
        List<TravelTraveler> travelTravelerList = travelTravelerRepository.findAllByTravel(travel);
        travelTravelerRepository.deleteAll(travelTravelerList);
        List<TravelPlan> travelPlans = travelPlanRepository.findAllByTravel(travel);
        travelPlanRepository.deleteAll(travelPlans);
        List<TravelTransportation> travelTransportations = travelTransportationRepository.findAllByTravel(travel);
        travelTransportationRepository.deleteAll(travelTransportations);
        travelRepository.delete(travel);
        return 200;
    }

    public int joinTravel(String code, Principal principal) {
        Optional<Travel> ot = travelRepository.findByCode(code);
        if (ot.isEmpty()) {
            return -1;
        }
        Travel travel = ot.get();
        Optional<Traveler> optionalTraveler = travelerRepository.findByUsername(principal.getName());
        if(optionalTraveler.isEmpty())
            return -2;
        Traveler traveler = optionalTraveler.get();

        List<TravelTraveler> travelTravelerList = travelTravelerRepository.findAllByTravel(travel);
        for (TravelTraveler tt : travelTravelerList) {
            if (Objects.equals(tt.getTraveler().getId(), traveler.getId()))
                return -3;
        }

        travel.setNum_traveler(travel.getNum_traveler() + 1);
        travelRepository.save(travel);
        TravelTraveler travelTraveler = new TravelTraveler();
        travelTraveler.setTraveler(traveler);
        travelTraveler.setTravel(travel);
        travelTravelerRepository.save(travelTraveler);
        return 200;
    }

    public List<Travel> getMyTravel(Principal principal) {
        Optional<Traveler> ot = travelerRepository.findByUsername(principal.getName());
        if(ot.isEmpty())
            return null;
        Traveler traveler = ot.get();

        List<TravelTraveler> travelTravelerList = travelTravelerRepository.findAllByTravelerId(traveler.getId());
        List<Travel> travels = new ArrayList<>();
        for (TravelTraveler travelTraveler : travelTravelerList) {
            travels.add(travelTraveler.getTravel());
        }

        return travels;
    }

    public List<Travel> getAllTravel(Principal principal) {
        Optional<Traveler> ot = travelerRepository.findByUsername(principal.getName());
        if(ot.isEmpty())
            return null;
        Traveler Admin = ot.get();
        if (Admin.getRole().equals("ROLE_ADMIN")) {
            return travelRepository.findAll();
        } else {
            return null;
        }

    }

    public Travel getTravelInfo( long id) {
        Optional<Travel> ot = travelRepository.findById(id);
        return ot.orElse(null);
    }


    public List<Traveler> getTravelTraveler(long id) {
        Optional<Travel> ot = travelRepository.findById(id);
        if (ot.isEmpty())
            return null;
        Travel travel = ot.get();
        List<TravelTraveler> travelTravelerList = travelTravelerRepository.findAllByTravelId(travel.getId());
        List<Traveler> travelers = new ArrayList<>();
        for (TravelTraveler travelTraveler : travelTravelerList) {
            travelers.add(travelTraveler.getTraveler());
        }
        return travelers;
    }

    public int deleteTravelTraveler(Principal principal, long travel_id, long travel_traveler_id) {
        Optional<Travel> ot = travelRepository.findById(travel_id);
        if (ot.isEmpty())
            return -1;
        Travel travel = ot.get();
        Optional<Traveler> otr = travelerRepository.findByUsername(principal.getName());
        if (otr.isEmpty())
            return -2;
        Traveler leader = otr.get();
        if (!Objects.equals(travel.getLeader(), leader.getId())) {
            return -3;
        }
        List<TravelTraveler> dotr = travelTravelerRepository.findAllByTravelerId(travel_traveler_id);
        if (dotr.isEmpty())
            return -4;
        TravelTraveler travelTraveler;
        for (TravelTraveler tt : dotr) {
            if (Objects.equals(tt.getTravel().getId(), travel.getId())) {
                travelTraveler = tt;
                travelTravelerRepository.deleteById(travelTraveler.getId());
                break;
            }
        }
        travel.setNum_traveler(travel.getNum_traveler() - 1);
        travelRepository.save(travel);

        return 200;
    }

    public int createSupply(long travel_id, SupplyDTO supplyDTO) {
        Optional<Travel> ot = travelRepository.findById(travel_id);
        if (ot.isEmpty())
            return -1;
        Travel travel = ot.get();
        Supply supply = supplyService.createSupply(supplyDTO);
        TravelSupply travelSupply = new TravelSupply();
        travelSupply.setTravel(travel);
        travelSupply.setSupply(supply);
        travelSupplyRepository.save(travelSupply);
        return 200;
    }

    public List<Supply> getAllTravelSupply(long travel_id){
        Optional<Travel> ot = travelRepository.findById(travel_id);
        if(ot.isEmpty())
            return null;
        Travel travel = ot.get();
        List<TravelSupply> travelSupplies = travelSupplyRepository.findAllByTravel(travel);
        return supplyService.getAllSupply(travelSupplies);
    }

    public int supplyDelete(long travel_id, SupplyDTO supplyDTO){
        Optional<Travel> ot = travelRepository.findById(travel_id);
        if(ot.isEmpty())
            return -1;
        Travel travel = ot.get();
        List<TravelSupply> travelSupplies = travelSupplyRepository.findAllByTravel(travel);
        for(TravelSupply travelSupply : travelSupplies){
            Supply supply = travelSupply.getSupply();
            if(supply.getName().equals(supplyDTO.getName())){
                travelSupplyRepository.delete(travelSupply);
                supplyRepository.delete(supply);
                return 200;
            }
        }
        return -2;
    }

    public ReviewDTO viewTravelReview(long travel_id,Principal principal){
        Optional<Traveler> ott = travelerRepository.findByUsername(principal.getName());
        if(ott.isEmpty())
            return null;
        Optional<Travel> ot = travelRepository.findById(travel_id);
        if(ot.isEmpty())
            return null;
        Travel travel = ot.get();
        Traveler traveler = ott.get();
        List<Review> reviews = reviewRepository.findAllByAuthor(traveler);
        for(Review review : reviews){
            if(Objects.equals(review.getTravel().getId(), travel.getId())){
                ReviewDTO reviewDTO = new ReviewDTO();
                reviewDTO.setContent(review.getContent());
                reviewDTO.setSubject(review.getSubject());
                reviewDTO.setRate(review.getRate());
                return reviewDTO;
            }
        }
        return null;
    }

    public int addBudget(long travel_id, BudgetDTO budget){
        Optional<Travel> ot = travelRepository.findById(travel_id);
        if(ot.isEmpty())
            return -1;
        Travel travel = ot.get();
        System.out.println(budget.getBudget());
        travel.setBudget_won(travel.getBudget_won()+budget.getBudget());
        travel.setRemain_budget(travel.getRemain_budget()+budget.getBudget());
        travelRepository.save(travel);
        return 200;
    }

    public int addTransportation(long travel_id, TransportationDTO transportationDTO){
        Optional<Travel> ot = travelRepository.findById(travel_id);
        if(ot.isEmpty())
            return -1;
        Travel travel = ot.get();
        Optional<Transportation> otp = transportationRepository.findById(transportationDTO.getTransportation_id());
        if(otp.isEmpty())
            return -2;
        Transportation transportation = otp.get();
        TravelTransportation travelTransportation = new TravelTransportation();
        travelTransportation.setTravel(travel);
        travelTransportation.setCost(transportationDTO.getCost());
        travelTransportation.setTransportation(transportation);
        travelTransportation.setStart_date(transportationDTO.getStart_date());
        travelTransportation.setFinish_date(transportationDTO.getFinish_date());
        travelTransportationRepository.save(travelTransportation);
        return 200;
    }

    public int quitTravel(Principal principal, long travel_id){
        Optional<Travel> ot = travelRepository.findById(travel_id);
        if(ot.isEmpty())
            return -1;
        Travel travel = ot.get();
        Optional<Traveler> ott = travelerRepository.findByUsername(principal.getName());
        if(ott.isEmpty())
            return -2;
        Traveler traveler = ott.get();
        travel.setNum_traveler(travel.getNum_traveler()-1);
        List<TravelTraveler> travelTravelers = travelTravelerRepository.findAllByTraveler(traveler);

        for(TravelTraveler travelTraveler : travelTravelers){
            if(Objects.equals(travelTraveler.getTravel().getId(), travel.getId())){
                travelTravelerRepository.delete(travelTraveler);
                return 200;
            }
        }
        return -3;
    }

    public int deleteTransport(long travelId, TransportationDTO transportationDTO) {
        Optional<Travel> ot = travelRepository.findById(travelId);
        if (ot.isEmpty()) {
            return -1;
        }

        Travel travel = ot.get();
        List<TravelTransportation> travelTransportations = travelTransportationRepository.findAllByTravel(travel);

        for (TravelTransportation travelTransportation : travelTransportations) {
            if (isMatchingTransportation(travelTransportation, transportationDTO)) {
                travelTransportationRepository.delete(travelTransportation);
                return 200;
            }
        }
        return -2;
    }

    private boolean isMatchingTransportation(TravelTransportation travelTransportation, TransportationDTO transportationDTO) {
        return travelTransportation.getTransportation().getId().equals(transportationDTO.getTransportation_id()) &&
                travelTransportation.getStart_date().equals(transportationDTO.getStart_date()) &&
                travelTransportation.getFinish_date().equals(transportationDTO.getFinish_date());
    }
}
