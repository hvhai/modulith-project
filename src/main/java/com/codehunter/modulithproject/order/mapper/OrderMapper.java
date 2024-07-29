package com.codehunter.modulithproject.order.mapper;

import com.codehunter.modulithproject.order.OrderDTO;
import com.codehunter.modulithproject.order.jpa.JpaOrder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDTO toOrderDTO(JpaOrder jpaOrder);
}
