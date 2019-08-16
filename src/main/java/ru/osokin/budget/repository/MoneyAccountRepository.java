package ru.osokin.budget.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.osokin.budget.entity.MoneyAccount;

import java.math.BigInteger;

@Repository
public interface MoneyAccountRepository extends JpaRepository<MoneyAccount, BigInteger> {

}
