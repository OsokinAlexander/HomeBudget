package ru.osokin.budget.operation;

import ru.osokin.budget.Money;
import ru.osokin.budget.MoneyAccount;
import ru.osokin.budget.entity.OperationTypeGroup;

public interface Operation {
    Money getAmount();
    Money getSignedAmount();
    MoneyAccount getDestinationAccount();
    OperationTypeGroup getType();
    String toString();
}
