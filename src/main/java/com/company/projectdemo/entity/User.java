package com.company.projectdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
//@Where(clause = "is_deleted=false")

public class User extends BaseEntity {

    private String username;
    private String password;
    private int twoauth;
    private String gsm;
    private String email;

//    private String firstname;
//    private String lastname;
//    private Boolean locked;
//    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    private Address address;


}

