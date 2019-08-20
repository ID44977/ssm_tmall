package cn.com.sparknet.tmall.service;

import cn.com.sparknet.tmall.pojo.Property;

import java.util.List;

public interface PropertyService {
    void add(Property property);
    void delete(int id);
    void update(Property property);
    Property get(int id);
    List list(int cid);
}
