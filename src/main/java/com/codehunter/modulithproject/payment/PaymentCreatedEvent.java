package com.codehunter.modulithproject.payment;

import com.codehunter.modulithproject.eventsourcing.PaymentDTO;
import org.jmolecules.event.types.DomainEvent;

public record PaymentCreatedEvent(PaymentDTO payment) implements DomainEvent {
}
