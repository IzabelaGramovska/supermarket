package com.supermarketapi.service;

import com.supermarketapi.dto.AddItemsToSupermarketDto;
import com.supermarketapi.dto.AddItemsToSupermarketDtoResponse;
import com.supermarketapi.dto.SupermarketDto;
import com.supermarketapi.dto.SupermarketDtoResponse;

public interface SupermarketService {
    SupermarketDto createSupermarket(SupermarketDto supermarketDto);

    AddItemsToSupermarketDtoResponse addItemsToSupermarket(AddItemsToSupermarketDto addItemsToSupermarketDto);

    SupermarketDtoResponse getSupermarketById(String supermarketId);

    SupermarketDto supermarketUpdate(SupermarketDto supermarketDto, String supermarketId);
}
