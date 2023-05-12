package com.sv.educenter.controller;


import com.sv.commonutils.JwtUtils;
import com.sv.commonutils.R;
import com.sv.commonutils.ordervo.UcenterMemberOrder;
import com.sv.educenter.entity.UcenterMember;
import com.sv.educenter.entity.vo.LoginVo;
import com.sv.educenter.entity.vo.RegisterVo;
import com.sv.educenter.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
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

    // Login for user
    @PostMapping("login")
    public R loginUser(@RequestBody LoginVo memberVo){
        // return token which use jwt
        String token = memberService.login(memberVo);
        return R.ok().data("token",token);
    }

    // Register user
    @PostMapping("register")
    public R registerUser(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return R.ok();
    }

    // Get user info by token
    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request){
        // Use jwt utils get user id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        // Get user info by user id
        UcenterMember member = memberService.getById(memberId);
        member.setPassword("");
        return R.ok().data("userInfo",member);
    }

    // Get member info by member id
    @PostMapping("getMemberInfoOrder/{id}")
    public UcenterMemberOrder getMemberInfoOrder(@PathVariable String id){
        UcenterMember member = memberService.getById(id);
        UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(member,ucenterMemberOrder);
        return ucenterMemberOrder;
    }

    // Get count No. of register member for one day
    @GetMapping("countRegister/{day}")
    public R countRegister(@PathVariable String day){
        Integer count = memberService.countRegisterDay(day);
        return R.ok().data("countRegister",count);
    }

}

