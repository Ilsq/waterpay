package org.coworking.ilsq.repository;

import org.coworking.ilsq.entity.Payment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaymentRepository extends CrudRepository<Payment, Integer> {
    List<Payment> findPaymentsByOrderaId(int id);

    @Query("SELECT sum (p.amount) FROM Payment p WHERE p.ordera_id = :id")
    int amountSum(@Param("id") int id);
}