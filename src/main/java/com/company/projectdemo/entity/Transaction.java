package com.company.projectdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "card_no",referencedColumnName = "cardno")
    private Card cardno;

    private Integer amountmoney;
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
