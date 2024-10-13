package com.slr.paymentservice.services;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.slr.paymentservice.models.StudentOrder;
import com.slr.paymentservice.repositories.StudentOrderRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    private StudentOrderRepository studentOrderRepository;

    @Value("${razorpay.key_id}")
    private String razorpayKey;
    @Value("${razorpay.key_secret}")
    private String razorpaySecret;

    private RazorpayClient razorpayClient;

    public PaymentServiceImpl(StudentOrderRepository studentOrderRepository) throws RazorpayException {
        this.studentOrderRepository = studentOrderRepository;

    }

    @Override
    public String createRazorpayOrder(String studentName, String courseName, String email, String mobile, Integer amount) throws RazorpayException {
        // Creating a student order
        StudentOrder studentOrder = createStudentOrder(studentName, courseName, email, mobile, amount);
        // Creating Razorpay order
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", studentOrder.getAmount() * 100); // amount in the smallest currency unit
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", studentOrder.getEmail());

        this.razorpayClient = new RazorpayClient(razorpayKey, razorpaySecret);
        Order razorpayOrder = razorpayClient.orders.create(orderRequest);

        // Updating the student order with Razorpay order id
        studentOrder.setRazorpayPaymentId(razorpayOrder.get("id"));
        studentOrder.setOrderStatus(razorpayOrder.get("status"));
        studentOrderRepository.save(studentOrder);

        return studentOrder.getRazorpayPaymentId();
    }

    @Override
    public StudentOrder updatePaymentStatus(String razorpayPaymentId) {
        // Fetching the student order
        StudentOrder studentOrder = studentOrderRepository.findByRazorpayPaymentId(razorpayPaymentId);
        studentOrder.setOrderStatus("PAID");
        studentOrderRepository.save(studentOrder);
        return studentOrder;
    }

    private StudentOrder createStudentOrder(String studentName, String courseName, String email, String mobile, Integer amount) {
        StudentOrder studentOrder = new StudentOrder();
        studentOrder.setStudentName(studentName);
        studentOrder.setCourseName(courseName);
        studentOrder.setEmail(email);
        studentOrder.setMobile(mobile);
        studentOrder.setAmount(amount);
        return studentOrderRepository.save(studentOrder);
    }
}
