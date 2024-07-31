package com.codehunter.modulithproject.order.mapper;

import com.codehunter.modulithproject.order.jpa.JpaOrder;
import com.codehunter.modulithproject.order.jpa.JpaOrderPayment;
import com.codehunter.modulithproject.order.jpa_repository.OrderPaymentRepository;
import com.codehunter.modulithproject.order.jpa_repository.OrderRepository;
import com.codehunter.modulithproject.payment.PaymentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class OrderPaymentMapper {
    @Autowired
    protected OrderRepository orderRepository;

    @Mapping(target = "id", source = "id")
    @Mapping(target = "order", expression = "java(getOrder(paymentDTO.orderId()))")
    @Mapping(target = "purchaseAt", source = "purchaseAt")
    public abstract JpaOrderPayment toJpaOrderPayment(PaymentDTO paymentDTO);

    @Mapping(target = "orderId", source = "order.id")
    public abstract PaymentDTO toPaymentDTO(JpaOrderPayment jpaOrderPayment);

    public JpaOrder getOrder(String orderId) {
        if (orderId == null) {
            return null;
        }
        Optional<JpaOrder> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            return null;
        }
        return orderOptional.get();
    }

}