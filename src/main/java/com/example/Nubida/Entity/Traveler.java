package com.example.Nubida.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class Traveler {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String nickname;

    @JsonIgnore
    private String password;

    @Column(unique=true)
    private String username;


    private String role;

//    @OneToMany(mappedBy = "traveler")
//    private List<TravelTraveler> travelList = new ArrayList<>();
}
