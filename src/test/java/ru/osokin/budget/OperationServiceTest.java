package ru.osokin.budget;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import ru.osokin.budget.entity.OperationDTO;
import ru.osokin.budget.entity.OperationType;
import ru.osokin.budget.service.OperationService;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import(OperationService.class)
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class OperationServiceTest {

    @Autowired
    private OperationService operationService;

    @Test
    @Commit
    public void createIncomeOperation() {
        OperationDTO result = operationService.createOperation(
                new OperationDTO()
                    .setDescription("Created operation")
                    .setOperationDate(LocalDate.now())
                    .setTypeId(OperationType.Income.getId())
                    .setSourceAmount(BigDecimal.valueOf(80450.33))
                    .setSourceMoneyAccountId(BigInteger.valueOf(1))
                    .setDestinationMoneyAccountId(BigInteger.valueOf(2))
                    .setExchangeRate(BigDecimal.ONE)
        );
        System.out.println(result);
    }

}
