package com.codehunter.modulithproject.eventsourcing;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.util.Set;

public record OrderDTO(
        @JsonInclude(JsonInclude.Include.NON_NULL) String id,
        @JsonInclude(JsonInclude.Include.NON_NULL) OrderStatus orderStatus,
        BigDecimal totalAmount,
        PaymentDTO payment,
        Set<ProductDTO> products) {
}
