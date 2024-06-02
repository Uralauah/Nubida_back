package com.example.Nubida.Service;

import com.example.Nubida.DTO.TravelerDTO;
import com.example.Nubida.Entity.Traveler;
import com.example.Nubida.Repository.TravelerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TravelerService {
    private final TravelerRepository travelerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ReviewService reviewService;
    private final TravelTravelerService travelTravelerService;
    public int create(TravelerDTO newuser){
        Traveler traveler = new Traveler();
        Optional<Traveler> ot = this.travelerRepository.findByUsername(newuser.getUsername());
        System.out.println(newuser.getUsername());
        if(ot.isPresent()) {
            System.out.println("haha");
            return -1;
        }
        ot = this.travelerRepository.findByNickname(newuser.getNickname());
        if(ot.isPresent())
            return -2;
        traveler.setUsername(newuser.getUsername());
        traveler.setNickname(newuser.getNickname());
        traveler.setPassword(bCryptPasswordEncoder.encode(newuser.getPassword()));
        traveler.setRole("ROLE_USER");
//        traveler.setRole("ROLE_ADMIN");
        travelerRepository.save(traveler);
        return 200;
    }

    public Long getId(Principal principal){
        Optional<Traveler> ot = travelerRepository.findByUsername(principal.getName());
        if(ot.isEmpty()){
            return -1L;
        }
        Traveler traveler = ot.get();
        return traveler.getId();
    }

    public List<Traveler> getAllTraveler(Principal principal){
        Optional<Traveler> ot = travelerRepository.findByUsername(principal.getName());
        if(ot.isEmpty())
            return null;
        Traveler traveler = ot.get();
        if(!traveler.getRole().equals("ROLE_ADMIN"))
            return null;
        return travelerRepository.findAll();
    }

    public int deleteTraveler(TravelerDTO travelerDTO){
        Optional<Traveler> ot = travelerRepository.findByUsername(travelerDTO.getUsername());
        if(ot.isEmpty())
            return -1;
        Traveler traveler = ot.get();
        reviewService.deleteByAuthor(traveler);
        travelTravelerService.delete(traveler);
        travelerRepository.delete(traveler);
        return 200;
    }
}
