package ru.osokin.budget;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import ru.osokin.budget.entity.Operation;
import ru.osokin.budget.entity.OperationType;
import ru.osokin.budget.repository.MoneyAccountRepository;
import ru.osokin.budget.repository.OperationRepository;

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

    @Test
    @Commit
    public void createExpenseOperation() {
        Operation operation = new Operation()
                .setOperationDate(LocalDate.now())
                .setDescription("Продукты")
                .setType(OperationType.Food)
                .setSourceMoneyAccount(moneyAccountRepository.findById(BigInteger.valueOf(1)).get())
                .setSourceAmount(BigDecimal.valueOf(123.45));
        operation = operationRepository.save(operation);
        assertNotNull(operation.getId());
        assertTrue(operationRepository.findById(operation.getId()).isPresent());
    }

}
