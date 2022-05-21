package com.supermarketapi.dto;

import com.supermarketapi.common.PaymentType;
import lombok.*;

import javax.persistence.Enumerated;
import javax.validation.constraints.*;
import java.util.List;

import static com.supermarketapi.common.ValidationMessages.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PurchaseDto {
    @NotBlank(message = NOT_ADDED_SUPERMARKET_ID)
    //Supermarket's id must not contain symbols other than "-".
    @Pattern(regexp = "[a-f0-9]{8}-[a-f0-9]{4}-4[a-f0-9]{3}-[89aAbB][a-f0-9]{3}-[a-f0-9]{12}", message = SUPERMARKET_ID_NOT_CORRECT)
    private String supermarketId;

    @NotEmpty(message = NOT_ADDED_ITEMS_IDS)
    private List<String> itemsIds;

    @NotNull(message = NOT_ADDED_PAYMENT_TYPE)
    @Enumerated
    private PaymentType paymentType;

    @NotNull(message = NOT_ADDED_CASH_AMOUNT)
    @DecimalMin(value = "0.01", message = CASH_AMOUNT_NOT_CORRECT)
    private Double cashAmount;
}
