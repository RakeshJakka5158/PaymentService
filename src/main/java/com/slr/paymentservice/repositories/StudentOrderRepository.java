package com.slr.paymentservice.repositories;

import com.slr.paymentservice.models.StudentOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentOrderRepository extends JpaRepository<StudentOrder, Long> {
    StudentOrder findByRazorpayPaymentId(String razorpayPaymentId);
}
