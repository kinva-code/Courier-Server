package com.example.xiangfengtea.service.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.example.xiangfengtea.entity.User;
import com.example.xiangfengtea.service.EmailService;
import com.example.xiangfengtea.service.RetrievePasswordService;
import com.example.xiangfengtea.service.UserService;
import com.example.xiangfengtea.util.CookieUtil;
import com.example.xiangfengtea.util.RedisUtil;
import com.example.xiangfengtea.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class RetrievePasswordServiceImpl implements RetrievePasswordService {
    @Autowired
    private TokenUtil tokenUtil;
    @Autowired
    private CookieUtil cookieUtil;
    private final UserService userService;
    private final EmailService emailService;
    private final RedisUtil redisUtil;

    public RetrievePasswordServiceImpl(UserService userService, EmailService emailService, RedisUtil redisUtil) {
        this.userService = userService;
        this.emailService = emailService;
        this.redisUtil = redisUtil;
    }

    @Override
    public Model setEmailAndName(String email,Model model) {
        model.addAttribute("email",email);
        User user=userService.findUserByUserEmail(email);
        if(user!=null){
            model.addAttribute("name",user.getUserName());
        }else {
            model.addAttribute("name","");
        }
        return model;
    }

    @Override
    public Object getRetrievePasswordVerifyCode(String email) {
        JSONObject jsonObject=new JSONObject();
        boolean emailRegisted=false;
        User user = userService.findUserByUserEmail(email);
        if(user!=null){
            emailRegisted=true;
            System.out.println("邮箱已注册");
        }
        jsonObject.put("emailRegisted",emailRegisted);
        boolean success=true;
        if(emailRegisted){
            String subject="湘丰茶叶邮箱验证码";
            String doing="湘丰茶叶购物网进行密码修改";
            String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);
            boolean hasSend = emailService.sendHtmlMail(email,subject,doing,verifyCode);
            if(hasSend){
                System.out.println("send email true");
                redisUtil.set(email,verifyCode,redisUtil.getVerifyCodeTimeout());
            }else {
                System.out.println("send email flase");
                success=false;
            }
        }else {
            success=false;
        }
        jsonObject.put("success",success);
        return jsonObject;
    }

    @Override
    public Object checkRetrievePasswordVerifyCode(String email, String code) {
        JSONObject jsonObject=new JSONObject();
        try{
            if(redisUtil.hasKey(email)){
                if(redisUtil.get(email).equals(code)){
                    System.out.println("checkRetrieveCode验证码正确");
                    jsonObject.put("success",true);
                }else {
                    System.out.println(code);
                    System.out.println(redisUtil.get(email));
                    System.out.println("checkRetrieveCode验证码不正确");
                    jsonObject.put("success",false);
                }
            }else {
                System.out.println("checkRetrieveCode验证码不存在");
                jsonObject.put("success",false);
            }
        }catch (Exception e){
        }
        return jsonObject;
    }

    @Override
    public Object checkUserInfoAndUpdate(String email, String password, HttpServletRequest request) {
        JSONObject jsonObject=new JSONObject();
        boolean success=true;
        try{
            User user=userService.findUserByUserEmail(email);
            if(user!=null){
                user.setUserPassword(password);
                userService.saveUser(user);
                String token=cookieUtil.getCookieValue(request,"token");
                if(token!=null){
                    String id=tokenUtil.getTokenUserId(token);
                    redisUtil.deleteKey(id);
                }
            }else {
                success=false;
                jsonObject.put("message","该账号不存在");
            }
        }catch (Exception e){
            e.printStackTrace();
            success=false;
            jsonObject.put("message","修改失败，请稍后重试");
        }
        jsonObject.put("success",success);
        return jsonObject;
    }
}
