package com.fh.controller;

import com.fh.common.JsonData;
import com.fh.entity.Order;
import com.fh.entity.Vip;
import com.fh.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("order")
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("findOrder")
    public JsonData findOrder(HttpServletRequest request){
        Vip loginUser_wxwu = (Vip) request.getAttribute("loginUser_wxwu");
        List<Order> orders = orderService.findOrder(loginUser_wxwu.getId());
        return JsonData.successJsonData(orders);
    }


}
