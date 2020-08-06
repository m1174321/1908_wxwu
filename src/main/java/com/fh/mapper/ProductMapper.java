package com.fh.mapper;

import com.fh.entity.Product;
import com.fh.entity.ProductVO;
import com.fh.util.PageBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {
    List<ProductVO> findProductData(ProductVO productVO);

    List<ProductVO> findProductIsHot();

    List<ProductVO> findProductById(Integer id);

    List<String> findAreaIdIsName(@Param("ids") String areaIds);

    Product selectProductById(Integer id);

//    void updateInventory(Product product);

    int updateInventory( @Param("id")Integer id, @Param("productcount")Integer productcount);
//    long findProductCount(PageBean pageBean);
//
//    List<ProductVO> findProductData(PageBean pageBean);


}
