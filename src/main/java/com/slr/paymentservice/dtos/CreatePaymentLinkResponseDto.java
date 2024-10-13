package com.slr.paymentservice.dtos;

import com.slr.paymentservice.models.Status;
import lombok.Data;

@Data
public class CreatePaymentLinkResponseDto {
    private String razorPayOrderId;
    private Status status;
}
