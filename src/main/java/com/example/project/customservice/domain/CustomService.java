package com.example.project.customservice.domain;

import com.example.project.tools.DurationToMinutesConverter;
import com.example.project.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serviceName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Convert(converter = DurationToMinutesConverter.class)
    Duration duration;
}