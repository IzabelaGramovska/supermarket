package com.supermarketapi.dto;

import com.supermarketapi.common.PaymentType;
import lombok.*;

import java.time.LocalTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PurchaseDtoResponse {
    private SupermarketDto supermarketDto;
    private List<ItemDto> itemsDto;
    private PaymentType paymentType;
    private double totalCashAmount;
    private double moneyGiven;
    private double change;
    private LocalTime timeOfPayment;
}
