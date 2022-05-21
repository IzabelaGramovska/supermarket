package com.supermarketapi.model;

import com.supermarketapi.common.ItemType;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Item {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Column(name = "name")
    @NotBlank
    private String name;

    @Column(name = "price")
    @NotNull
    private double price;

    @Column(name = "item_type")
    @NotNull
    @Enumerated(EnumType.STRING)
    private ItemType itemType;
}

