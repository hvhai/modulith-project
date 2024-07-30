package com.codehunter.modulithproject.warehouse.mapper;

import com.codehunter.modulithproject.warehouse.ProductDTO;
import com.codehunter.modulithproject.warehouse.WarehouseProductDTO;
import com.codehunter.modulithproject.warehouse.jpa.JpaWarehouseProduct;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WarehouseProductMapper {
    ProductDTO toProductDto(JpaWarehouseProduct jpaWarehouseProduct);

    List<ProductDTO> toProductDto(List<JpaWarehouseProduct> jpaWarehouseProductList);

    WarehouseProductDTO toWarehouseProductDto(JpaWarehouseProduct jpaWarehouseProduct);

    List<WarehouseProductDTO> toWarehouseProductDto(List<JpaWarehouseProduct> jpaWarehouseProductList);
}
