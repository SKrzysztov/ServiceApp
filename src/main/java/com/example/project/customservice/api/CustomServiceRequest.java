package com.example.project.customservice.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomServiceRequest {
    private String serviceName;
    private Long durationInMinutes;
    public void setDurationInMinutes(Long durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }
}
