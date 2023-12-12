package com.example.project.customservice.controller;

import com.example.project.customservice.api.CustomServiceRequest;
import com.example.project.customservice.domain.CustomService;
import com.example.project.customservice.service.CustomServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/services")
public class CustomServiceController {

    @Autowired
    private CustomServiceService customServiceService;

    @PostMapping
    public ResponseEntity<String> createService(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION) String authHeader,
            @RequestBody CustomServiceRequest serviceRequest) {
        try {
            CustomService newService = customServiceService.createService(authHeader, serviceRequest);
            return ResponseEntity.ok("Se    rvice created with ID: " + newService.getId());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public List<CustomService> getAllServices() {
        return customServiceService.getAllServices();
    }

    @GetMapping("/{serviceId}")
    public ResponseEntity<CustomService> getServiceById(@PathVariable Long serviceId) {
        Optional<CustomService> service = customServiceService.getServiceById(serviceId);
        return service.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{serviceId}")
    public ResponseEntity<String> updateService(
            @PathVariable Long serviceId,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION) String authHeader,
            @RequestBody CustomServiceRequest serviceRequest) {
        try {
            customServiceService.updateService(serviceId, authHeader, serviceRequest);
            return ResponseEntity.ok("Service updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{serviceId}")
    public ResponseEntity<String> deleteService(
            @PathVariable Long serviceId,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION) String authHeader) {
        try {
            customServiceService.deleteService(serviceId, authHeader);
            return ResponseEntity.ok("Service deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
