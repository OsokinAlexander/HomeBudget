package ru.osokin.budget;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import ru.osokin.budget.entity.Currency;

import java.math.BigInteger;
import java.time.LocalDateTime;

@ToString
@Getter
@EqualsAndHashCode
public class MoneyAccount {
    private BigInteger id;
    private String name;
    private Money startAmount;
    private Money currentAmount;
    private Currency currency;
    private LocalDateTime created;
    private MoneyAccountType type;

    public MoneyAccount(BigInteger id, String name, Money startAmount, Currency currency, MoneyAccountType type) {
        this.id = id;
        this.name = name;
        this.startAmount = startAmount;
        this.currency = currency;
        this.type = type;
        this.currentAmount = startAmount;
        this.created = LocalDateTime.now();
    }

    public void change(Money amount) {
        currentAmount = currentAmount.add(amount);
    }
}
