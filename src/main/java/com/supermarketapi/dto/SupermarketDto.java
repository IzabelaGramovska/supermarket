package com.supermarketapi.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.supermarketapi.common.ValidationMessages.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SupermarketDto {
    @NotBlank(message = NOT_ADDED_NAME)
    @Size(max = 64, min = 2, message = SIZE_NAME_NOT_CORRECT)
    //The name must not contain digits or symbols other than ".","-" or "'".
    @Pattern(regexp = "([A-Z])?[a-z]+((\\s)?((\\.|\\-|\\')?(([A-Z])?[a-z]+)+)){1,}", message = NAME_IS_NOT_VALID)
    private String name;

    @NotBlank(message = NOT_ADDED_COUNTRY)
    @Size(min = 1, max = 20, message = COUNTRY_SIZE_NOT_CORRECT)
    /*The regex bellow ensure us following:
    Will not be able to be created country with digits.
    Will not be able to be created country with symbols.*/
    @Pattern(regexp = "[a-zA-Z]{2,}", message = COUNTRY_IS_NOT_VALID)
    private String country;

    @NotBlank(message = NOT_ADDED_CITY)
    @Size(min = 1, max = 30, message = CITY_SIZE_NOT_CORRECT)
    /*The regex bellow ensure us following:
    Will not be able to be created city with digits.
    Will not be able to be created city with symbols.*/
    @Pattern(regexp = "[a-zA-Z]{2,}", message = CITY_IS_NOT_VALID)
    private String city;

    @NotBlank(message = NOT_ADDED_STREET)
    @Size(min = 1, max = 70, message = STREET_SIZE_NOT_CORRECT)
    /*The regex bellow ensure us following:
    Will not be able to be created street with not correct symbols.
    Will not be able to be created street with less than 3 symbols.*/
    @Pattern(regexp = "[#.0-9a-zA-Z\\s,-]{3,}", message = STREET_IS_NOT_VALID)
    private String street;

    @NotBlank(message = NOT_ADDED_PHONE_NUMBER)
    /*The regex bellow ensure us following:
    Will not be able to be created phone number which is not starting with 089, 088 or 087.*/
    @Pattern(regexp = "(089|088|087){1}\\d{7}", message = PHONE_NUMBER_IS_NOT_VALID)
    private String phoneNumber;

    @NotBlank(message = NOT_ADDED_WORK_HOURS)
    @Pattern(regexp = "(2[0-3]|[01]?[0-9])\\:([0-5]?[0-9])\\-(2[0-3]|[01]?[0-9])\\:([0-5]?[0-9])",
            message = WORK_HOURS_NOT_VALID)
    private String workHours;
}
