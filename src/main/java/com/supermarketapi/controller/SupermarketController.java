package com.supermarketapi.controller;

import com.supermarketapi.dto.AddItemsToSupermarketDto;
import com.supermarketapi.dto.AddItemsToSupermarketDtoResponse;
import com.supermarketapi.dto.SupermarketDto;
import com.supermarketapi.dto.SupermarketDtoResponse;
import com.supermarketapi.service.SupermarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/supermarkets")
public class SupermarketController {
    private final SupermarketService supermarketService;

    @Autowired
    public SupermarketController(SupermarketService supermarketService) {
        this.supermarketService = supermarketService;
    }

    @PostMapping("/create-supermarket")
    public ResponseEntity<SupermarketDto> createSupermarket(@Valid @RequestBody SupermarketDto supermarketDto) {
        return new ResponseEntity<>(supermarketService.createSupermarket(supermarketDto), HttpStatus.CREATED);
    }

    @PostMapping("/add-items-to-supermarket")
    public ResponseEntity<AddItemsToSupermarketDtoResponse> addItemsToSupermarket(@Valid @RequestBody AddItemsToSupermarketDto addItemsToSupermarketDto) {
        return new ResponseEntity<>(supermarketService.addItemsToSupermarket(addItemsToSupermarketDto), HttpStatus.OK);
    }

    @GetMapping("/get-supermarket-by-id-{supermarketId}")
    public ResponseEntity<SupermarketDtoResponse> getSupermarketById(@Valid @PathVariable(value = "supermarketId") String supermarketId) {
        return new ResponseEntity<>(supermarketService.getSupermarketById(supermarketId), HttpStatus.OK);
    }

    @PutMapping("/supermarket-update-{supermarketId}")
    public ResponseEntity<SupermarketDto> supermarketUpdate(@Valid @RequestBody SupermarketDto supermarketDto, @PathVariable(value = "supermarketId") String supermarketId) {

        return new ResponseEntity<>(supermarketService.supermarketUpdate(supermarketDto, supermarketId), HttpStatus.OK);
    }
}
