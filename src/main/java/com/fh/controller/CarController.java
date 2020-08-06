package com.fh.controller;

import com.alibaba.fastjson.JSONObject;
import com.fh.common.JsonData;
import com.fh.common.exception.CountException;
import com.fh.entity.AddRess;
import com.fh.entity.ProductCar;
import com.fh.entity.Vip;
import com.fh.service.CarService;
import com.fh.service.ProductService;
import com.fh.util.RedisPool;
import com.github.wxpay.sdk.FeiConfig;
import com.github.wxpay.sdk.WXPay;
import jdk.nashorn.internal.runtime.regexp.JoniRegExp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.soap.Addressing;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("car")
public class CarController {

    @Autowired
    private CarService carService;


    @RequestMapping("addCar")
    @ResponseBody
    public JsonData addCar(Integer id,Integer count){
            long countProduct = carService.addCar(id,count);
            return JsonData.successJsonData(countProduct);
    }


    @ResponseBody
    @RequestMapping("findCar")
    public JsonData findCar(HttpServletRequest httpServletRequest){
        Vip loginUser_wxwu = (Vip) httpServletRequest.getAttribute("loginUser_wxwu");
        Jedis jedis = RedisPool.getJedis();
        Map<String, String> stringStringMap = jedis.hgetAll("car_" + loginUser_wxwu.getUserPhone() + "_gwc");
        RedisPool.returnJedis(jedis);
        List<ProductCar> productCarList = new ArrayList<>();
        Iterator<Map.Entry<String, String>> iterator = stringStringMap.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, String> next = iterator.next();
            String value = next.getValue();
            ProductCar productCar = JSONObject.parseObject(value, ProductCar.class);
            productCarList.add(productCar);
        }

        return JsonData.successJsonData(productCarList);
    }

    @ResponseBody
    @RequestMapping("delCar")
    public JsonData delCar(Integer id){
        long count = carService.delCar(id);
        return JsonData.successJsonData(count);
    }


    @ResponseBody
    @RequestMapping("updateGwcStatusByIds")
    public JsonData updateGwcStatusByIds(String ids){
        carService.updateGwcStatusByIds(ids);
        return JsonData.successJsonData("success");
    }

    @RequestMapping("findAddRess")
    @ResponseBody
    public JsonData findAddRess(){
        List<AddRess> addRessList = carService.findAddRess();
        return JsonData.successJsonData(addRessList);
    }

    @ResponseBody
    @RequestMapping("findProductGwcRedis")
    public JsonData findProductGwcRedis(){
        List<ProductCar> productCarList = carService.findProductGwcRedis();
        return JsonData.successJsonData(productCarList);
    }

    @ResponseBody
    @RequestMapping("addOrderAddRess")
    public JsonData addOrderAddRess(Integer addRessId,Integer payTypeId,String falg)throws CountException {
        //解决多次点击提交订单
        //另外一种 在前台把提交按钮设置点击一次 给禁用  但是bug在于postman  那种测试接口 或者抓包工具
        Jedis jedis = RedisPool.getJedis();
        //如果返回true  则证明这是第二次/二次以上点击提交订单了
        boolean exists = jedis.exists(falg);
        RedisPool.returnJedis(jedis);
        if(exists == true){
            return JsonData.getJsonError(203,"好兄弟，你提交了很多次了");
        }else{
            RedisPool.setexRedis(falg,10,"");
        }
        Map map= carService.addOrderAddRess(addRessId,payTypeId);
        return JsonData.successJsonData(map);
    }

    @ResponseBody
    @RequestMapping("payWechat")
    public JsonData payWechat(Integer orderId, BigDecimal moneyAll) throws Exception {
        Map map = carService.payWechat(orderId,moneyAll);
        return JsonData.successJsonData(map);
    }

    @ResponseBody
    @RequestMapping("queryOrderDetail")
    public JsonData queryOrderDetail(String mill,Integer orderId) throws Exception {
        Integer status = carService.queryOrderDetail(mill,orderId);
        return JsonData.successJsonData(status);
    }



}
