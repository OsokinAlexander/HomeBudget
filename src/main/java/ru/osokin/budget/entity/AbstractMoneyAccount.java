package ru.osokin.budget.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.osokin.budget.Currency;
import ru.osokin.budget.MoneyAccountType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "money_account")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class AbstractMoneyAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MoneyAccount_gen")
    @SequenceGenerator(name="MoneyAccount_gen", sequenceName = "MoneyAccount_seq", allocationSize = 1)
    @Column(name = "ID", updatable = false, nullable = false)
    protected BigInteger id;

    @Version
    protected Long version;

    @Column(name = "account_name")
    protected String name;

    @Column
    protected BigDecimal startAmount;

    @Column
    protected BigDecimal currentAmount;

    @Column
    protected Integer currencyId;

    @Column
    @CreationTimestamp
    protected LocalDateTime created;

    @Column
    @UpdateTimestamp
    protected LocalDateTime updated;

    @Column
    protected Integer typeId;

    @Column
    protected Boolean archived = false;


    public MoneyAccountType getType() {
        return MoneyAccountType.getById(typeId);
    }

    public Currency getCurrency() {
        return Currency.getById(currencyId);
    }

    public void archive() {
        archived = true;
    }

    public void addAmount(BigDecimal amount) {
        currentAmount = currentAmount.add(amount);
    }

    public void deleteAmount(BigDecimal amount) {
        currentAmount = currentAmount.add(amount.negate());
    }

}
