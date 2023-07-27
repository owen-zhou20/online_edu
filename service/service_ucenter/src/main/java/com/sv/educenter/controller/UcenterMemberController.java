package com.sv.educenter.controller;


import com.sv.commonutils.JwtUtils;
import com.sv.commonutils.R;
import com.sv.commonutils.ordervo.UcenterMemberOrder;
import com.sv.educenter.entity.UcenterMember;
import com.sv.educenter.entity.vo.LoginVo;
import com.sv.educenter.entity.vo.RegisterVo;
import com.sv.educenter.service.UcenterMemberService;
import com.sv.servicebase.exceptionhandler.SvException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * User member center controller
 * </p>
 *
 * @author Owen
 * @since 2022-09-19
 */
@RestController
@RequestMapping("/educenter/member")
//@CrossOrigin
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService memberService;

    // Login for member
    @PostMapping("login")
    public R loginUser(@RequestBody LoginVo memberVo){
        // return token which member jwt
        String token = memberService.login(memberVo);
        return R.ok().data("token",token);
    }

    // Register member
    @PostMapping("register")
    public R registerUser(@RequestBody RegisterVo registerVo){
        boolean rs = memberService.register(registerVo);
        if(rs == true){
            return R.ok();
        }else{
            return R.error();
        }
    }

    // Get member info by token
    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request){
        System.out.println("request===>"+request);
        // Use jwt utils get member id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        System.out.println("memberId===>"+memberId);
        // Get user info by member id
        UcenterMember member = memberService.getById(memberId);
        if(!(member==null)){member.setPassword("");}
        System.out.println("member===>"+member);
        return R.ok().data("userInfo",member);
    }

    // Get member info by member id for member
    @PostMapping("getMemberInfoOrder/{id}")
    public UcenterMemberOrder getMemberInfoOrder(@PathVariable String id){
        if(Strings.isEmpty(id)){
            throw new SvException(20001,"Please login!");
        }
        UcenterMember member = memberService.getById(id);
        UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(member,ucenterMemberOrder);
        return ucenterMemberOrder;
    }

    // Get count Number of register member for one day for sta
    @GetMapping("countRegister/{day}")
    public R countRegister(@PathVariable String day){
        Integer count = memberService.countRegisterDay(day);
        return R.ok().data("countRegister",count);
    }

}

