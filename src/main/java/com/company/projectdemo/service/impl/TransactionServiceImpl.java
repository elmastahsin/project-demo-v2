package com.company.projectdemo.service.impl;

import com.company.projectdemo.dto.CardDTO;
import com.company.projectdemo.dto.TransactionDTO;
import com.company.projectdemo.entity.Card;
import com.company.projectdemo.entity.Log;
import com.company.projectdemo.entity.Transaction;
import com.company.projectdemo.mapper.MapperUtil;
import com.company.projectdemo.repository.CardRepository;
import com.company.projectdemo.repository.TransactionRepository;
import com.company.projectdemo.service.CardService;
import com.company.projectdemo.service.LogService;
import com.company.projectdemo.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final MapperUtil mapper;
    private final LogService logService;
    private final TransactionRepository transactionRepository;
    private final CardRepository cardRepository;
    private final CardService cardService;

    @Override
    public TransactionDTO save(TransactionDTO transactionDTO) {
        Transaction transaction = mapper.convert(transactionDTO, new Transaction());
        CardDTO cardDTO = cardService.findById(transaction.getCardno());

        if (!cardRepository.existsByCardno(transaction.getCardno())) {
            transactionDTO.setNote("No card available");
            transactionDTO.setCardno(null);
            return transactionDTO;
        }
        try {
            if (cardDTO.getAmountpublicmoney() < transaction.getAmountmoney() || transaction.getAmountmoney() == null) {
                transactionDTO.setNote("Not enough money to make transaction");
                return transactionDTO;
            }
            if (transaction.getAmountmoney() <= 0) {
                transactionDTO.setNote("Amount money must be positive");
                return transactionDTO;
            }
        } catch (RuntimeException e) {
            e.getMessage();
            return null;
        }


//       Map<String,Object> changedFields = EntityComparator.findChangedFields(new Transaction(), transaction);

        Log log = new Log();
        log.setTableName("transactions");
        log.setOperation("insert");
//        log.setChangedColumn("changedFields");
        log.setChangedBy(transaction.getName());
        log.setChangedAt(LocalDateTime.now());
        logService.save(log);
        transaction.setAct(transactionDTO.getAct() + "Database");
        transactionRepository.save(transaction);
        cardDTO.setAmountpublicmoney(cardDTO.getAmountpublicmoney() - transaction.getAmountmoney());
        cardService.update(cardDTO);
        return mapper.convert(transaction, new TransactionDTO());
    }

    //    @Override
//     public void update(TransactionDTO transactionDTO) {
//
//        Transaction transactionToUpdate = mapper.convert(transactionDTO, new Transaction());
//        Transaction savedTransaction = transactionRepository.findById(transactionToUpdate.getId()).get();
//
//
//
//        Map<String, Object> changedFields = EntityComparator.findChangedFields(savedTransaction, transactionToUpdate);
//
//        Log log = new Log();
//        log.setTableName("transactions");
//        log.setOperation("update");
//        log.setChangedColumn(changedFields);
//        log.setChangedBy(savedTransaction.getName());
//        log.setChangedAt(LocalDateTime.now());
//        logService.save(log);
//        transactionRepository.save(transactionToUpdate);
//
//    }
    @Override
    public TransactionDTO update(TransactionDTO transactionDTO) {

        Transaction transactionToUpdate = mapper.convert(transactionDTO, new Transaction());
        Transaction savedTransaction = transactionRepository.findById(transactionToUpdate.getId()).get();
        CardDTO cardDTO = cardService.findById(transactionToUpdate.getCardno());

        if (cardDTO == null) {
            transactionDTO.setNote("No card available");
            transactionDTO.setCardno(null);
            return transactionDTO;
        }
        if (cardDTO.getAmountpublicmoney() < transactionToUpdate.getAmountmoney()) {
            transactionDTO.setNote("Not enough money to make transaction");
            return transactionDTO;
        }
        Map<String, Object> changedFields = EntityComparator.findChangedFields(savedTransaction, transactionToUpdate);

        Log log = new Log();
        log.setTableName("transactions");
        log.setOperation("update");
        log.setChangedColumn(changedFields);
        log.setChangedBy(savedTransaction.getName());
        log.setChangedAt(LocalDateTime.now());
        logService.save(log);
        transactionRepository.save(transactionToUpdate);
        return mapper.convert(transactionToUpdate, new TransactionDTO());
    }


    @Override
    public boolean isExist(TransactionDTO transactionDTO) {
        return findByAll(transactionDTO).stream().filter(transactionDto1 -> transactionDto1.getAct().equals(transactionDTO.getAct())).count() > 0;
    }

    @Override
    public TransactionDTO findById(Long aLong) {
        return mapper.convert(transactionRepository.findById(aLong), new TransactionDTO());
    }

    @Override
    public List<TransactionDTO> findByAll(TransactionDTO transactionDTO) {
        Optional<Transaction> transaction = transactionRepository.findById(transactionDTO.getId());
        return transaction.stream().map(entity -> mapper.convert(entity, new TransactionDTO())).collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> listAll() {
        List<Transaction> transactionList = transactionRepository.findAll();
        return transactionList.stream().map(transaction -> mapper.convert(transaction, new TransactionDTO())).collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> findByFilter(String search) {
        List<Transaction> transactionList = transactionRepository.findByFilter(search);

        return transactionList.stream().map(transaction -> mapper.convert(transaction, new TransactionDTO())).collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> getTransactionsBySpecification(Specification<Transaction> spec) {
        List<Transaction> transactions = transactionRepository.findAll(spec);
        if (!transactions.isEmpty())
            return transactions.stream().map(transaction -> mapper.convert(transaction, new TransactionDTO())).collect(Collectors.toList());
        else return Collections.emptyList();

    }

    @Override
    public List<TransactionDTO> findByCardNo(Long cardno) {
        Card card = cardRepository.findByCardno(cardno);
        if (card == null) return Collections.emptyList();
        List<Transaction> transactions = transactionRepository.findByCardno(card);
        if (!transactions.isEmpty())
            return transactions.stream().map(transaction -> mapper.convert(transaction, new TransactionDTO())).collect(Collectors.toList());
        else return Collections.emptyList();
    }


}
