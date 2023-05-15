package com.sv.servicebase.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  SV Exception
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SvException extends RuntimeException{

    //exception code
    private Integer code;

    //exception message
    private String msg;

    @Override
    public String toString() {
        return "SvException{" +
                "code=" + code + ", " +
        "message=" + this.getMsg()
                +"}";
    }
}
