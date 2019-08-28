package ru.osokin.budget;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.osokin.budget.entity.Operation;
import ru.osokin.budget.repository.MoneyAccountRepository;
import ru.osokin.budget.repository.OperationRepository;
import ru.osokin.budget.repository.PartnerRepository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class OperationTest {

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private MoneyAccountRepository moneyAccountRepository;

    @Autowired
    private PartnerRepository partnerRepository;

    @Test
//    @Commit
    public void createExpenseOperation() {
        Operation operation = new Operation(
                "Продукты",
                OperationType.Food,
                LocalDate.now(),
                moneyAccountRepository.findById(BigInteger.valueOf(1)).get(),
                partnerRepository.findById(BigInteger.valueOf(2)).get(),
                BigDecimal.valueOf(123.45)
        );
        operation = operationRepository.save(operation);
        assertNotNull(operation.getId());
        assertTrue(operationRepository.findById(operation.getId()).isPresent());
    }

    @Test
    public void createOperation() {

    }

}
