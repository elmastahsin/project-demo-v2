package com.company.projectdemo.service;

import com.company.projectdemo.dto.CardDTO;
import com.company.projectdemo.entity.Card;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface CardService extends CrudService<CardDTO, Long> {
    List<CardDTO> getCardsBySpecification(Specification<Card> spec);
    void update(CardDTO cardDTO);
}
