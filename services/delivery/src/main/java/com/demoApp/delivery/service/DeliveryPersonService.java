package com.demoApp.delivery.service;

import com.demoApp.delivery.dto.DeliveryPersonDTO;
import com.demoApp.delivery.entity.DeliveryPerson;
import com.demoApp.delivery.repository.DeliveryPersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeliveryPersonService {
    private final DeliveryPersonRepository deliveryPersonRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    
    @Transactional
    public DeliveryPersonDTO createDeliveryPerson(DeliveryPersonDTO deliveryPersonDTO) {
        // Check if email already exists
        if (deliveryPersonRepository.findByEmail(deliveryPersonDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }
        
        // Check if mobile number already exists
        if (deliveryPersonRepository.findByMobileNumber(deliveryPersonDTO.getMobileNumber()).isPresent()) {
            throw new RuntimeException("Mobile number already registered");
        }
        
        DeliveryPerson deliveryPerson = modelMapper.map(deliveryPersonDTO, DeliveryPerson.class);
        
        // Encode password
        deliveryPerson.setPassword(passwordEncoder.encode(deliveryPersonDTO.getPassword()));
        
        // Set default values
        deliveryPerson.setStatus(DeliveryPerson.DeliveryPersonStatus.ACTIVE);
        deliveryPerson.setAvailable(true);
        deliveryPerson.setAverageRating(0.0);
        deliveryPerson.setTotalRatings(0);
        deliveryPerson.setCreatedAt(LocalDateTime.now());
        
        DeliveryPerson savedDeliveryPerson = deliveryPersonRepository.save(deliveryPerson);
        log.info("Created delivery person with ID: {}", savedDeliveryPerson.getId());
        
        return modelMapper.map(savedDeliveryPerson, DeliveryPersonDTO.class);
    }
    
    public DeliveryPersonDTO getDeliveryPersonById(Long id) {
        DeliveryPerson deliveryPerson = deliveryPersonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Delivery person not found with ID: " + id));
        
        return modelMapper.map(deliveryPerson, DeliveryPersonDTO.class);
    }
    
    public DeliveryPersonDTO getDeliveryPersonByEmail(String email) {
        DeliveryPerson deliveryPerson = deliveryPersonRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Delivery person not found with email: " + email));
        
        return modelMapper.map(deliveryPerson, DeliveryPersonDTO.class);
    }
    
    public List<DeliveryPersonDTO> getAllDeliveryPersons() {
        return deliveryPersonRepository.findAll().stream()
                .map(deliveryPerson -> modelMapper.map(deliveryPerson, DeliveryPersonDTO.class))
                .collect(Collectors.toList());
    }
    
    public List<DeliveryPersonDTO> getAvailableDeliveryPersons() {
        return deliveryPersonRepository.findByIsAvailableTrue().stream()
                .map(deliveryPerson -> modelMapper.map(deliveryPerson, DeliveryPersonDTO.class))
                .collect(Collectors.toList());
    }
    
    public List<DeliveryPersonDTO> getDeliveryPersonsByZone(DeliveryPerson.DeliveryZone zone) {
        return deliveryPersonRepository.findByZone(zone).stream()
                .map(deliveryPerson -> modelMapper.map(deliveryPerson, DeliveryPersonDTO.class))
                .collect(Collectors.toList());
    }
    
    public List<DeliveryPersonDTO> getAvailableDeliveryPersonsByZone(DeliveryPerson.DeliveryZone zone) {
        List<DeliveryPerson> deliveryPersons = deliveryPersonRepository.findByDeliveryZoneAndIsAvailableTrue(zone);
        
        return deliveryPersons.stream()
                .map(dp -> modelMapper.map(dp, DeliveryPersonDTO.class))
                .collect(Collectors.toList());
    }
    
    @Transactional
    public DeliveryPersonDTO updateDeliveryPerson(Long id, DeliveryPersonDTO deliveryPersonDTO) {
        DeliveryPerson deliveryPerson = deliveryPersonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Delivery person not found with ID: " + id));
        
        modelMapper.map(deliveryPersonDTO, deliveryPerson);
        DeliveryPerson updatedDeliveryPerson = deliveryPersonRepository.save(deliveryPerson);
        
        return modelMapper.map(updatedDeliveryPerson, DeliveryPersonDTO.class);
    }
    
    @Transactional
    public DeliveryPersonDTO updateAvailabilityStatus(Long id, boolean isAvailable) {
        DeliveryPerson deliveryPerson = deliveryPersonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Delivery person not found with ID: " + id));
        
        deliveryPerson.setAvailable(isAvailable);
        deliveryPerson.setUpdatedAt(LocalDateTime.now());
        
        DeliveryPerson updatedDeliveryPerson = deliveryPersonRepository.save(deliveryPerson);
        log.info("Delivery person availability updated. ID: {}, Available: {}", id, isAvailable);
        
        return modelMapper.map(updatedDeliveryPerson, DeliveryPersonDTO.class);
    }
    
    @Transactional
    public void updateDeliveryPersonStatus(Long id, DeliveryPerson.DeliveryPersonStatus status) {
        DeliveryPerson deliveryPerson = deliveryPersonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Delivery person not found with ID: " + id));
        
        deliveryPerson.setStatus(status);
        deliveryPersonRepository.save(deliveryPerson);
    }
    
    @Transactional
    public void updateDeliveryPersonZone(Long id, DeliveryPerson.DeliveryZone zone) {
        DeliveryPerson deliveryPerson = deliveryPersonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Delivery person not found with ID: " + id));
        
        deliveryPerson.setZone(zone);
        deliveryPersonRepository.save(deliveryPerson);
    }
    
    @Transactional
    public void deleteDeliveryPerson(Long id) {
        if (!deliveryPersonRepository.existsById(id)) {
            throw new RuntimeException("Delivery person not found with ID: " + id);
        }
        
        deliveryPersonRepository.deleteById(id);
        log.info("Delivery person deleted with ID: {}", id);
    }
    
    @Transactional
    public void updateDeliveryPersonRating(Long id, double rating) {
        DeliveryPerson deliveryPerson = deliveryPersonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Delivery person not found with ID: " + id));
        
        // Calculate new average rating
        double currentRating = deliveryPerson.getAverageRating();
        int totalRatings = deliveryPerson.getTotalRatings();
        
        double newRating;
        if (totalRatings == 0) {
            newRating = rating;
        } else {
            newRating = ((currentRating * totalRatings) + rating) / (totalRatings + 1);
        }
        
        deliveryPerson.setAverageRating(newRating);
        deliveryPerson.setTotalRatings(totalRatings + 1);
        
        deliveryPersonRepository.save(deliveryPerson);
        log.info("Delivery person rating updated. ID: {}, New Rating: {}", id, newRating);
    }
} 