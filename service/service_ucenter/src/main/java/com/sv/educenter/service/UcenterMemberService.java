package com.sv.educenter.service;

import com.sv.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sv.educenter.entity.vo.LoginVo;
import com.sv.educenter.entity.vo.RegisterVo;

/**
 * <p>
 * Ucenter Member service
 * </p>
 *
 * @author Owen
 * @since 2022-09-19
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    // Login for user
    String login(LoginVo member);

    // Register user
    boolean register(RegisterVo registerVo);

    // Check openid in DB, do not add this userInfo if this openid already exist.
    UcenterMember getOpenIdMember(String openid);

    // Get count Number of register member for one day for sta
    Integer countRegisterDay(String day);
}
