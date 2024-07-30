package com.codehunter.modulithproject.order;

import com.codehunter.modulithproject.warehouse.ProductDTO;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Set;

public record OrderDTO(
        @JsonInclude(JsonInclude.Include.NON_NULL) String id,
        @JsonInclude(JsonInclude.Include.NON_NULL) OrderStatus orderStatus,
        Set<ProductDTO> products) {
}
