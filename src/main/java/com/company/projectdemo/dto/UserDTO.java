package com.company.projectdemo.dto;

import com.company.projectdemo.entity.Address;
import com.company.projectdemo.enums.Gender;
import com.company.projectdemo.enums.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.OneToOne;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {



    private Long id;
    //    private String firstname;
//    private String lastname;
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // to hide password in response
    private String password;
    private int twoauth;
    private String gsm;
    private String email;
    private String note;

//    private Boolean locked;
//    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//     private AddressDTO address;

}
