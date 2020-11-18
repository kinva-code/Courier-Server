package com.example.xiangfengtea.controller;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.example.xiangfengtea.annotation.UserLoginToken;
import com.example.xiangfengtea.dao.TestDao;
import com.example.xiangfengtea.entity.Test;
import com.example.xiangfengtea.entity.User;
import com.example.xiangfengtea.service.EmailService;
import com.example.xiangfengtea.service.UserService;
import com.example.xiangfengtea.util.CookieUtil;
import com.example.xiangfengtea.util.RedisUtil;
import com.example.xiangfengtea.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

@Controller
public class LoginController {
    @Autowired
    private TestDao testDao;
    private final UserService userService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private CookieUtil cookieUtil;
    @Autowired
    private TokenUtil tokenUtil;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/home")
    public String getHome(Model model,HttpServletRequest request){
        String token=cookieUtil.getCookieValue(request,"token");
        String userId=null;
        User user=null;
        boolean isLogin=false;
        if(token!=null){
            userId=tokenUtil.getTokenUserId(token);
            try{
                user=userService.findUserByUserId(Long.parseLong(userId));
                if(user!=null){
                    isLogin=true;
                    model.addAttribute("userName","用户名："+user.getUserName());
                    SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                    Long userVipTime=df.parse(user.getUserVipDeadline()).getTime();
                    Long currentTime=System.currentTimeMillis();
                    if(currentTime<userVipTime){
                        df=new SimpleDateFormat("yyyy-MM-dd");
                        model.addAttribute("vipMessage","会员时间至："+df.format(userVipTime));
                    }else {
                        model.addAttribute("vipMessage","普通用户");
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        model.addAttribute("isLogin",isLogin);
        if(isLogin){
            if(user!=null && user.getUserHeadImage()!=null){
                model.addAttribute("userHeadImage",user.getUserHeadImage());
                return "home";
            }
        }
        model.addAttribute("userHeadImage","/image/user_head.png");
        return "home";
    }

    @RequestMapping("/login")
    public String login(HttpServletResponse response){
        cookieUtil.setCookieForThisTime("prePage","login",response);
        return "login";
    }

    @RequestMapping("/logout")
    @ResponseBody
    public Object logout(HttpServletResponse response){
        JSONObject jsonObject=new JSONObject();
        cookieUtil.removeCookie("token",response);
        return jsonObject;
    }

    @RequestMapping("/checkNameAndPassword")
    @ResponseBody
    public Object checkNameAndPassword(String account,String password,HttpServletResponse response){
        JSONObject jsonObject=new JSONObject();
        boolean login=true;
        boolean accountExist=false;
        User user=userService.findUserByUserName(account);
        if(user!=null){
            accountExist=true;
            if(user.getUserPassword().equals(password)){
                login=true;
            }else {
                login=false;
            }
        }else {
            user=userService.findUserByUserEmail(account);
            if(user!=null){
                accountExist=true;
                if(user.getUserPassword().equals(password)){
                    login=true;
                }else {
                    login=false;
                }
            }else{
                login=false;
            }
        }
        if(login){
            String token = tokenUtil.getToken(user);
            System.out.println("login and save token for 1h");
            redisUtil.set(user.getUserId().toString(),token,redisUtil.getRedisTokenTimeout());
            cookieUtil.setCookie("token",token,cookieUtil.getCookieMaxAge(),response);
            System.out.println("addCookie");
        }
        jsonObject.put("accountExist",accountExist);
        jsonObject.put("login",login);
        return jsonObject;
    }

    @RequestMapping("/back")
    public String back(HttpServletRequest request){
        String prePage = cookieUtil.getCookieValue(request,"prePage");
        System.out.println(prePage);
        if(prePage==null){
            return "redirect:home";
        }
        return "redirect:"+prePage;
    }

    @RequestMapping("/test")
    @ResponseBody
    public String test(HttpServletRequest request){
        BigDecimal p=new BigDecimal(30);
        Test t=testDao.findByGoodPrice(p);
        if(t==null){
            System.out.println("null");
        }else {
            System.out.println(t.getId()+"%"+t.getGoodLaunchTime());
        }

        Long time=System.currentTimeMillis();
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String strtime=df.format(time);
        try{
            Long get=df.parse(strtime).getTime();
            System.out.println(time);
            System.out.println(strtime);
            System.out.println(get);
        }catch (Exception e){
            e.printStackTrace();
        }


        User user=userService.findUserByUserEmail("1193013640@qq.com");
        if(user!=null){
            System.out.println("user != null");
            System.out.println(user.getUserPassword());
        }else {
            System.out.println("user == null");
        }
        JSONObject jsonObject=new JSONObject();
        return jsonObject.toJSONString();
    }
}
