package com.frame.intercepter;

import com.frame.po.User;
import com.frame.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginIntercepter implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        System.out.println(uri);
        if(request.getSession().getAttribute("userId")!=null){
            return true;
        }
        else{
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
                response.getWriter().print("noSession");
                return false;
            }
            User user = userService.login(username,password);
            if(user!=null){
                request.getSession().setAttribute("userId",user.getUser_id());
                return true;
            }
            response.getWriter().print("noSession");
            return false;
        }
    }
}
