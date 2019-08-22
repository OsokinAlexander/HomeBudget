package ru.osokin.budget.entity;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import ru.osokin.budget.BudgetException;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue("moneyAccount")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
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

}
