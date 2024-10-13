package com.slr.paymentservice.controllers;

import com.slr.paymentservice.dtos.CreatePaymentLinkRequestDto;
import com.slr.paymentservice.dtos.CreatePaymentLinkResponseDto;
import com.slr.paymentservice.models.Status;
import com.slr.paymentservice.models.StudentOrder;
import com.slr.paymentservice.services.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class PaymentController {

    private PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/")
    public String init(){
        return "index";
    }

    @PostMapping(value = "/create-payment-link",consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<CreatePaymentLinkResponseDto> createPaymentLink(@RequestBody CreatePaymentLinkRequestDto requestDto) throws Exception {
        CreatePaymentLinkResponseDto responseDto = new CreatePaymentLinkResponseDto();
        try {
            String razorpayOrderId = paymentService.createRazorpayOrder(requestDto.getStudentName(), requestDto.getCourseName(), requestDto.getEmail(), requestDto.getMobile(), requestDto.getAmount());
            responseDto.setRazorPayOrderId(razorpayOrderId);
            responseDto.setStatus(Status.SUCCESS);
        } catch (Exception e) {
            responseDto.setStatus(Status.FAILURE);
        }
        return new ResponseEntity<>(responseDto, responseDto.getStatus().equals(Status.SUCCESS) ? HttpStatus.CREATED : org.springframework.http.HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/handle-payment-callback",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = "application/json")
    public String updatePaymentStatus(@RequestParam Map<String,String> respPayload) {
        System.out.println("Payload received from Razorpay : "+respPayload);
        String razorpayPaymentId = respPayload.get("razorpay_order_id");
        StudentOrder studentOrder = paymentService.updatePaymentStatus(razorpayPaymentId);
        return "success";
    }
}
