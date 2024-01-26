package com.company.projectdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
    private Long id;
    private String cardno;
    private Double amountmoney;
    private BigInteger transactiontype;
    private int vattype;
    private String act;
    private BigInteger uid;
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
