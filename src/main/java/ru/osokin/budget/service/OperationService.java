package ru.osokin.budget.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.osokin.budget.BudgetException;
import ru.osokin.budget.entity.AbstractMoneyAccount;
import ru.osokin.budget.entity.MoneyAccount;
import ru.osokin.budget.entity.Operation;
import ru.osokin.budget.entity.OperationDTO;
import ru.osokin.budget.entity.OperationType;
import ru.osokin.budget.entity.Partner;
import ru.osokin.budget.repository.MoneyAccountRepository;
import ru.osokin.budget.repository.OperationRepository;
import ru.osokin.budget.repository.PartnerRepository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.osokin.budget.entity.OperationTypeGroup.Exchange;

@Service
public class OperationService {

    @Autowired
    private MoneyAccountRepository moneyAccountRepository;

    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private OperationRepository operationRepository;

    public List<OperationDTO> getOperations() {
        List<Operation> operations = operationRepository.findAll();
        return operations.stream().map(Operation::getDTO).collect(Collectors.toList());
    }

    public Optional<OperationDTO> getOperation(BigInteger id) {
        Optional<Operation> resultOperation = operationRepository.findById(id);
        OperationDTO operationDTO = null;
        if (resultOperation.isPresent()) {
            operationDTO = resultOperation.get().getDTO();
        }
        return Optional.ofNullable(operationDTO);
    }

    public OperationDTO createOperation(OperationDTO operationDTO) {
        Operation newOperation = getOperationFromDTO(operationDTO);
        changeAmounts(newOperation, true);
        Operation savedOperation = operationRepository.save(newOperation);
        return savedOperation.getDTO();
    }

    public Optional<OperationDTO> updateOperation(OperationDTO operationDTO) {
        Optional<Operation> foundOperation = operationRepository.findById(operationDTO.getId());
        if (!foundOperation.isPresent()) {
            return Optional.ofNullable(null);
        }
        Operation operation = foundOperation.get();
        changeAmounts(operation, false);
        operation.update(getOperationFromDTO(operationDTO));
        changeAmounts(operation, true);
        Operation savedOperation = operationRepository.save(operation);
        return Optional.ofNullable(savedOperation.getDTO());
    }

    public boolean deleteOperation(BigInteger id) {
        Optional<Operation> operationResult = operationRepository.findById(id);
        if (operationResult.isPresent()) {
            Operation operation = operationResult.get();
            changeAmounts(operation, false);
            // сохранить изменения в текущих суммах счетов / контрагентов
            operationRepository.save(operation);
            // удалить саму операцию
            operationRepository.delete(operation);
            return true;
        }
        return false;
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
        if (operation.getType().getGroup() != Exchange) {
            if (!sourceMoneyAccount.getCurrencyId().equals(destinationMoneyAccount.getCurrencyId())) {
                throw new BudgetException("Source and destination money accounts / partners must have equal currencies");
            }
        }
        if (!created) {
            sourceAmount = sourceAmount.negate();
        }
        sourceMoneyAccount.deleteAmount(sourceAmount);
        if (operation.getType().getGroup() == Exchange) {
            destinationMoneyAccount.addAmount(sourceAmount.multiply(operation.getExchangeRate()));
        } else {
            destinationMoneyAccount.addAmount(sourceAmount);
        }
    }
}
