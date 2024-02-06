package com.company.projectdemo.controller;


import com.company.projectdemo.dto.CardDTO;
import com.company.projectdemo.dto.ResponseWrapper;
import com.company.projectdemo.entity.Card;
import com.company.projectdemo.repository.filter.FilterCriteria;
import com.company.projectdemo.repository.filter.GenericSpecification;
import com.company.projectdemo.service.CardService;
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
public class CardController {
    private final CardService cardService;


    @PostMapping
    public ResponseEntity<ResponseWrapper> createCard(@RequestBody CardDTO card) {
        CardDTO cardDTO = cardService.save(card);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("card successfully created", cardDTO, HttpStatus.CREATED));
    }


    @GetMapping()
    public ResponseEntity<ResponseWrapper> getCard() {
        List<CardDTO> cards = cardService.listAll();
        return ResponseEntity.ok(new ResponseWrapper("cards successfully retrieved", cards, HttpStatus.OK));

    }


    // update
    @PutMapping
    public ResponseEntity<ResponseWrapper> updateCard(@RequestBody CardDTO card) {
        cardService.update(card);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("card successfully updated", card, HttpStatus.CREATED));
    }

    //filter
    @GetMapping("/{search}")
    public ResponseEntity<ResponseWrapper> getCardBySearch(@PathVariable("search") String search) {
        List<CardDTO> card = cardService.findByFilter(search);
        return ResponseEntity.ok(new ResponseWrapper("card successfully retrieved", card, HttpStatus.OK));
    }

    @GetMapping("/filter")
    public ResponseEntity<ResponseWrapper> getCards(
            @RequestParam Map<String, String> allParams) {
        //all entries should be trimmed

        List<FilterCriteria> criteriaList = allParams.entrySet().stream()
                .map(entry -> new FilterCriteria(entry.getKey(), "equals", entry.getValue()))
                .collect(Collectors.toList());

        Specification<Card> spec = new GenericSpecification<>(criteriaList);

        List<CardDTO> cards = cardService.getCardsBySpecification(spec);
        if (!cards.isEmpty())
            return ResponseEntity.ok(new ResponseWrapper("card successfully filtered", cards, HttpStatus.OK));
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper("card not found", HttpStatus.NOT_FOUND));
    }


}
