package com.frame.controller;

import com.frame.po.Artist;
import com.frame.po.User;
import com.frame.service.UserService;
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
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/session",method = RequestMethod.POST)
    public boolean isOk(String name, String password, HttpServletRequest request, HttpServletResponse response)throws Exception{
        User user = userService.login(name,password);
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
    @RequestMapping(value = "/session",method = RequestMethod.DELETE)
    public boolean logout(HttpSession session,HttpServletResponse response){
        session.removeAttribute("userId");
        Cookie cookie = new Cookie("username",null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        cookie = new Cookie("password",null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return true;
    }

    @RequestMapping(value = "/session",method = RequestMethod.GET)
    public User isOk(HttpServletRequest request){
        Cookie cookies[] = request.getCookies();
        if(cookies==null)return null;
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
        User user = userService.login(username,password);
        if(user!=null){
            request.getSession().setAttribute("userId",user.getUser_id());
        }

        return user;
    }

    @RequestMapping(value = "/user",method = RequestMethod.POST)
    public boolean registerUser(String name,String password,HttpServletRequest request,HttpServletResponse response)throws Exception{
        User user = userService.register(name,password);
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
        return userService.getName(name);
    }

    @RequestMapping(value = "/user/info/avatar",method = RequestMethod.POST)
    public String updateAvatar(HttpServletRequest request) throws Exception{
        return userService.updateAvatar(request);
    }
    @RequestMapping(value = "/user/info/comment",method = RequestMethod.POST)
    public boolean updateComment(HttpSession session,String comment) throws Exception{
        return userService.updateComment((int)session.getAttribute("userId"),comment);
    }

    @RequestMapping(value = "/artist/{id}",method = RequestMethod.GET)
    public Artist getArtistByArtwork(@PathVariable("id")int ArtworkId, HttpSession session){
        Object userId = session.getAttribute("userId");
        if(userId!=null){
            return userService.getUserByArtworkId(ArtworkId,(int)userId);
        }
        else{
            return userService.getUserByArtworkId(ArtworkId,-1);
        }
    }
    @RequestMapping(value = "/artist/{id}/info",method = RequestMethod.GET)
    public Artist getArtistById(@PathVariable("id")int artistId, HttpSession session){
        Object userId = session.getAttribute("userId");
        if(userId!=null){
            return userService.getUserById((int)userId,artistId);
        }
        else{
            return userService.getUserById(-1,artistId);
        }
    }
    @RequestMapping(value = "/admin/role/{id}",method = RequestMethod.POST)
    public boolean addRole(@PathVariable("id")int artistId, HttpSession session){
        return userService.alterRole((int)session.getAttribute("userId"),artistId,1);
    }
    @RequestMapping(value = "/admin/role/{id}",method = RequestMethod.DELETE)
    public boolean deleteRole(@PathVariable("id")int artistId, HttpSession session){
        return userService.alterRole((int)session.getAttribute("userId"),artistId,0);
    }
}
