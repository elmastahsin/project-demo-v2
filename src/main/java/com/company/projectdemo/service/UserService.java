package com.company.projectdemo.service;

import com.company.projectdemo.dto.UserDTO;
import com.company.projectdemo.repository.filter.FilterCriteria;

import java.util.List;

public interface UserService extends CrudService<UserDTO, Long> {
    public List<UserDTO> getUsersBySpecification(FilterCriteria searchCriteria);


}
