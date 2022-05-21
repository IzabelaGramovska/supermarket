package com.supermarketapi.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddItemsToSupermarketDtoResponse {
    private String supermarketId;
    private List<String> itemsNames;
}
