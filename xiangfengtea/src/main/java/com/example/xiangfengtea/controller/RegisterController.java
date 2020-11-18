package com.example.xiangfengtea.controller;

import com.example.xiangfengtea.service.RegisterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RegisterController {
    private final RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @RequestMapping("/register/page1")
    public String get_register_page1(){
        return "register/page1";
    }

    @RequestMapping("/register/page2")
    public String get_register_page2(String email,Model model){
        if(email!=null){
            model.addAttribute("email",email);
            return "register/page2";
        }
        return "redirect:page1";
    }

    @RequestMapping("/register/page3")
    public String get_register_page3(){
        return "register/page3";
    }

    @RequestMapping("/register/getVerifyCode")
    @ResponseBody
    public Object getVerifyCode(String email){
        return registerService.getRegisterVerifyCode(email);
    }

    @RequestMapping("/register/checkCode")
    @ResponseBody
    public Object checkCode(String email,String code){
        return registerService.checkRegisterVerifyCode(email,code);
    }

    @RequestMapping("/register/checkUserInfoAndSave")
    @ResponseBody
    public Object checkUserInfoAndSave(String email,String name,String password,String phonenum){
        return registerService.checkUserInfoAndSave(email,name,password,phonenum);
    }
}
