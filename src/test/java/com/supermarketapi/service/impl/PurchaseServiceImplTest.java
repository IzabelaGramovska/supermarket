package com.supermarketapi.service.impl;

import com.supermarketapi.common.ItemType;
import com.supermarketapi.common.PaymentType;
import com.supermarketapi.dto.PurchaseDto;
import com.supermarketapi.dto.PurchaseDtoResponse;
import com.supermarketapi.exception.SupermarketNotFoundException;
import com.supermarketapi.model.Item;
import com.supermarketapi.model.Purchase;
import com.supermarketapi.model.Supermarket;
import com.supermarketapi.repository.ItemRepository;
import com.supermarketapi.repository.PurchaseRepository;
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

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.supermarketapi.common.ExceptionMessages.SUPERMARKET_NOT_FOUND;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@RunWith(MockitoJUnitRunner.class)
class PurchaseServiceImplTest {
    @Mock
    PurchaseRepository purchaseRepository;

    @Mock
    private SupermarketRepository supermarketRepository;

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private PurchaseServiceImpl underTest;

    private Supermarket supermarket;
    private Item itemOne;
    private Item itemTwo;
    private Purchase purchaseOne;
    private List<Purchase> purchases;
    private List<String> itemIds;

    @BeforeEach
    void setUp() {
        List<Item> items = new ArrayList<>();
        itemIds = new ArrayList<>();
        purchases = new ArrayList<>();
        itemOne = new Item(UUID.randomUUID().toString(), "Donut", 0.89, ItemType.FOOD);
        itemTwo = new Item(UUID.randomUUID().toString(), "Coca Cola", 1.45, ItemType.DRINKS);
        items.add(itemOne);
        items.add(itemTwo);
        supermarket = new Supermarket(UUID.randomUUID().toString(), "Fantastico", "Bulgaria", "Sofia",
                "Veliko Turnovo 73", "0882421474", "08:00-22:00", items);
        purchaseOne = new Purchase(UUID.randomUUID().toString(), supermarket, items, PaymentType.CASH, 20, 100, LocalTime.now());
        Purchase purchaseTwo = new Purchase(UUID.randomUUID().toString(), supermarket, items, PaymentType.CASH, 20, 50, LocalTime.now());
    }

    @Test
    void buyItemsFromSupermarketShouldBuyItems() {
        Mockito.when(supermarketRepository.findById(supermarket.getId())).thenReturn(Optional.of(supermarket));
        Mockito.when(itemRepository.findById(itemOne.getId())).thenReturn(Optional.of(itemOne));
        Mockito.when(itemRepository.findById(itemTwo.getId())).thenReturn(Optional.of(itemTwo));
        Mockito.when(purchaseRepository.save(Mockito.any())).thenReturn(purchaseOne);

        itemIds.add(itemOne.getId());
        itemIds.add(itemTwo.getId());

        PurchaseDto purchaseDto = new PurchaseDto(supermarket.getId(), itemIds, PaymentType.CASH, 100.0);

        PurchaseDtoResponse purchaseDtoResponse = underTest.buyItemsFromSupermarket(purchaseDto);

        assertThat(purchaseDtoResponse.getItemsDto().size()).isEqualTo(purchaseDto.getItemsIds().size());
        assertThat(purchaseDtoResponse.getPaymentType()).isEqualTo(purchaseDto.getPaymentType());
        assertThat(purchaseDtoResponse.getMoneyGiven()).isEqualTo(purchaseDto.getCashAmount());
    }

    @Test
    void buyItemsFromSupermarketThrowsException() {
        Mockito.when(supermarketRepository.findById(supermarket.getId())).
                thenThrow(new SupermarketNotFoundException(SUPERMARKET_NOT_FOUND));

        PurchaseDto purchaseDto = new PurchaseDto(supermarket.getId(), itemIds, PaymentType.CASH, 100.0);

        Throwable exception = assertThrows(SupermarketNotFoundException.class,
                () -> underTest.buyItemsFromSupermarket(purchaseDto));

        assertThat(SUPERMARKET_NOT_FOUND).isEqualTo(exception.getMessage());
    }

    @Test
    void getAllPurchasesShouldGetAllPurchases() {
        Mockito.when(purchaseRepository.findAll()).thenReturn(purchases);
        List<PurchaseDto> purchasesDto = underTest.getAllPurchases();
        assertThat(purchasesDto.size()).isEqualTo(purchases.size());
    }


    @Test
    void exportPurchaseToCsvShouldExportCsv() throws IOException {
    }


    @Test
    void importPurchasesFromFile() {
    }


    @Test
    void getPurchasesFiltered() {
    }
}