package com.mutiny.demo.component;

import com.mutiny.demo.FHE.Key;
import com.mutiny.demo.FHE.KeyGeneration;
import com.mutiny.demo.dao.KeyFileMapper;
import com.mutiny.demo.pojo.KeyFile;
import com.mutiny.demo.pojo.KeyFileExample;
import com.mutiny.demo.util.CSVUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Anonsmd
 * @Date: 2020/3/18 17:07
 */
@Component
public class KeyFileComponent {
    @Autowired
    private KeyFileMapper keyFileMapper;
    @Autowired
    private FilePath filePath;

    public int creatKeyFile(int size){
        Key key = KeyGeneration.keyGeneration(size);
        String path = CSVUtils.writeCsvFile_s(filePath.returnBasicPath()+"key",key);
        KeyFile keyFile = new KeyFile();
        KeyFileExample keyFileExample = new KeyFileExample();
        keyFileExample.createCriteria();
        int keySize = keyFileMapper.selectByExample(keyFileExample).size();
        keyFile.setFileurl(path);
        keyFile.setKeyfileid(keySize+1);
        keyFileMapper.insertSelective(keyFile);
        return keySize+1;
    }

    public Key getKey(int keyId){
        KeyFile keyFile = keyFileMapper.selectByPrimaryKey(keyId);
        Map<String, List<String>> keyFileMap = CSVUtils.readCsvFile(keyFile.getFileurl(),-1);
        Key key = new Key();
        key.P1 = new BigInteger(keyFileMap.get("P1").get(0));
        key.N = new BigInteger(keyFileMap.get("N").get(0));
        key.g1 = new BigInteger(keyFileMap.get("g1").get(0));
        key.g2 = new BigInteger(keyFileMap.get("g2").get(0));
        key.T = new BigInteger(keyFileMap.get("T").get(0));
        return key;
    }

}
