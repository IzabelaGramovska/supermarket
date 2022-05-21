package com.supermarketapi.dto;

import com.supermarketapi.common.ItemType;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemDtoTest {
    @Test
    @SneakyThrows
    public void testWithGivenValidDto() {
        ItemDto itemDto = new ItemDto("Apple", 0.90, ItemType.FOOD);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

        final Validator validator = factory.getValidator();

        Set<ConstraintViolation<ItemDto>> constraintViolations = validator.validate(itemDto);

        assertThat(constraintViolations.size()).isZero();
    }

    @Test
    @SneakyThrows
    public void testWithGivenInvalidName() {
        ItemDto itemDto = new ItemDto("(*&^%777", 3.50, ItemType.DRINKS);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();

        Set<ConstraintViolation<ItemDto>> constraintViolations = validator.validate(itemDto);

        constraintViolations.forEach(constraintViolation -> assertEquals(constraintViolation.getMessage(), "Please " + "provide a valid name."));

        assertThat(constraintViolations.size()).isOne();
    }

    @Test
    @SneakyThrows
    public void testWithGivenNullValueForName() {
        ItemDto itemDto = new ItemDto(null, 3.50, ItemType.DRINKS);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();

        Set<ConstraintViolation<ItemDto>> constraintViolations = validator.validate(itemDto);

        constraintViolations.forEach(constraintViolation -> assertEquals(constraintViolation.getMessage(), "Please provide a name."));

        assertThat(constraintViolations.size()).isOne();
    }

    @Test
    @SneakyThrows
    public void testWithGivenInvalidSizeName() {
        ItemDto itemDto = new ItemDto("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", 3.50, ItemType.DRINKS);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();

        Set<ConstraintViolation<ItemDto>> constraintViolations = validator.validate(itemDto);

        constraintViolations.forEach(constraintViolation -> assertEquals(constraintViolation.getMessage(), "The size of the name must be between 2 and 64 characters."));

        assertThat(constraintViolations.size()).isOne();
    }

    @Test
    @SneakyThrows
    public void testWithGivenInvalidPrice() {
        ItemDto itemDto = new ItemDto("Fanta", 0.0, ItemType.DRINKS);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();

        Set<ConstraintViolation<ItemDto>> constraintViolations = validator.validate(itemDto);

        constraintViolations.forEach(constraintViolation -> assertEquals(constraintViolation.getMessage(), "The price must be between 0.01 and 9999.99."));

        assertThat(constraintViolations.size()).isOne();
    }

    @Test
    @SneakyThrows
    public void testWithGivenNullValueForPrice() {
        ItemDto itemDto = new ItemDto("Fanta", null, ItemType.DRINKS);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();

        Set<ConstraintViolation<ItemDto>> constraintViolations = validator.validate(itemDto);

        constraintViolations.forEach(constraintViolation -> assertEquals(constraintViolation.getMessage(), "Please provide a price."));

        assertThat(constraintViolations.size()).isOne();
    }

    @Test
    @SneakyThrows
    public void testWithGivenNullValueForItemType() {
        ItemDto itemDto = new ItemDto("Fanta", 1.50, null);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();

        Set<ConstraintViolation<ItemDto>> constraintViolations = validator.validate(itemDto);

        constraintViolations.forEach(constraintViolation -> assertEquals(constraintViolation.getMessage(), "Please provide an item type."));

        assertThat(constraintViolations.size()).isOne();
    }
}