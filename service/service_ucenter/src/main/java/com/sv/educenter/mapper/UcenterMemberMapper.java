package com.sv.educenter.mapper;

import com.sv.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * Ucenter Member Mapper
 * </p>
 *
 * @author Owen
 * @since 2022-09-19
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    // Get count Number of register member for one day for sta
    Integer countRegisterDay(String day);
}
