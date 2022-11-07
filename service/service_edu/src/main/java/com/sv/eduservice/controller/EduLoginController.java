package com.sv.eduservice.controller;

import com.sv.commonutils.R;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eduservice/user")
//@CrossOrigin   //to fix CORS
public class EduLoginController {


    //login
    @PostMapping("login")
    public R login(){

        return R.ok().data("token","admin");
    }

    //info
    @GetMapping("info")
    public R info(){

        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://img0.baidu.com/it/u=3524086258,727431468&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500");
    }


}
