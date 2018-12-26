package ru.osokin.budget;

public class BudgetException extends RuntimeException {
    public BudgetException(Throwable cause) {
        super(cause);
    }

    public BudgetException(String message) {
        super(message);
    }

    public BudgetException(String message, Throwable cause) {
        super(message, cause);
    }
}
