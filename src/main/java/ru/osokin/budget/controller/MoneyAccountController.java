package ru.osokin.budget.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.osokin.budget.entity.MoneyAccount;
import ru.osokin.budget.entity.MoneyAccountDTO;
import ru.osokin.budget.repository.MoneyAccountRepository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/money-accounts")
public class MoneyAccountController {

    @Autowired
    private MoneyAccountRepository moneyAccountRepository;

    @GetMapping
    public List<MoneyAccountDTO> getAccounts() {
        return moneyAccountRepository.findAll().stream().map(MoneyAccount::getDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public MoneyAccountDTO getAccount(@PathVariable(name = "id") BigInteger id) {
        Optional<MoneyAccount> result = moneyAccountRepository.findById(id);
        if (result.isPresent()) {
            return result.get().getDTO();
        }
        return null;
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<MoneyAccountDTO> createAccount(
            @Validated(MoneyAccountDTO.New.class) @RequestBody MoneyAccountDTO moneyAccountDTO) {
        MoneyAccount savedMoneyAccount = moneyAccountRepository.save(new MoneyAccount(moneyAccountDTO));
        return new ResponseEntity(savedMoneyAccount.getDTO(), HttpStatus.OK);
    }

    @PutMapping
    @ResponseBody
    public ResponseEntity<MoneyAccountDTO> updateAccount(
            @Validated(MoneyAccountDTO.Existing.class) @RequestBody MoneyAccountDTO moneyAccountDTO) {
        Optional<MoneyAccount> result = moneyAccountRepository.findById(moneyAccountDTO.getId());
        if (!result.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        MoneyAccount savedMoneyAccount = moneyAccountRepository.save(new MoneyAccount(moneyAccountDTO));
        return new ResponseEntity(savedMoneyAccount.getDTO(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable(name = "id") BigInteger id) {
        Optional<MoneyAccount> result = moneyAccountRepository.findById(id);
        if (result.isPresent()) {
            moneyAccountRepository.delete(result.get());
        }
        return;
    }

}
