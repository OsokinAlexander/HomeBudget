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
public class ExchangeOperation extends TransferOperation {
    private Money exchangeAmount;

    public ExchangeOperation(BigInteger id, LocalDate date, String description, MoneyAccount sourceAccount,
                             MoneyAccount destinationAccount, double exchangeAmount, double resultAmount) {
        super(id, date, description, OperationTypeGroup.Exchange, sourceAccount, destinationAccount, resultAmount);
        this.exchangeAmount = new Money(exchangeAmount, sourceAccount.getCurrency());
    }
}
