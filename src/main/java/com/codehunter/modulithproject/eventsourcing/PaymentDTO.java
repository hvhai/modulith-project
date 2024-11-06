package com.codehunter.modulithproject.eventsourcing;

import java.math.BigDecimal;
import java.time.Instant;

public record PaymentDTO(String id, String orderId, BigDecimal totalAmount, Instant purchaseAt) {
}
