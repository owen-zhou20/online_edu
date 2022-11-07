package com.sv.educenter.mapper;

import com.sv.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author Owen
 * @since 2022-09-19
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    // Get count No. of register member for one day
    Integer countRegisterDay(String day);
}
