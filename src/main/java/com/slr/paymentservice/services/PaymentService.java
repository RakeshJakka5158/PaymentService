package com.slr.paymentservice.services;

import com.razorpay.RazorpayException;
import com.slr.paymentservice.models.StudentOrder;

public interface PaymentService {
    String createRazorpayOrder(String studentName, String courseName, String email, String mobile, Integer amount) throws RazorpayException;
    StudentOrder updatePaymentStatus(String razorpayPaymentId);

}
