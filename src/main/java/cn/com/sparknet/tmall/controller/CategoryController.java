package cn.com.sparknet.tmall.controller;

import cn.com.sparknet.tmall.pojo.Category;
import cn.com.sparknet.tmall.service.CategoryService;

import cn.com.sparknet.tmall.util.ImageUtil;
import cn.com.sparknet.tmall.util.Page;
import cn.com.sparknet.tmall.util.UploadedImageFile;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller//注解@Controller声明当前类是一个控制器
@RequestMapping("")//注解@RequestMapping("")表示访问的时候无需额外的地址
public class CategoryController {
    @Autowired//注解@Autowired把CategoryServiceImpl自动装配进了CategoryService 接口
    CategoryService categoryService;

    /*
    在list方法中，通过categoryService.list()获取所有的Category对象，然后放在"cs"中，并服务端跳转到 “admin/listCategory” 视图。
    “admin/listCategory” 会根据后续的springMVC.xml 配置文件，跳转到 WEB-INF/jsp/admin/listCategory.jsp 文件

    @RequestMapping("admin_category_list")//注解@RequestMapping("admin_category_list") 映射admin_category_list路径的访问
    public String list(Model model, Page page) {//为方法list增加参数Page用于获取浏览器传递过来的分页信息
        List<Category> cs = categoryService.list(page);//获取当前页的分类集合
        int total = categoryService.total();//获取分类总数
        page.setTotal(total);//为分页对象设置总数
        model.addAttribute("page", page);//把分页对象放在 "page“ 中
        model.addAttribute("cs", cs);//把分类集合放在"cs"中
        return "admin/listCategory";
    }
     */
    @RequestMapping("admin_category_list")
    public String list(Model model, Page page) {
        PageHelper.offsetPage(page.getStart(), page.getCount());
        //通过分页插件指定分页参数
        List<Category> cs = categoryService.list();
        //调用list() 获取对应分页的数据
        int total = (int) new PageInfo<>(cs).getTotal();
        //通过PageInfo调取总数
        page.setTotal(total);
        model.addAttribute("cs", cs);
        model.addAttribute("page", page);
        return "admin/listCategory";
    }

    @RequestMapping("admin_category_add")//add方法映射路径admin_category_add的访问
    public String add(Category c, HttpSession session, UploadedImageFile uploadedImageFile) throws IOException {
        //参数 Category c接受页面提交的分类名称
        //参数 session 用于在后续获取当前应用的路径
        //UploadedImageFile 用于接受上传的图片
        categoryService.add(c);//通过categoryService保存c对象
        File imageFolder = new File(session.getServletContext().getRealPath("img/category"));
        //通过session获取ControllerContext,再通过getRealPath定位存放分类图片的路径
        File file = new File (imageFolder, c.getId() + ".jpg");
        //根据分类id创建文件名
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        //如果/img/category目录不存在，则创建该目录
        uploadedImageFile.getImage().transferTo(file);
        //通过UploadedImageFile 把浏览器传递过来的图片保存在上述指定的位置
        BufferedImage img = ImageUtil.change2jpg(file);
        //通过ImageUtil.change2jpg(file); 确保图片格式一定是jpg，而不仅仅是后缀名是jpg
        ImageIO.write(img, "jpg", file);
        //source:img,formatName:jpg,output:file
        return "redirect:/admin_category_list";
    }

    @RequestMapping("admin_category_delete")//映射路径admin_category_delete
    public String delete(int id, HttpSession session) throws IOException {
        //参数id接受id注入（通过id指定删除哪个）
        //提供session参数，用于后续定位文件位置
        categoryService.delete(id);
        //通过categoryService删除数据
        File imageFolder = new File(session.getServletContext().getRealPath("img/category"));
        //通过session获取ControllerContext然后获取分类图片位置
        File file = new File(imageFolder, id + ".jpg");
        file.delete();
        //接着删除分类图片
        return "redirect:/admin_category_list";
    }

    @RequestMapping("admin_category_edit")//映射admin_category_edit路径的访问
    public String edit(int id, Model model) throws IOException {
        //参数id用来接受注入
        Category c = categoryService.get(id);
        //通过categoryService.get获取Category对象
        model.addAttribute("c", c);
        //把对象放在“c"上
        return "admin/editCategory";
        //返回editCategory.jsp
    }

    @RequestMapping("admin_category_update")
    public String update(Category c, HttpSession session, UploadedImageFile uploadedImageFile) throws IOException {
        //参数 Category c接受页面提交的分类名称
        //参数 session 用于在后续获取当前应用的路径
        //UploadedImageFile 用于接受上传的图片
        categoryService.update(c);
        //通过categoryService更新c对象
        MultipartFile image = uploadedImageFile.getImage();
        if (null!=image && !image.isEmpty()) {//首先判断是否有上传图片
            File imageFolder = new File(session.getServletContext().getRealPath("img/category"));
            //如果有上传，那么通过session获取ControllerContext,再通过getRealPath定位存放分类图片的路径
            File file = new File(imageFolder, c.getId() + ".jpg");
            //根据分类id创建文件名
            image.transferTo(file);
            BufferedImage img = ImageUtil.change2jpg(file);
            //通过ImageUtil.change2jpg(file); 确保图片格式一定是jpg，而不仅仅是后缀名是jpg
            ImageIO.write(img, "jpg", file);
            //source:img,formatName:jpg,output:file
        }
        return "redirect:/admin_category_list";
    }
}
