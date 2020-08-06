package com.fh.mapper;

import com.fh.entity.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CarMapper {
    ProductCar findCarIdByProId(Integer id);

    Integer findProInventory(Integer id);

    List<AddRess> findAddRess(Integer id);

    Integer addOrder(Order order);

    void batchOrderDetail(@Param("list")List<OrderDetail> orderDetailList, @Param("orderid") Integer id);

    Order findOrderById(Integer orderId);

    void updateById(Order order);
}
