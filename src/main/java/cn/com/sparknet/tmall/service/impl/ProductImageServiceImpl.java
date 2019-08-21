package cn.com.sparknet.tmall.service.impl;

import cn.com.sparknet.tmall.mapper.ProductImageMapper;
import cn.com.sparknet.tmall.pojo.ProductImage;
import cn.com.sparknet.tmall.pojo.ProductImageExample;
import cn.com.sparknet.tmall.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImageServiceImpl implements ProductImageService {
    @Autowired
    ProductImageMapper productImageMapper;

    @Override
    public void add(ProductImage productImage) {
        productImageMapper.insert(productImage);
    }

    @Override
    public void delete(int id) {
        productImageMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(ProductImage productImage) {
        productImageMapper.updateByPrimaryKeySelective(productImage);
    }

    @Override
    public ProductImage get(int id) {
        return productImageMapper.selectByPrimaryKey(id);
    }

    @Override
    public List list(int pid, String type) {
        ProductImageExample example =new ProductImageExample();
        example.createCriteria()
                .andPidEqualTo(pid)
                .andTypeEqualTo(type);
        //匹配pid和type
        example.setOrderByClause("id desc");
        return productImageMapper.selectByExample(example);
    }
}
