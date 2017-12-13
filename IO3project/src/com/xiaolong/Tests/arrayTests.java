package com.xiaolong.Tests;

import com.xiaolong.util.GeneralUtil;
import org.junit.Test;

import java.util.Iterator;

/**
 * Created by xiaolong on 2017/12/5.
 */
public class arrayTests {

    @Test
    public void intsStackTest(){

        int []A = {28,0,17,66,1,4,2,88,0,0,88,88,99,2,2,1};
        GeneralUtil generalUtil = new GeneralUtil();
        A=generalUtil.intArraySort(A);

        int []B =new int[A.length];
        int []C = new int[A.length];
        int cacheLength = A.length;
        for(int i=0;i<A.length;i++){
            B[--cacheLength]=A[i];
        }
        cacheLength = A.length;
        for(int i=0;i<A.length;i++){
            C[--cacheLength]=B[i];
        }
        StringBuffer stringBuffer = new StringBuffer();
        String str = ",";
        for(int i:A){
            stringBuffer.append(i).append(str);
        }
        stringBuffer.delete(stringBuffer.length()-str.length(),stringBuffer.length());
        System.out.println(stringBuffer);

        stringBuffer = new StringBuffer();
        for(int i:B){
            stringBuffer.append(i).append(str);
        }
        stringBuffer.delete(stringBuffer.length()-str.length(),stringBuffer.length());
        System.out.println(stringBuffer);

        stringBuffer = new StringBuffer();
        for(int i:C){
            stringBuffer.append(i).append(str);
        }
        stringBuffer.delete(stringBuffer.length()-str.length(),stringBuffer.length());
        System.out.println(stringBuffer);
    }

    @Test
    public void linkHandle(){
        int []A = {28,0,17,66,1,4,2,88,0,0,88,88,99,2,2,1};

        Object [] objects = new Object[2];
        objects[0]=A[0];
        System.out.println(objects[0]);
        System.out.println(objects[1]);
        Object [] objCache = objects;
        Object [] objCacheInner;
        for(int i:A){
            while(objCache!=null){

                int y=(int)objCache[0];
                if(i<=y){
                    objects= new Object[]{i, objects};
                }else if(i>y){
                    objCacheInner = (Object [])objects[1];
                    while(null!=objCacheInner){
                        int z = (int)objCacheInner[0];
                        if(i<=z){
                            //objCache
                        }
                    }

                }
                objCache=(Object [])objects[1];
            }

        }

    }

    @Test
    public void linkHandle2(){
        int []A = {8,6,10,0,0,1,2,4,2};

        Object [] objects = new Object[2];
        Object [] objCache;
        Object [] objCacheInner;
        for(int i=0;i<A.length;i++){
            if(i==0){
                objects[0]=A[0];
                continue;
            }
            int y=(int)objects[0];
            if(A[i]<=y){
                objects= new Object[]{A[i], objects};
            }else if(A[i]>y){
                objCache = objects;
                if(null == objCache[1]){
                    objCache[1] = new Object[]{A[i],null};
                    continue;
                }
                objCacheInner = (Object [])objCache[1];
                while(true){
                    int z = (int)objCacheInner[0];
                    if(A[i]<=z){
                        objCache[1] = new Object[]{A[i],objCacheInner};
                        break;
                    }else if(null == objCacheInner[1]){
                        objCacheInner[1] = new Object[]{A[i],null};
                        break;
                    }else{
                        objCache = objCacheInner;
                        if(null == objCacheInner[1]){
                            break;
                        }
                        objCacheInner = (Object [])objCacheInner[1];
                    }
                }
            }
        }
        int t = 0;
        objCache = objects;
        while(true){
            A[t++] = (int)objCache[0];
            if(null == objCache[1]){
                break;
            }
            objCache = (Object [])objCache[1];
        }
        System.out.println("asd");
    }

    @Test
    public void intArrayLinkSortTest(){
        int []ints = new int[]{3,2,6,79,11,21,21,0,0,4};
        GeneralUtil generalUtil = new GeneralUtil();
        ints = generalUtil.intArrayLinkSort(ints);
        StringBuffer stringBuffer = new StringBuffer();
        String str = "-";
        for(int i:ints){
            stringBuffer.append(i).append(str);
        }
        stringBuffer.delete(stringBuffer.length()-str.length(),stringBuffer.length());
        System.out.println(stringBuffer);
    }

}
