package com.mutiny.demo.dto;

import java.util.*;

/**
 * @Author: Anonsmd
 * @Date: 2020/1/30 12:25
 */
public class FileModuleConDTO {
    private List<FileModuleUploaderDTO> fileModuleUploaderDTOList;
    private int totle;
    private int now;

    public FileModuleUploaderDTO Synthesis() {
        FileModuleUploaderDTO fileModuleUploaderDTO = new FileModuleUploaderDTO();
        FileModuleUploaderDTO temp=fileModuleUploaderDTOList.get(0);
        fileModuleUploaderDTO.setModuleID(temp.getModuleID());
        fileModuleUploaderDTO.setEncodeAlgorithm(temp.getEncodeAlgorithm());
        if (temp.getKeyFileId()!=0){
            fileModuleUploaderDTO.setKeyFileId(temp.getKeyFileId());
        }
        fileModuleUploaderDTO.setIs_default(temp.isIs_default());
        fileModuleUploaderDTO.setTotal(temp.getTotal());
        fileModuleUploaderDTO.setData(new HashMap<String, List<String>>());
        Iterator<String> keySet=temp.getData().keySet().iterator();
        while (keySet.hasNext()){
            String key = keySet.next();
            fileModuleUploaderDTO.getData().put(key,new ArrayList<String>());
        }
        for (int i=0;i<fileModuleUploaderDTOList.size();i++){
            Iterator<Map.Entry< String,List<String> >> it =fileModuleUploaderDTOList.get(i).getData().entrySet().iterator();
            while(it.hasNext()){
                //得到每一对对应关系
                Map.Entry< String,List<String> > entry = it.next();
                //通过每一对对应关系获取对应的key
                String key = entry.getKey();
                //通过每一对对应关系获取对应的value
                List<String> value = entry.getValue();
                for (int j=0;j<value.size();j++){
                    fileModuleUploaderDTO.getData().get(key).add(value.get(j));
                }
//                System.out.println(key+"="+value);
            }
        }
        return fileModuleUploaderDTO;
    }

    public List<FileModuleUploaderDTO> getFileModuleUploaderDTOList() {
        return fileModuleUploaderDTOList;
    }

    public void setFileModuleUploaderDTOList(List<FileModuleUploaderDTO> fileModuleUploaderDTOList) {
        this.fileModuleUploaderDTOList = fileModuleUploaderDTOList;
    }

    public int getTotle() {
        return totle;
    }

    public void setTotle(int totle) {
        this.totle = totle;
    }

    public int getNow() {
        return now;
    }

    public void setNow(int now) {
        this.now = now;
    }
}
