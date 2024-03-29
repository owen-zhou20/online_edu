package com.sv.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sv.commonutils.JwtUtils;
import com.sv.commonutils.MD5;
import com.sv.educenter.entity.UcenterMember;
import com.sv.educenter.entity.vo.LoginVo;
import com.sv.educenter.entity.vo.RegisterVo;
import com.sv.educenter.mapper.UcenterMemberMapper;
import com.sv.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sv.servicebase.exceptionhandler.SvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * User member center serviceImpl
 * </p>
 *
 * @author Owen
 * @since 2022-09-19
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    // Login for member
    @Override
    public String login(LoginVo member) {
        // Get mobile and password for login
        String mobile = member.getMobile();
        String password = member.getPassword();

        // mobile and password is not null
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            throw new SvException(20001,"Fail to login!");
        }
        // Check mobile in the database
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        UcenterMember mobileMember = baseMapper.selectOne(wrapper);
        // Check this mobileMember is not null
        if(null == mobileMember){
            throw new SvException(20001,"Fail to login!");
        }

        // Check password is correct
        // Encrypt password use MD5 before check with password in database
        if(!MD5.encrypt(password).equals(mobileMember.getPassword())){
            throw new SvException(20001,"Fail to login!");
        }

        // Check is_disabled for this member is not true
        if(mobileMember.getIsDisabled()){
            throw new SvException(20001,"This member is forbidden to login!");
        }

        // Success to login
        // Get token string member JwtUtils
        String jwtToken = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());

        return jwtToken;
    }

    // Register member
    @Override
    public boolean register(RegisterVo registerVo) {
        // Get data for register
        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();

        // check all data is not null
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password) || StringUtils.isEmpty(code) || StringUtils.isEmpty(nickname)){
            throw new SvException(20001,"Fail to register!");
        }

        // Check code compare with code in redis
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if(!code.equals(redisCode)){
            throw new SvException(20001,"Fail to register!");
        }

        // Check this mobile which is not in the database
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if(count > 0){
            throw new SvException(20001,"Fail to register!");
        }

        // Add member to database
        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false);
        member.setIsDeleted(false);
        int rs = baseMapper.insert(member);
        System.out.println("rs===>"+rs);
        if(rs == 1){
            return true;
        } else {
            return false;
        }
    }

    // Check openid in DB, do not add this memberInfo if this openid already exist.
    @Override
    public UcenterMember getOpenIdMember(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        UcenterMember member = baseMapper.selectOne(wrapper);
        return member;
    }

    // Get count Number of register member for one day for sta
    @Override
    public Integer countRegisterDay(String day) {
        Integer count = baseMapper.countRegisterDay(day);
        return count;
    }
}
