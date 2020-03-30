package com.mutiny.demo.controller;

import com.mutiny.demo.api.CommonResult;
import com.mutiny.demo.api.MyLog;
import com.mutiny.demo.component.FilePath;
import com.mutiny.demo.dao.FileOtherMapper;
import com.mutiny.demo.dto.FileModuleUploaderDTO;
import com.mutiny.demo.pojo.FileOther;
import com.mutiny.demo.pojo.FileOtherExample;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: Anonsmd
 * @Date: 2020/1/10 16:36
 */
@Controller
@Api(tags = "FileOtherController", description = "其他文件管理")
@RequestMapping("/file/other")
public class FileOtherController {
    @Autowired
    private FilePath filePaths;
    @Autowired
    private FileOtherMapper fileOtherMapper;

    @MyLog(operation = "上传文件",database = "File_Other",flag = false)
    @ApiOperation(value = "上传文件")
    @PostMapping(value = "/upload")
    @ResponseBody
    public ResponseEntity<Map<String,Object>> uploadData(@RequestParam(value = "file") MultipartFile file) throws IOException {
        try {
            if (file.isEmpty()) {
                return CommonResult.failed("文件为空");
            }
            // 获取文件名
            String fileName = file.getOriginalFilename();
            System.out.println(fileName);
            // 获取文件的后缀名
            String suffixName = ".png";
            // 设置文件存储路径
            fileName = UUID.randomUUID() + suffixName;
            String filePath = filePaths.returnBasicPath();
            String path = filePath + fileName;
            File dest = new File(path);
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            file.transferTo(dest);
            FileOther fileOther =new FileOther();
            fileOther.setFileName(filePaths.returnBasicPath());
            fileOther.setFileUrl(filePaths.returnBasicPath()+fileName);
            fileOtherMapper.insert(fileOther);
            FileOtherExample fileOtherExample = new FileOtherExample();
            fileOtherExample.createCriteria().andFileUrlEqualTo(filePaths.returnBasicPath()+fileName);
            int fileID = fileOtherMapper.selectByExample(fileOtherExample).get(0).getFileId();
            Map<String,Object> map = new HashMap<>();
            map.put("fileurl","/image/"+fileName);
            map.put("fileID", fileID);
            return CommonResult.success(map);
        } catch (IllegalStateException e) {
            return CommonResult.failed();
        } catch (IOException e) {
            return CommonResult.failed();
        }
    }
}
