package com.example.project.reservation.controller;

import com.example.project.reservation.api.ReservationRequest;
import com.example.project.reservation.domain.Reservation;
import com.example.project.reservation.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/create/{userId}/{serviceId}")
    public ResponseEntity<Reservation> createReservation(
            @PathVariable Long userId,
            @PathVariable Long serviceId,
            @RequestBody ReservationRequest reservationRequest) {
        try {
            Reservation newReservation = reservationService.createReservation(userId, serviceId, reservationRequest);
            return ResponseEntity.ok(newReservation);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{reservationId}/update/{userId}")
    public ResponseEntity<Reservation> updateReservation(
            @PathVariable Long reservationId,
            @PathVariable Long userId,
            @RequestBody ReservationRequest reservationRequest) {
        try {
            Reservation updatedReservation = reservationService.updateReservation(reservationId, userId, reservationRequest);
            return ResponseEntity.ok(updatedReservation);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{reservationId}/delete/{userId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long reservationId, @PathVariable Long userId) {
        try {
            reservationService.deleteReservation(reservationId, userId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

