package com.xiaolong.service;

import com.xiaolong.domain.User;

import java.util.List;

/**
 * Created by xiaolong on 2017/12/2.
 */
public interface UserService {

    public List<User> selectUsers(User user,Integer pageNum,Integer pageSize);

    public List<User> selectUsers2(User user,Integer ifPage,Integer pageNum,Integer pageSize);

    //是否存在用户
    public Integer selectUserExists(User user);

}
