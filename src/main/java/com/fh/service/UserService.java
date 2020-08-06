package com.fh.service;

import com.fh.entity.Vip;

public interface UserService {
    boolean findPhone(String phone);

    Integer addVip(Vip vip);

    Vip findNamePhone(String phone);
}
