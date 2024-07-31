package com.codehunter.modulithproject.order;

import com.codehunter.modulithproject.warehouse.ProductDTO;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.util.Set;

public record OrderDTO(
        @JsonInclude(JsonInclude.Include.NON_NULL) String id,
        @JsonInclude(JsonInclude.Include.NON_NULL) OrderStatus orderStatus,
        String paymentId,
        BigDecimal totalAmount,
        Set<ProductDTO> products) {
}
