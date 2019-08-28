package ru.osokin.budget.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class OperationDTO {

    @Null(groups = {MoneyAccountDTO.New.class})
    @NotNull(groups = {MoneyAccountDTO.Existing.class})
    private BigInteger id;

    @NotNull(groups = {MoneyAccountDTO.New.class, MoneyAccountDTO.Existing.class})
    private String description;

    @NotNull(groups = {MoneyAccountDTO.New.class, MoneyAccountDTO.Existing.class})
    LocalDate operationDate;

    @NotNull(groups = {MoneyAccountDTO.New.class, MoneyAccountDTO.Existing.class})
    private Integer typeId;

    @NotNull(groups = {MoneyAccountDTO.New.class, MoneyAccountDTO.Existing.class})
    private BigInteger sourceMoneyAccountId;

    @NotNull(groups = {MoneyAccountDTO.New.class, MoneyAccountDTO.Existing.class})
    private BigInteger destinationMoneyAccountId;

    @NotNull(groups = {MoneyAccountDTO.New.class, MoneyAccountDTO.Existing.class})
    private BigDecimal sourceAmount;

    @NotNull(groups = {MoneyAccountDTO.New.class, MoneyAccountDTO.Existing.class})
    private BigDecimal exchangeRate;

    public interface New {}
    public interface Existing {}

}
