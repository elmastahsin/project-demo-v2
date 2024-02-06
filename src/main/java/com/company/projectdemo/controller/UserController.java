package com.company.projectdemo.controller;

import com.company.projectdemo.dto.ResponseWrapper;
import com.company.projectdemo.dto.UserDTO;
import com.company.projectdemo.entity.User;
import com.company.projectdemo.repository.filter.FilterCriteria;
import com.company.projectdemo.repository.filter.GenericSpecification;
import com.company.projectdemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<ResponseWrapper> createUser(@RequestBody UserDTO user) {
        UserDTO userDto = userService.save(user);
        if (userDto.getNote() != null && userDto.getNote().equals("User already exist"))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseWrapper(userDto.getNote(), HttpStatus.FORBIDDEN));
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("user successfully created", userDto, HttpStatus.CREATED));
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

    @GetMapping("/filter")
    public ResponseEntity<ResponseWrapper> getUsers(
            @RequestParam Map<String, String> allParams) {
        //all entries should be trimmed

        List<FilterCriteria> criteriaList = allParams.entrySet().stream()
                .map(entry -> new FilterCriteria(entry.getKey(), "like", entry.getValue()))
                .collect(Collectors.toList());

        Specification<User> spec = new GenericSpecification<>(criteriaList);

        List<UserDTO> users = userService.getUsersBySpecification(spec);
        if (!users.isEmpty())
            return ResponseEntity.ok(new ResponseWrapper("user successfully filtered", users, HttpStatus.OK));
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper("user not found", HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper> deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper("user successfully deleted", HttpStatus.OK));
    }

}
