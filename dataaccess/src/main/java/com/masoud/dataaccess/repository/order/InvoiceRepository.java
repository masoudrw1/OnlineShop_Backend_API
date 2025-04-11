package com.masoud.dataaccess.repository.order;

import com.masoud.dataaccess.entity.order.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice>findAllByUser_Id(Long id);

}
