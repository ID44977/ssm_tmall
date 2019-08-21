package cn.com.sparknet.tmall.service;

import cn.com.sparknet.tmall.pojo.Product;

import java.util.List;

public interface ProductService {
    void add(Product product);
    void delete(int id);
    void update(Product product);
    Product get(int id);
    List list(int cid);
    void setFirstProductImage(Product product);
//  void setFirstProductImage(List<Product> p);
}
