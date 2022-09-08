package com.lechi.yxx.service.impl;

import com.lechi.yxx.model.User;
import com.lechi.yxx.mapper.UserMapper;
import com.lechi.yxx.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author zf
 * @since 2022-08-05
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
