package com.company.projectdemo.service;

import com.company.projectdemo.dto.RoleDTO;
import com.company.projectdemo.dto.RoleDTO;
import com.company.projectdemo.entity.Role;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface RoleService extends CrudService<RoleDTO, Long> {
    List<RoleDTO> getRolesBySpecification(Specification<Role> spec);
}
