package com.example.Nubida.CustomUser;

import com.example.Nubida.Traveler.Traveler;
import com.example.Nubida.Traveler.TravelerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final TravelerRepository travelerRepository;

    public CustomUserDetailsService(TravelerRepository travelerRepository){
        this.travelerRepository = travelerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Traveler travelerData = travelerRepository.findByUsername(username);
        if(travelerData!=null)
            return new CustomUserDetails(travelerData);
        return null;
    }
}
