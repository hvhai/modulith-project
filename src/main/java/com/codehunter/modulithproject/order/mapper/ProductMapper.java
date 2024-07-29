package com.codehunter.modulithproject.order.mapper;

import com.codehunter.modulithproject.order.jpa.JpaOrderProduct;
import com.codehunter.modulithproject.shared.ProductDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    JpaOrderProduct toJpaOrderProduct(ProductDTO productDTO);

    List<JpaOrderProduct> toJpaOrderProduct(List<ProductDTO> productDTO);
}
