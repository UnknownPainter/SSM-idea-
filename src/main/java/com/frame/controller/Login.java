package com.frame.controller;

import com.frame.po.User;
import com.frame.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller

@ResponseBody
public class Login {

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/session",method = RequestMethod.POST)
    public boolean isOk(String name, String password, HttpServletRequest request, HttpServletResponse response)throws Exception{
        User user = loginService.login(name,password);
        if(user!=null){
            request.getSession().setAttribute("userId",user.getUser_id());
            Cookie cookie = new Cookie("username",user.getUser_name());
            cookie.setMaxAge(60*60*24*14);
            cookie.setPath("/");
            response.addCookie(cookie);
            cookie = new Cookie("password",user.getUser_password());
            cookie.setMaxAge(60*60*24*14);
            cookie.setPath("/");
            response.addCookie(cookie);
            return true;
        }
        else{
            return false;
        }
    }

    @RequestMapping(value = "/session",method = RequestMethod.GET)
    public User isOk(HttpServletRequest request){
        Cookie cookies[] = request.getCookies();
        String username="";String password="";
        for(Cookie cookie:cookies){
            if("username".equals(cookie.getName())){
                username = cookie.getValue();
            }
            if("password".equals(cookie.getName())){
                password = cookie.getValue();
            }
        }
        if(username.equals("")||password.equals("")){
            return null;
        }
        User user = loginService.login(username,password);
        if(user!=null){
            request.getSession().setAttribute("userId",user.getUser_id());
        }

        return user;
    }

    @RequestMapping(value = "/user",method = RequestMethod.POST)
    public boolean registerUser(String name,String password,HttpServletRequest request,HttpServletResponse response)throws Exception{
        User user = loginService.register(name,password);
        request.getSession().setAttribute("userId",user.getUser_id());
        Cookie cookie = new Cookie("username",user.getUser_name());
        cookie.setMaxAge(60*60*24*14);
        cookie.setPath("/");
        response.addCookie(cookie);
        cookie = new Cookie("password",user.getUser_password());
        cookie.setMaxAge(60*60*24*14);
        cookie.setPath("/");
        response.addCookie(cookie);
        return true;
    }

    @RequestMapping(value = "/user/{name}",method = RequestMethod.GET)
    public boolean nameIsOk(@PathVariable("name") String name){
        return loginService.getName(name);
    }
}
