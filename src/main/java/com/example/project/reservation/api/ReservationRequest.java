package com.example.project.reservation.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReservationRequest {
    @JsonFormat(pattern = "yy-MM-dd HH:mm")
    private LocalDateTime startDateTime;
}
