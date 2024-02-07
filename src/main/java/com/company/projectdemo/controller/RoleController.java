package com.company.projectdemo.controller;

import com.company.projectdemo.dto.ResponseWrapper;
import com.company.projectdemo.dto.RoleDTO;
import com.company.projectdemo.entity.Role;
import com.company.projectdemo.repository.filter.FilterCriteria;
import com.company.projectdemo.repository.filter.GenericSpecification;
import com.company.projectdemo.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@RequiredArgsConstructor
@RequestMapping("/role")
@Tag(name = "Role", description = "Role CRUD operations")
public class RoleController {
    private final RoleService roleService;


    @PostMapping
    @Operation(summary = "Create a new role")

    @ApiResponse(responseCode = "201", description = "Role successfully created",
            content = @Content(mediaType = "application/json"),
            headers = {@Header(name = "Connection ", description = "keep-alive")})


    public ResponseEntity<ResponseWrapper> createRole(@RequestBody RoleDTO role) {
        RoleDTO roleDTO = roleService.save(role);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("role successfully created", roleDTO, HttpStatus.CREATED));
    }

    //get

    @GetMapping
    @Operation(summary = "Get all roles")
    public ResponseEntity<ResponseWrapper> getRole() {
        List<RoleDTO> roles = roleService.listAll();
        return ResponseEntity.ok(new ResponseWrapper("roles successfully retrieved", roles, HttpStatus.OK));

    }


    // update
    @PutMapping
    @Operation(summary = "Update a role")
    public ResponseEntity<ResponseWrapper> updateRole(@RequestBody RoleDTO role) {
        roleService.update(role);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("role successfully updated", role, HttpStatus.CREATED));
    }

    //filter
    @GetMapping("/{search}")
    @Operation(summary = "Get role by search")
    public ResponseEntity<ResponseWrapper> getRoleBySearch(@PathVariable("search") String search) {
        List<RoleDTO> role = roleService.findByFilter(search);
        return ResponseEntity.ok(new ResponseWrapper("role successfully retrieved", role, HttpStatus.OK));
    }

    @GetMapping("/filter")
    @Operation(summary = "Get role by filter")
    public ResponseEntity<ResponseWrapper> getRoles(
            @RequestParam Map<String, String> allParams) {
        //all entries should be trimmed

        List<FilterCriteria> criteriaList = allParams.entrySet().stream()
                .map(entry -> new FilterCriteria(entry.getKey(), "equals", entry.getValue()))
                .collect(Collectors.toList());

        Specification<Role> spec = new GenericSpecification<>(criteriaList);

        List<RoleDTO> roles = roleService.getRolesBySpecification(spec);
        if (!roles.isEmpty())
            return ResponseEntity.ok(new ResponseWrapper("role successfully filtered", roles, HttpStatus.OK));
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper("role not found", HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a role")
    public ResponseEntity<ResponseWrapper> deleteRole(@PathVariable Long id) {
        roleService.delete(id);
        return ResponseEntity.ok(new ResponseWrapper("role successfully deleted", HttpStatus.OK));
    }

}
