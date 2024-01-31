package com.company.projectdemo.service.impl;

import com.company.projectdemo.dto.CardDTO;
import com.company.projectdemo.dto.TransactionDTO;
import com.company.projectdemo.entity.Card;
import com.company.projectdemo.entity.LogHistory;
import com.company.projectdemo.entity.Transaction;
import com.company.projectdemo.mapper.MapperUtil;
import com.company.projectdemo.repository.CardRepository;
import com.company.projectdemo.service.CardService;
import com.company.projectdemo.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final MapperUtil mapper;
    private final LogService logService;
    private final CardRepository cardRepository;


    @Override
    public CardDTO findById(Long aLong) {
        return mapper.convert(cardRepository.findById(aLong), new CardDTO());

    }

    @Override
    public List<CardDTO> findByAll(CardDTO cardDTO) {
        return null;
    }

    @Override
    public CardDTO save(CardDTO cardDTO) {
        Card card = mapper.convert(cardDTO, new Card());

        LogHistory log = new LogHistory();
//        Map<String, Object> changedFields = EntityComparator.findChangedFields(new Card(), card);

        log.setTableName("cards");
        log.setOperation("insert");
//        log.setChangedColumn(changedFields);
        log.setChangedBy(card.getName());
        log.setChangedAt(LocalDateTime.now());
        logService.save(log);
        card.setProjectid(cardDTO.getProjectid()+23456);
       cardRepository.save(card);
        return mapper.convert(card, new CardDTO());
    }

    @Override
    public void update(CardDTO cardDTO) {
        Card cardToUpdate = mapper.convert(cardDTO, new Card());
        Card savedCard = cardRepository.findById(cardToUpdate.getCardno()).get();
        Map<String, Object> changedFields = EntityComparator.findChangedFields(savedCard, cardToUpdate);

        LogHistory log = new LogHistory();
        log.setTableName("cards");
        log.setOperation("update");
        log.setChangedColumn(changedFields);
        log.setChangedBy(savedCard.getName());
        log.setChangedAt(LocalDateTime.now());
        logService.save(log);

        cardRepository.save(cardToUpdate);
    }


    @Override
    public boolean isExist(CardDTO cardDTO) {
        return findByAll(cardDTO).stream().filter(userDto1 -> userDto1.getCardno().equals(cardDTO.getCardno())).count() > 0;


    }

    @Override
    public List<CardDTO> findByFilter(String search) {
        List<Card> cardList = cardRepository.findByFilter(search);

        return cardList.stream().map(card -> mapper.convert(card, new CardDTO())).collect(Collectors.toList());
    }


    @Override
    public List<CardDTO> listAll() {
        List<Card> cardList = cardRepository.findAll();
        return cardList.stream().map(card -> mapper.convert(card, new CardDTO())).collect(Collectors.toList());
    }
    @Override
    public List<CardDTO> getCardsBySpecification(Specification<Card> spec) {
        List<Card> cards = cardRepository.findAll(spec);
        if (!cards.isEmpty())
            return cards.stream().map(card -> mapper.convert(card, new CardDTO())).collect(Collectors.toList());
        else return Collections.emptyList();

    }



}
