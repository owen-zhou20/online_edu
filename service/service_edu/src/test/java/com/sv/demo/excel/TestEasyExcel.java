package com.sv.demo.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {

    public static void main(String[] args) {
/*
        // excel write
        String fileName = "D:\\write.xlsx";
        EasyExcel.write(fileName, DemoData.class).sheet("SutdentList").doWrite(getList());
*/

        // excel read
        String fileName = "D:\\write.xlsx";
        EasyExcel.read(fileName, DemoData.class, new ExcelListener()).sheet().doRead();


    }

    private static List<DemoData> getList() {
        List<DemoData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setSno(i);
            data.setSname("Lucy"+i);
            list.add(data);
        }
        return list;
    }
}
