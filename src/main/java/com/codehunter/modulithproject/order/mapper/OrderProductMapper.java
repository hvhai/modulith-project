package com.codehunter.modulithproject.order.mapper;

import com.codehunter.modulithproject.order.jpa.JpaOrderProduct;
import com.codehunter.modulithproject.warehouse.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface OrderProductMapper {
    @Mapping(target = "orders", ignore = true)
    JpaOrderProduct toJpaOrderProduct(ProductDTO productDTO);

    List<JpaOrderProduct> toJpaOrderProduct(List<ProductDTO> productDTOList);
    Set<JpaOrderProduct> toJpaOrderProduct(Set<ProductDTO> productDTOSet);
}
