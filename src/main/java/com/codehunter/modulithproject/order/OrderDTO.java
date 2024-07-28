package com.codehunter.modulithproject.order;

import com.codehunter.modulithproject.shared.ProductDTO;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public record OrderDTO(
        @JsonInclude(JsonInclude.Include.NON_NULL) String id,
        @JsonInclude(JsonInclude.Include.NON_NULL) OrderStatus orderStatus,
        List<ProductDTO> productList) {
}
