package com.sv.servicebase.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

// insert data for MP
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    // create data
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("gmtCreate",new Date(), metaObject);
        this.setFieldValByName("gmtModified",new Date(), metaObject);
    }

    // update data
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("gmtModified",new Date(), metaObject);
    }
}
