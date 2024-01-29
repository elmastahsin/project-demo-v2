package com.company.projectdemo.controller;

import com.company.projectdemo.dto.ResponseWrapper;
import com.company.projectdemo.dto.TransactionDTO;
import com.company.projectdemo.dto.TransactionDTO;
import com.company.projectdemo.entity.Transaction;
import com.company.projectdemo.repository.filter.FilterCriteria;
import com.company.projectdemo.repository.filter.GenericSpecification;
import com.company.projectdemo.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<ResponseWrapper> createTransaction(@RequestBody TransactionDTO transactionDTO) {

        transactionService.save(transactionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("transaction successfully created", transactionDTO, HttpStatus.CREATED));
    }


    //get

    @GetMapping
    public ResponseEntity<ResponseWrapper> getTransaction() {
        List<TransactionDTO> transactions = transactionService.listAll();
        return ResponseEntity.ok(new ResponseWrapper("transactions successfully retrieved", transactions, HttpStatus.OK));

    }


    // update
    @PutMapping
    public ResponseEntity<ResponseWrapper> updateTransaction(@RequestBody TransactionDTO transactionDTO) {
        transactionService.update(transactionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("transaction successfully updated", transactionDTO, HttpStatus.CREATED));
    }

    //filter
    @GetMapping("/{search}")
    public ResponseEntity<ResponseWrapper> getTransactionBySearch(@PathVariable("search") String search) {
        List<TransactionDTO> transaction = transactionService.findByFilter(search);
        return ResponseEntity.ok(new ResponseWrapper("transaction successfully retrieved", transaction, HttpStatus.OK));
    }

    @GetMapping("/filter")
    public ResponseEntity<ResponseWrapper> getTransactions(
            @RequestParam Map<String, String> allParams) {
        //all entries should be trimmed

        List<FilterCriteria> criteriaList = allParams.entrySet().stream()
                .map(entry -> new FilterCriteria(entry.getKey(), "equals", entry.getValue()))
                .collect(Collectors.toList());

        Specification<Transaction> spec = new GenericSpecification<>(criteriaList);

        List<TransactionDTO> transactions = transactionService.getTransactionsBySpecification(spec);
        if(!transactions.isEmpty()) return ResponseEntity.ok(new ResponseWrapper("transaction successfully filtered", transactions, HttpStatus.OK));
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper("transaction not found", HttpStatus.NOT_FOUND));
    }


}
