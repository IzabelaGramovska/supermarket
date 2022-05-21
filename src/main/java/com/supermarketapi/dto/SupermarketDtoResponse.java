package com.supermarketapi.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SupermarketDtoResponse {
    private String name;
    private String country;
    private String city;
    private String street;
    private String phoneNumber;
    private String workHours;
    private List<ItemDto> itemsDto;
}
