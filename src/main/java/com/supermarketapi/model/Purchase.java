package com.supermarketapi.model;

import com.opencsv.bean.CsvBindByName;
import com.supermarketapi.common.PaymentType;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Purchase {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "supermarket")
    @CsvBindByName
    private Supermarket supermarket;

    @NotEmpty
    @Column(name = "items")
    @ManyToMany(cascade = CascadeType.ALL)
    @CsvBindByName
    @ToString.Exclude
    private List<Item> items;

    @Column(name = "payment_type")
    @NotNull
    @Enumerated(EnumType.STRING)
    @CsvBindByName
    private PaymentType paymentType;

    @Column(name = "total_cash_amount")
    @CsvBindByName
    private double totalCashAmount;

    @Column(name = "money_given")
    @CsvBindByName
    private double moneyGiven;

    @Column(name = "time_of_payment")
    @CsvBindByName
    private LocalTime timeOfPayment;
}






