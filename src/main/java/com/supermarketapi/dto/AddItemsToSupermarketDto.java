package com.supermarketapi.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

import static com.supermarketapi.common.ValidationMessages.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddItemsToSupermarketDto {
    @NotBlank(message = NOT_ADDED_SUPERMARKET_ID)
    //Supermarket's id must not contain symbols other than "-".
    @Pattern(regexp = "[a-f0-9]{8}-[a-f0-9]{4}-4[a-f0-9]{3}-[89aAbB][a-f0-9]{3}-[a-f0-9]{12}", message = SUPERMARKET_ID_NOT_CORRECT)
    private String supermarketId;

    @NotEmpty(message = NOT_ADDED_ITEMS_IDS)
    private List<String> itemsIds;
}
