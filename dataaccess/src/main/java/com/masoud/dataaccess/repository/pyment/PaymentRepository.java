package com.masoud.dataaccess.repository.pyment;

import com.masoud.dataaccess.entity.pymant.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findFirstByNameEqualsIgnoreCase(String name);
}
