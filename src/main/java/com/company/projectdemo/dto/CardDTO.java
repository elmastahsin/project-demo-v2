package com.company.projectdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardDTO {
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

}
