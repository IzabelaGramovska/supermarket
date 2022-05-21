package com.supermarketapi.dto;

import com.supermarketapi.common.ItemType;
import com.supermarketapi.model.Item;
import com.supermarketapi.model.Supermarket;
import lombok.SneakyThrows;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AddItemsToSupermarketDtoTest {
    private AddItemsToSupermarketDto addItemsToSupermarketDto;

    @BeforeEach
    void setUp() {
        Item itemOne = new Item(UUID.randomUUID().toString(), "Donut", 0.89, ItemType.FOOD);
        Item itemTwo = new Item(UUID.randomUUID().toString(), "Pizza", 8.20, ItemType.FOOD);

        List<String> itemsIds = new ArrayList<>();
        itemsIds.add(itemOne.getId());
        itemsIds.add(itemTwo.getId());

        List<Item> itemList = new ArrayList<>();
        itemList.add(itemOne);
        itemList.add(itemTwo);

        Supermarket supermarket = new Supermarket(UUID.randomUUID().toString(), "Kaufland", "Bulgaria", "Sofia",
                "Knyaginya Klementina 189", "0895678900", "07:00-22:30", itemList);

        addItemsToSupermarketDto = new AddItemsToSupermarketDto(supermarket.getId(), itemsIds);
    }

    @Test
    @SneakyThrows
    public void testWithGivenValidDto() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

        final Validator validator = factory.getValidator();

        Set<ConstraintViolation<AddItemsToSupermarketDto>> constraintViolations = validator.validate(addItemsToSupermarketDto);

        assertThat(constraintViolations.size()).isZero();
    }

    @SneakyThrows
    @Test
    public void testWithGivenNullValueForSupermarketId() {
        addItemsToSupermarketDto.setSupermarketId(null);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();

        Set<ConstraintViolation<AddItemsToSupermarketDto>> constraintViolations = validator.validate(addItemsToSupermarketDto);

        constraintViolations.forEach(constraintViolation -> assertEquals(constraintViolation.getMessage(),
                "Please provide a supermarket id."));

        AssertionsForClassTypes.assertThat(constraintViolations.size()).isOne();
    }

    @SneakyThrows
    @Test
    public void testWithGivenInvalidSupermarketId() {
        addItemsToSupermarketDto.setSupermarketId("#$%^&*()");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();

        Set<ConstraintViolation<AddItemsToSupermarketDto>> constraintViolations = validator.validate(addItemsToSupermarketDto);

        constraintViolations.forEach(constraintViolation -> assertEquals(constraintViolation.getMessage(),
                "Please provide a valid supermarket id."));

        AssertionsForClassTypes.assertThat(constraintViolations.size()).isOne();
    }

    @SneakyThrows
    @Test
    public void testWithGivenEmptyListWithItemsIds() {
        addItemsToSupermarketDto.setItemsIds(null);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();

        Set<ConstraintViolation<AddItemsToSupermarketDto>> constraintViolations = validator.validate(addItemsToSupermarketDto);

        constraintViolations.forEach(constraintViolation -> assertEquals(constraintViolation.getMessage(),
                "Please provide items ids."));

        AssertionsForClassTypes.assertThat(constraintViolations.size()).isOne();
    }
}