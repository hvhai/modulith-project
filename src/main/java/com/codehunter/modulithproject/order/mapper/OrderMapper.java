package com.codehunter.modulithproject.order.mapper;

import com.codehunter.modulithproject.order.OrderDTO;
import com.codehunter.modulithproject.order.jpa.JpaOrder;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDTO toOrderDTO(JpaOrder jpaOrder);
    List<OrderDTO> toOrderDTO(List<JpaOrder> jpaOrder);
}
