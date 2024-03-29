package com.example.Nubida.Traveler;

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

    private String password;

    @Column(unique=true)
    private String username;

    private String role;
}
