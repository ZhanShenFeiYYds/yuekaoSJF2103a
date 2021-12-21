package cn.ty.controller;

import cn.ty.pojo.User;
import cn.ty.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("/toLogin")
    public String toLogin(){
        int i= 0;
        System.out.println("tianjia");
        return "user/login";
    }
    @RequestMapping("/login")
    public String login(User user, HttpSession session){
        User login = userService.login(user);
        if(login == null){
            return "user/login";
        }else {//登录成功，把账号信息保存到session中，供登录控制使用的
            session.setAttribute("user",login);
            return "redirect:/teacher/findAll";//登陆成功后 访问讲师findAll 到讲师管理模块
        }
    }
    @RequestMapping("/toRegist")
    public String toRegist(){
        return "user/regist";
    }
    @RequestMapping("/regist")
    public String regist(User user){
        userService.regist(user);
        return "user/login";
    }
}
