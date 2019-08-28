package ru.osokin.budget.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import ru.osokin.budget.BudgetException;

/** Множество мировых валют.
 * "ОК (МК (ИСО 4217) 003-97) 014-2000. Общероссийский классификатор валют" (утв. Постановлением Госстандарта России
 * от 25.12.2000 N 405-ст) (ред. от 24.10.2018).
 * http://www.consultant.ru/document/cons_doc_LAW_31966/
 */
@Getter
@ToString
@AllArgsConstructor
public enum Currency {
    AUD("AUD", 36, "Австралийский доллар"),
    AZN("AZN", 944, "Азербайджанский манат"),
    DZD("DZD", 12, "Алжирский динар"),
    ARS("ARS", 32, "Аргентинское песо"),
    AMD("AMD", 51, "Армянский драм"),
    AWG("AWG", 533, "Арубанский флорин"),
    AFN("AFN", 971, "Афгани"),
    BSD("BSD", 44, "Багамский доллар"),
    PAB("PAB", 590, "Бальбоа"),
    BBD("BBD", 52, "Барбадосский доллар"),
    THB("THB", 764, "Бат"),
    BHD("BHD", 48, "Бахрейнский динар"),
    BZD("BZD", 84, "Белизский доллар"),
    BYN("BYN", 933, "Белорусский рубль"),
    BMD("BMD", 60, "Бермудский доллар"),
    BGN("BGN", 975, "Болгарский лев"),
    VES("VES", 928, "Боливар Соберано"),
    BOB("BOB", 68, "Боливиано"),
    BRL("BRL", 986, "Бразильский реал"),
    BND("BND", 96, "Брунейский доллар"),
    BIF("BIF", 108, "Бурундийский франк"),
    VUV("VUV", 548, "Вату"),
    KRW("KRW", 410, "Вона"),
    XCD("XCD", 951, "Восточно-карибский доллар"),
    GYD("GYD", 328, "Гайанский доллар"),
    GHS("GHS", 936, "Ганский седи"),
    GNF("GNF", 324, "Гвинейский франк"),
    GIP("GIP", 292, "Гибралтарский фунт"),
    HKD("HKD", 344, "Гонконгский доллар"),
    UAH("UAH", 980, "Гривна"),
    PYG("PYG", 600, "Гуарани"),
    HTG("HTG", 332, "Гурд"),
    GMD("GMD", 270, "Даласи"),
    DKK("DKK", 208, "Датская крона"),
    MKD("MKD", 807, "Денар"),
    AED("AED", 784, "Дирхам (ОАЭ)"),
    STN("STN", 930, "Добра"),
    ZWL("ZWL", 932, "Доллар Зимбабве"),
    KYD("KYD", 136, "Доллар Островов Кайман"),
    NAD("NAD", 516, "Доллар Намибии"),
    SBD("SBD", 90, "Доллар Соломоновых Островов"),
    USD("USD", 840, "Доллар США", true),
    TTD("TTD", 780, "Доллар Тринидада и Тобаго"),
    FJD("FJD", 242, "Доллар Фиджи"),
    DOP("DOP", 214, "Доминиканское песо"),
    VND("VND", 704, "Донг"),
    EUR("EUR", 978, "Евро", true),
    EGP("EGP", 818, "Египетский фунт"),
    COU("COU", 970, "Единица реальной стоимости"),
    ZMW("ZMW", 967, "Замбийская квача"),
    PLN("PLN", 985, "Злотый"),
    NIO("NIO", 558, "Золотая кордоба"),
    INR("INR", 356, "Индийская рупия"),
    JOD("JOD", 400, "Иорданский динар"),
    IQD("IQD", 368, "Иракский динар"),
    IRR("IRR", 364, "Иранский риал"),
    ISK("ISK", 352, "Исландская крона"),
    YER("YER", 886, "Йеменский риал"),
    JPY("JPY", 392, "Иена"),
    CAD("CAD", 124, "Канадский доллар"),
    QAR("QAR", 634, "Катарский риал"),
    AOA("AOA", 973, "Кванза"),
    MWK("MWK", 454, "Малавийская квача"),
    KES("KES", 404, "Кенийский шиллинг"),
    GTQ("GTQ", 320, "Кетсаль"),
    PGK("PGK", 598, "Кина"),
    LAK("LAK", 418, "Лаосский кип"),
    COP("COP", 170, "Колумбийское песо"),
    BAM("BAM", 977, "Конвертируемая марка"),
    CUC("CUC", 931, "Конвертируемое песо"),
    CDF("CDF", 976, "Конголезский франк"),
    CRC("CRC", 188, "Коста-риканский колон"),
    CUP("CUP", 192, "Кубинское песо"),
    KWD("KWD", 414, "Кувейтский динар"),
    MMK("MMK", 104, "Кьят"),
    GEL("GEL", 981, "Лари"),
    ALL("ALL", 8, "Лек"),
    HNL("HNL", 340, "Лемпира"),
    SLL("SLL", 694, "Леоне"),
    LRD("LRD", 430, "Либерийский доллар"),
    LBP("LBP", 422, "Ливанский фунт"),
    LYD("LYD", 434, "Ливийский динар"),
    SZL("SZL", 748, "Лилангени"),
    LSL("LSL", 426, "Лоти"),
    MUR("MUR", 480, "Маврикийская рупия"),
    MGA("MGA", 969, "Малагасийский ариари"),
    MYR("MYR", 458, "Малайзийский ринггит"),
    MAD("MAD", 504, "Марокканский дирхам"),
    MXN("MXN", 484, "Мексиканское песо"),
    MZN("MZN", 943, "Мозамбикский метикал"),
    MDL("MDL", 498, "Молдавский лей"),
    NGN("NGN", 566, "Найра"),
    ERN("ERN", 232, "Накфа"),
    BTN("BTN", 64, "Нгултрум"),
    NPR("NPR", 524, "Непальская рупия"),
    ANG("ANG", 532, "Нидерландский антильский гульден"),
    NZD("NZD", 554, "Новозеландский доллар"),
    ILS("ILS", 376, "Новый израильский шекель"),
    RON("RON", 946, "Румынский лей"),
    PEN("PEN", 604, "Соль"),
    TWD("TWD", 901, "Новый тайваньский доллар"),
    TMT("TMT", 934, "Новый туркменский манат"),
    NOK("NOK", 578, "Норвежская крона"),
    OMR("OMR", 512, "Оманский риал"),
    TOP("TOP", 776, "Паанга"),
    PKR("PKR", 586, "Пакистанская рупия"),
    MOP("MOP", 446, "Патака"),
    BWP("BWP", 72, "Пула"),
    KHR("KHR", 116, "Риель"),
    RUB("RUB", 643, "Российский рубль", true),
    IDR("IDR", 360, "Рупия"),
    MVR("MVR", 462, "Руфия"),
    ZAR("ZAR", 710, "Рэнд"),
    SVC("SVC", 222, "Сальвадорский колон"),
    SAR("SAR", 682, "Саудовский риял"),
    XDR("XDR", 960, "СДР (специальные права заимствования)"), //Международный валютный фонд (МВФ)
    KPW("KPW", 408, "Северокорейская вона"),
    SCR("SCR", 690, "Сейшельская рупия"),
    RSD("RSD", 941, "Сербский динар"),
    SGD("SGD", 702, "Сингапурский доллар"),
    SYP("SYP", 760, "Сирийский фунт"),
    KGS("KGS", 417, "Сом"),
    SOS("SOS", 706, "Сомалийский шиллинг"),
    TJS("TJS", 972, "Сомони"),
    SDG("SDG", 938, "Суданский фунт"),
    SRD("SRD", 968, "Суринамский доллар"),
    BDT("BDT", 50, "Така"),
    WST("WST", 882, "Тала"),
    TZS("TZS", 834, "Танзанийский шиллинг"),
    KZT("KZT", 398, "Тенге"),
    MNT("MNT", 496, "Тугрик"),
    TND("TND", 788, "Тунисский динар"),
    TRY("TRY", 949, "Турецкая лира"),
    UGX("UGX", 800, "Угандийский шиллинг"),
    MRU("MRU", 929, "Угия"),
    UZS("UZS", 860, "Узбекский сум"),
    UYU("UYU", 858, "Уругвайское песо"),
    UYI("UYI", 940, "Уругвайское песо в индексированных единицах"),
    PHP("PHP", 608, "Филиппинское песо"),
    HUF("HUF", 348, "Форинт"),
    DJF("DJF", 262, "Франк Джибути"),
    KMF("KMF", 174, "Коморский франк"),
    XAF("XAF", 950, "Франк КФА ВЕАС"),
    XOF("XOF", 952, "Франк КФА ВСЕАО"),
    XPF("XPF", 953, "Франк КФП"),
    RWF("RWF", 646, "Франк Руанды"),
    SHP("SHP", 654, "Фунт Святой Елены"),
    GBP("GBP", 826, "Фунт стерлингов"),
    FKP("FKP", 238, "Фунт Фолклендских островов"),
    HRK("HRK", 191, "Куна"),
    CZK("CZK", 203, "Чешская крона"),
    CLP("CLP", 152, "Чилийское песо"),
    SEK("SEK", 752, "Шведская крона"),
    CHF("CHF", 756, "Швейцарский франк"),
    JMD("JMD", 388, "Ямайский доллар"),
    ETB("ETB", 230, "Эфиопский быр"),
    SSP("SSP", 728, "Южносуданский фунт"),
    CVE("CVE", 132, "Эскудо Кабо-Верде"),
    CNY("CNY", 156, "Юань"),
    LKR("LKR", 144, "Шри-ланкийская рупия");

    /** 3-х значный буквенный код. */
    private String alpha3;

    /** 3-х значный цифровой код. */
    private int id;

    /** Название валюты. */
    private String shortName;

    /** Использовать по умолчанию. */
    private boolean isDefault;

    Currency(String alpha3, int id, String shortName) {
        this.alpha3 = alpha3;
        this.id = id;
        this.shortName = shortName;
        this.isDefault = false;
    }

    /**
     * Найти валюту по цифровому коду.
     * @param id код
     * @return валюта
     */
    public static Currency getById(Integer id) {
        if (id == null) {
            return null;
        }
        for (Currency currency: Currency.values()) {
            if (currency.id == id) {
                return currency;
            }
        }
        throw new BudgetException("Could not find currency with id: " + id);
    }

    /**
     * Найти валюту по буквенному коду.
     * @param alpha3 код
     * @return валюта
     */
    public static Currency getByAlpha3(String alpha3) {
        if (alpha3 == null) {
            return null;
        }
        for (Currency currency: Currency.values()) {
            if (currency.alpha3.equalsIgnoreCase(alpha3)) {
                return currency;
            }
        }
        throw new BudgetException("Could not find currency with alpah3: " + alpha3);
    }

    /**
     * Найти валюту по названию.
     * @param shortName название
     * @return валюта
     */
    public static Currency getByShortName(String shortName) {
        if (shortName == null) {
            return null;
        }
        for (Currency currency: Currency.values()) {
            if (currency.shortName.equalsIgnoreCase(shortName)) {
                return currency;
            }
        }
        throw new BudgetException("Could not find currency with short name: " + shortName);
    }
}
