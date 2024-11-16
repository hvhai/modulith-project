package com.codehunter.modulithproject.shared;

import java.math.BigDecimal;
import java.time.Instant;

public record PaymentDTO(String id, String orderId, BigDecimal totalAmount, Instant purchaseAt) {
}
