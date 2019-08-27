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
        this(name, type, currency, startAmount, startAmount, false);
    }

    public MoneyAccount(MoneyAccountDTO dto) {
        this(dto.getName(), MoneyAccountType.getById(dto.getTypeId()),
                Currency.getById(dto.getCurrencyId()), dto.getStartAmount(), dto.getCurrentAmount(), dto.getArchived());
    }

    private MoneyAccount(String name, MoneyAccountType type, Currency currency,
                         BigDecimal startAmount, BigDecimal currentAmount, Boolean archived) {
        this.name = name;
        this.typeId = getTypeId(type);
        this.currencyId = currency.getNumber();
        this.startAmount = startAmount;
        this.currentAmount = currentAmount;
        this.archived = archived;
    }

    public MoneyAccountDTO getDTO() {
        return new MoneyAccountDTO()
                .setId(id)
                .setName(name)
                .setCurrencyId(currencyId)
                .setTypeId(typeId)
                .setStartAmount(startAmount)
                .setCurrentAmount(currentAmount)
                .setArchived(archived);
    }

    private static Integer getTypeId(MoneyAccountType type) {
        if (type.getGroup() == MoneyAccountType.MoneyAccountTypeGroup.Partners) {
            throw new BudgetException("MoneyAccount could not be a partner");
        }
        return type.getId();
    }

    public MoneyAccount update(MoneyAccountDTO dto, boolean hasOperations) {
        this.name = dto.getName();
        this.archived = dto.getArchived();
        if (!hasOperations) {
            this.currencyId = dto.getCurrencyId();
            this.startAmount = dto.getStartAmount();
            this.currentAmount = dto.getCurrentAmount();
        }
        return this;
    }

}
