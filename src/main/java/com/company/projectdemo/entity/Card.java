package com.company.projectdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "card")
//@Where(clause = "is_deleted=false")

public class Card extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardno;
    private Double amountfoodmoney;
    private Double amountclothesmoney;
    private Double amountpublicmoney;
    private Double amountprivatemoney;
    private Double amountrewfoodmoney;
    private Double amountrewpublicmoney;
    private Double amountrewprivatemoney;
    private LocalDateTime expirestartdatetime;
    private LocalDateTime expireenddatetime;
    private String gsm;
    private String email;
    private Long projectid;
    private String cardcvv;
    private String mifareno;
    private String region;
    private String note;


    public void onPrePersist() {
        // Generate a unique card number here. This is just an example.
        this.cardno = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
    }

}
