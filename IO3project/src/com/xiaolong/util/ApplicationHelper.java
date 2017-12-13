package com.xiaolong.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by xiaolong on 2017/12/8.
 */
public class ApplicationHelper implements ApplicationContextAware {


    private ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }


    public  ApplicationContext getApplicationContext(){
        return this.applicationContext;
    }
}
