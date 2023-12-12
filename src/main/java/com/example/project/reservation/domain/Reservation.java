package com.example.project.reservation.domain;

import com.example.project.customservice.domain.CustomService;
import com.example.project.user.domain.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "yy-MM-dd HH:mm")
    private LocalDateTime startDateTime;

    @JsonFormat(pattern = "yy-MM-dd HH:mm")
    private LocalDateTime endDateTime;
    @ManyToOne
    @JoinColumn(name = "users")
    private User user;

    @ManyToOne
    @JoinColumn(name = "services")
    private CustomService service;
}