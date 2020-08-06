package com.fh.service.impl;

import com.fh.entity.Type;
import com.fh.mapper.TypeMapper;
import com.fh.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeMapper typeMapper;


    @Override
    public List<Type> findTypesAll() {
        return typeMapper.findTypesAll();
    }
}
