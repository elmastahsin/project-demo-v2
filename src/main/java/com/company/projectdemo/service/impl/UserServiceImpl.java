package com.company.projectdemo.service.impl;

import com.company.projectdemo.dto.UserDTO;
import com.company.projectdemo.entity.LogHistory;
import com.company.projectdemo.entity.User;
import com.company.projectdemo.mapper.MapperUtil;
import com.company.projectdemo.repository.UserRepository;
import com.company.projectdemo.service.LogService;
import com.company.projectdemo.service.UserService;
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
public class UserServiceImpl implements UserService {
    private final MapperUtil mapper;
    private final LogService logService;
    private final UserRepository userRepository;


    @Override
    public UserDTO save(UserDTO userDTO) {
        User user = mapper.convert(userDTO, new User());

        Map<String, Object> changedFields = EntityComparator.findChangedFields(new User(), user);
        if (changedFields.isEmpty()) return null;

        LogHistory log = new LogHistory();
        log.setTableName("users");
        log.setOperation("insert");
//            log.setChangedColumn(changedFields);
        log.setChangedBy(user.getUsername());
        log.setChangedAt(LocalDateTime.now());
        logService.save(log);
        user.setUsername(user.getUsername()+"DATABASE");
        userRepository.save(user);
        return mapper.convert(user, new UserDTO());

    }


    @Override
    public void update(UserDTO userDTO) {

        User userToUpdate = mapper.convert(userDTO, new User());
        User savedUser = userRepository.findById(userToUpdate.getId()).get();


        Map<String, Object> changedFields = EntityComparator.findChangedFields(savedUser, userToUpdate);
        if (changedFields.isEmpty()) return;
        LogHistory log = new LogHistory();
        log.setTableName("users");
        log.setOperation("update");
        log.setChangedColumn(changedFields);
        log.setChangedBy(savedUser.getUsername());
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
    public List<UserDTO> findByFilter(String search) {
        List<User> userList = userRepository.findByFilter(search);

        return userList.stream().map(user -> mapper.convert(user, new UserDTO())).collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> listAll() {
        List<User> userList = userRepository.findAll();
        return userList.stream().map(user -> mapper.convert(user, new UserDTO())).collect(Collectors.toList());
    }

    public List<UserDTO> getUsersBySpecification(Specification<User> spec) {
        List<User> users = userRepository.findAll(spec);
        if (!users.isEmpty())
            return users.stream().map(user -> mapper.convert(user, new UserDTO())).collect(Collectors.toList());
        else return Collections.emptyList();

    }
}
