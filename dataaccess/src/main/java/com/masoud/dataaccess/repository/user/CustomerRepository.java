package com.masoud.dataaccess.repository.user;

import com.masoud.dataaccess.entity.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
