package ru.osokin.budget.entity;

import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import ru.osokin.budget.BudgetException;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue("moneyAccount")
@ToString
@Accessors(chain = true)
@NoArgsConstructor
public class MoneyAccount extends AbstractMoneyAccount {

    public MoneyAccount(String name, MoneyAccountType type, Currency currency, BigDecimal startAmount) {
        if (type.getGroup() == MoneyAccountType.MoneyAccountTypeGroup.Partners) {
            throw new BudgetException("MoneyAccount could not be a partner");
        }
        this.name = name;
        this.typeId = type.getId();
        this.currencyId = currency.getNumber();
        this.startAmount = startAmount;
        this.currentAmount = startAmount;
    }

    public MoneyAccountType getType() {
        return MoneyAccountType.getById(currencyId);
    }

//    public MoneyAccount setType(MoneyAccountType type) {
//        typeId = type.getId();
//        return this;
//    }

    public Currency getCurrency() {
        return Currency.getById(currencyId);
    }

//    public MoneyAccount setCurrency(Currency currency) {
//        currencyId = currency.getNumber();
//        return this;
//    }
//
//    public Money getStartAmount() {
//        return new Money(startAmount, getCurrency());
//    }
//
//    public MoneyAccount setStartAmount(Money startAmount) {
//        this.startAmount = startAmount.getValue();
//        return this;
//    }
//
//    public Money getCurrentAmount() {
//        return new Money(currentAmount, getCurrency());
//    }
//
//    public MoneyAccount setCurrentAmount(Money currentAmount) {
//        this.currentAmount = currentAmount.getValue();
//        return this;
//    }
}
