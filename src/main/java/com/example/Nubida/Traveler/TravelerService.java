package com.example.Nubida.Traveler;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TravelerService {
    private final TravelerRepository travelerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public void create(TravelerDTO newuser){
        Traveler traveler = new Traveler();
        traveler.setUsername(newuser.getUsername());
        traveler.setNickname(newuser.getNickname());
        traveler.setPassword(bCryptPasswordEncoder.encode(newuser.getPassword()));
        traveler.setRole("ROLE_USER");
        travelerRepository.save(traveler);
    }
}
