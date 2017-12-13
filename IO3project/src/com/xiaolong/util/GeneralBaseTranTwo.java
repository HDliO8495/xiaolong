package com.xiaolong.util;

import com.xiaolong.dao.PublicDao;
import com.xiaolong.domain.User;
import com.xiaolong.service.impl.UserServiceImpl;

import java.util.List;

/**
 * Created by xiaolong on 2017/12/9.
 */
public class GeneralBaseTranTwo extends UserServiceImpl {
    private PublicDao publicDao;

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

    public PublicDao getPublicDao() {
        return publicDao;
    }

    public void setPublicDao(PublicDao publicDao) {
        this.publicDao = publicDao;
    }
}
