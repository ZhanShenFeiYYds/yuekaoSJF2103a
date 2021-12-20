package cn.ty.controller;

import cn.ty.pojo.Computer;
import cn.ty.pojo.User;
import cn.ty.service.ComputerService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Controller
@RequestMapping("computer")
public class ComputerController {
    @Autowired
    private ComputerService computerService;
    @RequestMapping("/findAll")
    public String findAll(Model model, String name, @RequestParam(defaultValue = "1")int pageNum){
        int pageSize=3;
        PageInfo<Computer> pageInfo =computerService.findAll(pageNum,pageSize,name);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("name",name);
        return "computer/computer_list";
    }
    @RequestMapping("/toAdd")
    public String toAdd(){
        return "computer/computer_add";
    }
    @RequestMapping("/add")    //MultipartFile uploadFile接收上传文件，uploadFile要和页面中的name保持一致
    public String add(Computer computer, MultipartFile uploadFile, HttpServletRequest req) throws IOException, IOException {
        //定义文件名
        String fileName = "";
        //处理上传的头像，先判断是否上传了文件
        if(uploadFile != null && uploadFile.getSize()>0){
            //1.得到上传文件的名字
            String uploadFileName = uploadFile.getOriginalFilename();
            //2.截取文件扩展名
            String fix = uploadFileName.substring(uploadFileName.lastIndexOf("."));
            //3.把文件加上随机数，防止文件重复,替换-为“”（去掉-）,转换为大写字母
            String uuid = UUID.randomUUID().toString().replace("-","").toUpperCase();
            fileName = uuid+fix;
            //4.获取文件路径
            String basePath = "D:/temp/images";
            //5.解决同一文件夹中文件过多问题,细化到以年月日作为文件夹
            String datePath = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            //6.创建 根目录+uploads+年月日 文件夹 ，判断路径是否存在
            File file = new File(basePath+"/uploads/"+datePath);
            if(!file.exists()) {
                file.mkdirs();
            }
            fileName = "uploads/"+datePath+"/"+fileName;//
            //7.使用 MulitpartFile 接口中方法，把上传的文件写到指定位置
            uploadFile.transferTo(new File(basePath,fileName));


            computer.setPhoto(fileName);//接收文件完毕，把路径保存到对象中，接着持久化
        }
        computerService.add(computer);
        return "redirect:findAll";
    }
    @RequestMapping("/delete")
    public String delete(Integer id){
        computerService.deleteByPrimaryKey(id);
        return "redirect:findAll";
    }
    @RequestMapping("/findId")
    public String findId(Model model,Integer id){
        Computer _id = computerService.selectByPrimaryKey(id);
        model.addAttribute("_id",_id);
        return "computer/computer_update";
    }
    @RequestMapping("/update")
    public String update(Computer computer , MultipartFile uploadFile, HttpServletRequest req) throws IOException {
        //定义文件名
        String fileName = "";
        //处理上传的头像，先判断是否上传了文件
        if(uploadFile != null && uploadFile.getSize()>0) {
            //1.得到上传文件的名字
            String uploadFileName = uploadFile.getOriginalFilename();
            //2.截取文件扩展名
            String fix = uploadFileName.substring(uploadFileName.lastIndexOf("."));
            //3.把文件加上随机数，防止文件重复,替换-为“”（去掉-）,转换为大写字母
            String uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
            fileName = uuid + fix;
            //4.获取文件路径
//            String basePath = req.getServletContext().getRealPath("/");//C:\Users\jerry\AppData\Local\Temp\tomcat-docbase.1961634436091737165.8080，显示照片时不可用
            //1.。。把照片保存到项目static中，但是会导致项目很大，不易移植
//            String basePath = ClassUtils.getDefaultClassLoader().getResource("static").getPath();// /D:/IdeaProjects/themeleafuserdemo/target/classes/static
            //2.。。把照片保存到自定义的静态路径（和项目没有关系）中，不会导致项目很大，便于移植
            String basePath = "D:/temp/images";//使用自定义的静态资源路径
            //5.解决同一文件夹中文件过多问题,细化到以年月日作为文件夹
            String datePath = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            //6.创建 根目录+uploads+年月日 文件夹 ，判断路径是否存在
            File file = new File(basePath + "/uploads/" + datePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            fileName = "uploads/" + datePath + "/" + fileName;//
            //7.使用 MulitpartFile 接口中方法，把上传的文件写到指定位置
            uploadFile.transferTo(new File(basePath, fileName));
        }
        computer.setPhoto(fileName);
        computerService.updateByPrimaryKeySelective(computer);
        return "redirect:findAll";
    }
    @RequestMapping("/updateStatus")
    public String updateStatus(Computer computer){
        computerService.updateStatus(computer);
        return "redirect:findAll";
    }
}
