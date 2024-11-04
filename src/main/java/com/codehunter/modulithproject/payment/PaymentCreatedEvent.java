package com.codehunter.modulithproject.payment;

import org.jmolecules.event.types.DomainEvent;

public record PaymentCreatedEvent(PaymentDTO payment) implements DomainEvent {
}
