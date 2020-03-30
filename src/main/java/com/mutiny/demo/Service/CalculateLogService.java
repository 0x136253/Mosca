package com.mutiny.demo.Service;

import com.mutiny.demo.dto.CalculateShowCalDTO;

import java.util.List;

/**
 * @Author: Anonsmd
 * @Date: 2020/2/10 12:22
 */
public interface CalculateLogService {
    List<CalculateShowCalDTO> showansw(int day) throws Exception;
}
