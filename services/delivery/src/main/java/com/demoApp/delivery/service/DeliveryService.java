package com.demoApp.delivery.service;

import com.demoApp.delivery.dto.DeliveryDTO;
import com.demoApp.delivery.dto.DeliveryTrackingDTO;
import com.demoApp.delivery.entity.Delivery;
import com.demoApp.delivery.entity.DeliveryPerson;
import com.demoApp.delivery.entity.PickupPoint;
import com.demoApp.delivery.repository.DeliveryRepository;
import com.demoApp.delivery.repository.DeliveryPersonRepository;
import com.demoApp.delivery.repository.PickupPointRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final DeliveryPersonRepository deliveryPersonRepository;
    private final PickupPointRepository pickupPointRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public DeliveryDTO createDelivery(DeliveryDTO deliveryDTO) {
        Delivery delivery = modelMapper.map(deliveryDTO, Delivery.class);

        // Validate pickup point
        if (deliveryDTO.getPickupPointId() != null) {
            PickupPoint pickupPoint = pickupPointRepository.findById(deliveryDTO.getPickupPointId())
                    .orElseThrow(() -> new RuntimeException("Pickup point not found with ID: " + deliveryDTO.getPickupPointId()));
            delivery.setPickupPoint(pickupPoint);
        }

        // Set initial status if not provided
        if (delivery.getStatus() == null) {
            delivery.setStatus(Delivery.DeliveryStatus.PENDING);
        }

        // Set creation timestamp
        delivery.setCreatedAt(LocalDateTime.now());

        // Mock geocoding for delivery address
        if (delivery.getDeliveryLatitude() == 0 && delivery.getDeliveryLongitude() == 0
                && delivery.getDeliveryAddress() != null && !delivery.getDeliveryAddress().isEmpty()) {
            log.info("Using mock geocoding for address: {}", delivery.getDeliveryAddress());
            delivery.setDeliveryLatitude(18.5204);  // Default latitude (Pune)
            delivery.setDeliveryLongitude(73.8567); // Default longitude (Pune)
        }

        // Set pickup point coordinates as initial location for tracking
        if (delivery.getPickupPoint() != null) {
            delivery.setCurrentLatitude(delivery.getPickupPoint().getLatitude());
            delivery.setCurrentLongitude(delivery.getPickupPoint().getLongitude());
            delivery.setLocationUpdatedAt(LocalDateTime.now());
        }

        // If delivery person assigned, validate
        if (deliveryDTO.getDeliveryPersonId() != null) {
            DeliveryPerson deliveryPerson = deliveryPersonRepository.findById(deliveryDTO.getDeliveryPersonId())
                    .orElseThrow(() -> new RuntimeException("Delivery person not found with ID: " + deliveryDTO.getDeliveryPersonId()));

            // Check if delivery person is available
            if (!deliveryPerson.isAvailable()) {
                throw new RuntimeException("Delivery person with ID " + deliveryDTO.getDeliveryPersonId() + " is not available");
            }

            delivery.setDeliveryPerson(deliveryPerson);
            deliveryPerson.setAvailable(false); // Mark as unavailable
            deliveryPersonRepository.save(deliveryPerson);
        }

        Delivery savedDelivery = deliveryRepository.save(delivery);
        log.info("Delivery created with ID: {}", savedDelivery.getId());

        return modelMapper.map(savedDelivery, DeliveryDTO.class);
    }

    public DeliveryDTO getDeliveryById(Long id) {
        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Delivery not found with ID: " + id));

        return modelMapper.map(delivery, DeliveryDTO.class);
    }

    public List<DeliveryDTO> getAllDeliveries() {
        List<Delivery> deliveries = deliveryRepository.findAll();

        return deliveries.stream()
                .map(d -> modelMapper.map(d, DeliveryDTO.class))
                .collect(Collectors.toList());
    }

    public List<DeliveryDTO> getDeliveriesByStatus(Delivery.DeliveryStatus status) {
        List<Delivery> deliveries = deliveryRepository.findByStatus(status);

        return deliveries.stream()
                .map(d -> modelMapper.map(d, DeliveryDTO.class))
                .collect(Collectors.toList());
    }

    public List<DeliveryDTO> getDeliveriesByDeliveryPerson(Long deliveryPersonId) {
        DeliveryPerson deliveryPerson = deliveryPersonRepository.findById(deliveryPersonId)
                .orElseThrow(() -> new RuntimeException("Delivery person not found with ID: " + deliveryPersonId));

        List<Delivery> deliveries = deliveryRepository.findByDeliveryPerson(deliveryPerson);

        return deliveries.stream()
                .map(d -> modelMapper.map(d, DeliveryDTO.class))
                .collect(Collectors.toList());
    }

    public List<DeliveryDTO> getDeliveriesByPickupPoint(Long pickupPointId) {
        PickupPoint pickupPoint = pickupPointRepository.findById(pickupPointId)
                .orElseThrow(() -> new RuntimeException("Pickup point not found with ID: " + pickupPointId));

        List<Delivery> deliveries = deliveryRepository.findByPickupPoint(pickupPoint);

        return deliveries.stream()
                .map(d -> modelMapper.map(d, DeliveryDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public DeliveryDTO assignDeliveryPerson(Long deliveryId, Long deliveryPersonId) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new RuntimeException("Delivery not found with ID: " + deliveryId));

        DeliveryPerson deliveryPerson = deliveryPersonRepository.findById(deliveryPersonId)
                .orElseThrow(() -> new RuntimeException("Delivery person not found with ID: " + deliveryPersonId));

        if (!deliveryPerson.isAvailable()) {
            throw new RuntimeException("Delivery person with ID " + deliveryPersonId + " is not available");
        }

        if (delivery.getStatus() != Delivery.DeliveryStatus.PENDING) {
            throw new RuntimeException("Delivery with ID " + deliveryId + " is not in PENDING status and cannot be assigned");
        }

        delivery.setDeliveryPerson(deliveryPerson);
        delivery.setStatus(Delivery.DeliveryStatus.ASSIGNED);
        delivery.setAssignedAt(LocalDateTime.now());

        deliveryPerson.setAvailable(false);
        deliveryPersonRepository.save(deliveryPerson);

        Delivery updatedDelivery = deliveryRepository.save(delivery);
        log.info("Delivery person assigned to delivery. Delivery ID: {}, Delivery Person ID: {}", deliveryId, deliveryPersonId);

        return modelMapper.map(updatedDelivery, DeliveryDTO.class);
    }

    @Transactional
    public DeliveryDTO updateDeliveryStatus(Long deliveryId, Delivery.DeliveryStatus status) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new RuntimeException("Delivery not found with ID: " + deliveryId));

        validateStatusTransition(delivery.getStatus(), status);

        delivery.setStatus(status);
        updateStatusTimestamps(delivery, status);
        delivery.addStatusHistory(status, "Status updated to " + status);

        if (status == Delivery.DeliveryStatus.DELIVERED || status == Delivery.DeliveryStatus.CANCELLED) {
            if (delivery.getDeliveryPerson() != null) {
                DeliveryPerson deliveryPerson = delivery.getDeliveryPerson();
                deliveryPerson.setAvailable(true);
                deliveryPersonRepository.save(deliveryPerson);
            }
        }

        Delivery updatedDelivery = deliveryRepository.save(delivery);
        log.info("Delivery status updated. Delivery ID: {}, Status: {}", deliveryId, status);

        return modelMapper.map(updatedDelivery, DeliveryDTO.class);
    }

    private void updateStatusTimestamps(Delivery delivery, Delivery.DeliveryStatus newStatus) {
        LocalDateTime now = LocalDateTime.now();

        switch (newStatus) {
            case PENDING:
                break;
            case ASSIGNED:
                delivery.setAssignedAt(now);
                break;
            case ACCEPTED:
                delivery.setAcceptedTime(now);
                break;
            case PICKED_UP:
                delivery.setPickedUpTime(now);
                break;
            case IN_TRANSIT:
                delivery.setInTransitAt(now);
                break;
            case DELIVERED:
                delivery.setDeliveredTime(now);
                break;
            case CANCELLED:
                delivery.setCancelledAt(now);
                break;
            case FAILED:
                break;
            default:
                log.warn("No timestamp handling for status: {}", newStatus);
        }

        delivery.setUpdatedAt(now);
    }

    @Transactional
    public Delivery updateDelivery(Long id, DeliveryDTO deliveryDTO) {
        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Delivery not found with ID: " + id));
        
        // Update delivery fields from DTO
        if (deliveryDTO.getStatus() != null) {
            validateStatusTransition(delivery.getStatus(), deliveryDTO.getStatus());
            delivery.setStatus(deliveryDTO.getStatus());
            updateStatusTimestamps(delivery, deliveryDTO.getStatus());
        }
        
        if (deliveryDTO.getDeliveryPersonId() != null) {
            DeliveryPerson deliveryPerson = deliveryPersonRepository.findById(deliveryDTO.getDeliveryPersonId())
                    .orElseThrow(() -> new RuntimeException("Delivery person not found with ID: " + deliveryDTO.getDeliveryPersonId()));
            delivery.setDeliveryPerson(deliveryPerson);
        }
        
        // Add other field updates as needed
        return deliveryRepository.save(delivery);
    }
    
    @Transactional
    public Delivery updateDeliveryLocation(Long id, Double latitude, Double longitude) {
        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Delivery not found with ID: " + id));
                
        delivery.setCurrentLatitude(latitude);
        delivery.setCurrentLongitude(longitude);
        delivery.setLocationUpdatedAt(LocalDateTime.now());
        
        return deliveryRepository.save(delivery);
    }
    
    @Transactional
    public Delivery rateDeliveryPerson(Long id, Double rating, String feedback) {
        if (rating < 1 || rating > 5) {
            throw new RuntimeException("Rating must be between 1 and 5");
        }
        
        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Delivery not found with ID: " + id));
                
        if (delivery.getStatus() != Delivery.DeliveryStatus.DELIVERED) {
            throw new RuntimeException("Cannot rate a delivery that hasn't been delivered");
        }
        
        DeliveryPerson deliveryPerson = delivery.getDeliveryPerson();
        if (deliveryPerson == null) {
            throw new RuntimeException("No delivery person assigned to this delivery");
        }
        
        // Update delivery with rating and feedback
        delivery.setDeliveryRating(rating.intValue());
        delivery.setDeliveryFeedback(feedback);
        
        // Update delivery person's average rating
        List<Delivery> ratedDeliveries = deliveryRepository.findByDeliveryPersonAndDeliveryRatingIsNotNull(deliveryPerson);
        double averageRating = ratedDeliveries.stream()
                .mapToInt(Delivery::getDeliveryRating)
                .average()
                .orElse(0.0);
                
        deliveryPerson.setAverageRating(Math.round(averageRating * 10.0) / 10.0); // Round to 1 decimal place
        deliveryPersonRepository.save(deliveryPerson);
        
        return deliveryRepository.save(delivery);
    }
    
    public DeliveryTrackingDTO getDeliveryTracking(Long id) {
        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Delivery not found with ID: " + id));
                
        return DeliveryTrackingDTO.builder()
                .deliveryId(delivery.getId())
                .status(delivery.getStatus())
                .currentLatitude(delivery.getCurrentLatitude())
                .currentLongitude(delivery.getCurrentLongitude())
                .locationUpdatedAt(delivery.getLocationUpdatedAt())
                .build();
    }
    
    private void validateStatusTransition(Delivery.DeliveryStatus currentStatus, Delivery.DeliveryStatus newStatus) {
        if (currentStatus == newStatus) {
            return;
        }

        switch (currentStatus) {
            case PENDING:
                if (newStatus != Delivery.DeliveryStatus.ASSIGNED && newStatus != Delivery.DeliveryStatus.CANCELLED) {
                    throw new RuntimeException("Invalid status transition from " + currentStatus + " to " + newStatus);
                }
                break;
            case ASSIGNED:
                if (newStatus != Delivery.DeliveryStatus.PICKED_UP && newStatus != Delivery.DeliveryStatus.CANCELLED) {
                    throw new RuntimeException("Invalid status transition from " + currentStatus + " to " + newStatus);
                }
                break;
            case ACCEPTED:
                if (newStatus != Delivery.DeliveryStatus.PICKED_UP && newStatus != Delivery.DeliveryStatus.CANCELLED) {
                    throw new RuntimeException("Invalid status transition from " + currentStatus + " to " + newStatus);
                }
                break;
            case PICKED_UP:
                if (newStatus != Delivery.DeliveryStatus.IN_TRANSIT && newStatus != Delivery.DeliveryStatus.CANCELLED) {
                    throw new RuntimeException("Invalid status transition from " + currentStatus + " to " + newStatus);
                }
                break;
            case IN_TRANSIT:
                if (newStatus != Delivery.DeliveryStatus.DELIVERED && newStatus != Delivery.DeliveryStatus.FAILED) {
                    throw new RuntimeException("Invalid status transition from " + currentStatus + " to " + newStatus);
                }
                break;
            case DELIVERED:
            case CANCELLED:
            case FAILED:
                throw new RuntimeException("Cannot transition from terminal state: " + currentStatus);
            default:
                throw new RuntimeException("Unknown delivery status: " + currentStatus);
        }
    }
}
