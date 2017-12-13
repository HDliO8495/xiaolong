package com.xiaolong.dao.impl;

import com.github.pagehelper.PageHelper;
import com.xiaolong.dao.PublicDao;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by xiaolong on 2017/12/2.
 */
public class PublicDaoImpl extends SqlSessionDaoSupport implements PublicDao{

    @Override
    public <T> List<T> selectList(String sql, Object param, int skip, int max) {
        if(null==sql || "".equals(sql)){
            return Collections.emptyList();
        }
        skip = skip <= 0 ? 1:skip;
        max = max <= 0 ? 10:max;
        List<T> list=this.getSqlSession().selectList(sql,param,new RowBounds(skip,max));
        return list;
    }

    @Override
    public <T> List<T> selectList2(String sql,Object param,Integer ifPage,Integer pageNum,Integer pageSize) {
        if(null==sql || "".equals(sql)){
            return Collections.emptyList();
        }
        if(null!=ifPage && 1==ifPage){
            pageNum = pageNum == null ? 1:pageNum;
            pageSize = pageSize == null? 10:pageSize;
            PageHelper.startPage(pageNum,pageSize);
        }
        List<T> list=this.getSqlSession().selectList(sql,param);
        return list;
    }

    @Override
    public <T> Object selectOne(String sql, T param) {
        if(null==sql || "".equals(sql)){
            return Collections.emptyList();
        }
        return this.getSqlSession().selectOne(sql,param);
    }
}
