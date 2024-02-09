package com.company.projectdemo.controller;

import com.company.projectdemo.dto.ResponseWrapper;
import com.company.projectdemo.dto.TransactionDTO;
import com.company.projectdemo.entity.Transaction;
import com.company.projectdemo.repository.filter.FilterCriteria;
import com.company.projectdemo.repository.filter.GenericSpecification;
import com.company.projectdemo.service.CardService;
import com.company.projectdemo.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Transaction", description = "Transaction CRUD operations")
public class TransactionController {
    private final TransactionService transactionService;
    private final CardService cardService;

    @PostMapping
    @Operation(summary = "Create a new transaction")
    @ApiResponse(responseCode = "201", description = "Transaction successfully created",
            content = @Content(mediaType = "application/json"),
            headers = {@Header(name = "Connection ", description = "keep-alive")})
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
    @Operation(summary = "Get all transactions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction successfully retrieved",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = TransactionDTO.class
                                    )))),
            @ApiResponse(responseCode = "404", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
    })
    public ResponseEntity<ResponseWrapper> getTransaction() {
        List<TransactionDTO> transactions = transactionService.listAll();
        return ResponseEntity.ok(new ResponseWrapper("transactions successfully retrieved", transactions, HttpStatus.OK));

    }


    // update
    @PutMapping
    @Operation(summary = "Update a transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction successfully updated",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = TransactionDTO.class
                                    )))),
            @ApiResponse(responseCode = "404", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
    })
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction successfully filtered",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = TransactionDTO.class
                                    )))),
            @ApiResponse(responseCode = "404", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
    })
    @Operation(summary = "Get transaction by search")
    public ResponseEntity<ResponseWrapper> getTransactionBySearch(@PathVariable("search") String search) {
        List<TransactionDTO> transaction = transactionService.findByFilter(search);
        return ResponseEntity.ok(new ResponseWrapper("transaction successfully retrieved", transaction, HttpStatus.OK));
    }

    @GetMapping("/filter")
    @Operation(summary = "Filter transactions by criteria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction successfully filtered",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = TransactionDTO.class
                                    )))),
            @ApiResponse(responseCode = "404", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
    })
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
    @Operation(summary = "Filter transactions by card number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction successfully filtered by card no",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = TransactionDTO.class
                                    )))),
            @ApiResponse(responseCode = "404", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
    })
    public ResponseEntity<ResponseWrapper> getTransactions(
            @PathVariable Long cardno) {

        List<TransactionDTO> transactions = transactionService.findByCardNo(cardno);
        if (!transactions.isEmpty())
            return ResponseEntity.ok(new ResponseWrapper("transaction successfully filtered", transactions, HttpStatus.OK));
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper("transaction not found", HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction successfully deleted",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = TransactionDTO.class
                                    )))),
            @ApiResponse(responseCode = "404", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
    })
    public ResponseEntity<ResponseWrapper> deleteTransaction(@PathVariable("id") Long id) {
        transactionService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper("transaction successfully deleted", HttpStatus.OK));
    }

}
