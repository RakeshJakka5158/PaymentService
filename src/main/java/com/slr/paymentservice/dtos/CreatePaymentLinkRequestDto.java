package com.slr.paymentservice.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class CreatePaymentLinkRequestDto {
    @JsonAlias("Student Name")
    private String studentName;
    @JsonAlias("Course Name")
    private String courseName;
    @JsonAlias("Email")
    private String email;
    @JsonAlias("Phone Number")
    private String mobile;
    @JsonAlias("Amount")
    private Integer amount;
}
