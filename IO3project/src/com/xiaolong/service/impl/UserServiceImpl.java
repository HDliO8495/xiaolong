package com.xiaolong.service.impl;

import com.xiaolong.common.BaseService;
import com.xiaolong.domain.User;
import com.xiaolong.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xiaolong on 2017/12/2.
 */
@Service("userService")
public class UserServiceImpl extends BaseService implements UserService {
    @Override
    public List<User> selectUsers(User user, Integer pageNum, Integer pageSize) {
        pageNum=1;
        pageSize=10;
        List<User> list=publicDao.selectList("selectUserByID",user,pageNum,pageSize);
        return list;
    }

    @Override
    public List<User> selectUsers2(User user, Integer ifPage, Integer pageNum, Integer pageSize) {
        List<User> list=publicDao.selectList2("selectUserByID",user,ifPage,pageNum,pageSize);
        return list;
    }

    @Override
    public Integer selectUserExists(User user) {
        user=(User) publicDao.selectOne("selectUser",user);
        if(null != user){
            return 1;
        }
        return 0;
    }
}
