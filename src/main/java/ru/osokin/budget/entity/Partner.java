package ru.osokin.budget.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import java.math.BigInteger;

@Entity
@Getter
@NoArgsConstructor
public class Partner {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Partner_gen")
    @SequenceGenerator(name="Partner_gen", sequenceName = "Partner_seq")
    @Column(name = "ID", updatable = false, nullable = false)
    private BigInteger id;

    @Column(name = "partner_name")
    private String name;
    
}
