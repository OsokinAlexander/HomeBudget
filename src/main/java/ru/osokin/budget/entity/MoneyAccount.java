package ru.osokin.budget.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.osokin.budget.Money;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Entity
@Accessors(chain = true)
@NoArgsConstructor
public class MoneyAccount {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MoneyAccount_gen")
    @SequenceGenerator(name="MoneyAccount_gen", sequenceName = "MoneyAccount_seq")
    @Column(name = "ID", updatable = false, nullable = false)
    private BigInteger id;

    @Column
    private BigDecimal startAmount;

    @Column
    private BigDecimal currentAmount;

    @Column
    private Integer currencyId;

    @Getter
    @Column
    private LocalDateTime created;

    @Getter
    @Column
    private LocalDateTime updated;

    @Column(name = "account_type")
    private Integer typeId;

    public MoneyAccountType getType() {
        return MoneyAccountType.getById(currencyId);
    }

    public MoneyAccount setType(MoneyAccountType type) {
        typeId = type.getId();
        return this;
    }

    public Currency getCurrency() {
        return Currency.getById(currencyId);
    }

    public MoneyAccount setCurrency(Currency currency) {
        currencyId = currency.getNumber();
        return this;
    }

    public Money getStartAmount() {
        return new Money(startAmount, getCurrency());
    }

    public MoneyAccount setStartAmount(Money startAmount) {
        this.startAmount = startAmount.getValue();
        return this;
    }

    public Money getCurrentAmount() {
        return new Money(currentAmount, getCurrency());
    }

    public MoneyAccount setCurrentAmount(Money currentAmount) {
        this.currentAmount = currentAmount.getValue();
        return this;
    }
}
