package com.example.project.reservation.service;

import com.example.project.customservice.domain.CustomService;
import com.example.project.customservice.repository.CustomServiceRepository;
import com.example.project.reservation.api.ReservationRequest;
import com.example.project.reservation.domain.Reservation;
import com.example.project.reservation.repository.ReservationRepository;
import com.example.project.user.domain.User;
import com.example.project.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomServiceRepository customServiceRepository;

    public List<Reservation> getAllReservationsByUserId(Long userId) {
        return reservationRepository.findByUserId(userId);
    }

    public List<Reservation> getAllReservationsByServiceId(Long serviceId) {
        return reservationRepository.findByServiceId(serviceId);
    }

    public Reservation createReservation(Long userId, Long serviceId, ReservationRequest reservationRequest) {
        // Sprawdź, czy właściciel usługi jest różny od użytkownika dokonującego rezerwacji
        CustomService service = customServiceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Usługa o podanym ID nie istnieje"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Użytkownik o podanym ID nie istnieje"));

        if (service.getUser().equals(user)) {
            throw new RuntimeException("Właściciel usługi nie może dokonać rezerwacji własnej usługi");
        }

        Reservation reservation = new Reservation();
        reservation.setReservationDateTime(reservationRequest.getReservationDateTime());
        reservation.setUser(user);
        reservation.setService(service);

        return reservationRepository.save(reservation);
    }

    public Reservation updateReservation(Long reservationId, Long userId, ReservationRequest reservationRequest) {
        Reservation existingReservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Rezerwacja o podanym ID nie istnieje"));

        if (!existingReservation.getUser().getId().equals(userId)) {
            throw new RuntimeException("Nie jesteś właścicielem tej rezerwacji");
        }

        CustomService service = existingReservation.getService();
        if (!service.getUser().getId().equals(userId)) {
            throw new RuntimeException("Nie jesteś właścicielem usługi przypisanej do tej rezerwacji");
        }

        existingReservation.setReservationDateTime(reservationRequest.getReservationDateTime());

        return reservationRepository.save(existingReservation);
    }

    public void deleteReservation(Long reservationId, Long userId) {
        Reservation existingReservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Rezerwacja o podanym ID nie istnieje"));
        if (!existingReservation.getUser().getId().equals(userId)) {
            throw new RuntimeException("Nie jesteś właścicielem tej rezerwacji");
        }
        CustomService service = existingReservation.getService();
        if (!service.getUser().getId().equals(userId)) {
            throw new RuntimeException("Nie jesteś właścicielem usługi przypisanej do tej rezerwacji");
        }
        reservationRepository.deleteById(reservationId);
    }


}