package com.fh.mapper;

import com.fh.entity.User;
import com.fh.entity.Vip;

public interface UserMapper {


    User findPhone(String phone);

    Integer addVip(Vip vip);

    Vip findNamePhone(String phone);
}
