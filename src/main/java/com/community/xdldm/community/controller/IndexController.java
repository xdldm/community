package com.community.xdldm.community.controller;


import com.community.xdldm.community.mapper.UserMapper;
import com.community.xdldm.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class IndexController {

    @Autowired
    UserMapper userMapper;
    @GetMapping("/")
    public String index(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);//根据token寻找用户判断是否已经登录
                    if (user!= null) {
                        request.getSession().setAttribute("user", user);//把user写入session
                    }
                    break;
                }
            }
        }


        return "index";
    }

}
