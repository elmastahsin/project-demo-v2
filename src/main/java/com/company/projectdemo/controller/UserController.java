package com.company.projectdemo.controller;

import com.company.projectdemo.dto.ResponseWrapper;
import com.company.projectdemo.dto.UserDTO;
import com.company.projectdemo.entity.User;
import com.company.projectdemo.repository.filter.FilterCriteria;
import com.company.projectdemo.repository.filter.GenericSpecification;
import com.company.projectdemo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "User", description = "User CRUD operations")
public class UserController {

    private final UserService userService;

    @PostMapping
    @Operation(summary = "Create a new user")
    @ApiResponse(responseCode = "201", description = "User successfully created",
            content = @Content(mediaType = "application/json"),
            headers = {@Header(name = "Connection ", description = "keep-alive")})
    public ResponseEntity<ResponseWrapper> createUser(@RequestBody UserDTO user) {
        UserDTO userDto = userService.save(user);
        if (userDto.getNote() != null && userDto.getNote().equals("User already exist"))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseWrapper(userDto.getNote(), HttpStatus.FORBIDDEN));
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("user successfully created", userDto, HttpStatus.CREATED));
    }

    //get

    @GetMapping
    @Operation(summary = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully retrieved",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = UserDTO.class
                                    )))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
    })
    public ResponseEntity<ResponseWrapper> getUser() {
        List<UserDTO> users = userService.listAll();
        return ResponseEntity.ok(new ResponseWrapper("users successfully retrieved", users, HttpStatus.OK));

    }


    // update
    @PutMapping
    @Operation(summary = "Update a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully updated",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = UserDTO.class
                                    )))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
    })
    public ResponseEntity<ResponseWrapper> updateUser(@RequestBody UserDTO userDTO) {
        userService.update(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("user successfully updated", userDTO, HttpStatus.CREATED));
    }

    //filter
    @GetMapping("/{search}")
    @Operation(summary = "Get user by search")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully filtered",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = UserDTO.class
                                    )))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
    })
    public ResponseEntity<ResponseWrapper> getUserBySearch(@PathVariable("search") String search) {
        List<UserDTO> user = userService.findByFilter(search);
        return ResponseEntity.ok(new ResponseWrapper("user successfully retrieved", user, HttpStatus.OK));
    }

    @GetMapping("/filter")
    @Operation(summary = "Filter users by criteria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully filtered",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = UserDTO.class
                                    )))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
    })
    public ResponseEntity<ResponseWrapper> getUsers(
            @RequestParam Map<String, String> allParams) {
        //all entries should be trimmed

        List<FilterCriteria> criteriaList = allParams.entrySet().stream()
                .map(entry -> new FilterCriteria(entry.getKey(), "equals", entry.getValue()))
                .collect(Collectors.toList());

        Specification<User> spec = new GenericSpecification<>(criteriaList);

        List<UserDTO> users = userService.getUsersBySpecification(spec);
        if (!users.isEmpty())
            return ResponseEntity.ok(new ResponseWrapper("user successfully filtered", users, HttpStatus.OK));
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper("user not found", HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully deleted",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = UserDTO.class
                                    )))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    })
            public ResponseEntity<ResponseWrapper>deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper("user successfully deleted", HttpStatus.OK));
    }

}
