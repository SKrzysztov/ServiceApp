package com.example.project.reservation.api;

import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class ReservationRequest {
    private LocalDateTime reservationDateTime;
}
