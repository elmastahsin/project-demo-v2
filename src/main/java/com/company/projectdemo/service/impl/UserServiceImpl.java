package com.company.projectdemo.service.impl;

import com.company.projectdemo.dto.UserDTO;
import com.company.projectdemo.entity.LogHistory;
import com.company.projectdemo.entity.User;
import com.company.projectdemo.mapper.MapperUtil;
import com.company.projectdemo.repository.UserRepository;
import com.company.projectdemo.repository.filter.FilterCriteria;
import com.company.projectdemo.repository.filter.SearchCriteria;
import com.company.projectdemo.service.LogService;
import com.company.projectdemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.company.projectdemo.repository.filter.GenericSpecification;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final MapperUtil mapper;
    private final LogService logService;
    private final UserRepository userRepository;


    @Override
    public void save(UserDTO userDTO) {
        User user = mapper.convert(userDTO, new User());

        Map<String, Object> changedFields = EntityComparator.findChangedFields(new User(), user);

        LogHistory log = new LogHistory();
        log.setTableName("users");
        log.setOperation("save");
        log.setChangedColumn(changedFields);
        log.setChangedBy(user.getName());
        log.setChangedAt(LocalDateTime.now());
        logService.save(log);

        userRepository.save(user);

    }

    @Override
    public void update(UserDTO userDTO) {
        //if user has id, then update
        //if user has not id then save

        User userToUpdate = mapper.convert(userDTO, new User());
        User savedUser = userRepository.findById(userToUpdate.getId()).get();

        Map<String, Object> changedFields = EntityComparator.findChangedFields(savedUser, userToUpdate);

        LogHistory log = new LogHistory();
        if (changedFields.equals("address")) log.setTableName("address");

        else log.setTableName("users");
        log.setOperation("update");
        log.setChangedColumn(changedFields);
        log.setChangedBy(savedUser.getName());
        log.setChangedAt(LocalDateTime.now());
        logService.save(log);
        userRepository.save(userToUpdate);
    }


    @Override
    public boolean isExist(UserDTO userDTO) {
        return findByAll(userDTO).stream().filter(userDto1 -> userDto1.getUsername().equals(userDTO.getUsername())).count() > 0;
    }

    @Override
    public UserDTO findById(Long aLong) {
        return mapper.convert(userRepository.findById(aLong), new UserDTO());
    }

    @Override
    public List<UserDTO> findByAll(UserDTO userDTO) {
        Optional<User> user = userRepository.findById(userDTO.getId());
        return user.stream().map(entity -> mapper.convert(entity, new UserDTO())).collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> listAll() {
        List<User> userList = userRepository.findAll();
        return userList.stream().map(user -> mapper.convert(user, new UserDTO())).collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> findByFilter(String search) {
//        List<User> userList = userRepository.findByFilter(search);

//        return userList.stream().map(user -> mapper.convert(user, new UserDTO())).collect(Collectors.toList());

    return null;
    }
    public List<UserDTO> getUsersBySpecification(FilterCriteria criteria) {
        GenericSpecification<User> spec = new GenericSpecification<>(criteria);
        List<User> userList = userRepository.findAll(spec);
        return userList.stream().map(user -> mapper.convert(user, new UserDTO())).collect(Collectors.toList());
    }
}





