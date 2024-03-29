package com.company.projectdemo.dto;

import com.company.projectdemo.entity.Card;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionDTO {
    private Long id;
    private Long cardno;
    private Double amountmoney;
    private Integer transactiontype;
    private Integer vattype;
    private String act;
    private Integer uid;
    private String ip;
    private Long bid;
    private String tuniquenumber;
    private Long mediatorid;
    private String terminalid;
    private Long storeid;
    private Long refundid;
    private String note;
    private String saletype;


}
