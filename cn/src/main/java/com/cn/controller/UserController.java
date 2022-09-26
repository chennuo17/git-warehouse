package com.cn.controller;

import com.cn.entity.User;
import com.cn.service.UserService;
import com.cn.utils.VerifyCodeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("user")
public class UserController {

    private static final Logger log=LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService=userService;
    }

    /**
     * 安全退出
     * @param session
     * @return
     */
    @RequestMapping("logout")
    public String logout(HttpSession session){
        session.invalidate();//session失效
        return "redirect:/login";//跳转到登录界面
    }

    @RequestMapping("login")
    public String login(String username,String password,HttpSession session){
        log.debug("本次登录用户名:{}",username);
        log.debug("本次登录密码:{}",password);
        try {
            //1.调用业务层进行登录
            User user=userService.login(username,password);
            //2.保存用户信息
            session.setAttribute("user",user);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/login";//登录失败回到登录界面
        }
        return "redirect:/employee/lists";//登陆成功后，跳转到查询所有员工信息控制器路径
    }

    /*
    * 用户注册
    * @return
    * */
    @RequestMapping("register")
    public String register(User user, String code,HttpSession session){
        log.debug("用户名:  {},真实姓名:  {},密码:  {},性别:  {},",user.getUsername(),user.getRealname(),user.getPassword(),user.getGender());
        log.debug("用户输入验证码:  {}",code);

        try {
            String sessionCode=session.getAttribute("code").toString();
            if (!sessionCode.equalsIgnoreCase(code))throw new RuntimeException("验证码错误！");
            //2.注册用户
            userService.register(user);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return "redirect:/register";//注册失败，回到注册
        }
        return "redirect:/login";//注册成功，跳转登录
    }

    /*
    生成验证码
    * */
    @RequestMapping("generateImageCode")
    public void generateImageCode(HttpSession session, HttpServletResponse response) throws IOException {
        //1.生成4位随机数
        String code = VerifyCodeUtils.generateVerifyCode(4);
        //2.保存到session作用域
        session.setAttribute("code", code);
        //3.根据随机数生成图片 && 4.通过response响应图片 && 5.设置响应类型
        response.setContentType("image/png");
        ServletOutputStream os=response.getOutputStream();
        VerifyCodeUtils.outputImage(220, 60, os, code);
    }


}





