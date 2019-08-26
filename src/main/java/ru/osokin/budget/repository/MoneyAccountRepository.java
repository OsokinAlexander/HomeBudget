package ru.osokin.budget.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.osokin.budget.entity.MoneyAccount;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public interface MoneyAccountRepository extends JpaRepository<MoneyAccount, BigInteger> {

    List<MoneyAccount> findAllByArchived(Boolean archived);

    Optional<MoneyAccount> findByIdAndArchived(BigInteger id, Boolean archived);

}
