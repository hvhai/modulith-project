package com.codehunter.modulithproject.payment.mapper;

import com.codehunter.modulithproject.payment.jpa.JpaPayment;
import com.codehunter.modulithproject.shared.PaymentDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentDTO toPaymentDTO(JpaPayment jpaPayment);

    List<PaymentDTO> toPaymentDTO(List<JpaPayment> jpaPayment);
}
