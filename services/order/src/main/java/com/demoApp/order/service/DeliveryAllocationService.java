package com.demoApp.order.service;

import com.demoApp.order.entity.DeliveryPerson;
import com.demoApp.order.entity.Order;
import com.demoApp.order.entity.DeliveryPersonStatus;
import com.demoApp.order.repository.DeliveryPersonRepository;
import com.demoApp.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeliveryAllocationService {
    private final DeliveryPersonRepository deliveryPersonRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public Optional<DeliveryPerson> allocateDeliveryPerson(Order order) {
        LocalTime currentTime = LocalTime.now();
        
        // Find all available delivery persons in current shift
        List<DeliveryPerson> availableDeliveryPersons = deliveryPersonRepository
            .findByStatusAndIsAvailableAndShiftStartTimeLessThanEqualAndShiftEndTimeGreaterThanEqual(
                DeliveryPersonStatus.AVAILABLE,
                true,
                currentTime,
                currentTime
            );

        if (availableDeliveryPersons.isEmpty()) {
            log.warn("No delivery persons available for order: {}", order.getId());
            return Optional.empty();
        }

        // Find delivery person with minimum orders and good rating
        Optional<DeliveryPerson> selectedDeliveryPerson = availableDeliveryPersons.stream()
            .filter(DeliveryPerson::canAcceptOrder)
            .min(Comparator
                .comparingInt(DeliveryPerson::getCurrentOrderCount)
                .thenComparing(dp -> -dp.getRating())); // Higher rating is preferred

        if (selectedDeliveryPerson.isPresent()) {
            DeliveryPerson deliveryPerson = selectedDeliveryPerson.get();
            deliveryPerson.incrementOrderCount();
            
            // Optionally update delivery person's current location if available
            
            deliveryPersonRepository.save(deliveryPerson);
            log.info("Allocated delivery person {} to order {}", deliveryPerson.getId(), order.getId());
            return Optional.of(deliveryPerson);
        }

        log.warn("No available delivery persons with capacity for order: {}", order.getId());
        return Optional.empty();
    }

    @Transactional
    public void releaseDeliveryPerson(Order order) {
        // No direct reference to deliveryPerson in Order entity. Implement logic as needed.
    }

    @Transactional
    public void updateDeliveryPersonRating(DeliveryPerson deliveryPerson, Double rating) {
        deliveryPerson.updateRating(rating);
        deliveryPersonRepository.save(deliveryPerson);
        log.info("Updated rating for delivery person {}: {}", deliveryPerson.getId(), deliveryPerson.getRating());
    }
} 