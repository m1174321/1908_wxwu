package com.fh.mapper;

import com.fh.entity.Order;

import java.util.List;

public interface OrderMapper {
    List<Order> findOrder(Integer id);
}
