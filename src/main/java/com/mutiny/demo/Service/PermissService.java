package com.mutiny.demo.Service;

import com.mutiny.demo.dto.PermissRequestDTO;
import com.mutiny.demo.dto.PermissionApplyDTO;
import com.mutiny.demo.dto.ProjectListDTO;

import java.util.List;

/**
 * @Author: Anonsmd
 * @Date: 2020/2/5 11:36
 */
public interface PermissService {
    String partJoin(String inviteCode,String username) throws Exception;
    String watchJoin(String inviteCode,String username) throws Exception;
    String govePermissRequest(PermissRequestDTO permissRequestDTO,String username) throws Exception;
    List<PermissionApplyDTO> showPermissGet(String username) throws Exception;
    String pushPermiss(int retionId,int staus,String username) throws Exception;
}
