package com.supermarketapi.service.impl;

import com.supermarketapi.common.ExceptionMessages;
import com.supermarketapi.dto.*;
import com.supermarketapi.exception.ItemNotFoundException;
import com.supermarketapi.exception.SupermarketAlreadyExists;
import com.supermarketapi.exception.SupermarketNotFoundException;
import com.supermarketapi.model.Item;
import com.supermarketapi.model.Supermarket;
import com.supermarketapi.repository.ItemRepository;
import com.supermarketapi.repository.SupermarketRepository;
import com.supermarketapi.service.SupermarketService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SupermarketServiceImpl implements SupermarketService {
    private final SupermarketRepository supermarketRepository;
    private final ItemRepository itemRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public SupermarketServiceImpl(SupermarketRepository supermarketRepository, ItemRepository itemRepository) {
        this.supermarketRepository = supermarketRepository;
        this.itemRepository = itemRepository;
        modelMapper = new ModelMapper();
    }

    @Override
    public SupermarketDto createSupermarket(SupermarketDto supermarketDto) {
        Supermarket supermarket = modelMapper.map(supermarketDto, Supermarket.class);

        if (supermarketRepository.existsSupermarketByName(supermarket.getName())) {
            throw new SupermarketAlreadyExists(ExceptionMessages.SUPERMARKET_ALREADY_EXISTS);
        }

        Supermarket newSupermarket = supermarketRepository.save(supermarket);

        return modelMapper.map(newSupermarket, SupermarketDto.class);
    }

    @Override
    public AddItemsToSupermarketDtoResponse addItemsToSupermarket(AddItemsToSupermarketDto addItemsToSupermarketDto) {
        Supermarket supermarket = supermarketRepository.findById(addItemsToSupermarketDto.getSupermarketId()).orElseThrow(() -> new SupermarketNotFoundException(ExceptionMessages.SUPERMARKET_NOT_FOUND));

        List<Item> items = findItemsByIdFromList(addItemsToSupermarketDto.getItemsIds());
        supermarket.setItems(items);

        Supermarket savedSupermarket = supermarketRepository.save(supermarket);

        return new AddItemsToSupermarketDtoResponse(savedSupermarket.getId(), findItemsByNameFromList(supermarket.getItems()));
    }

    private List<Item> findItemsByIdFromList(List<String> listOfItemsIds) {
        List<Item> items = new ArrayList<>();

        for (String itemId : listOfItemsIds) {
            Item item = itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException(ExceptionMessages.ITEM_NOT_FOUND));
            items.add(item);
        }

        return items;
    }

    public List<String> findItemsByNameFromList(List<Item> items) {
        List<String> itemNames = new ArrayList<>();

        for (Item item : items) {
            itemNames.add(item.getName());
        }

        return itemNames;
    }

    @Override
    public SupermarketDtoResponse getSupermarketById(String supermarketId) {
        Supermarket supermarket = supermarketRepository.findById(supermarketId).orElseThrow(() -> {
            throw new SupermarketNotFoundException(ExceptionMessages.SUPERMARKET_NOT_FOUND);
        });

        List<ItemDto> itemsDto = mapsItemsToItemsDto(supermarket.getItems());

        return new SupermarketDtoResponse(supermarket.getName(), supermarket.getCountry(), supermarket.getCity(), supermarket.getStreet(), supermarket.getPhoneNumber(), supermarket.getWorkHours(), itemsDto);
    }

    @Override
    public SupermarketDto supermarketUpdate(SupermarketDto supermarketDto, String supermarketId) {
        Supermarket mappedSupermarket = modelMapper.map(supermarketDto, Supermarket.class);

        Supermarket supermarketToUpdate = supermarketRepository.findById(supermarketId).orElseThrow(() -> {
            throw new SupermarketNotFoundException(ExceptionMessages.SUPERMARKET_NOT_FOUND);
        });

        supermarketToUpdate.setItems(mappedSupermarket.getItems());
        supermarketToUpdate.setWorkHours(mappedSupermarket.getWorkHours());
        supermarketToUpdate.setPhoneNumber(mappedSupermarket.getPhoneNumber());
        supermarketToUpdate.setCountry(mappedSupermarket.getCountry());
        supermarketToUpdate.setCity(mappedSupermarket.getCity());
        supermarketToUpdate.setStreet(mappedSupermarket.getStreet());
        supermarketToUpdate.setName(mappedSupermarket.getName());

        Supermarket updatedSupermarket = supermarketRepository.save(supermarketToUpdate);

        return modelMapper.map(updatedSupermarket, SupermarketDto.class);
    }

    public List<ItemDto> mapsItemsToItemsDto(List<Item> items) {
        List<ItemDto> itemsDto = new ArrayList<>();

        for (Item item : items) {
            itemsDto.add(modelMapper.map(item, ItemDto.class));
        }

        return itemsDto;
    }
}