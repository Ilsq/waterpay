package org.coworking.ilsq.repository;

import org.coworking.ilsq.entity.Levy;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.List;

public interface LevyRepository extends CrudRepository<Levy, Integer> {
    Levy findByDate(Date date);

    List<Levy> findAll();
}