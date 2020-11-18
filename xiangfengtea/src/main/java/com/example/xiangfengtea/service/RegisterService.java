package com.example.xiangfengtea.service;

public interface RegisterService {
    Object getRegisterVerifyCode(String email);
    Object checkRegisterVerifyCode(String email,String code);
    Object checkUserInfoAndSave(String email,String name,String password,String phonenum);
}
