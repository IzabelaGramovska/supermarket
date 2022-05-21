package com.supermarketapi.service.impl;

import com.sipios.springsearch.anotation.SearchSpec;
import com.supermarketapi.common.ExceptionMessages;
import com.supermarketapi.common.PaymentType;
import com.supermarketapi.dto.ItemDto;
import com.supermarketapi.dto.PurchaseDto;
import com.supermarketapi.dto.PurchaseDtoResponse;
import com.supermarketapi.dto.SupermarketDto;
import com.supermarketapi.exception.ItemNotFoundException;
import com.supermarketapi.exception.NotEnoughMoneyException;
import com.supermarketapi.exception.PurchaseNotFoundException;
import com.supermarketapi.exception.SupermarketNotFoundException;
import com.supermarketapi.model.Item;
import com.supermarketapi.model.Purchase;
import com.supermarketapi.model.Supermarket;
import com.supermarketapi.repository.ItemRepository;
import com.supermarketapi.repository.PurchaseRepository;
import com.supermarketapi.repository.SupermarketRepository;
import com.supermarketapi.service.PurchaseService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.supermarketapi.common.ExceptionMessages.*;

@Service
public class PurchaseServiceImpl implements PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final SupermarketRepository supermarketRepository;
    private final ItemRepository itemRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PurchaseServiceImpl(PurchaseRepository purchaseRepository, SupermarketRepository supermarketRepository, ItemRepository itemRepository) {
        this.purchaseRepository = purchaseRepository;
        this.supermarketRepository = supermarketRepository;
        this.itemRepository = itemRepository;
        modelMapper = new ModelMapper();
    }

    @Override
    public PurchaseDtoResponse buyItemsFromSupermarket(PurchaseDto purchaseDto) {
        Supermarket supermarket = supermarketRepository.findById(purchaseDto.getSupermarketId())
                .orElseThrow(() -> new SupermarketNotFoundException(SUPERMARKET_NOT_FOUND));

        List<Item> items = findItemsByIdFromList(purchaseDto.getItemsIds());

        Purchase newPurchase = purchaseRepository.save(
                new Purchase(UUID.randomUUID().toString(), supermarket, items,
                        purchaseDto.getPaymentType(), calculateTotalAmount(items),
                        purchaseDto.getCashAmount(), LocalTime.now()));

        return getPurchaseDtoResponse(newPurchase);
    }

    @Override
    public Page<PurchaseDto> getPurchasesFiltered(@SearchSpec Specification<Purchase> specification, Pageable pageable) {
        Page<Purchase> all = purchaseRepository.findAll(Specification.where(specification), pageable);

        Page<PurchaseDto> purchasesDto = new PageImpl<>(all.stream().map(purchase ->
                modelMapper.map(purchase, PurchaseDto.class)).collect(Collectors.toList()));

        if (purchasesDto.isEmpty()) {
            throw new PurchaseNotFoundException(ExceptionMessages.PURCHASE_NOT_FOUND);
        }

        return purchasesDto;
    }

    @Override
    public List<PurchaseDto> getAllPurchases() {
        List<PurchaseDto> purchasesDto = new ArrayList<>();

        List<Purchase> purchases = purchaseRepository.findAll();

        for (Purchase purchase : purchases) {
            List<String> idsToString = mapItemsIdToString(purchase.getItems());
            PurchaseDto purchaseDto = modelMapper.map(purchase, PurchaseDto.class);
            purchaseDto.setItemsIds(idsToString);

            purchasesDto.add(purchaseDto);
        }

        return purchasesDto;
    }

    @Override
    public void exportCsvFileWithPurchases(Specification<Purchase> specification, Writer writer, Pageable pageable) throws IOException {
        Page<Purchase> purchases = purchaseRepository.findAll(Specification.where(specification), pageable);

        try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            csvPrinter.printRecord("Supermarket name, Supermarket country, Supermarket city, Supermarket street, " +
                    " Supermarket working hours, " + " Supermarket phone number, Item name, Item price, Item type," +
                    "Total money, Given money, Change, Time of payment");

            for (Purchase purchase : purchases) {
                csvPrinter.printRecord(purchase.getSupermarket().getName(),
                        purchase.getSupermarket().getCountry(),
                        purchase.getSupermarket().getCity(),
                        purchase.getSupermarket().getStreet(),
                        purchase.getSupermarket().getWorkHours(),
                        purchase.getSupermarket().getPhoneNumber(),
                        purchase.getItems().stream().map(item -> item.getName() + "," + item.getPrice() + "," + item.getItemType()).collect(Collectors.joining(",")),
                        purchase.getPaymentType(),
                        purchase.getTotalCashAmount(),
                        purchase.getMoneyGiven(),
                        calculateChange(purchase.getMoneyGiven(), purchase.getTotalCashAmount()),
                        purchase.getTimeOfPayment());
            }
        } catch (IOException exception) {
            throw new IOException(CANNOT_EXPORT_FILE);
        }
    }

    @Override
    public List<Purchase> importPurchasesFromFile(MultipartFile multipartFile) throws IOException {
        File file = convertMultipartToFile(multipartFile);

        List<Purchase> purchases = new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(Path.of(file.getPath()), StandardCharsets.US_ASCII)) {
            String line = null;
            int i = 0;

            HashMap<String, Integer> columns = new HashMap<>();

            while ((line = br.readLine()) != null) {
                if (i == 0) {
                    String[] attributes = line.split(",");
                    columns.put(attributes[0], 0);
                    columns.put(attributes[1], 1);
                    columns.put(attributes[2], 2);
                    columns.put(attributes[3], 3);
                    columns.put(attributes[4], 4);
                    columns.put(attributes[5], 5);
                }

                if (i++ != 0) {
                    String[] attributes = line.split(",");
                    Purchase purchase = createUserFromCsvFile(attributes, columns);
                    purchases.add(purchase);
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return purchaseRepository.saveAll(purchases);
    }

    private Purchase createUserFromCsvFile(String[] metadata, HashMap<String, Integer> columns) {
        Purchase purchase = new Purchase();

        purchase.setSupermarket(supermarketRepository.findSupermarketById(metadata[columns.get("supermarket")]).
                orElseThrow(() -> {
                    throw new SupermarketNotFoundException(SUPERMARKET_NOT_FOUND);
                }));
        purchase.setItems(itemRepository.findItemsById(metadata[columns.get("items")]).
                orElseThrow(() -> {
                    throw new ItemNotFoundException(ITEM_NOT_FOUND);
                }));
        purchase.setPaymentType(PaymentType.valueOf(metadata[columns.get("paymentType")]));
        purchase.setTotalCashAmount(Double.parseDouble(metadata[columns.get("totalCashAmount")]));
        purchase.setMoneyGiven(Double.parseDouble(metadata[columns.get("moneyGiven")]));
        purchase.setTimeOfPayment(LocalTime.parse(metadata[columns.get("timeOfPayment")]));

        return purchase;
    }

    private File convertMultipartToFile(MultipartFile multipartFile) throws IOException {
        File convertedFile = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));

        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(multipartFile.getBytes());
        }

        return convertedFile;
    }

    private PurchaseDtoResponse getPurchaseDtoResponse(Purchase purchase) {
        return new PurchaseDtoResponse(modelMapper.map(purchase.getSupermarket(), SupermarketDto.class),
                mapItemsToItemsDto(purchase.getItems()),
                purchase.getPaymentType(),
                purchase.getTotalCashAmount(),
                purchase.getMoneyGiven(),
                calculateChange(purchase.getMoneyGiven(), purchase.getTotalCashAmount()),
                purchase.getTimeOfPayment());
    }

    private List<String> mapItemsIdToString(List<Item> items) {
        List<String> itemsIdToString = new ArrayList<>();

        for (Item item : items) {
            itemsIdToString.add(item.getId());
        }

        return itemsIdToString;
    }

    private List<Item> findItemsByIdFromList(List<String> itemsIds) {
        List<Item> items = new ArrayList<>();

        for (String itemId : itemsIds) {
            Item item = itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException(ITEM_NOT_FOUND));
            items.add(item);
        }

        return items;
    }

    private double calculateTotalAmount(List<Item> items) {
        double totalAmount = 0;

        for (Item item : items) {
            totalAmount += item.getPrice();
        }

        return totalAmount;
    }

    public double calculateChange(double givenMoney, double totalAmount) {
        if (givenMoney < totalAmount) {
            throw new NotEnoughMoneyException(NOT_ENOUGH_MONEY);
        }

        return givenMoney - totalAmount;
    }

    public List<ItemDto> mapItemsToItemsDto(List<Item> items) {
        List<ItemDto> itemsDto = new ArrayList<>();

        for (Item item : items) {
            itemsDto.add(modelMapper.map(item, ItemDto.class));
        }

        return itemsDto;
    }
}
