package cn.com.sparknet.tmall.mapper;

import cn.com.sparknet.tmall.pojo.Category;
import cn.com.sparknet.tmall.util.Page;

import java.util.List;

public interface CategoryMapper {
    List<Category> list();
    List<Category> list(Page page);
    int total();
    void add(Category category);
    void delete(int id);
    Category get(int id);
    void update(Category category);
}
