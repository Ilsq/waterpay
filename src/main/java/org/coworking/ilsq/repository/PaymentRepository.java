package org.coworking.ilsq.repository;

import org.coworking.ilsq.entity.Payment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends CrudRepository<Payment, Integer> {
    List<Payment> findPaymentsByOrderaId(int id);

    @Query("SELECT sum (p.amount) FROM Payment p WHERE p.orderaId = :id")
    Optional<Integer> amountSum(@Param("id") int id);

    Integer countPaymentsByPayerAndOrderaId(String payer, int id);
}