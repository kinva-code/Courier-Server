package com.example.xiangfengtea.service;

import com.example.xiangfengtea.entity.ReceiveInfo;
import com.example.xiangfengtea.entity.User;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface PersonalCenterService {
    void setPrePageForCallBack(String prePage, HttpServletResponse response);
    User getUserWithTokenFromDataBase(String token);
    int getReceiveInfoHaveSaveNum(List<ReceiveInfo> receiveInfoList);
    ReceiveInfo getUpdateReceiveInfoByReceiveInfoId(String receiveInfoId,List<ReceiveInfo> receiveInfoList);
    boolean saveUserHeadImg(MultipartFile file,String userId);
    String getUserHeadImgFileName(MultipartFile file,String userId);
    void deleteUserHeadImg(String filename);
    String getPreImgName(String userHeadImg);
    void resetUserReceiveInfoId(User user,Long receiveInfoId);
}
