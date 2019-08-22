package ru.osokin.budget.entity;

import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue("partner")
@ToString
@Accessors(chain = true)
@NoArgsConstructor
public class Partner extends AbstractMoneyAccount {

    public Partner(String name, Currency currency) {
        this.name = name;
        this.typeId = MoneyAccountType.Partners.getId();
        this.currencyId = currency.getNumber();
        this.startAmount = BigDecimal.ZERO;
        this.currentAmount = startAmount;
    }
    
}
