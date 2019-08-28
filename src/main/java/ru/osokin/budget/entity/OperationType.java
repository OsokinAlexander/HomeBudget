package ru.osokin.budget.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.osokin.budget.BudgetException;

/** Типы операций. */
@Getter
@AllArgsConstructor
public enum OperationType {

    Income(100, "Доход", OperationTypeGroup.Income),
    Salary(101, "Зарплата", OperationTypeGroup.Income),
    BankPercent(102, "Банковский процент", OperationTypeGroup.Income),
    Business(103, "Бизнес", OperationTypeGroup.Income),
    Gift(104, "Подарок", OperationTypeGroup.Income),

    Expense(200, "Расход", OperationTypeGroup.Expense),
    Property(201, "Поддержка имущества, ремонт, кварплата", OperationTypeGroup.Expense),
    Pets(202, "Домашние животные", OperationTypeGroup.Expense),
    Health(203, "Здоровье", OperationTypeGroup.Expense),
    Bank(204, "Кредиты, банковское обслуживание", OperationTypeGroup.Expense),
    Children(205, "Дети", OperationTypeGroup.Expense),
    Education(206, "Образование", OperationTypeGroup.Expense),
    Clothes(207, "Одежда, обувь, головные уборы, сумки, аксессуары", OperationTypeGroup.Expense),
    Rest(208, "Отдых", OperationTypeGroup.Expense),
    Religion(209, "Религия, благотворительность", OperationTypeGroup.Expense),
    Houseware(210, "Предметы быта", OperationTypeGroup.Expense),
    PO(211, "Программное обеспечение", OperationTypeGroup.Expense),
    Food(212, "Продукты", OperationTypeGroup.Expense),
    Work(213, "Работа, бизнес", OperationTypeGroup.Expense),
    Communication(214, "Связь, интернет", OperationTypeGroup.Expense),
    Transport(215, "Транспорт", OperationTypeGroup.Expense),
    Hobby(216, "Хобби", OperationTypeGroup.Expense),
    Consumables(217, "Хоз.товары, расходники", OperationTypeGroup.Expense),

    Transfer(300, "Перевод", OperationTypeGroup.Transfer),

    Exchange(400, "Обмен", OperationTypeGroup.Exchange);

    private int id;
    private String name;
    private OperationTypeGroup group;

    public static OperationType getById(Integer id) {
        if (id == null) {
            return null;
        }
        for (OperationType type: OperationType.values()) {
            if (type.id == id) {
                return type;
            }
        }
        throw new BudgetException("Could not find operation type with id: " + id);
    }

}
