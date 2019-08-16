package ru.osokin.budget.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.osokin.budget.Money;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class Operation {

    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Operation_gen")
    @SequenceGenerator(name="Operation_gen", sequenceName = "Operation_seq", allocationSize = 1)
    @Column(name = "ID", updatable = false, nullable = false)
    private BigInteger id;

    @Column
    private String description;

    @Setter(AccessLevel.NONE)
    @Column
    @CreationTimestamp
    private LocalDateTime created;

    @Setter(AccessLevel.NONE)
    @Column
    @UpdateTimestamp
    private LocalDateTime updated;

    @Setter(AccessLevel.NONE)
    @Column
    private Integer typeId;

    @Column
    private LocalDate operationDate;

    @ManyToOne
    @JoinColumn(name = "source_account_id")
    private MoneyAccount sourceMoneyAccount;

    @ManyToOne
    @JoinColumn(name = "destination_account_id")
    private MoneyAccount destinationMoneyAccount;

    @Column
    private BigDecimal sourceAmount;

    @Column
    private BigDecimal exchangeRate;

    public Money getSourceAmount() {
        return new Money(sourceAmount, sourceMoneyAccount.getCurrency());
    }

    public Money getDestinationAmount() {
        return new Money(sourceAmount.multiply(exchangeRate), destinationMoneyAccount.getCurrency());
    }

    public Operation setType(OperationType type) {
        this.typeId = type.getId();
        return this;
    }

}
