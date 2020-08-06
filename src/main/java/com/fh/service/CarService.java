package com.fh.service;

import com.fh.common.exception.CountException;
import com.fh.entity.AddRess;
import com.fh.entity.ProductCar;
import org.omg.CORBA.OMGVMCID;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface CarService {
    long addCar(Integer id, Integer count);

    long delCar(Integer id);

    void updateGwcStatusByIds(String ids);

    List<AddRess> findAddRess();

    List<ProductCar> findProductGwcRedis();

    Map addOrderAddRess(Integer addRessId, Integer payTypeId)throws CountException;

    Map payWechat(Integer orderId, BigDecimal moneyAll) throws Exception;

    Integer queryOrderDetail(String mill, Integer orderId) throws Exception;
}
