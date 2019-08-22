package ru.osokin.budget;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import ru.osokin.budget.entity.Currency;
import ru.osokin.budget.entity.MoneyAccountType;
import ru.osokin.budget.repository.MoneyAccountRepository;
import ru.osokin.budget.entity.MoneyAccount;
import ru.osokin.budget.entity.Partner;
import ru.osokin.budget.repository.PartnerRepository;

import java.math.BigDecimal;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class MoneyAccountTest {

    @Autowired
    private MoneyAccountRepository moneyAccountRepository;

    @Autowired
    private PartnerRepository partnerRepository;

    @Test
    @Commit
    public void createAccount() {
        MoneyAccount moneyAccount = new MoneyAccount("Кошелёк", MoneyAccountType.Cash, Currency.RUB, BigDecimal.ZERO);
        moneyAccount = moneyAccountRepository.save(moneyAccount);
        assertNotNull(moneyAccount.getId());
        assertTrue(moneyAccountRepository.findById(moneyAccount.getId()).isPresent());
    }

    @Test
    @Commit
    public void createPartner() {
        Partner partner = new Partner("Пятёрочка", Currency.RUB);
        partner = partnerRepository.save(partner);
        assertNotNull(partner.getId());
        assertTrue(partnerRepository.findById(partner.getId()).isPresent());
    }

}
