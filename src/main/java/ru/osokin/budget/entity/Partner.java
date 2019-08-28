package ru.osokin.budget.entity;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.math.BigInteger;

@Entity
@DiscriminatorValue("partner")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
public class Partner extends AbstractMoneyAccount {

    public Partner(String name, Currency currency) {
        this(null, name, currency, BigDecimal.ZERO, BigDecimal.ZERO, false);
    }

    public Partner(PartnerDTO partnerDTO) {
        this(partnerDTO.getId(), partnerDTO.getName(), Currency.getById(partnerDTO.getCurrencyId()),
                partnerDTO.getStartAmount(), partnerDTO.getCurrentAmount(), partnerDTO.getArchived());
    }

    private Partner(BigInteger id, String name, Currency currency,
                    BigDecimal startAmount, BigDecimal currentAmount, Boolean archived) {
        this.id = id;
        this.name = name;
        this.typeId = MoneyAccountType.Partners.getId();
        this.currencyId = currency.getId();
        this.startAmount = startAmount;
        this.currentAmount = currentAmount;
        this.archived = archived;
    }

    public PartnerDTO getDTO() {
        return (PartnerDTO) new PartnerDTO()
                .setId(id)
                .setName(name)
                .setCurrencyId(currencyId)
                .setTypeId(typeId)
                .setStartAmount(startAmount)
                .setArchived(archived);
    }

    public Partner update(PartnerDTO dto, boolean hasOperations) {
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
