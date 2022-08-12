package com.sv.demo.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

public class ExcelListener extends AnalysisEventListener<DemoData> {
    // read column by column from excel file
    @Override
    public void invoke(DemoData data, AnalysisContext analysisContext) {

        System.out.println("===>"+ data);
    }

    // read table head
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("table head ===>"+headMap);
    }

    // do this after read
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
