package com.xiaolong.util;

import java.util.*;

/**
 * Created by xiaolong on 2017/12/3.
 */
public class GeneralUtil {
    //移除list对应索引的元素
    public static <T> Map<String,Object> listRemoveIndex(List<T> list, List<Integer> indexsList){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("result", list);
        if(list.size()==0 || indexsList.size()==0){
            map.put("code","400");
            map.put("msg", "size is zero");
            return map;
        }else if(indexsList.size()>list.size()){
            map.put("code","300");
            map.put("msg", "indexsList too mach element");
            return map;
        }else{
            for(int i:indexsList){
                if(i>list.size()-1){
                    map.put("code","500");
                    map.put("msg", "indexsList element out of list size");
                    return map;
                }
            }
        }
        int current;//当前indexsList元素值
        int totals=0;
        List<Integer> changeList = new ArrayList<Integer>();//保存变化后indexsList
        for(int i=0;i<indexsList.size();i++){
            current = indexsList.get(i);
            if(0!=i){
                for(int y=0;y<i;y++){//循环当前indexsList元素前面元素个数次
                    if(indexsList.get(y)<current){
                        totals++;//前y位元素值小于当前indexsList元素值，跌一层
                    }
                }
                changeList.add(current-totals);
                totals=0;
            }else{
                changeList.add(current);
            }
        }
        for(Integer integer2:changeList){
            int integer = integer2;
            list.remove(integer);
        }
        map.put("code","200");
        map.put("msg", "success");
        map.put("result", list);
        return map;
    }

    /**
     * 获取时间差,返回hh:MM:ss字符串
     * @param beforeDate
     * @param afterDate
     * @return
     */
    public Map<String,Object> getTimeInterval2(Date beforeDate, Date afterDate){
        long timeInterval = afterDate.getTime()-beforeDate.getTime();
        //小时间隔
        long hour = timeInterval/(1000*60*60);
        //分钟间隔
        long minute = (timeInterval-(hour*1000*60*60))/(1000*60);
        //秒间隔
        long second = (timeInterval-(hour*1000*60*60)-(minute*1000*60))/(1000);
        //时间间隔，hh:MM:ss字符串
        String hhMMss=Long.toString(hour)+":"+Long.toString(minute)+":"+Long.toString(second);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("hour",hour);
        map.put("minute",minute);
        map.put("second",second);
        map.put("hhMMss",hhMMss);
        return map;
    }

    /**
     * 移位式整形数组排序
     * */
    public int[] intArraySort(int[] param){
        int[] ints = param;
        int[] intsCache = new int[ints.length];
        int intsIndexZero = 0;
        int times = 0;
        for(int i:ints){
            if(intsIndexZero==0){
                intsCache[0]=i;
                intsIndexZero++;
            }else{
                for(int y=0;y<intsCache.length;y++){
                    if(y==intsCache.length){
                        intsCache[y]=i;
                        break;
                    }else if(i>=intsCache[y] && y==times){
                        intsCache[y+1]=i;
                        break;
                    }else if(i<intsCache[y]){
                        int cacheOne = i;
                        int cacheTwo = 0;
                        for(int z=y;z<intsCache.length;z++){
                            if(intsCache[z]==0){
                                intsCache[z]=cacheOne;
                                break;
                            }
                            cacheTwo=intsCache[z];
                            intsCache[z]=cacheOne;
                            cacheOne=cacheTwo;
                        }
                        break;
                    }
                }
                times++;
            }
        }
        return intsCache;
    }

    public int[] intArrayLinkSort(int[] param){
        int []A = param;
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
        return A;
    }

}
