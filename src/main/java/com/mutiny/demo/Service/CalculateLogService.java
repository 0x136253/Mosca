package com.mutiny.demo.Service;

import com.mutiny.demo.dto.CalculateShowModuleDTO;

import java.util.List;

/**
 * @Author: Anonsmd
 * @Date: 2020/2/10 12:22
 */
public interface CalculateLogService {
    CalculateShowModuleDTO showansw(int day, int moduleId, String type, boolean isDefault) throws Exception;

    List<CalculateShowModuleDTO> showDefaultAnswAll(int day, String answ) throws Exception;
}
