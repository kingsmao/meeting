package com.chainup.service;

/**
 * @author lili
 * @date 2020/10/18 22:08
 * @see
 * @since
 */
public interface UserService {

    /**
     * 创建新用户
     *
     * @param nickName 用户微信昵称
     * @param openId   用户在小程序唯一标识
     * @param wxImg    微信头像
     */
     void createUser(String nickName, String openId, String wxImg);

    /**
     * 用户是否存在
     * @param openId 用户在小程序唯一标识
     * @return
     */
     boolean isUserExist(String openId);
}
