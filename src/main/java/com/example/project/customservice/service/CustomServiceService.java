package com.example.project.customservice.service;


import com.example.project.customservice.api.CustomServiceRequest;
import com.example.project.customservice.domain.CustomService;
import com.example.project.customservice.repository.CustomServiceRepository;
import com.example.project.user.domain.User;
import com.example.project.user.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Service
public class CustomServiceService {

    @Autowired
    private CustomServiceRepository serviceRepository;

    @Autowired
    private AuthenticationService authenticationService;

    public List<CustomService> getAllServices() {
        return serviceRepository.findAll();
    }

    public Optional<CustomService> getServiceById(Long serviceId) {
        return serviceRepository.findById(serviceId);
    }

    public CustomService createService(String authHeader, CustomServiceRequest serviceRequest) {
        Optional<User> authenticatedUser = authenticationService.authenticateBasicAuth(authHeader);
        if (authenticatedUser.isPresent()) {
            CustomService serviceEntity = new CustomService();
            serviceEntity.setServiceName(serviceRequest.getServiceName());
            serviceEntity.setUser(authenticatedUser.get());
            setDuration(serviceEntity, serviceRequest);
            return serviceRepository.save(serviceEntity);
        } else {
            throw new RuntimeException("Uwierzytelnianie nieudane");
        }
    }

    public CustomService updateService(Long serviceId, String authHeader, CustomServiceRequest serviceRequest) {
        Optional<CustomService> optionalService = serviceRepository.findById(serviceId);

        if (optionalService.isPresent()) {
            CustomService serviceEntity = optionalService.get();

            Optional<User> authenticatedUser = authenticationService.authenticateBasicAuth(authHeader);
            if (!authenticatedUser.isPresent() || !serviceEntity.getUser().getId().equals(authenticatedUser.get().getId())) {
                throw new RuntimeException("Nie jesteś właścicielem tej usługi");
            }

            serviceEntity.setServiceName(serviceRequest.getServiceName());
            setDuration(serviceEntity, serviceRequest);
            return serviceRepository.save(serviceEntity);
        } else {
            throw new RuntimeException("Serwis o podanym ID nie istnieje");
        }
    }

    public void deleteService(Long serviceId, String authHeader) {
        Optional<CustomService> optionalService = serviceRepository.findById(serviceId);

        if (optionalService.isPresent()) {
            CustomService serviceEntity = optionalService.get();

            Optional<User> authenticatedUser = authenticationService.authenticateBasicAuth(authHeader);
            if (!authenticatedUser.isPresent() || !serviceEntity.getUser().getId().equals(authenticatedUser.get().getId())) {
                throw new RuntimeException("Nie jesteś właścicielem tej usługi");
            }
            serviceRepository.deleteById(serviceId);
        } else {
            throw new RuntimeException("Serwis o podanym ID nie istnieje");
        }
    }

    private void setDuration(CustomService serviceEntity, CustomServiceRequest serviceRequest) {
        if (serviceRequest.getDurationInMinutes() != null) {
            serviceEntity.setDuration(Duration.ofMinutes(serviceRequest.getDurationInMinutes()));
        } else {
            serviceEntity.setDuration(Duration.ofMinutes(60));
        }
    }
}

