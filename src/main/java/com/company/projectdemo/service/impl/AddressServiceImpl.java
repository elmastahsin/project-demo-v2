package com.company.projectdemo.service.impl;

import com.company.projectdemo.dto.AddressDTO;
import com.company.projectdemo.service.AddressService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    @Override
    public AddressDTO findById(Long aLong) {
        throw new IllegalStateException("Not Implemented");
    }

    @Override
    public List<AddressDTO> findByAll(AddressDTO addressDto) {
        throw new IllegalStateException("Not Implemented");
    }

    @Override
    public AddressDTO save(AddressDTO addressDto) {
        throw new IllegalStateException("Not Implemented");
    }

    @Override
    public boolean isExist(AddressDTO addressDto) {
        throw new IllegalStateException("Not Implemented");
    }

    @Override
    public List<AddressDTO> findByFilter(String search) {
        return null;
    }

    @Override
    public List<AddressDTO> listAll() {
        return null;
    }
}
