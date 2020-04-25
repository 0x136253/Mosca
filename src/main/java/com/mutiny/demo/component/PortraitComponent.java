package com.mutiny.demo.component;


import com.mutiny.demo.dao.FileOtherMapper;
import com.mutiny.demo.dao.UserMapper;
import com.mutiny.demo.pojo.FileOther;
import com.mutiny.demo.pojo.FileOtherExample;
import com.mutiny.demo.pojo.User;
import com.mutiny.demo.util.PortraitUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class PortraitComponent {
    @Autowired
    private FilePath filePath;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private FileOtherMapper fileOtherMapper;
    public String getImg(String username) throws Exception{
        User user = userMapper.selectByPrimaryKey(username);
        FileOther fileOther = null;
        if (user.getPortraitId()!=null && user.getPortraitId()!=0){
            try {
                fileOther = fileOtherMapper.selectByPrimaryKey(user.getPortraitId());
                File file = new File(fileOther.getFileUrl());
                if (file.exists()){
                    return fileOther.getFileUrl();
                }
            }
            catch (Exception e){
            }
        }
        String str = username;
        if (user.getName()!=null){
            str = user.getName();
        }
        String url = PortraitUtils.generateImg(str);
        FileOther fileOther1 = new FileOther();
        fileOther1.setFileName(username+"'s Portrait");
        fileOther1.setFileUrl(url);
        fileOther1.setType(0);
        fileOtherMapper.insertSelective(fileOther1);
        FileOtherExample fileOtherExample = new FileOtherExample();
        fileOtherExample.createCriteria().andFileUrlEqualTo(url);
        user.setPortraitId(fileOtherMapper.selectByExample(fileOtherExample).get(0).getFileId());
        userMapper.updateByPrimaryKeySelective(user);
        return url;
    }
}
