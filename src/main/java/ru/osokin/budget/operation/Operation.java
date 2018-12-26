package ru.osokin.budget.operation;

import ru.osokin.budget.Money;
import ru.osokin.budget.MoneyAccount;

public interface Operation {
    Money getAmount();
    Money getSignedAmount();
    MoneyAccount getDestinationAccount();
    OperationType getType();
    String toString();
}
