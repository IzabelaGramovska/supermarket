package com.supermarketapi.dto;

import com.supermarketapi.common.ItemType;
import com.supermarketapi.common.PaymentType;
import com.supermarketapi.model.Item;
import com.supermarketapi.model.Supermarket;
import lombok.SneakyThrows;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PurchaseDtoTest {
    private PurchaseDto purchaseDto;

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

        Supermarket supermarket = new Supermarket(UUID.randomUUID().toString(), "Kaufland", "Bulgaria", "Sofia", "Knyaginya Klementina 189", "0895678900", "07:00-22:30", itemList);

        purchaseDto = new PurchaseDto(supermarket.getId(), itemsIds, PaymentType.CASH, 320.50);
    }

    @SneakyThrows
    @Test
    public void testWithGivenValidDto() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();

        Set<ConstraintViolation<PurchaseDto>> constraintViolations = validator.validate(purchaseDto);

        assertThat(constraintViolations.size()).isZero();
    }

    @SneakyThrows
    @Test
    public void testWithGivenNullValueForSupermarketId() {
        purchaseDto.setSupermarketId(null);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();

        Set<ConstraintViolation<PurchaseDto>> constraintViolations = validator.validate(purchaseDto);

        constraintViolations.forEach(constraintViolation -> assertEquals(constraintViolation.getMessage(), "Please provide a supermarket id."));

        assertThat(constraintViolations.size()).isOne();
    }

    @SneakyThrows
    @Test
    public void testWithGivenInvalidSupermarketId() {
        purchaseDto.setSupermarketId("#$%^&*()");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();

        Set<ConstraintViolation<PurchaseDto>> constraintViolations = validator.validate(purchaseDto);

        constraintViolations.forEach(constraintViolation -> assertEquals(constraintViolation.getMessage(), "Please provide a valid supermarket id."));

        assertThat(constraintViolations.size()).isOne();
    }

    @SneakyThrows
    @Test
    public void testWithGivenEmptyListWithItemsIds() {
        purchaseDto.setItemsIds(null);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();

        Set<ConstraintViolation<PurchaseDto>> constraintViolations = validator.validate(purchaseDto);

        constraintViolations.forEach(constraintViolation -> assertEquals(constraintViolation.getMessage(), "Please provide items ids."));

        assertThat(constraintViolations.size()).isOne();
    }

    @SneakyThrows
    @Test
    public void testWithGivenNullValueForPaymentType() {
        purchaseDto.setPaymentType(null);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();

        Set<ConstraintViolation<PurchaseDto>> constraintViolations = validator.validate(purchaseDto);

        constraintViolations.forEach(constraintViolation -> assertEquals(constraintViolation.getMessage(), "Please provide a payment type."));

        assertThat(constraintViolations.size()).isOne();
    }

    @SneakyThrows
    @Test
    public void testWithGivenNullValueForCashAmount() {
        purchaseDto.setCashAmount(null);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();

        Set<ConstraintViolation<PurchaseDto>> constraintViolations = validator.validate(purchaseDto);

        constraintViolations.forEach(constraintViolation -> assertEquals(constraintViolation.getMessage(), "Please provide a cash amount."));

        assertThat(constraintViolations.size()).isOne();
    }

    @SneakyThrows
    @Test
    public void testWithGivenInvalidValueForCashAmount() {
        purchaseDto.setCashAmount(0.0);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();

        Set<ConstraintViolation<PurchaseDto>> constraintViolations = validator.validate(purchaseDto);

        constraintViolations.forEach(constraintViolation -> assertEquals(constraintViolation.getMessage(), "The cash amount must be at least 0.01."));

        assertThat(constraintViolations.size()).isOne();
    }
}