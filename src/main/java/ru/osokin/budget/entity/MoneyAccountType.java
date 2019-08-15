package ru.osokin.budget.entity;

import lombok.Getter;

@Getter
public enum MoneyAccountType {

    /** Прочие активы. */
    Assert(MoneyAccountTypeGroup.Asset, 1),
    /** Дача денег в долг человеку (часто безнадёжно). */
    AssertToPerson(MoneyAccountTypeGroup.Asset, 2),
    /** Наличные. */
    Cash(MoneyAccountTypeGroup.Asset, 3),
    /** Вклад. */
    BankDeposit(MoneyAccountTypeGroup.Asset, 4),
    /** Дебетовая (например, зарплатная) карта. */
    DebitCard(MoneyAccountTypeGroup.Asset, 5),
    /** Движимое имущество. */
    MovableProperty(MoneyAccountTypeGroup.Asset, 6),
    /** Недвижимое имущество. */
    ImmovableProperty(MoneyAccountTypeGroup.Asset, 7),

    /** Прочие пассивы. */
    Liability(MoneyAccountTypeGroup.Liability, 100),
    /** Долг перед человеком (отдавать в первую очередь). */
    LiabilityToPerson(MoneyAccountTypeGroup.Liability, 101),
    /** Кредитная карта (деньги принадлежат банку). */
    CreditCard(MoneyAccountTypeGroup.Liability,102),
    /** Ипотека. */
    Mortgage(MoneyAccountTypeGroup.Liability, 103);

    private final MoneyAccountTypeGroup group;
    private final int id;

    MoneyAccountType(MoneyAccountTypeGroup group, int id) {
        this.group = group;
        this.id = id;
    }

    public enum MoneyAccountTypeGroup {
        /** Актив. */
        Asset,
        /** Пассив. */
        Liability
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
