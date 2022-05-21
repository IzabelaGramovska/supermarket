package com.supermarketapi.controller;

import com.sipios.springsearch.anotation.SearchSpec;
import com.supermarketapi.dto.PurchaseDto;
import com.supermarketapi.dto.PurchaseDtoResponse;
import com.supermarketapi.exception.NotEnoughMoneyException;
import com.supermarketapi.model.Purchase;
import com.supermarketapi.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.supermarketapi.common.ExceptionMessages.NOT_ENOUGH_MONEY;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {
    private final PurchaseService purchaseService;

    @Autowired
    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping("/buy-items-from-supermarket")
    public ResponseEntity<PurchaseDtoResponse> buyItemsFromSupermarket(@Valid @RequestBody PurchaseDto purchaseDto) {
        if (purchaseDto.getPaymentType().toString().equals("CASH") && Objects.isNull(purchaseDto.getCashAmount())) {
            throw new NotEnoughMoneyException(NOT_ENOUGH_MONEY);
        }
        return new ResponseEntity<>(purchaseService.buyItemsFromSupermarket(purchaseDto), HttpStatus.OK);
    }

    @GetMapping("/get-all-purchases")
    public ResponseEntity<List<PurchaseDto>> getAllPurchases() {
        return new ResponseEntity<>(purchaseService.getAllPurchases(), HttpStatus.OK);
    }

    @GetMapping("/get-all-purchases-filtered")
    public ResponseEntity<Page<PurchaseDto>> getAllPurchasesFiltered(@SearchSpec Specification<Purchase> specification,
                                                                     org.springframework.data.domain.Pageable pageable) {
        return new ResponseEntity<>(purchaseService.getPurchasesFiltered(Specification.where(specification),
                pageable), HttpStatus.OK);
    }

    @GetMapping("/export-purchases-in-csv")
    public void exportAllPurchasesInCsv(HttpServletResponse httpServletResponse,
                                        @SearchSpec Specification<Purchase> specification, Pageable pageable) throws IOException {
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());

        httpServletResponse.setContentType("text/csv");
        httpServletResponse.setHeader("Content-Disposition", "attachment; filename=purchases_" + currentDateTime + ".csv");

        purchaseService.exportCsvFileWithPurchases(specification, httpServletResponse.getWriter(), pageable);
    }

    @RequestMapping(value = "/import-csv-file", method = RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public List<Purchase> uploadFile(@RequestPart(value = "file") MultipartFile multiPartFile) throws IOException {
        return purchaseService.importPurchasesFromFile(multiPartFile);
    }
}
