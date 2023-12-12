package com.example.project.customservice.service;


import com.example.project.customservice.api.CustomServiceRequest;
import com.example.project.customservice.domain.CustomService;
import com.example.project.customservice.repository.CustomServiceRepository;
import com.example.project.user.domain.User;
import com.example.project.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomServiceService {

    @Autowired
    private CustomServiceRepository serviceRepository;
    @Autowired
    private UserRepository userRepository;
    public List<CustomService> getAllServices() {
        return serviceRepository.findAll();
    }

    public Optional<CustomService> getServiceById(Long serviceId) {
        return serviceRepository.findById(serviceId);
    }

    public CustomService createService(Long userId, CustomServiceRequest serviceRequest) {
        CustomService serviceEntity = new CustomService();
        serviceEntity.setServiceName(serviceRequest.getServiceName());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Użytkownik o podanym ID nie istnieje"));
        serviceEntity.setUser(user);
        return serviceRepository.save(serviceEntity);
    }

    public CustomService updateService(Long serviceId, CustomServiceRequest serviceRequest) {
        Optional<CustomService> optionalService = serviceRepository.findById(serviceId);

        if (optionalService.isPresent()) {
            CustomService serviceEntity = optionalService.get();
            serviceEntity.setServiceName(serviceRequest.getServiceName());
            return serviceRepository.save(serviceEntity);
        } else {
            throw new RuntimeException("Serwis o podanym ID nie istnieje");
        }
    }

    public void deleteService(Long serviceId) {
        serviceRepository.deleteById(serviceId);
    }
}