package com.company.projectdemo.controller;

import com.company.projectdemo.dto.LogDTO;
import com.company.projectdemo.dto.ResponseWrapper;
import com.company.projectdemo.dto.UserDTO;
import com.company.projectdemo.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/logs")

@RequiredArgsConstructor
public class LogController {
private final LogService logService;
    @GetMapping
    public ResponseEntity<ResponseWrapper> getUser() {
        List<LogDTO> users = logService.listAll();
        return ResponseEntity.ok(new ResponseWrapper("users successfully retrieved", users, HttpStatus.OK));

    }
}
