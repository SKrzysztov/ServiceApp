package com.example.project.user.domain;

import com.example.project.customservice.domain.CustomService;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name= "users")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<CustomService> services;
}
