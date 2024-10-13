package com.slr.paymentservice.models;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class StudentOrder extends BaseModel{
    private String studentName;
    private String courseName;
    private String email;
    private String mobile;
    private Long orderId;
    private Integer amount;
    private String orderStatus;
    private String razorpayPaymentId;
}
