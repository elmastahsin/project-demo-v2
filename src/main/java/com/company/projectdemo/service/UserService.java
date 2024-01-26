package com.company.projectdemo.service;

import com.company.projectdemo.dto.UserDTO;
import com.company.projectdemo.entity.User;
import com.company.projectdemo.repository.filter.SearchCriteria;


import java.util.List;

public interface UserService extends CrudService<UserDTO, Long>{
    public List<UserDTO> getUsersBySpecification(SearchCriteria searchCriteria);


}
