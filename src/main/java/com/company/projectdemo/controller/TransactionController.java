package com.company.projectdemo.controller;

import com.company.projectdemo.dto.ResponseWrapper;
import com.company.projectdemo.dto.TransactionDTO;
import com.company.projectdemo.entity.Card;
import com.company.projectdemo.entity.Transaction;
import com.company.projectdemo.repository.filter.FilterCriteria;
import com.company.projectdemo.repository.filter.GenericSpecification;
import com.company.projectdemo.service.CardService;
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
    private final CardService cardService;

    @PostMapping
    public ResponseEntity<ResponseWrapper> createTransaction(@RequestBody TransactionDTO transaction) {

        TransactionDTO transactionDTO = transactionService.save(transaction);

        if (transactionDTO.getCardno() == null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseWrapper("No card available", HttpStatus.BAD_REQUEST));
        else if (transactionDTO.getNote().equals("Not enough money to make transaction"))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseWrapper(transactionDTO.getNote(), HttpStatus.BAD_REQUEST));
        else if (transactionDTO.getNote().equals("Amount money must be positive"))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseWrapper(transactionDTO.getNote(), HttpStatus.BAD_REQUEST));
        else
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
        if (transactionDTO.getCardno() == null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseWrapper("No card available", HttpStatus.BAD_REQUEST));
        else if (transactionDTO.getNote().equals("Not enough money to make transaction"))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseWrapper(transactionDTO.getNote(), HttpStatus.BAD_REQUEST));
        else
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("transaction successfully created", transactionDTO, HttpStatus.CREATED));

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
        if (!transactions.isEmpty())
            return ResponseEntity.ok(new ResponseWrapper("transaction successfully filtered", transactions, HttpStatus.OK));
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper("transaction not found", HttpStatus.NOT_FOUND));
    }

    @GetMapping("/filter-by-card/{cardno}")
    public ResponseEntity<ResponseWrapper> getTransactions(
            @PathVariable Long cardno) {

        List<TransactionDTO> transactions = transactionService.findByCardNo(cardno);
        if (!transactions.isEmpty())
            return ResponseEntity.ok(new ResponseWrapper("transaction successfully filtered", transactions, HttpStatus.OK));
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper("transaction not found", HttpStatus.NOT_FOUND));
    }


}
