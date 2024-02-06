package com.company.projectdemo.service.impl;

import com.company.projectdemo.dto.CardDTO;
import com.company.projectdemo.dto.TransactionDTO;
import com.company.projectdemo.entity.Card;
import com.company.projectdemo.entity.Log;
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
import java.util.Random;
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
        cardDTO.setCardno(genereateRandomCardNo(cardDTO.getProjectid()));
        Card card = mapper.convert(cardDTO, new Card());

        Log log = new Log();
//        Map<String, Object> changedFields = EntityComparator.findChangedFields(new Card(), card);

        log.setTableName("cards");
        log.setOperation("insert");
//        log.setChangedColumn(changedFields);
        log.setChangedBy(card.getName());
        log.setChangedAt(LocalDateTime.now());
        logService.save(log);
    
        cardRepository.save(card);
        return mapper.convert(card, new CardDTO());
    }

    @Override
    public void update(CardDTO cardDTO) {
        Card cardToUpdate = mapper.convert(cardDTO, new Card());
        Card savedCard = cardRepository.findById(cardToUpdate.getCardno()).get();
        Map<String, Object> changedFields = EntityComparator.findChangedFields(savedCard, cardToUpdate);

        Log log = new Log();
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
            return cards.stream().map(card -> mapper.convert(card, new CardDTO()))
                    .collect(Collectors.toList());
        else
            return Collections.emptyList();
    }

    @Override
    public Long genereateRandomCardNo(Long projectid) {
        // Generate a unique card number First 4 digit should be project id and last 12 digit should be random
        Random random = new Random();
        // First 4 digit should be project id 
       String id = String.valueOf(projectid).substring(0,4)+String.format("%012d", random.nextInt(1000000000));
        Long cardno = Long.parseLong(id);
        if (cardRepository.findByCardno(cardno)!=null) {
            return genereateRandomCardNo(projectid);
            
        }
        return cardno;
    }

}
