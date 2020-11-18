package com.example.xiangfengtea.controller;

import com.example.xiangfengtea.service.RetrievePasswordService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RetrievePasswordController {
    private final RetrievePasswordService retrievePasswordService;

    public RetrievePasswordController(RetrievePasswordService retrievePasswordService) {
        this.retrievePasswordService = retrievePasswordService;
    }

    @RequestMapping("/update_password/page1")
    public String get_retrieve_password_page1(String email,Model model){
        if(email!=null){
            model.addAttribute("email",email);
        }
        return "update_password/page1";
    }

    @RequestMapping("/update_password/page2")
    public String retrievePassword(String email, Model model){
        if(email!=null){
            retrievePasswordService.setEmailAndName(email,model);
            return "update_password/page2";
        }
        return "redirect:page1";
    }

    @RequestMapping("/update_password/page3")
    public String get_retrieve_password_page3(){
        return "update_password/page3";
    }

    @RequestMapping("/update_password/getVerifyCode")
    @ResponseBody
    public Object getRetrievePasswordVerifyCode(String email){
        return retrievePasswordService.getRetrievePasswordVerifyCode(email);
    }

    @RequestMapping("/update_password/checkVerifyCode")
    @ResponseBody
    public Object checkRetrievePasswordVerifyCode(String email,String code){
        return retrievePasswordService.checkRetrievePasswordVerifyCode(email,code);
    }

    @RequestMapping("/update_password/checkUserInfoAndUpdate")
    @ResponseBody
    public Object checkUserInfoAndUpdate(String email, String password, HttpServletRequest request){
        return retrievePasswordService.checkUserInfoAndUpdate(email,password,request);
    }
}
