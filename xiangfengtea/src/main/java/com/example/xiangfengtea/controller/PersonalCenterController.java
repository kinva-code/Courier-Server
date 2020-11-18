package com.example.xiangfengtea.controller;

import com.example.xiangfengtea.annotation.UserLoginToken;
import com.example.xiangfengtea.entity.ReceiveInfo;
import com.example.xiangfengtea.entity.User;
import com.example.xiangfengtea.service.PersonalCenterService;
import com.example.xiangfengtea.service.UserService;
import com.example.xiangfengtea.util.CookieUtil;
import com.example.xiangfengtea.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class PersonalCenterController {
    @Autowired
    private CookieUtil cookieUtil;
    @Autowired
    private UserService userService;
    @Autowired
    private TokenUtil tokenUtil;

    @Value("${upload.image.userHead.absolute}")
    private String path;

    @Value("${upload.image.userHead.relative}")
    private String relative;

    @Value(("${address.receiveAddress.topLimit}"))
    private int topLimitReceiveAddress;

    @Autowired
    private PersonalCenterService personalCenterService;

    @UserLoginToken
    @RequestMapping("/my_xiangfeng/personal_center")
    public String personal_center(){
        return "my_xiangfeng/personal_center";
    }

    @UserLoginToken
    @RequestMapping("/account_manage/account_security")
    public String account_security(Model model, HttpServletRequest request, HttpServletResponse response){
        personalCenterService.setPrePageForCallBack("account_security",response);
        String token=cookieUtil.getCookieValue(request,"token");
        User user=personalCenterService.getUserWithTokenFromDataBase(token);
        if(user!=null){
            model.addAttribute("userName",user.getUserName());
            model.addAttribute("email",user.getUserEmail());
            model.addAttribute("phoneNum",user.getPhoneNum());
        }
        return "account_manage/account_security";
    }

    @UserLoginToken
    @RequestMapping("/account_manage/personal_info")
    public String personal_info(Model model,HttpServletRequest request){
        String token=cookieUtil.getCookieValue(request,"token");
        User user=personalCenterService.getUserWithTokenFromDataBase(token);
        if(user!=null){
            if(user.getUserHeadImage()==null){
                model.addAttribute("userHeadImage","/image/user_head.png");
            }else {
                model.addAttribute("userHeadImage",user.getUserHeadImage());
            }
            model.addAttribute("sex",user.getUserSex());
            model.addAttribute("age",user.getUserAge());
        }
        return "account_manage/personal_info";
    }

    @UserLoginToken
    @RequestMapping("/account_manage/receive_address")
    public String receive_address(Model model,HttpServletRequest request,String receiveInfoId){
        String token=cookieUtil.getCookieValue(request,"token");
        User user=personalCenterService.getUserWithTokenFromDataBase(token);
        if(user!=null){
            List<ReceiveInfo> receiveInfoList=userService.findReceiveInfoByUserId(user.getUserId());
            int haveSave = personalCenterService.getReceiveInfoHaveSaveNum(receiveInfoList);
            model.addAttribute("haveSave",haveSave);
            model.addAttribute("canSave",topLimitReceiveAddress-haveSave);
            model.addAttribute("receiveInfoList",receiveInfoList);
            model.addAttribute("defaultReceiveInfo",user.getReceiveInfoId());
            ReceiveInfo receiveInfo = personalCenterService.getUpdateReceiveInfoByReceiveInfoId(receiveInfoId,receiveInfoList);
            if(receiveInfo!=null){
                model.addAttribute("receiveInfoId",receiveInfoId);
                model.addAttribute("receiveName",receiveInfo.getReceiveName());
                model.addAttribute("addressInfo",receiveInfo.getAddressInfo());
                model.addAttribute("receiveAddress",receiveInfo.getReceiveAddress());
                model.addAttribute("phoneNum",receiveInfo.getPhoneNum());
                if(user.getReceiveInfoId()==receiveInfo.getReceiveInfoId()){
                    model.addAttribute("isDefault",true);
                }else {
                    model.addAttribute("isDefault",false);
                }
            }
        }
        return "account_manage/receive_address";
    }

    @UserLoginToken
    @RequestMapping("/account_manage/updateUserPhoneNum")
    public String updateUserPhone(String newPhoneNum,HttpServletRequest request){
        String token=cookieUtil.getCookieValue(request,"token");
        User user=personalCenterService.getUserWithTokenFromDataBase(token);
        if(user!=null){
            user.setPhoneNum(newPhoneNum);
            userService.saveUser(user);
        }
        return "redirect:account_security";
    }

    @UserLoginToken
    @RequestMapping("/account_manage/uploadImg")
    public String uploadImg(@RequestParam("file") MultipartFile file,HttpServletRequest request){
        String token=cookieUtil.getCookieValue(request,"token");
        User user = personalCenterService.getUserWithTokenFromDataBase(token);
        if(user!=null){
            String filename = personalCenterService.getUserHeadImgFileName(file,user.getUserId().toString());
            boolean fileWriteToSuccess = personalCenterService.saveUserHeadImg(file,filename);
            //文件写入成功
            if(fileWriteToSuccess){
                try{
                    String preUserHeadImgName = personalCenterService.getPreImgName(user.getUserHeadImage());
                    user.setUserHeadImage(relative+filename);
                    userService.saveUser(user);
                    personalCenterService.deleteUserHeadImg(preUserHeadImgName);
                }catch (Exception e){
                    System.out.println("文件写入成功，但user表保存失败");
                    //删除保存失败的文件
                    personalCenterService.deleteUserHeadImg(filename);
                }
            }
        }
        return "redirect:personal_info";
    }

    @UserLoginToken
    @RequestMapping("/account_manage/saveUserInfo")
    public String saveUserInfo(String sex,String age,HttpServletRequest request){
        String token=cookieUtil.getCookieValue(request,"token");
        User user = personalCenterService.getUserWithTokenFromDataBase(token);
        if(user!=null){
            user.setUserSex(sex);
            user.setUserAge(Integer.parseInt(age));
            userService.saveUser(user);
        }
        return "redirect:personal_info";
    }

    @UserLoginToken
    @RequestMapping("/account_manage/saveReceiveInfo")
    public String saveReceiveInfo(String receiveInfoId,String addressInfo,String receiveAddress,String receiveName,String phoneNum,String isDefaultAddress,HttpServletRequest request){
        String token=cookieUtil.getCookieValue(request,"token");
        User user = personalCenterService.getUserWithTokenFromDataBase(token);
        if(user!=null){
            ReceiveInfo receiveInfo=new ReceiveInfo();
            try{
                Long id=Long.parseLong(receiveInfoId);
                if(id>=0){
                    receiveInfo.setReceiveInfoId(Long.parseLong(receiveInfoId));
                }
            }catch (Exception e){
                System.out.println("Long.parseLong(receiveInfoId) error");
            }
            receiveInfo.setAddressInfo(addressInfo);
            receiveInfo.setReceiveAddress(receiveAddress);
            receiveInfo.setReceiveName(receiveName);
            receiveInfo.setPhoneNum(phoneNum);
            receiveInfo.setUserId(user.getUserId());
            receiveInfo = userService.saveReceiveInfo(receiveInfo);
            if(isDefaultAddress != null){
                user.setReceiveInfoId(receiveInfo.getReceiveInfoId());
            }else {
                if(receiveInfo.getReceiveInfoId()!=null && user.getReceiveInfoId()==receiveInfo.getReceiveInfoId()){
                    user.setReceiveInfoId(0L);
                }
            }
            userService.saveUser(user);
        }
        return "redirect:receive_address";
    }

    @UserLoginToken
    @RequestMapping("/account_manage/deleteReceiveInfo")
    public String deleteReceiveInfo(String receiveInfoId,HttpServletRequest request){
        userService.deleteReceiveInfoByReceiveInfoId(Long.parseLong(receiveInfoId));
        String token=cookieUtil.getCookieValue(request,"token");
        User user = personalCenterService.getUserWithTokenFromDataBase(token);
        if(user!=null){
            try{
                Long id=Long.parseLong(receiveInfoId);
                if(user.getReceiveInfoId()==id){
                    personalCenterService.resetUserReceiveInfoId(user,0L);
                }
            }catch (Exception e){
                System.out.println("Long.parseLong(receiveInfoId) error");
            }
        }
        return "redirect:receive_address";
    }

    @UserLoginToken
    @RequestMapping("/account_manage/setDefaultReceiveInfo")
    public String setDefaultReceiveInfo(String receiveInfoId,HttpServletRequest request){
        String token=cookieUtil.getCookieValue(request,"token");
        User user = personalCenterService.getUserWithTokenFromDataBase(token);
        if(user!=null){
            try{
                Long id=Long.parseLong(receiveInfoId);
                if(id>=0){
                    personalCenterService.resetUserReceiveInfoId(user,id);
                }
            }catch (Exception e){
                System.out.println("Long.parseLong(receiveInfoId) error");
            }
        }
        return "redirect:receive_address";
    }
}
