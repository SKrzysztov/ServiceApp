package com.example.project.customservice.controller;

import com.example.project.customservice.api.CustomServiceRequest;
import com.example.project.customservice.domain.CustomService;
import com.example.project.customservice.service.CustomServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
public class CustomServiceController {

    @Autowired
    private CustomServiceService customServiceService;

    @GetMapping
    public List<CustomService> getAllServices() {
        return customServiceService.getAllServices();
    }

    @GetMapping("/{serviceId}")
    public CustomService getServiceById(@PathVariable Long serviceId) {
        return customServiceService.getServiceById(serviceId)
                .orElseThrow(() -> new RuntimeException("Serwis o podanym ID nie istnieje"));
    }

    @PostMapping("/create/{userId}")
    public CustomService createService(@PathVariable Long userId, @RequestBody CustomServiceRequest serviceRequest) {
        return customServiceService.createService(userId, serviceRequest);
    }

    @PutMapping("/{serviceId}")
    public CustomService updateService(@PathVariable Long serviceId, @RequestBody CustomServiceRequest serviceRequest) {
        return customServiceService.updateService(serviceId, serviceRequest);
    }

    @DeleteMapping("/{serviceId}")
    public void deleteService(@PathVariable Long serviceId) {
        customServiceService.deleteService(serviceId);
    }
}