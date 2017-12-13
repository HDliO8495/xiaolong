package com.xiaolong.common;

import com.xiaolong.dao.PublicDao;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by xiaolong on 2017/12/2.
 */
public abstract class BaseService {
    @Autowired
    protected PublicDao publicDao;
}
