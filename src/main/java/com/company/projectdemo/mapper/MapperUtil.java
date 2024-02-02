package com.company.projectdemo.mapper;

import org.springframework.stereotype.Component;
import org.modelmapper.ModelMapper;

import java.lang.reflect.Type;

@Component
public class MapperUtil {

    private final ModelMapper modelMapper;

    public MapperUtil(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public <T> T convert(Object objectToBeConverted, T convertedObject) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper.map(objectToBeConverted, (Type) convertedObject.getClass());
    }

}