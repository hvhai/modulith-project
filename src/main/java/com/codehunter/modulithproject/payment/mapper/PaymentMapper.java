package com.codehunter.modulithproject.payment.mapper;

import com.codehunter.modulithproject.payment.PaymentDTO;
import com.codehunter.modulithproject.payment.jpa.JpaPayment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentDTO toPaymentDTO(JpaPayment jpaPayment);
}
