package com.fh.service.impl;

import com.fh.entity.User;
import com.fh.entity.Vip;
import com.fh.mapper.UserMapper;
import com.fh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public boolean findPhone(String phone) {
        User user = userMapper.findPhone(phone);
        if(user==null){
            return false;
        }
        return true;
    }

    @Override
    public Integer addVip(Vip vip) {
        userMapper.addVip(vip);
        return vip.getId();
    }

    @Override
    public Vip findNamePhone(String phone) {
        return userMapper.findNamePhone(phone);
    }
}
