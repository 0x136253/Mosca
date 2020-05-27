package com.mutiny.demo.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.support.ExcelTypeEnum;
public class ExcelUtils {
    public static void writeWithoutHead(Map<String,List<String>> map,String name) throws IOException {
        try (OutputStream out = new FileOutputStream("/home/anon/"+name+".xlsx");) {
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
            Sheet sheet1 = new Sheet(1, 0);
            sheet1.setSheetName("sheet1");
            Set<String> keySet = map.keySet();
            List<List<String>> head = new ArrayList<List<String>>();
            List<List<String>> data = new ArrayList<>();
            for (String record:keySet){
                List<String> headCoulumn = new ArrayList<String>();
                headCoulumn.add(record);
                head.add(headCoulumn);
                data.add(map.get(record));
            }
            Table table = new Table(1);
            table.setHead(head);
            writer.write(data, sheet1, table);
            writer.finish();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        try {
//            writeWithoutHead();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
