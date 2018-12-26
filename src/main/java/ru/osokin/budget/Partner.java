package ru.osokin.budget;

import lombok.AllArgsConstructor;
import lombok.ToString;

import java.math.BigInteger;

@AllArgsConstructor
@ToString
public class Partner {
    private BigInteger id;
    String name;
}
