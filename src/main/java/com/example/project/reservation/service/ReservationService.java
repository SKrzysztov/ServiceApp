package com.example.project.reservation.service;

import com.example.project.customservice.domain.CustomService;
import com.example.project.customservice.repository.CustomServiceRepository;
import com.example.project.reservation.api.ReservationRequest;
import com.example.project.reservation.domain.Reservation;
import com.example.project.reservation.repository.ReservationRepository;
import com.example.project.tools.ReservationTimeCalculator;
import com.example.project.user.domain.User;
import com.example.project.user.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;


    @Autowired
    private CustomServiceRepository customServiceRepository;

    @Autowired
    private AuthenticationService authenticationService;

    public List<Reservation> getAllReservationsByUserId(Long userId) {
        return reservationRepository.findByUserId(userId);
    }

    public List<Reservation> getAllReservationsByServiceId(Long serviceId) {
        return reservationRepository.findByServiceId(serviceId);
    }

    public Reservation createReservation(String authHeader, Long serviceId, ReservationRequest reservationRequest) {
        Optional<User> authenticatedUser = authenticationService.authenticateBasicAuth(authHeader);
        if (authenticatedUser.isPresent()) {
            CustomService service = customServiceRepository.findById(serviceId)
                    .orElseThrow(() -> new RuntimeException("Usługa o podanym ID nie istnieje"));

            if (service.getUser().equals(authenticatedUser.get())) {
                throw new RuntimeException("Właściciel usługi nie może dokonać rezerwacji własnej usługi");
            }

            Reservation reservation = new Reservation();
            reservation.setStartDateTime(reservationRequest.getStartDateTime());
            reservation.setUser(authenticatedUser.get());
            reservation.setService(service);

            reservation.setEndDateTime(ReservationTimeCalculator.calculateEndDateTime(reservation.getStartDateTime(), service));

            return reservationRepository.save(reservation);
        } else {
            throw new RuntimeException("Uwierzytelnianie nieudane");
        }
    }

    public Reservation updateReservation(Long reservationId, String authHeader, ReservationRequest reservationRequest) {
        Reservation existingReservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Rezerwacja o podanym ID nie istnieje"));
        Optional<User> authenticatedUser = authenticationService.authenticateBasicAuth(authHeader);
        if (!authenticatedUser.isPresent() || !existingReservation.getUser().getId().equals(authenticatedUser.get().getId())) {
            throw new RuntimeException("Nie jesteś właścicielem tej rezerwacji");
        }

        CustomService service = existingReservation.getService();

        existingReservation.setStartDateTime(reservationRequest.getStartDateTime());

        existingReservation.setEndDateTime(ReservationTimeCalculator.calculateEndDateTime(existingReservation.getStartDateTime(), service));

        return reservationRepository.save(existingReservation);
    }

    public void deleteReservation(Long reservationId, String authHeader) {
        Reservation existingReservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Rezerwacja o podanym ID nie istnieje"));

        Optional<User> authenticatedUser = authenticationService.authenticateBasicAuth(authHeader);
        if (!authenticatedUser.isPresent() || !existingReservation.getUser().getId().equals(authenticatedUser.get().getId())) {
            throw new RuntimeException("Nie jesteś właścicielem tej rezerwacji");
        }

        CustomService service = existingReservation.getService();

        reservationRepository.deleteById(reservationId);
    }
}
