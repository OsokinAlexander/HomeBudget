package ru.osokin.budget;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** Группы типов операций. */
@Getter
@AllArgsConstructor
public enum OperationTypeGroup {
    /** Приход. */
    Income(1, "Приход"),
    /** Расход. */
    Expense(2, "Расход"),
    /** Перевод со счёта на счёт. */
    Transfer(3, "Перевод"),
    /** Обмен валют. */
    Exchange(4, "Обмен");

    private int id;
    private String name;

    public static OperationTypeGroup getById(Integer id) {
        if (id == null) {
            return null;
        }
        for (OperationTypeGroup type: OperationTypeGroup.values()) {
            if (type.id == id) {
                return type;
            }
        }
        return null;
    }

}
