package ru.osokin.budget.operation;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import ru.osokin.budget.Money;
import ru.osokin.budget.MoneyAccount;
import ru.osokin.budget.entity.OperationTypeGroup;

import java.math.BigInteger;
import java.time.LocalDate;

@ToString(callSuper = true)
@Getter
@EqualsAndHashCode(callSuper = true)
public class ExpenseOperation extends AbstractOperation {
    public ExpenseOperation(BigInteger id, LocalDate date, String description, MoneyAccount destinationAccount, double amount) {
        super(id, date, description, OperationTypeGroup.Expense, destinationAccount, amount);
    }

    @Override
    public Money getSignedAmount() {
        return amount.negate();
    }
}
