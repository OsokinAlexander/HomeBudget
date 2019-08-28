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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.osokin.budget.entity.MoneyAccount;
import ru.osokin.budget.entity.MoneyAccountDTO;
import ru.osokin.budget.entity.Operation;
import ru.osokin.budget.repository.MoneyAccountRepository;
import ru.osokin.budget.repository.OperationRepository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/money-accounts")
public class MoneyAccountController {

    @Autowired
    private MoneyAccountRepository moneyAccountRepository;

    @Autowired
    private OperationRepository operationRepository;

    @GetMapping
    public List<MoneyAccountDTO> getAccounts(@RequestParam(required = false) Boolean archived) {
        List<MoneyAccount> moneyAccounts;
        if (archived == null) {
            moneyAccounts = moneyAccountRepository.findAll();
        } else {
            moneyAccounts = moneyAccountRepository.findAllByArchived(archived);
        }
        return moneyAccounts.stream().map(MoneyAccount::getDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MoneyAccountDTO> getAccount(@PathVariable(name = "id") BigInteger id,
                                                      @RequestParam(required = false) Boolean archived) {
        Optional<MoneyAccount> result;
        if (archived == null) {
            result = moneyAccountRepository.findById(id);
        } else {
            result = moneyAccountRepository.findByIdAndArchived(id, archived);
        }
        if (result.isPresent()) {
            return new ResponseEntity(result.get().getDTO(), HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<MoneyAccountDTO> createAccount(
            @Validated(MoneyAccountDTO.New.class) @RequestBody MoneyAccountDTO moneyAccountDTO) {
        MoneyAccount savedMoneyAccount = moneyAccountRepository.save(new MoneyAccount(moneyAccountDTO));
        return new ResponseEntity(savedMoneyAccount.getDTO(), HttpStatus.CREATED);
    }

    @PutMapping
    @ResponseBody
    public ResponseEntity<MoneyAccountDTO> updateAccount(
            @Validated(MoneyAccountDTO.Existing.class) @RequestBody MoneyAccountDTO moneyAccountDTO) {
        Optional<MoneyAccount> result = moneyAccountRepository.findById(moneyAccountDTO.getId());
        if (!result.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        MoneyAccount moneyAccount = result.get();
        moneyAccount.update(moneyAccountDTO, hasOperations(moneyAccount));
        MoneyAccount savedMoneyAccount = moneyAccountRepository.save(moneyAccount);
        return new ResponseEntity(savedMoneyAccount.getDTO(), HttpStatus.OK);
    }

    /**
     * Удалить счёт, если с ним нет связанных операций, иначе переместить в архив.
     * @param id - идентификатор счёта
     * @return статус удаления
     */
    @DeleteMapping("/{id}")
    public ResponseEntity deleteAccount(@PathVariable(name = "id") BigInteger id) {
        Optional<MoneyAccount> moneyAccountResult = moneyAccountRepository.findById(id);
        if (moneyAccountResult.isPresent()) {
            MoneyAccount moneyAccount = moneyAccountResult.get();
            if (hasOperations(moneyAccount)) {
                moneyAccount.archive();
                moneyAccountRepository.save(moneyAccount);
            } else {
                moneyAccountRepository.delete(moneyAccount);
            }
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    private boolean hasOperations(MoneyAccount moneyAccount) {
        Operation operation = operationRepository
                .findFirstBySourceMoneyAccountOrDestinationMoneyAccount(moneyAccount, moneyAccount);
        return operation != null;
    }

}
