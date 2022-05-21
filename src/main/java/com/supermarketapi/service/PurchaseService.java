package com.supermarketapi.service;

import com.supermarketapi.dto.PurchaseDto;
import com.supermarketapi.dto.PurchaseDtoResponse;
import com.supermarketapi.model.Purchase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public interface PurchaseService {
    PurchaseDtoResponse buyItemsFromSupermarket(PurchaseDto purchaseDto);

    Page<PurchaseDto> getPurchasesFiltered(Specification<Purchase> specification, org.springframework.data.domain.Pageable pageable);

    List<PurchaseDto> getAllPurchases();

    void exportCsvFileWithPurchases(Specification<Purchase> specification, Writer writer, Pageable pageable) throws IOException;

    List<Purchase> importPurchasesFromFile(MultipartFile multipartFile) throws IOException;
}
