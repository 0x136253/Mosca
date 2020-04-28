package com.mutiny.demo.util;

import com.mutiny.demo.FHE.Key;
import com.mutiny.demo.dto.FileModuleUploaderDTO;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.math.BigInteger;
import java.util.*;

/**
 * @Author: Anonsmd
 * @Date: 2020/1/30 17:19
 */
public class CSVUtils {

    //CSV文件分隔符
    private static final String NEW_LINE_SEPARATOR = "\n";

    public static Map<String, List<String>> readCsvFile(String filename,int num){
//        File file = new File(filename);
        try {

            Reader in = new FileReader(filename);
            in = new InputStreamReader(new FileInputStream(filename),"UTF-8");
            CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
            List<String> header = csvParser.getHeaderNames();
            Map<String,List<String> > map = new HashMap<>();
            List<String> tempList = new ArrayList<>();
            for(String str:header){
                if (str.startsWith("\uFEFF")){
                    str = str.replace("\uFEFF", "");
                }else if (str.endsWith("\uFEFF")){
                    str = str.replace("\uFEFF","");
                }
                tempList.add(str);
                map.put(str,new ArrayList<>());
            }
            Iterable<CSVRecord> records = csvParser;
            for (CSVRecord record: records){
                num--;
                for (int i=0;i<header.size();i++){
                    map.get(tempList.get(i)).add(record.get(header.get(i)));
                }
                if (num == 0){
                    return  map;
                }
            }
            return map;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String writeCsvFile_s(String dir, Key datamap){
        Map<String,List<String>> map = new HashMap<>();
        List<String> P1 = new ArrayList<>();
        P1.add(datamap.P1.toString());
        map.put("P1",P1);
        List<String> N = new ArrayList<>();
        N.add(datamap.N.toString());
        map.put("N",N);
        List<String> g1 = new ArrayList<>();
        g1.add(datamap.g1.toString());
        map.put("g1",g1);
        List<String> g2 = new ArrayList<>();
        g2.add(datamap.g2.toString());
        map.put("g2",g2);
        List<String> T = new ArrayList<>();
        T.add(datamap.T.toString());
        map.put("T",T);
        return writeCsvFile(dir,map);
    }
    /**
     * 写CSV文件
     */
    public static String writeCsvFile(String dir,Map<String,List<String>> datamap){
        //获取文件夹名
        String fileName =  UUID.randomUUID()+".csv";
        File dirFile = new File(dir);
        //判断文件是否存在
        if(!dirFile.exists()) {
            dirFile.mkdirs();
        }
        fileName=dir+"//"+fileName;
        File file=new File(fileName);
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileWriter fileWriter = null;
        CSVPrinter csvFilePrinter = null;
        //创建 CSVFormat
        Set<String> keySet = datamap.keySet();
        Map<String,List<String>> map=datamap;
        String[] header=new String[keySet.size()];
        String[][] data = new String[keySet.size()][];
        int cnt=0;
        for (String str : keySet) {
            header[cnt]=str;
            List<String> temp = map.get(str);
            data[cnt++]= temp.toArray(new String[temp.size()]);
//            System.out.println(str);
        }
        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);
        try{
            //初始化FileWriter
            fileWriter = new FileWriter(fileName);
            //初始化 CSVPrinter
            csvFilePrinter = new CSVPrinter(fileWriter,csvFileFormat);
            //创建CSV文件头
            csvFilePrinter.printRecord(header);
            //遍历写进CSV
            Iterator<Map.Entry< String,List<String> >> it =datamap.entrySet().iterator();
            int length=data[0].length;
            for (int i=0;i<length;i++){
                List<String> FileDTO = new ArrayList<>();
                for (int j=0;j<header.length;j++){
                    FileDTO.add(data[j][i]);
                }
                csvFilePrinter.printRecord(FileDTO);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                fileWriter.flush();
                fileWriter.close();
                csvFilePrinter.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return fileName;
    }
}