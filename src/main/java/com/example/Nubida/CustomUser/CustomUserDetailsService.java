package com.example.Nubida.CustomUser;

import com.example.Nubida.Entity.Traveler;
import com.example.Nubida.Repository.TravelerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final TravelerRepository travelerRepository;

    public CustomUserDetailsService(TravelerRepository travelerRepository){
        this.travelerRepository = travelerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Traveler> ot = travelerRepository.findByUsername(username);
        if(ot.isPresent()) {
            Traveler travelerData = ot.get();
            if (travelerData != null)
                return new CustomUserDetails(travelerData);
        }
        return null;
    }
}
