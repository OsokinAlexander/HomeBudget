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
        this(name, getTypeId(type), currency.getNumber(), startAmount);
    }

    public MoneyAccount(MoneyAccountDTO dto) {
        this(dto.getName(), MoneyAccountType.getById(dto.getTypeId()),
                Currency.getById(dto.getCurrencyId()), dto.getStartAmount());
    }

    private MoneyAccount(String name, Integer typeId, Integer currencyId, BigDecimal startAmount) {
        this.name = name;
        this.typeId = typeId;
        this.currencyId = currencyId;
        this.startAmount = startAmount;
        this.currentAmount = startAmount;
    }

    public MoneyAccountDTO getDTO() {
        return new MoneyAccountDTO()
                .setId(id)
                .setName(name)
                .setCurrencyId(currencyId)
                .setTypeId(typeId)
                .setStartAmount(startAmount)
                .setCurrentAmount(currentAmount);
    }

    private static Integer getTypeId(MoneyAccountType type) {
        if (type.getGroup() == MoneyAccountType.MoneyAccountTypeGroup.Partners) {
            throw new BudgetException("MoneyAccount could not be a partner");
        }
        return type.getId();
    }

}
