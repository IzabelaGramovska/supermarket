package com.supermarketapi.dto;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SupermarketDtoTest {
    private SupermarketDto supermarketDto;

    @BeforeEach
    void setUp() {
        supermarketDto = new SupermarketDto("Metro", "Bulagria", "Sofia", "Ivan Vazov 3", "0895678977", "09:00-22:30");
    }

    @Test
    @SneakyThrows
    public void testWithGivenValidDto() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();

        Set<ConstraintViolation<SupermarketDto>> constraintViolations = validator.validate(supermarketDto);

        assertThat(constraintViolations.size()).isZero();
    }

    @Test
    @SneakyThrows
    public void testWithGivenInvalidName() {
        supermarketDto.setName("$%^&*(");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();

        Set<ConstraintViolation<SupermarketDto>> constraintViolations = validator.validate(supermarketDto);

        constraintViolations.forEach(constraintViolation -> assertEquals(constraintViolation.getMessage(), "Please " + "provide a valid name."));

        assertThat(constraintViolations.size()).isOne();
    }

    @Test
    @SneakyThrows
    public void testWithGivenNullValueForName() {
        supermarketDto.setName(null);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();

        Set<ConstraintViolation<SupermarketDto>> constraintViolations = validator.validate(supermarketDto);

        constraintViolations.forEach(constraintViolation -> assertEquals(constraintViolation.getMessage(), "Please provide a name."));

        assertThat(constraintViolations.size()).isOne();
    }

    @Test
    @SneakyThrows
    public void testWithGivenInvalidSizeName() {
        supermarketDto.setName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();

        Set<ConstraintViolation<SupermarketDto>> constraintViolations = validator.validate(supermarketDto);

        constraintViolations.forEach(constraintViolation -> assertEquals(constraintViolation.getMessage(), "The size of the name must be between 2 and 64 characters."));

        assertThat(constraintViolations.size()).isOne();
    }

    @Test
    @SneakyThrows
    public void testWithGivenInvalidCountry() {
        supermarketDto.setCountry("$%^&*(");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();

        Set<ConstraintViolation<SupermarketDto>> constraintViolations = validator.validate(supermarketDto);

        constraintViolations.forEach(constraintViolation -> assertEquals(constraintViolation.getMessage(), "Please provide a valid country."));

        assertThat(constraintViolations.size()).isOne();
    }

    @Test
    @SneakyThrows
    public void testWithGivenNullValueForCountry() {
        supermarketDto.setCountry(null);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();

        Set<ConstraintViolation<SupermarketDto>> constraintViolations = validator.validate(supermarketDto);

        constraintViolations.forEach(constraintViolation -> assertEquals(constraintViolation.getMessage(), "Please provide a country."));

        assertThat(constraintViolations.size()).isOne();
    }

    @Test
    @SneakyThrows
    public void testWithGivenInvalidSizeCountry() {
        supermarketDto.setCountry("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();

        Set<ConstraintViolation<SupermarketDto>> constraintViolations = validator.validate(supermarketDto);

        constraintViolations.forEach(constraintViolation -> assertEquals(constraintViolation.getMessage(), "The size of the country must be between 1 and 20 characters."));

        assertThat(constraintViolations.size()).isOne();
    }

    @Test
    @SneakyThrows
    public void testWithGivenInvalidCity() {
        supermarketDto.setCity("$%^&*(");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();

        Set<ConstraintViolation<SupermarketDto>> constraintViolations = validator.validate(supermarketDto);

        constraintViolations.forEach(constraintViolation -> assertEquals(constraintViolation.getMessage(), "Please provide a valid city."));

        assertThat(constraintViolations.size()).isOne();
    }

    @Test
    @SneakyThrows
    public void testWithGivenNullValueForCity() {
        supermarketDto.setCity(null);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();

        Set<ConstraintViolation<SupermarketDto>> constraintViolations = validator.validate(supermarketDto);

        constraintViolations.forEach(constraintViolation -> assertEquals(constraintViolation.getMessage(), "Please " + "provide a city."));

        assertThat(constraintViolations.size()).isOne();
    }

    @Test
    @SneakyThrows
    public void testWithGivenInvalidSizeCity() {
        supermarketDto.setCity("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();

        Set<ConstraintViolation<SupermarketDto>> constraintViolations = validator.validate(supermarketDto);

        constraintViolations.forEach(constraintViolation -> assertEquals(constraintViolation.getMessage(), "The size of the city must be between 1 and 30 characters."));

        assertThat(constraintViolations.size()).isOne();
    }

    @Test
    @SneakyThrows
    public void testWithGivenInvalidStreet() {
        supermarketDto.setStreet("$%^&*(");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();

        Set<ConstraintViolation<SupermarketDto>> constraintViolations = validator.validate(supermarketDto);

        constraintViolations.forEach(constraintViolation -> assertEquals(constraintViolation.getMessage(), "Please provide a valid street."));

        assertThat(constraintViolations.size()).isOne();
    }

    @Test
    @SneakyThrows
    public void testWithGivenNullValueForStreet() {
        supermarketDto.setStreet(null);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();

        Set<ConstraintViolation<SupermarketDto>> constraintViolations = validator.validate(supermarketDto);

        constraintViolations.forEach(constraintViolation -> assertEquals(constraintViolation.getMessage(), "Please " + "provide a street."));

        assertThat(constraintViolations.size()).isOne();
    }

    @Test
    @SneakyThrows
    public void testWithGivenInvalidSizeStreet() {
        supermarketDto.setStreet("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();

        Set<ConstraintViolation<SupermarketDto>> constraintViolations = validator.validate(supermarketDto);

        constraintViolations.forEach(constraintViolation -> assertEquals(constraintViolation.getMessage(), "The size of the street must be between 1 and 70 characters."));

        assertThat(constraintViolations.size()).isOne();
    }

    @Test
    @SneakyThrows
    public void testWithGivenInvalidPhoneNumber() {
        supermarketDto.setPhoneNumber("$%^&*(");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();

        Set<ConstraintViolation<SupermarketDto>> constraintViolations = validator.validate(supermarketDto);

        constraintViolations.forEach(constraintViolation -> assertEquals(constraintViolation.getMessage(), "Please provide a valid phone number."));

        assertThat(constraintViolations.size()).isOne();
    }

    @Test
    @SneakyThrows
    public void testWithGivenNullValueForPhoneNumber() {
        supermarketDto.setPhoneNumber(null);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();

        Set<ConstraintViolation<SupermarketDto>> constraintViolations = validator.validate(supermarketDto);

        constraintViolations.forEach(constraintViolation -> assertEquals(constraintViolation.getMessage(), "Please " + "provide a phone number."));

        assertThat(constraintViolations.size()).isOne();
    }

    @Test
    @SneakyThrows
    public void testWithGivenNullValueForWorkHours() {
        supermarketDto.setWorkHours(null);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();

        Set<ConstraintViolation<SupermarketDto>> constraintViolations = validator.validate(supermarketDto);

        constraintViolations.forEach(constraintViolation -> assertEquals(constraintViolation.getMessage(), "Please " + "provide work hours."));

        assertThat(constraintViolations.size()).isOne();
    }

    @Test
    @SneakyThrows
    public void testWithGivenInvalidWorkHours() {
        supermarketDto.setWorkHours("0:0");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();

        Set<ConstraintViolation<SupermarketDto>> constraintViolations = validator.validate(supermarketDto);

        constraintViolations.forEach(constraintViolation -> assertEquals(constraintViolation.getMessage(), "Please provide valid work hours in format {HH:mm}-{HH:mm}."));

        assertThat(constraintViolations.size()).isOne();
    }
}