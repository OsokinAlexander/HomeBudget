package ru.osokin.budget.operation;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import ru.osokin.budget.Money;
import ru.osokin.budget.MoneyAccount;
import ru.osokin.budget.entity.OperationTypeGroup;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

@ToString
@Getter
@EqualsAndHashCode
public abstract class AbstractOperation implements Operation {
    protected BigInteger id;
    protected String description;
    protected OperationTypeGroup type;
    protected MoneyAccount destinationAccount;
    protected Money amount;
    protected LocalDate date;
    protected LocalDateTime created;
    protected LocalDateTime updated;

    protected AbstractOperation(BigInteger id, LocalDate date, String description, OperationTypeGroup type,
                             MoneyAccount destinationAccount, double amount) {
        this.id = id;
        this.date = date;
        this.description = description;
        this.type = type;
        this.destinationAccount = destinationAccount;
        this.amount = new Money(amount, destinationAccount.getCurrency());
        this.created = LocalDateTime.now();
    }

    public Money getSignedAmount() {
        return amount;
    }
}
