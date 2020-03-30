package com.mutiny.demo.Service;

/**
 * @Author: Anonsmd
 * @Date: 2020/2/10 12:21
 */
public interface CalculateService {
    String calModule(int moduleID,String username) throws Exception;
    String calDefaultModule(int defaultDataId,String username) throws Exception;
}
