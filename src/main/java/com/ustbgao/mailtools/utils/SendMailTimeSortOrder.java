package com.ustbgao.mailtools.utils;

import java.util.Comparator;
import java.util.Date;
import java.util.TreeMap;

/**
 * 根据定时任务中的邮件发送时间进行排序存储
 * author gaoqi
 * created At 2015/10/21.
 */
public class SendMailTimeSortOrder {

    //对定时发送邮件的发送时间进行排序
    public static final TreeMap<Date,Object> taskMap = new TreeMap<Date, Object>(
            new Comparator<Date>() {
                @Override
                public int compare(Date o1, Date o2) {
                    return o1.compareTo(o2);
                }
            }
    );
    public static synchronized void putTaskToMap(Date date, Object object){

        taskMap.put(date, object);
    }
}
