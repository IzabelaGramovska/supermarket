package com.supermarketapi.service.impl;

import com.supermarketapi.dto.ItemDto;
import com.supermarketapi.model.Item;
import com.supermarketapi.repository.ItemRepository;
import com.supermarketapi.service.ItemService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
        modelMapper = new ModelMapper();
    }

    @Override
    public ItemDto createItem(ItemDto itemDto) {
        Item itemToSave = modelMapper.map(itemDto, Item.class);

        Item newItem = itemRepository.save(itemToSave);

        return modelMapper.map(newItem, ItemDto.class);
    }
}
