package cn.com.sparknet.tmall.service;

import cn.com.sparknet.tmall.pojo.ProductImage;

import java.util.List;

public interface ProductImageService {

    String type_single = "type_single";//单个图片
    String type_detail = "type_detail";//详情图片

    void add(ProductImage productImage);
    void delete(int id);
    void update(ProductImage productImage);
    ProductImage get(int id);
    List list(int pid, String type);//根据产品id和图片类型查询的list方法
}
