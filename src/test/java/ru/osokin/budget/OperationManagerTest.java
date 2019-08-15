package ru.osokin.budget;

import org.junit.Before;
import org.junit.Test;
import ru.osokin.budget.operation.*;

import java.math.BigInteger;
import java.time.LocalDate;

import static junit.framework.TestCase.assertEquals;
import static ru.osokin.budget.entity.Currency.RUB;
import static ru.osokin.budget.entity.Currency.USD;

public class OperationManagerTest {
    private MoneyAccount aliceAccount;
    private MoneyAccount bobAccount;
    private MoneyAccount chrisAccount;
    private OperationManager operationManager;

    @Before
    public void init() {
        aliceAccount = new MoneyAccount(BigInteger.valueOf(1),"Alice account", new Money(0, RUB),
                RUB, new MoneyAccountType(BigInteger.valueOf(1), "Credit card"));
        bobAccount = new MoneyAccount(BigInteger.valueOf(2),"Bob account", new Money(0, RUB),
                RUB, new MoneyAccountType(BigInteger.valueOf(2), "Cash"));
        chrisAccount = new MoneyAccount(BigInteger.valueOf(3),"Chris account", new Money(0, USD),
                RUB, new MoneyAccountType(BigInteger.valueOf(1), "Credit card"));

        operationManager = new OperationManager();
    }

    @Test
    public void addOneAccountOperations() {
        operationManager.addOperation(
                new IncomeOperation(null, LocalDate.now(), "Salary", aliceAccount, 10000));
        assertEquals(new Money(10000, RUB), aliceAccount.getCurrentAmount());
        operationManager.addOperation(
                new ExpenseOperation(null,LocalDate.now(), "Bread", aliceAccount, 62.30));
        assertEquals(new Money(9937.70, RUB), aliceAccount.getCurrentAmount());
        operationManager.addOperation(
                new ExpenseOperation(null,LocalDate.now(), "Return", aliceAccount, -12.30));
        assertEquals(new Money(9950, RUB), aliceAccount.getCurrentAmount());
        System.out.println(operationManager);
    }

    @Test
    public void addTransferOperations() {
        operationManager.addOperation(
                new TransferOperation(null,LocalDate.now(), "Transfer 1",
                        aliceAccount, bobAccount, 300.53)
        );
        operationManager.addOperation(
                new TransferOperation(null,LocalDate.now(), "Transfer 2",
                        aliceAccount, bobAccount, 299.47)
        );
        assertEquals(new Money(600, RUB), bobAccount.getCurrentAmount());
        assertEquals(new Money(-600, RUB), aliceAccount.getCurrentAmount());

        operationManager.addOperation(
                new TransferOperation(null,LocalDate.now(), "Transfer 3",
                        bobAccount, aliceAccount, 300.53)
        );
        assertEquals(new Money(299.47, RUB), bobAccount.getCurrentAmount());
        assertEquals(new Money(-299.47, RUB), aliceAccount.getCurrentAmount());

        operationManager.addOperation(
                new TransferOperation(null,LocalDate.now(), "Transfer 4",
                        bobAccount, bobAccount, 10000)
        );
        assertEquals(new Money(299.47, RUB), bobAccount.getCurrentAmount());
        System.out.println(operationManager);
    }

    @Test
    public void addExchangeOperations() {
        operationManager.addOperation(
                new ExchangeOperation(null,LocalDate.now(), "Exchange 1", aliceAccount, chrisAccount,
                        6000, 100)
        );
        operationManager.addOperation(
                new ExchangeOperation(null,LocalDate.now(), "Exchange 2", chrisAccount, aliceAccount,
                        10, 600)
        );
        assertEquals(new Money(-5400, RUB), aliceAccount.getCurrentAmount());
        assertEquals(new Money(90, USD), chrisAccount.getCurrentAmount());
        System.out.println(operationManager);
    }
}
