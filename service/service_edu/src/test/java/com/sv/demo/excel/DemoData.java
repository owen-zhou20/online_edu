package com.sv.demo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class DemoData {

    @ExcelProperty(value = "Student No.", index = 0)
    private Integer sno;

    @ExcelProperty(value = "Student name", index = 1)
    private String sname;
}
