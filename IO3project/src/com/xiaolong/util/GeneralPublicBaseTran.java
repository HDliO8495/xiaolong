package com.xiaolong.util;

import com.xiaolong.dao.PublicDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * Created by xiaolong on 2017/12/8.
 */
public class GeneralPublicBaseTran {

    private ApplicationContext applicationContext;
    private PublicDao publicDao;

    {
        if(null == applicationContext){
            String path = this.getClass().getClassLoader().getResource("").getPath();
            String filepath = path + "/config/spring/beanContext.xml";
            applicationContext = new FileSystemXmlApplicationContext(filepath);
            publicDao = (PublicDao)applicationContext.getBean("publicDao");
        }
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public PublicDao getPublicDao() {
        return publicDao;
    }

    public void setPublicDao(PublicDao publicDao) {
        this.publicDao = publicDao;
    }

}
