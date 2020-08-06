package com.fh.service;

import com.fh.entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> findOrder(Integer id);
}
