package com.mutiny.demo.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author: Anonsmd
 * @Date: 2020/3/18 15:08
 */
@Component
public class FilePath {
    @Value("${file.datapath.win}")
    private String windowsPath;
    @Value("${file.datapath.linux}")
    private String linuxPath;
    public  String returnBasicPath(){
        String system = System.getProperty("os.name");
        if (system.startsWith("Windows")){
            return windowsPath;
        }
        else if (system.startsWith("Linux")){
            return linuxPath;
        }
        else {
            return windowsPath;
        }
    }
}
