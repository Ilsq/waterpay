package org.coworking.ilsq.repository;

import org.coworking.ilsq.entity.Payment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PaymentRepository extends CrudRepository<Payment, Integer> {
    List<Payment> findAll();

}