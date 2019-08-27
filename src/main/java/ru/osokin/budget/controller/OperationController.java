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
import ru.osokin.budget.BudgetException;
import ru.osokin.budget.entity.AbstractMoneyAccount;
import ru.osokin.budget.entity.MoneyAccount;
import ru.osokin.budget.entity.Operation;
import ru.osokin.budget.entity.OperationDTO;
import ru.osokin.budget.entity.OperationType;
import ru.osokin.budget.entity.OperationTypeGroup;
import ru.osokin.budget.entity.Partner;
import ru.osokin.budget.repository.MoneyAccountRepository;
import ru.osokin.budget.repository.OperationRepository;
import ru.osokin.budget.repository.PartnerRepository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/operations")
public class OperationController {

    @Autowired
    private MoneyAccountRepository moneyAccountRepository;

    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private OperationRepository operationRepository;

    @GetMapping
    public List<OperationDTO> getOperations() {
        List<Operation> operations = operationRepository.findAll();
        return operations.stream().map(Operation::getDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OperationDTO> getOperation(@PathVariable(name = "id") BigInteger id) {
        Optional<Operation> result = operationRepository.findById(id);
        if (result.isPresent()) {
            return new ResponseEntity(result.get().getDTO(), HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<OperationDTO> createOperation(
            @Validated(OperationDTO.New.class) @RequestBody OperationDTO operationDTO) {
        Operation newOperation = getOperationFromDTO(operationDTO);
        changeAmounts(newOperation, true);
        Operation savedOperation = operationRepository.save(newOperation);
        return new ResponseEntity(savedOperation.getDTO(), HttpStatus.CREATED);
    }

    @PutMapping
    @ResponseBody
    public ResponseEntity<OperationDTO> updateOperation(
            @Validated(OperationDTO.Existing.class) @RequestBody OperationDTO operationDTO) {
        Optional<Operation> result = operationRepository.findById(operationDTO.getId());
        if (!result.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Operation operation = result.get();
        changeAmounts(operation, false);
        operation.update(getOperationFromDTO(operationDTO));
        changeAmounts(operation, true);
        Operation savedOperation = operationRepository.save(operation);
        return new ResponseEntity(savedOperation.getDTO(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteOperation(@PathVariable(name = "id") BigInteger id) {
        Optional<Operation> operationResult = operationRepository.findById(id);
        if (operationResult.isPresent()) {
            Operation operation = operationResult.get();
            changeAmounts(operation, false);
            // сохранить изменения в текущих суммах счетов / контрагентов
            operationRepository.save(operation);
            // удалить саму операцию
            operationRepository.delete(operation);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    private Operation getOperationFromDTO(OperationDTO operationDTO) {
        return new Operation(
                operationDTO.getDescription(),
                OperationType.getById(operationDTO.getTypeId()),
                operationDTO.getOperationDate(),
                getMoneyAccount(operationDTO.getSourceMoneyAccountId()),
                getMoneyAccount(operationDTO.getDestinationMoneyAccountId()),
                operationDTO.getSourceAmount(),
                operationDTO.getExchangeRate()
        );
    }

    private AbstractMoneyAccount getMoneyAccount(BigInteger id) {
        Optional<MoneyAccount> moneyAccount = moneyAccountRepository.findById(id);
        if (moneyAccount.isPresent()) {
            return moneyAccount.get();
        }
        Optional<Partner> partner = partnerRepository.findById(id);
        if (partner.isPresent()) {
            return partner.get();
        }
        throw new BudgetException("Could not find money account or partner with id: " + id);
    }

    /**
     * Скорректировать суммы счетов для фиксации операции.
     * @param operation операция
     * @param created true - операция создана, false - удалена
     */
    private void changeAmounts(Operation operation, boolean created) {
        AbstractMoneyAccount sourceMoneyAccount = operation.getSourceMoneyAccount();
        AbstractMoneyAccount destinationMoneyAccount = operation.getDestinationMoneyAccount();
        BigDecimal sourceAmount = operation.getSourceAmount().getValue();
        if (operation.getType().getGroup() != OperationTypeGroup.Exchange) {
            if (sourceMoneyAccount.getCurrencyId() != destinationMoneyAccount.getCurrencyId()) {
                throw new BudgetException("Source and destination money accounts / partners must have equal currencies");
            }
        }
        if (!created) {
            sourceAmount = sourceAmount.negate();
        }
        sourceMoneyAccount.deleteAmount(sourceAmount);
        switch (operation.getType().getGroup()) {
            case Income:
            case Expense:
            case Transfer:
                destinationMoneyAccount.addAmount(sourceAmount);
                break;
            case Exchange:
                destinationMoneyAccount.addAmount(sourceAmount.multiply(operation.getExchangeRate()));
                break;
            default:
                throw new BudgetException("Unknown operation type group " + operation.getType().getGroup());
        }
    }

}
