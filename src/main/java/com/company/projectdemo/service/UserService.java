package com.company.projectdemo.service;

import com.company.projectdemo.dto.UserDTO;
import com.company.projectdemo.entity.User;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface UserService extends CrudService<UserDTO, Long> {
    List<UserDTO> listAll();


    List<UserDTO> findByFilter(String search);

    List<UserDTO> getUsersBySpecification(Specification<User> spec);

    void update(UserDTO userDTO);
}
