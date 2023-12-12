package com.example.project.reservation.controller;

import com.example.project.reservation.api.ReservationRequest;
import com.example.project.reservation.domain.Reservation;
import com.example.project.reservation.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/user/{userId}")
    public List<Reservation> getAllReservationsByUserId(@PathVariable Long userId) {
        return reservationService.getAllReservationsByUserId(userId);
    }

    @GetMapping("/service/{serviceId}")
    public List<Reservation> getAllReservationsByServiceId(@PathVariable Long serviceId) {
        return reservationService.getAllReservationsByServiceId(serviceId);
    }

    @PostMapping("/{serviceId}")
    public ResponseEntity<String> createReservation(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION) String authHeader,
            @PathVariable Long serviceId,
            @RequestBody ReservationRequest reservationRequest) {
        try {
            Reservation newReservation = reservationService.createReservation(authHeader, serviceId, reservationRequest);
            return ResponseEntity.ok("Reservation created with ID: " + newReservation.getId());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{reservationId}")
    public ResponseEntity<String> updateReservation(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION) String authHeader,
            @PathVariable Long reservationId,
            @RequestBody ReservationRequest reservationRequest) {
        try {
            reservationService.updateReservation(reservationId, authHeader, reservationRequest);
            return ResponseEntity.ok("Reservation updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<String> deleteReservation(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION) String authHeader,
            @PathVariable Long reservationId) {
        try {
            reservationService.deleteReservation(reservationId, authHeader);
            return ResponseEntity.ok("Reservation deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}


