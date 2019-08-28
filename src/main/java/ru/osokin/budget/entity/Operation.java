package ru.osokin.budget.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.osokin.budget.BudgetException;
import ru.osokin.budget.MoneyAccountType;
import ru.osokin.budget.OperationType;
import ru.osokin.budget.dto.OperationDTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
@NoArgsConstructor
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Operation_gen")
    @SequenceGenerator(name="Operation_gen", sequenceName = "Operation_seq", allocationSize = 1)
    @Column(name = "ID", updatable = false, nullable = false)
    private BigInteger id;

    @Version
    private Long version;

    @Column
    private String description;

    @Column
    @CreationTimestamp
    private LocalDateTime created;

    @Column
    @UpdateTimestamp
    private LocalDateTime updated;

    @Column
    private Integer typeId;

    @Column
    private LocalDate operationDate;

    @ManyToOne
    @JoinColumn(name = "source_account_id")
    private AbstractMoneyAccount sourceMoneyAccount;

    @ManyToOne
    @JoinColumn(name = "destination_account_id")
    private AbstractMoneyAccount destinationMoneyAccount;

    @Column
    private BigDecimal sourceAmount;

    @Column
    private BigDecimal exchangeRate;

    public Operation(String description, OperationType type, LocalDate operationDate, AbstractMoneyAccount sourceMoneyAccount, AbstractMoneyAccount destinationMoneyAccount, BigDecimal sourceAmount) {
        this(description, type, operationDate, sourceMoneyAccount, destinationMoneyAccount, sourceAmount, BigDecimal.ONE);
    }

    public Operation(String description, OperationType type, LocalDate operationDate, AbstractMoneyAccount sourceMoneyAccount, AbstractMoneyAccount destinationMoneyAccount, BigDecimal sourceAmount, BigDecimal exchangeRate) {
        if (sourceMoneyAccount.getType().equals(destinationMoneyAccount.getType())
                && sourceMoneyAccount.getType().getGroup() == MoneyAccountType.MoneyAccountTypeGroup.Partners) {
            throw new BudgetException("Operation could not be between partner and partner");
        }
        this.description = description;
        this.typeId = type.getId();
        this.operationDate = operationDate;
        this.sourceMoneyAccount = sourceMoneyAccount;
        this.destinationMoneyAccount = destinationMoneyAccount;
        this.sourceAmount = sourceAmount;
        this.exchangeRate = exchangeRate;
    }

    public OperationType getType() {
        return OperationType.getById(typeId);
    }

    public OperationDTO getDTO() {
        return new OperationDTO()
                .setId(id)
                .setDescription(description)
                .setTypeId(typeId)
                .setOperationDate(operationDate)
                .setSourceMoneyAccountId(sourceMoneyAccount.getId())
                .setSourceAmount(sourceAmount)
                .setDestinationMoneyAccountId(destinationMoneyAccount.getId())
                .setExchangeRate(exchangeRate);
    }

    public Operation update(Operation newOperaton) {
        this.description = newOperaton.description;
        this.typeId = newOperaton.typeId;
        this.operationDate = newOperaton.operationDate;
        this.sourceMoneyAccount = newOperaton.sourceMoneyAccount;
        this.destinationMoneyAccount = newOperaton.destinationMoneyAccount;
        this.sourceAmount = newOperaton.sourceAmount;
        this.exchangeRate = newOperaton.exchangeRate;
        return this;
    }

}
