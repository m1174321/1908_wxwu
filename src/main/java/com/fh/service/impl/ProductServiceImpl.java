package com.fh.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fh.entity.Area;
import com.fh.entity.Product;
import com.fh.entity.ProductVO;
import com.fh.mapper.ProductMapper;
import com.fh.service.ProductService;
import com.fh.util.PageBean;
import com.fh.util.RedisPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;


//    @Override
//    public PageBean findProductData(PageBean pageBean) {
//        long productCount = productMapper.findProductCount(pageBean);
//        pageBean.setRecordsFiltered(productCount);
//        pageBean.setRecordsTotal(productCount);
//        List<ProductVO> productData = productMapper.findProductData(pageBean);
//        pageBean.setData(productData);
//        return pageBean;
//    }

    @Override
    public  List<ProductVO> findProductData(ProductVO productVO) {
        return productMapper.findProductData(productVO);
    }

    @Override
    public List<ProductVO> findProductIsHot() {
        return productMapper.findProductIsHot();
    }

    @Override
    public List<ProductVO> findProductById(Integer id) {
        List<ProductVO> productById = productMapper.findProductById(id);
        String[] split = productById.get(0).getAreaIds().split(",");
        if(StringUtils.isEmpty(split) == false){
            StringBuffer sb = new StringBuffer();
            Jedis jedis = RedisPool.getJedis();
            for (int i = 0; i < split.length; i++) {
                String area_wxwu = jedis.hget("area_wxwu", split[i]);
                Area area = JSONObject.parseObject(area_wxwu, Area.class);
                sb.append(area.getName()+" ");
            }
            RedisPool.returnJedis(jedis);
            productById.get(0).setAreaIds(sb.toString());
        }
        return productById;
    }
}
