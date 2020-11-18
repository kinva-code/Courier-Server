package com.example.xiangfengtea.service.serviceimpl;

import com.example.xiangfengtea.entity.ReceiveInfo;
import com.example.xiangfengtea.entity.User;
import com.example.xiangfengtea.service.PersonalCenterService;
import com.example.xiangfengtea.service.UserService;
import com.example.xiangfengtea.util.CookieUtil;
import com.example.xiangfengtea.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class PersonalCenterServiceImpl implements PersonalCenterService {
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

    @Override
    public void setPrePageForCallBack(String prePage, HttpServletResponse response){
        cookieUtil.setCookieForThisTime("prePage",prePage,response);
    }

    @Override
    public User getUserWithTokenFromDataBase(String token) {
        try{
            if(token!=null){
                String userId=tokenUtil.getTokenUserId(token);
                User user=userService.findUserByUserId(Long.parseLong(userId));
                if(user!=null){
                    return user;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("getUserWithTokenFromDataBase error");
            return null;
        }
        return null;
    }

    @Override
    public int getReceiveInfoHaveSaveNum(List<ReceiveInfo> receiveInfoList) {
        if(receiveInfoList==null){
            return 0;
        }
        return receiveInfoList.size();
    }

    @Override
    public ReceiveInfo getUpdateReceiveInfoByReceiveInfoId(String receiveInfoId,List<ReceiveInfo> receiveInfoList) {
        try{
            Long id=Long.parseLong(receiveInfoId);
            if(id>=0){
                for(ReceiveInfo receiveInfo : receiveInfoList){
                    if(receiveInfo.getReceiveInfoId()==id){
                        return receiveInfo;
                    }
                }
            }
        }catch (Exception e){
            System.out.println("Long.parseLong(receiveInfoId) error");
        }
        return null;
    }

    @Override
    public boolean saveUserHeadImg(MultipartFile file,String filename) {
        // 新建文件
        File filesave=new File(path+filename);
        try {
            if(!filesave.exists()){
                filesave.createNewFile();
            }
            // 写入文件
            file.transferTo(filesave);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String getUserHeadImgFileName(MultipartFile file, String userId) {
        // 获取上传文件名
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        String filename = System.currentTimeMillis() + "_user" + userId + "." + suffix;
        return filename;
    }

    @Override
    public void deleteUserHeadImg(String filename) {
        if(filename==null){
            return;
        }
        File file = new File(path+filename);
        if(file.exists()){
            file.delete();
        }
    }

    @Override
    public String getPreImgName(String userHeadImg) {
        String preImgName;
        if(userHeadImg==null){
            preImgName = null;
        }else {
            preImgName = userHeadImg.substring(userHeadImg.lastIndexOf("/")+1);
        }
        return preImgName;
    }

    @Override
    public void resetUserReceiveInfoId(User user,Long receiveInfoId) {
        user.setReceiveInfoId(receiveInfoId);
        userService.saveUser(user);
    }
}
