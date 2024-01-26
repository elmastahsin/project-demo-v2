package com.company.projectdemo.controller;

import com.company.projectdemo.dto.ResponseWrapper;
import com.company.projectdemo.dto.RoleDTO;
import com.company.projectdemo.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/role")

public class RoleController {
    private final RoleService roleService;


    @PostMapping
    public ResponseEntity<ResponseWrapper> createRole(@RequestBody RoleDTO role) {
        roleService.save(role);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("role successfully created",role, HttpStatus.CREATED));
    }

    //get

    @GetMapping
    public ResponseEntity<ResponseWrapper> getRole() {
        List<RoleDTO> roles = roleService.listAll();
        return ResponseEntity.ok(new ResponseWrapper("roles successfully retrieved", roles, HttpStatus.OK));

    }


    // update
    @PutMapping
    public ResponseEntity<ResponseWrapper> updateRole(@RequestBody RoleDTO role) {
        roleService.update(role);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("role successfully updated", role,HttpStatus.CREATED));
    }

    //filter
    @GetMapping("/{search}")
    public ResponseEntity<ResponseWrapper> getRoleBySearch(@PathVariable("search") String search) {
        List<RoleDTO> role = roleService.findByFilter(search);
        return ResponseEntity.ok(new ResponseWrapper("role successfully retrieved", role, HttpStatus.OK));
    }

}
