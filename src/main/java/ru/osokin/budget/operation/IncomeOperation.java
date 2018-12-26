package ru.osokin.budget.operation;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import ru.osokin.budget.Money;
import ru.osokin.budget.MoneyAccount;

import java.math.BigInteger;
import java.time.LocalDate;

@ToString(callSuper = true)
@Getter
@EqualsAndHashCode(callSuper = true)
public class IncomeOperation extends AbstractOperation {
    public IncomeOperation(BigInteger id, LocalDate date, String description, MoneyAccount destinationAccount, double amount) {
        super(id, date, description, OperationType.INCOME, destinationAccount, amount);
    }
}
