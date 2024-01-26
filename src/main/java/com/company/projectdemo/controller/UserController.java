package com.company.projectdemo.controller;

import com.company.projectdemo.dto.ResponseWrapper;
import com.company.projectdemo.dto.UserDTO;
import com.company.projectdemo.repository.filter.FilterCriteria;
import com.company.projectdemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping
    public ResponseEntity<ResponseWrapper> createUser(@RequestBody UserDTO user) {
        userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("user successfully created", user, HttpStatus.CREATED));
    }

    //get

    @GetMapping
    public ResponseEntity<ResponseWrapper> getUser() {
        List<UserDTO> users = userService.listAll();
        return ResponseEntity.ok(new ResponseWrapper("users successfully retrieved", users, HttpStatus.OK));

    }


    // update
    @PutMapping
    public ResponseEntity<ResponseWrapper> updateUser(@RequestBody UserDTO userDTO) {
        userService.update(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("user successfully updated", userDTO, HttpStatus.CREATED));
    }

    //filter
    @GetMapping("/{search}")
    public ResponseEntity<ResponseWrapper> getUserBySearch(@PathVariable("search") String search) {
        List<UserDTO> user = userService.findByFilter(search);
        return ResponseEntity.ok(new ResponseWrapper("user successfully retrieved", user, HttpStatus.OK));
    }

    @GetMapping("/?key=firstname&operation=equals&value=Ahmet\n")
    public ResponseEntity<ResponseWrapper> getCards(
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "operation", required = false) String operation,
            @RequestParam(value = "value", required = false) String value) {

        FilterCriteria criteria = new FilterCriteria(key, operation, value);
        List<UserDTO> cards = userService.getUsersBySpecification(criteria);
        return ResponseEntity.ok(new ResponseWrapper("user successfully retrieved", cards, HttpStatus.OK));
    }


}
