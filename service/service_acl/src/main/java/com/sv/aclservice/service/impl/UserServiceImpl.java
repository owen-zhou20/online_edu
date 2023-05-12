package com.sv.aclservice.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sv.aclservice.entity.User;
import com.sv.aclservice.mapper.UserMapper;
import com.sv.aclservice.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * UserServiceImpl
 * </p>
 *
 * @author Owen
 * @since
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    // Select a user by username
    @Override
    public User selectByUsername(String username) {
        return baseMapper.selectOne(new QueryWrapper<User>().eq("username", username));
    }

    // 1. Pagination condition select user list
    @Override
    public IPage<User> selectPage(Page<User> pageParam, QueryWrapper<User> wrapper) {
        return baseMapper.selectPage(pageParam,wrapper);
    }
}
