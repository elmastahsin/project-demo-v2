package com.company.projectdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction extends BaseEntity{

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
