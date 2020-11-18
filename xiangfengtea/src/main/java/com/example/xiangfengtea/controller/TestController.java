package com.example.xiangfengtea.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
public class TestController {
    @Value("${upload.image.userHead.absolute}")
    String path;

    @Value("${upload.image.userHead.relative}")
    String relative;

    // 跳转上传页面
    @RequestMapping("/filetest")
    public String test() {
        return "upload";
    }

    // 执行上传
    @RequestMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file, Model model) {
        // 获取上传文件名
        String filename = file.getOriginalFilename();
        // 定义上传文件保存路径
        //String path = "D:/xiangfengtea/images/user/";
        // 新建文件
        File filesave=new File(path+filename);
        try {
            if(!filesave.exists()){
                filesave.createNewFile();
            }
            // 写入文件
            file.transferTo(filesave);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 将src路径发送至html页面
        model.addAttribute("filename", relative+filename);
        return "upload";
    }
}
