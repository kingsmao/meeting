package com.chainup.service.impl;

import com.chainup.dao.UserMapper;
import com.chainup.entity.User;
import com.chainup.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * @author lili
 * @date 2020/10/18 22:11
 * @see
 * @since
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void createUser(String nickName, String openId, String wxImg) {
        User user = new User();
        user.setOpenId(openId);
        user.setCtime(new Date());
        user.setMtime(new Date());
        user.setUserName("");
        user.setDepartmentId(1);
        user.setNickName(nickName);
        userMapper.insertSelective(user);
    }

    @Override
    public boolean isUserExist(String openId) {
        return Objects.nonNull(userMapper.findUserByOpenId(openId));
    }
}
