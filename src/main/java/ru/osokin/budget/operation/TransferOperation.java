package ru.osokin.budget.operation;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import ru.osokin.budget.MoneyAccount;
import ru.osokin.budget.entity.OperationTypeGroup;

import java.math.BigInteger;
import java.time.LocalDate;

@ToString(callSuper = true)
@Getter
@EqualsAndHashCode(callSuper = true)
public class TransferOperation extends AbstractOperation {
    private MoneyAccount sourceAccount;

    public TransferOperation(BigInteger id, LocalDate date, String description, MoneyAccount sourceAccount,
                             MoneyAccount destinationAccount, double amount) {
        this(id, date, description, OperationTypeGroup.Transfer, sourceAccount, destinationAccount, amount);
    }

    protected TransferOperation(BigInteger id, LocalDate date, String description, OperationTypeGroup type,
                                MoneyAccount sourceAccount, MoneyAccount destinationAccount, double amount) {
        super(id, date, description, type, destinationAccount, amount);
        this.sourceAccount = sourceAccount;
    }
}
