package com.sv.commonutils;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.HashMap;
import java.util.Map;

// Return common result
@Data
public class R {

    @ApiModelProperty(value = "return success/fail")
    private Boolean success;

    @ApiModelProperty(value = "return code")
    private Integer code;

    @ApiModelProperty(value = "return message")
    private String message;

    @ApiModelProperty(value = "return data")
    private Map<String, Object> data = new HashMap<String, Object>();

    //private construction
    private R(){}

    //success static method
    public static R ok(){
        R r = new R();
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS);
        r.setMessage("success");
        return r;
    }

    //false static method
    public static R error(){
        R r = new R();
        r.setSuccess(false);
        r.setCode(ResultCode.ERROR);
        r.setMessage("error");
        return r;
    }

    public R success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public R message(String message){
        this.setMessage(message);
        return this;
    }

    public R code(Integer code){
        this.setCode(code);
        return this;
    }

    public R data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    public R data(Map<String, Object> map){
        this.setData(map);
        return this;
    }

}
