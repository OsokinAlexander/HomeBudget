package ru.osokin.budget;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** Типы денежных "счетов" или категорий долгов и имущества. */
@Getter
@AllArgsConstructor
public enum MoneyAccountType {

    /** Прочие деньги. */
    Money(0, "Деньги", MoneyAccountTypeGroup.Money, null),
    /** Наличные (кошелёк, копилка и пр.). */
    Cash(1, "Наличные", MoneyAccountTypeGroup.Money, Money),
    /** Вклад или расчётный счёт. */
    BankDeposit(2, "Вклад или расчётный счёт", MoneyAccountTypeGroup.Money, Money),
    /** Дебетовая (например, зарплатная) карта. */
    DebitCard(3, "Дебетовая карта", MoneyAccountTypeGroup.Money, Money),
    /** Электронный кошелёк. */
    ElectronicPurse(4, "Электронный кошелёк", MoneyAccountTypeGroup.Money, Money),

    /** Прочее имущество. */
    Property(100, "Имущество", MoneyAccountTypeGroup.Property, null),
    /** Движимое имущество. */
    MovableProperty(101, "Движимое имущество", MoneyAccountTypeGroup.Property, Property),
    /** Недвижимое имущество. */
    ImmovableProperty(102, "Недвижимое имущество", MoneyAccountTypeGroup.Property, Property),

    /** Прочие долги. */
    Creditors(200, "Долги", MoneyAccountTypeGroup.Creditors, null),
    /** Долг перед человеком (отдавать в первую очередь). */
    PersonCreditor(201, "Долги людям", MoneyAccountTypeGroup.Creditors, Creditors),
    /** Кредитная карта (деньги принадлежат банку). */
    CreditCard(202, "Кредитная карта", MoneyAccountTypeGroup.Creditors, Creditors),
    /** Ипотека. */
    Mortgage(203, "Ипотека", MoneyAccountTypeGroup.Creditors, Creditors),

    /** Прочие должники. */
    Borrowers(300, "Должники", MoneyAccountTypeGroup.Borrowers, null),
    /** Должник (человек). */
    PersonBorrower(301, "Должники (люди)", MoneyAccountTypeGroup.Borrowers, Borrowers),

    /** Контрагенты. */
    Partners(400, "Контрагенты", MoneyAccountTypeGroup.Partners, null);

    private final int id;
    private final String name;
    private final MoneyAccountTypeGroup group;
    private final MoneyAccountType parent;

    public enum MoneyAccountTypeGroup {
        /** Деньги. */
        Money,
        /** Имущество. */
        Property,
        /** Долги. */
        Creditors,
        /** Должники. */
        Borrowers,
        /** Партнёры (контрагенты). */
        Partners
    }

    public static MoneyAccountType getById(Integer id) {
        if (id == null) {
            return null;
        }
        for (MoneyAccountType type: MoneyAccountType.values()) {
            if (type.id == id) {
                return type;
            }
        }
        return null;
    }

}
