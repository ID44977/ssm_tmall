package cn.com.sparknet.tmall.controller;

import cn.com.sparknet.tmall.pojo.Category;
import cn.com.sparknet.tmall.pojo.Property;
import cn.com.sparknet.tmall.service.CategoryService;
import cn.com.sparknet.tmall.service.PropertyService;
import cn.com.sparknet.tmall.util.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("")
public class PropertyController {
    @Autowired

    CategoryService categoryService;
    @Autowired
    PropertyService propertyService;

    @RequestMapping("admin_property_add")
    public String add(Property p) {
        propertyService.add(p);
        return "redirect:admin_property_list?cid="+p.getCid();
    }

    @RequestMapping("admin_property_delete")
    public String delete(int id) {
        Property p = propertyService.get(id);
        //根据id获取该property对象
        propertyService.delete(id);
        return "redirect:admin_property_list?cid="+p.getCid();
    }

    @RequestMapping("admin_property_edit")
    public String edit(Model model, int id) {
        Property p = propertyService.get(id);
        //根据id获取Property对象放在p里
        Category c = categoryService.get(p.getCid());
        //根据Property对象的cid属性获取Category对象
        p.setCategory(c);
        //将该Category对象设置在Property对象的category属性上
        model.addAttribute("p", p);
        return "admin/editProperty";
    }

    @RequestMapping("admin_property_update")
    public String update(Property p) {
        propertyService.update(p);
        return "redirect:admin_property_list?cid="+p.getCid();
    }

    @RequestMapping("admin_property_list")
    public String list(int cid, Model model,  Page page) {
        Category c = categoryService.get(cid);
        //获取分类cid（即categoryId，一个分类有多个属性id）
        PageHelper.offsetPage(page.getStart(),page.getCount());
        //通过PageHelper设置分页参数
        List<Property> ps = propertyService.list(cid);
        //基于cid，获取当前分类下的属性集合
        int total = (int) new PageInfo<>(ps).getTotal();
        //通过PageInfo获取属性总数
        page.setTotal(total);
        //把总数设置给分页page对象
        page.setParam("&cid="+c.getId());
        //取出cid放入param，待jsp页面通过param取出cid
        model.addAttribute("ps", ps);
        model.addAttribute("c", c);
        model.addAttribute("page", page);

        return "admin/listProperty";
    }
}
