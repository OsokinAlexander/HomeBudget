package ru.osokin.budget.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class MoneyAccountDTO {

    @Null(groups = {New.class})
    @NotNull(groups = {Existing.class})
    private BigInteger id;

    @NotNull(groups = {New.class, Existing.class})
    private String name;

    @NotNull(groups = {New.class, Existing.class})
    private BigDecimal startAmount;

    @Null(groups = {New.class})
    @NotNull(groups = {Existing.class})
    private BigDecimal currentAmount;

    @NotNull(groups = {New.class, Existing.class})
    private Integer currencyId;

    @NotNull(groups = {New.class, Existing.class})
    private Integer typeId;

    @NotNull(groups = {Existing.class})
    private Boolean archived;

    public interface New {}
    public interface Existing {}

}
