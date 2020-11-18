package com.example.xiangfengtea.service.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.example.xiangfengtea.entity.User;
import com.example.xiangfengtea.service.EmailService;
import com.example.xiangfengtea.service.RegisterService;
import com.example.xiangfengtea.service.UserService;
import com.example.xiangfengtea.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class RegisterServiceImpl implements RegisterService {
    private final UserService userService;
    private final EmailService emailService;
    private final RedisUtil redisUtil;

    public RegisterServiceImpl(UserService userService, EmailService emailService, RedisUtil redisUtil) {
        this.userService = userService;
        this.emailService = emailService;
        this.redisUtil = redisUtil;
    }

    @Override
    public Object getRegisterVerifyCode(String email) {
        JSONObject jsonObject=new JSONObject();
        boolean emailRegisted=false;
        User user = userService.findUserByUserEmail(email);
        if(user!=null){
            emailRegisted=true;
            System.out.println("邮箱已注册");
        }
        jsonObject.put("emailRegisted",emailRegisted);
        boolean success=true;
        if(!emailRegisted){
            String subject="湘丰茶叶账号注册验证码";
            String doing="注册湘丰茶叶购物网的账号";
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
    public Object checkRegisterVerifyCode(String email, String code) {
        JSONObject jsonObject=new JSONObject();
        try{
            if(redisUtil.hasKey(email)){
                if(redisUtil.get(email).equals(code)){
                    System.out.println("checkCode验证码正确");
                    jsonObject.put("success",true);
                }else {
                    System.out.println(code);
                    System.out.println(redisUtil.get(email));
                    System.out.println("checkCode验证码不正确");
                    jsonObject.put("success",false);
                }
            }else {
                System.out.println("checkCode验证码不存在");
                jsonObject.put("success",false);
            }
        }catch (Exception e){
        }
        return jsonObject;
    }

    @Override
    public Object checkUserInfoAndSave(String email, String name, String password, String phonenum) {
        JSONObject jsonObject=new JSONObject();
        boolean saveUser=true;
        User user = userService.findUserByUserEmail(email);
        if(user!=null){
            jsonObject.put("emailRegisted",true);
            System.out.println("邮箱已注册");
            saveUser=false;
        }else {
            jsonObject.put("emailRegisted",false);
        }
        user = userService.findUserByUserName(name);
        if(user!=null){
            jsonObject.put("nameRegisted",true);
            System.out.println("用户名已注册");
            saveUser=false;
        }else {
            jsonObject.put("nameRegisted",false);
        }
        if(saveUser){
            System.out.println("saveUser");
            try{
                Timestamp timestamp=new Timestamp(System.currentTimeMillis());
                User newUser=new User();
                newUser.setUserEmail(email);
                newUser.setUserName(name);
                newUser.setUserPassword(password);
                newUser.setPhoneNum(phonenum);
                newUser.setUserVipDeadline(timestamp.toString());
                userService.saveUser(newUser);
                jsonObject.put("success",true);
            }catch (Exception e){
                e.printStackTrace();
                jsonObject.put("success",false);
            }
        }else{
            jsonObject.put("success",false);
        }
        return jsonObject;
    }
}
