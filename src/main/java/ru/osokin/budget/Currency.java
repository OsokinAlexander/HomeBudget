package ru.osokin.budget;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigInteger;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Currency {
    private BigInteger id;
    private String code;
    private String name;
}
