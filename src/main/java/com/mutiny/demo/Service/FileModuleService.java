package com.mutiny.demo.Service;

import com.mutiny.demo.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author: Anonsmd
 * @Date: 2020/1/30 12:16
 */
public interface FileModuleService {
    String delete(String username,int FileID) throws Exception;
    FileModuleTempShowListDTO show(int moduleid,boolean IsDefualt) throws Exception;
    String uploadData(MultipartFile file, int moduleId, boolean isDefault, String username) throws Exception;
    List<ModuleUploadInfoDTO> showUploadNeed(String username, int moduleId, boolean isDefault) throws Exception;
    List<DefaultDataStatusDTO> showDefaultDataStatus(String username) throws Exception;
    List<ModuleStatusDTO> showModuleStatus(String getUsername) throws Exception;

    List<DefaultDataStatusDTO> showReadyCal_Gover(String getUsername) throws Exception;

    List<ModuleStatusDTO> showReadyCal_Comp(String getUsername)throws Exception;

}
