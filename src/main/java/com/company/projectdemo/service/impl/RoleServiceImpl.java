package com.company.projectdemo.service.impl;

import com.company.projectdemo.dto.RoleDTO;
import com.company.projectdemo.entity.Log;
import com.company.projectdemo.entity.Role;
import com.company.projectdemo.mapper.MapperUtil;
import com.company.projectdemo.repository.RoleRepository;
import com.company.projectdemo.service.LogService;
import com.company.projectdemo.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final MapperUtil mapper;
    private final LogService logService;

    @Override
    public RoleDTO findById(Long aLong) {
        return null;
    }

    @Override
    public List<RoleDTO> findByAll(RoleDTO roleDTO) {
        Optional<Role> role = roleRepository.findById(roleDTO.getId());
        return role.stream().map(entity -> mapper.convert(entity, new RoleDTO())).collect(Collectors.toList());
    }

    @Override
    public RoleDTO save(RoleDTO roleDTO) {
        Role role = mapper.convert(roleDTO, new Role());

        Log log = new Log();
//        Map<String, Object> changedFields = EntityComparator.findChangedFields(new Role(), savedRole);

        log.setTableName("roles");
        log.setOperation("insert");
//        log.setChangedColumn(changedFields);
        log.setChangedBy(roleDTO.getRank());
        log.setChangedAt(LocalDateTime.now());
        role.setRank(roleDTO.getRank() + "DATABASE");
        roleRepository.save(role);
        logService.save(log);
        return mapper.convert(role, new RoleDTO());
    }

    @Override
    public void update(RoleDTO roleDTO) {
        Role roleToUpdate = mapper.convert(roleDTO, new Role());
        Role savedRole = roleRepository.findById(roleToUpdate.getId()).get();

        Map<String, Object> changedFields = EntityComparator.findChangedFields(savedRole, roleToUpdate);

        Log log = new Log();
        log.setTableName("roles");
        log.setOperation("update");
        log.setChangedColumn(changedFields);
        log.setChangedBy(savedRole.getName());
        log.setChangedAt(LocalDateTime.now());
        logService.save(log);

        roleRepository.save(roleToUpdate);
    }

    @Override
    public boolean isExist(RoleDTO roleDTO) {
        return findByAll(roleDTO).stream().filter(roleDto1 -> roleDto1.getId().equals(roleDTO.getId())).count() > 0;

    }

    @Override
    public List<RoleDTO> findByFilter(String search) {
        List<Role> roleList = roleRepository.findByFilter(search);

        return roleList.stream().map(role -> mapper.convert(role, new RoleDTO())).collect(Collectors.toList());

    }

    @Override
    public List<RoleDTO> listAll() {
        List<Role> roleList = roleRepository.findAll();
        return roleList.stream().map(role -> mapper.convert(role, new RoleDTO())).collect(Collectors.toList());

    }

    @Override
    public void delete(Long aLong) {
        Role role = roleRepository.findById(aLong).get();
        Log log = new Log();
        log.setTableName("roles");
        log.setOperation("delete");
        log.setChangedColumn(null);
        log.setChangedBy(role.getName());
        log.setChangedAt(LocalDateTime.now());
        logService.save(log);
        roleRepository.deleteById(aLong);
    }

    public List<RoleDTO> getRolesBySpecification(Specification<Role> spec) {
        List<Role> roles = roleRepository.findAll(spec);
        if (!roles.isEmpty())
            return roles.stream().map(role -> mapper.convert(role, new RoleDTO())).collect(Collectors.toList());
        else return Collections.emptyList();

    }
}

