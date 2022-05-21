package com.supermarketapi.service.impl;

import com.supermarketapi.common.ItemType;
import com.supermarketapi.dto.AddItemsToSupermarketDto;
import com.supermarketapi.dto.AddItemsToSupermarketDtoResponse;
import com.supermarketapi.dto.SupermarketDto;
import com.supermarketapi.dto.SupermarketDtoResponse;
import com.supermarketapi.exception.SupermarketAlreadyExists;
import com.supermarketapi.exception.SupermarketNotFoundException;
import com.supermarketapi.model.Item;
import com.supermarketapi.model.Supermarket;
import com.supermarketapi.repository.ItemRepository;
import com.supermarketapi.repository.SupermarketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.supermarketapi.common.ExceptionMessages.SUPERMARKET_ALREADY_EXISTS;
import static com.supermarketapi.common.ExceptionMessages.SUPERMARKET_NOT_FOUND;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@RunWith(MockitoJUnitRunner.class)
class SupermarketServiceImplTest {
    @Mock
    ItemRepository itemRepository;

    @Mock
    SupermarketRepository supermarketRepository;

    @Mock
    ModelMapper modelMapper;

    @InjectMocks
    private SupermarketServiceImpl underTest;

    private Supermarket supermarket;
    private SupermarketDto supermarketDto;
    private List<String> itemIds;
    private List<Item> itemList;
    private Item itemOne;
    private Item itemTwo;


    @BeforeEach
    void setUp() {
        supermarketDto = new SupermarketDto("Kaufland", "Bulgaria", "Sofia",
                "Knyaginya Klementina 189", "0895678900", "07:00-22:30");

        itemIds = new ArrayList<>();
        itemList = new ArrayList<>();
        itemOne = new Item(UUID.randomUUID().toString(), "Pizza", 1.20, ItemType.FOOD);
        itemTwo = new Item(UUID.randomUUID().toString(), "Orange juice", 3.10, ItemType.DRINKS);
        itemList.add(itemOne);
        itemList.add(itemTwo);

        supermarket = new Supermarket(UUID.randomUUID().toString(), "Kaufland", "Bulgaria", "Sofia",
                "Knyaginya Klementina 189", "0895678900", "07:00-22:30", itemList);

        Mockito.when(modelMapper.map(supermarketDto, Supermarket.class)).thenReturn(supermarket);
        Mockito.when(modelMapper.map(supermarket, SupermarketDto.class)).thenReturn(supermarketDto);
    }

    @Test
    void createSupermarketShouldCreateSupermarket() {
        Mockito.when(supermarketRepository.save(Mockito.any())).thenReturn(supermarket);

        SupermarketDto newSupermarket = underTest.createSupermarket(supermarketDto);

        assertThat(newSupermarket.getName()).isEqualTo(supermarketDto.getName());
        assertThat(newSupermarket.getCountry()).isEqualTo(supermarketDto.getCountry());
        assertThat(newSupermarket.getCity()).isEqualTo(supermarketDto.getCity());
        assertThat(newSupermarket.getStreet()).isEqualTo(supermarketDto.getStreet());
        assertThat(newSupermarket.getPhoneNumber()).isEqualTo(supermarketDto.getPhoneNumber());
        assertThat(newSupermarket.getWorkHours()).isEqualTo(supermarketDto.getWorkHours());
    }

    @Test
    void createSupermarketThrowsException() {
        Mockito.when(supermarketRepository.existsSupermarketByName(supermarket.getName())).
                thenReturn(true).thenThrow(new SupermarketAlreadyExists(SUPERMARKET_ALREADY_EXISTS));

        Throwable exception = assertThrows(SupermarketAlreadyExists.class,
                () -> underTest.createSupermarket(supermarketDto));

        assertThat(SUPERMARKET_ALREADY_EXISTS).isEqualTo(exception.getMessage());
    }

    @Test
    void addItemsToSupermarketShouldAddItems() {
        Mockito.when(supermarketRepository.findById(supermarket.getId())).thenReturn(Optional.of(supermarket));
        Mockito.when(itemRepository.findById(itemOne.getId())).thenReturn(Optional.of(itemOne));
        Mockito.when(itemRepository.findById(itemTwo.getId())).thenReturn(Optional.of(itemTwo));
        Mockito.when(supermarketRepository.save(Mockito.any())).thenReturn(supermarket);

        itemIds.add(itemOne.getId());
        itemIds.add(itemTwo.getId());

        AddItemsToSupermarketDtoResponse addItemsToSupermarketDtoResponse =
                underTest.addItemsToSupermarket(new AddItemsToSupermarketDto(supermarket.getId(), itemIds));

        assertThat(addItemsToSupermarketDtoResponse.getItemsNames().size()).isEqualTo(itemIds.size());
    }

    @Test
    void addItemsToSupermarketThrowsException() {
        Mockito.when(supermarketRepository.findSupermarketById(supermarket.getId())).
                thenThrow(new SupermarketNotFoundException(SUPERMARKET_NOT_FOUND));
        Mockito.when(itemRepository.findById(itemOne.getId())).thenReturn(Optional.of(itemOne));
        Mockito.when(itemRepository.findById(itemTwo.getId())).thenReturn(Optional.of(itemTwo));

        itemIds.add(itemOne.getId());
        itemIds.add(itemTwo.getId());

        Throwable exception = assertThrows(SupermarketNotFoundException.class,
                () -> underTest.addItemsToSupermarket(new AddItemsToSupermarketDto(supermarket.getId(), itemIds)));

        assertThat(SUPERMARKET_NOT_FOUND).isEqualTo(exception.getMessage());
    }

    @Test
    void getSupermarketByIdShouldReturnSupermarket() {
        Mockito.when(supermarketRepository.findById(supermarket.getId())).thenReturn(Optional.of(supermarket));
        SupermarketDtoResponse foundSupermarket = underTest.getSupermarketById(supermarket.getId());
        assertThat(foundSupermarket.getName()).isEqualTo(supermarket.getName());
        assertThat(foundSupermarket.getCountry()).isEqualTo(supermarket.getCountry());
        assertThat(foundSupermarket.getCity()).isEqualTo(supermarket.getCity());
        assertThat(foundSupermarket.getStreet()).isEqualTo(supermarket.getStreet());
        assertThat(foundSupermarket.getPhoneNumber()).isEqualTo(supermarket.getPhoneNumber());
        assertThat(foundSupermarket.getWorkHours()).isEqualTo(supermarket.getWorkHours());
    }

    @Test
    void getSupermarketByIdShouldThrowException() {
        Mockito.when(supermarketRepository.findById(supermarket.getId())).thenThrow(new SupermarketNotFoundException(SUPERMARKET_NOT_FOUND));
        Throwable exception = assertThrows(SupermarketNotFoundException.class, () -> underTest.getSupermarketById(supermarket.getId()));
        assertThat(SUPERMARKET_NOT_FOUND).isEqualTo(exception.getMessage());
    }

    @Test
    void supermarketUpdateShouldUpdateSupermarket() {
        Mockito.when(supermarketRepository.findSupermarketById(supermarket.getId())).thenReturn(Optional.of(supermarket));
        Mockito.when(supermarketRepository.save(Mockito.any())).thenReturn(supermarket);

        SupermarketDto newSupermarket = underTest.supermarketUpdate(supermarketDto, supermarket.getId());

        assertThat(newSupermarket.getName()).isEqualTo(supermarketDto.getName());
        assertThat(newSupermarket.getCountry()).isEqualTo(supermarketDto.getCountry());
        assertThat(newSupermarket.getCity()).isEqualTo(supermarketDto.getCity());
        assertThat(newSupermarket.getStreet()).isEqualTo(supermarketDto.getStreet());
        assertThat(newSupermarket.getPhoneNumber()).isEqualTo(supermarketDto.getPhoneNumber());
        assertThat(newSupermarket.getWorkHours()).isEqualTo(supermarketDto.getWorkHours());
    }

    @Test
    void setSupermarketUpdateThrowsException() {
        Mockito.when(supermarketRepository.findSupermarketById(supermarket.getId())).
                thenThrow(new SupermarketNotFoundException(SUPERMARKET_NOT_FOUND));

        Throwable exception = assertThrows(SupermarketNotFoundException.class,
                () -> underTest.supermarketUpdate(supermarketDto, supermarket.getId()));

        assertThat(SUPERMARKET_NOT_FOUND).isEqualTo(exception.getMessage());
    }
}