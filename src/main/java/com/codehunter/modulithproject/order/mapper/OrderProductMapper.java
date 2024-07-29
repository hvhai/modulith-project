package com.codehunter.modulithproject.order.mapper;

import com.codehunter.modulithproject.order.jpa.JpaOrderProduct;
import com.codehunter.modulithproject.shared.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderProductMapper {
    @Mapping(target = "orderList", ignore = true)
    JpaOrderProduct toJpaOrderProduct(ProductDTO productDTO);

    List<JpaOrderProduct> toJpaOrderProduct(List<ProductDTO> productDTOList);
}
