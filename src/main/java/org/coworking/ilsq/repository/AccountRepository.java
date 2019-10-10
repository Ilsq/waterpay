package org.coworking.ilsq.repository;

import org.coworking.ilsq.entity.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Integer> {

    Account findByLogin(String login);
}