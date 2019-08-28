package ru.osokin.budget.operation;

import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.osokin.budget.entity.OperationTypeGroup;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@ToString
public class OperationManager {
//    private Set<MoneyAccount> accounts = new HashSet<>();
    private List<Operation> operations = new ArrayList<>();

//    public OperationManager(Set<MoneyAccount> accounts) {
//        this.accounts = accounts;
//    }
//
//    public boolean addAccount(MoneyAccount account) {
//        return accounts.add(account);
//    }

    public boolean addOperation(Operation operation) {
        if (operation.getType() == OperationTypeGroup.Transfer) {
            ((TransferOperation)operation).getSourceAccount().change(operation.getAmount().negate());
        } else if (operation.getType() == OperationTypeGroup.Exchange) {
            ((ExchangeOperation)operation).getSourceAccount().change(
                    ((ExchangeOperation) operation).getExchangeAmount().negate());
        }
        operation.getDestinationAccount().change(operation.getSignedAmount());
        return operations.add(operation);
    }

//    public boolean updateOperation(Operation operation) {
//        int index = operations.indexOf(operation);
//        if (index < 0) {
//            return false;
//        }
//        Operation updOperation = operations.get(index);
//        updOperation = new Operation(operation);
//        return true;
//    }

    public boolean deleteOperation(Operation operation) {
        if (operation.getType() == OperationTypeGroup.Transfer) {
            ((TransferOperation)operation).getSourceAccount().change(operation.getAmount());
        } else if (operation.getType() == OperationTypeGroup.Exchange) {
            ((ExchangeOperation)operation).getSourceAccount().change(
                    ((ExchangeOperation) operation).getExchangeAmount());
        }
        operation.getDestinationAccount().change(operation.getSignedAmount().negate());
        return operations.remove(operation);
    }
}
