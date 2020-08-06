package com.fh.controller;

import com.fh.Annotion.LogAop;
import com.fh.common.JsonData;
import com.fh.entity.ProductVO;
import com.fh.service.ProductService;
import com.fh.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("product")
public class ProductApi {

    @Autowired
    private ProductService    productService;


    @RequestMapping("findProductData")
    @ResponseBody
    //@CrossOrigin
    public JsonData findProductData(ProductVO productVO){
        List<ProductVO> productData = productService.findProductData(productVO);
        return JsonData.successJsonData(productData);
    }

    @RequestMapping("findProductIsHot")
    @ResponseBody
   // @CrossOrigin
    public JsonData findProductIsHot(){
        List<ProductVO> productData = productService.findProductIsHot();
        return JsonData.successJsonData(productData);
    }

    @RequestMapping("findProductById")
    @ResponseBody
   // @CrossOrigin
    public JsonData findProductById(Integer id){
        List<ProductVO> productVO = productService.findProductById(id);
        return JsonData.successJsonData(productVO);
    }

    


}
