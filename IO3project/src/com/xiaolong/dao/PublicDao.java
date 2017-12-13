package com.xiaolong.dao;

import java.util.List;

/**
 * Created by xiaolong on 2017/12/2.
 */
public interface PublicDao{

    public <T>List<T> selectList(String sql,Object param,int skip,int max);

    public <T>List<T> selectList2(String sql,Object param,Integer ifPage,Integer pageNum,Integer pageSize);

    public <T>Object selectOne(String sql,T param);

}
