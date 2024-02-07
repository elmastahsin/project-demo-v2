package com.company.projectdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Random;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "card")
//@Where(clause = "is_deleted=false")

public class Card extends BaseEntity {

    @Id
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

//    @OneToMany(mappedBy = "cardno") // in OneToMany relationship ownership belongs to many side
//    private Transaction transaction;


    public void onPrePersist() {
        // Generate a unique card number First 4 digit should be project id and last 12 digit should be random
        Random random = new Random();
        String id = String.format("%04d", this.projectid) + String.format("%012d", random.nextInt(1000000000));
        this.cardno = Long.parseLong(id);
    }

}
