package ru.osokin.budget;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.osokin.budget.entity.Currency;

import java.math.BigDecimal;

import static java.math.BigDecimal.ROUND_HALF_UP;

@ToString
@EqualsAndHashCode
public class Money {
    private static final int SCALE = 2;
    private final BigDecimal value;
    private final Currency currency;

    public Money(String value, Currency currency) {
        this.value = getNumber(new BigDecimal(value));
        this.currency = currency;
    }

    public Money(Integer value, Currency currency) {
        this.value = getNumber(value == null ? 0 : value);
        this.currency = currency;
    }

    public Money(Double value, Currency currency) {
        this.value = getNumber(value == null ? 0 : value);
        this.currency = currency;
    }

    public Money(BigDecimal value, Currency currency) {
        this.value = getNumber(value);
        this.currency = currency;
    }

    public BigDecimal getValue() {
        return value;
    }

    public Money negate() {
        return new Money(value.negate(), currency);
    }

    public Money add(Money addValue) {
        return new Money(value.add(addValue.value), currency);
    }

    public Currency getCurrency() {
        return this.currency;
    }

    private BigDecimal getNumber(double number) {
        return new BigDecimal(number).setScale(SCALE, ROUND_HALF_UP);
    }

    private BigDecimal getNumber(BigDecimal number) {
        return number.setScale(SCALE, ROUND_HALF_UP);
    }
}
