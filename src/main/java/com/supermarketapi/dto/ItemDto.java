package com.supermarketapi.dto;

import com.supermarketapi.common.ItemType;
import lombok.*;

import javax.persistence.Enumerated;
import javax.validation.constraints.*;

import static com.supermarketapi.common.ValidationMessages.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ItemDto {
    @NotBlank(message = NOT_ADDED_NAME)
    @Size(max = 64, min = 2, message = SIZE_NAME_NOT_CORRECT)
    //The name must not contain digits or symbols other than "." or "-".
    @Pattern(regexp = "([A-Z])?[a-z]+((\\s)?((\\.|\\-)?(([A-Z])?[a-z]+)+)){1,}", message = NAME_IS_NOT_VALID)
    private String name;

    @NotNull(message = NOT_ADDED_PRICE)
    @DecimalMax(value = "9999.99", message = ITEM_PRICE_NOT_CORRECT)
    @DecimalMin(value = "0.01", message = ITEM_PRICE_NOT_CORRECT)
    private Double price;

    @NotNull(message = NOT_ADDED_ITEM_TYPE)
    @Enumerated
    private ItemType itemType;
}
