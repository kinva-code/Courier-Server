package com.example.xiangfengtea.service;

import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

public interface RetrievePasswordService {
    Object getRetrievePasswordVerifyCode(String email);
    Object checkRetrievePasswordVerifyCode(String email,String code);
    Object checkUserInfoAndUpdate(String email, String password, HttpServletRequest request);
    Model setEmailAndName(String email,Model model);
}
