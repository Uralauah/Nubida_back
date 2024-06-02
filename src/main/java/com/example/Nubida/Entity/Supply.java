package com.example.Nubida.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Supply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer count;

    @Column(name = "`check`", columnDefinition = "TINYINT(1)")
    private Boolean check;
}
