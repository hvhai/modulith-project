package com.codehunter.modulithproject.order.mapper;

import com.codehunter.modulithproject.eventsourcing.OrderDTO;
import com.codehunter.modulithproject.order.jpa.JpaOrder;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {OrderPaymentMapper.class})
public interface OrderMapper {
    OrderDTO toOrderDTO(JpaOrder jpaOrder);

    List<OrderDTO> toOrderDTO(List<JpaOrder> jpaOrder);

    Set<OrderDTO> toOrderDTO(Set<JpaOrder> jpaOrder);
}
