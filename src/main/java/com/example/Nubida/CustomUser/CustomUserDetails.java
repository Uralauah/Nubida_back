package com.example.Nubida.CustomUser;

import com.example.Nubida.Traveler.Traveler;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {
    private final Traveler traveler;

    public CustomUserDetails(Traveler traveler){
        this.traveler = traveler;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { //Role 리턴
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return traveler.getRole();
            }
        });
        return collection;
    }

    @Override
    public String getPassword() {
        return traveler.getPassword();
    }

    @Override
    public String getUsername() {
        return traveler.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
