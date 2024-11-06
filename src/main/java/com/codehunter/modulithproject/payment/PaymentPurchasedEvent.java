package com.codehunter.modulithproject.payment;

import com.codehunter.modulithproject.eventsourcing.PaymentDTO;

public record PaymentPurchasedEvent(PaymentDTO payment) {
}
