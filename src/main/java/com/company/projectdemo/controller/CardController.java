package com.company.projectdemo.controller;


import com.company.projectdemo.dto.CardDTO;
import com.company.projectdemo.dto.ResponseWrapper;
import com.company.projectdemo.dto.UserDTO;
import com.company.projectdemo.entity.Card;
import com.company.projectdemo.repository.filter.FilterCriteria;
import com.company.projectdemo.repository.filter.GenericSpecification;
import com.company.projectdemo.service.CardService;
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
@RequestMapping("/card")
@RequiredArgsConstructor
@Tag(name = "Card", description = "Card CRUD operations")
public class CardController {
    private final CardService cardService;


    @PostMapping
    @Operation(summary = "Create a new card")
    @ApiResponse(responseCode = "201", description = "Card successfully created",
            content = @Content(mediaType = "application/json"),
            headers = {@Header(name = "Connection ", description = "keep-alive")})
    public ResponseEntity<ResponseWrapper> createCard(@RequestBody CardDTO card) {
        CardDTO cardDTO = cardService.save(card);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseWrapper("card successfully created", cardDTO, HttpStatus.CREATED));
    }


    @GetMapping()
    @Operation(summary = "Get all cards")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Card successfully retrieved",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = CardDTO.class
                                    )))),
            @ApiResponse(responseCode = "404", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
    })
    public ResponseEntity<ResponseWrapper> getCard() {
        List<CardDTO> cards = cardService.listAll();
        return ResponseEntity.ok(new ResponseWrapper("cards successfully retrieved", cards, HttpStatus.OK));

    }


    // update
    @PutMapping
    @Operation(summary = "Update a card")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Card successfully updated",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = CardDTO.class
                                    )))),
            @ApiResponse(responseCode = "404", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
    })
    public ResponseEntity<ResponseWrapper> updateCard(@RequestBody CardDTO card) {
        cardService.update(card);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("card successfully updated", card, HttpStatus.CREATED));
    }

    //filter
    @GetMapping("/{search}")
    @Operation(summary = "Get card by search")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Card successfully filtered",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = CardDTO.class
                                    )))),
            @ApiResponse(responseCode = "404", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
    })
    public ResponseEntity<ResponseWrapper> getCardBySearch(@PathVariable("search") String search) {
        List<CardDTO> card = cardService.findByFilter(search);
        return ResponseEntity.ok(new ResponseWrapper("card successfully retrieved", card, HttpStatus.OK));
    }

    @GetMapping("/filter")
    @Operation(summary = "Get card by filter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Card successfully filtered",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = CardDTO.class
                                    )))),
            @ApiResponse(responseCode = "404", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
    })
    public ResponseEntity<ResponseWrapper> getFilterCards(
            @RequestParam Map<String, String> allParams) {
        //all entries should be trimmed

        List<FilterCriteria> criteriaList = allParams.entrySet().stream()
                .map(entry -> new FilterCriteria(entry.getKey().trim(), "equals", entry.getValue().trim()))
                .collect(Collectors.toList());

        Specification<Card> spec = new GenericSpecification<>(criteriaList);

        List<CardDTO> cards = cardService.getCardsBySpecification(spec);
        if (!cards.isEmpty())
            return ResponseEntity.ok(new ResponseWrapper("card successfully filtered", cards, HttpStatus.OK));
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper("card not found", HttpStatus.NOT_FOUND));
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a card")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Card successfully deleted",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = CardDTO.class
                                    )))),
            @ApiResponse(responseCode = "404", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
    })
    public ResponseEntity<ResponseWrapper> deleteCard(@PathVariable Long id) {
        cardService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper("card successfully deleted", HttpStatus.OK));
    }


}
