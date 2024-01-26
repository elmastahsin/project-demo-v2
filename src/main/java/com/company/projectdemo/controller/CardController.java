package com.company.projectdemo.controller;


import com.company.projectdemo.dto.CardDTO;
import com.company.projectdemo.dto.ResponseWrapper;
import com.company.projectdemo.dto.CardDTO;
import com.company.projectdemo.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/card")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;


    @PostMapping
    public ResponseEntity<ResponseWrapper> createCard(@RequestBody CardDTO card) {
        cardService.save(card);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("card successfully created",card, HttpStatus.CREATED));
    }


    @GetMapping
    public ResponseEntity<ResponseWrapper> getCard() {
        List<CardDTO> cards = cardService.listAll();
        return ResponseEntity.ok(new ResponseWrapper("cards successfully retrieved", cards, HttpStatus.OK));

    }


    // update
    @PutMapping
    public ResponseEntity<ResponseWrapper> updateCard(@RequestBody CardDTO card) {
        cardService.update(card);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("card successfully updated", card,HttpStatus.CREATED));
    }

    //filter
    @GetMapping("/{search}")
    public ResponseEntity<ResponseWrapper> getCardBySearch(@PathVariable("search") String search) {
        List<CardDTO> card = cardService.findByFilter(search);
        return ResponseEntity.ok(new ResponseWrapper("card successfully retrieved", card, HttpStatus.OK));
    }


}
