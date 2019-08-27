package ru.osokin.budget.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.osokin.budget.entity.Partner;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, BigInteger> {

    List<Partner> findAllByArchived(Boolean archived);

    Optional<Partner> findByIdAndArchived(BigInteger id, Boolean archived);

}
