package com.codehunter.modulithproject.warehouse.mapper;

import com.codehunter.modulithproject.shared.ProductDTO;
import com.codehunter.modulithproject.shared.WarehouseProductDTO;
import com.codehunter.modulithproject.warehouse.jpa.JpaWarehouseProduct;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface WarehouseProductMapper {
    ProductDTO toProductDto(JpaWarehouseProduct jpaWarehouseProduct);

    List<ProductDTO> toProductDto(List<JpaWarehouseProduct> jpaWarehouseProductList);

    Set<ProductDTO> toProductDto(Set<JpaWarehouseProduct> jpaWarehouseProductSet);

    WarehouseProductDTO toWarehouseProductDto(JpaWarehouseProduct jpaWarehouseProduct);

    List<WarehouseProductDTO> toWarehouseProductDto(List<JpaWarehouseProduct> jpaWarehouseProductList);

    Set<WarehouseProductDTO> toWarehouseProductDto(Set<JpaWarehouseProduct> jpaWarehouseProductSet);
}
