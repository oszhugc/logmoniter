package com.oszhugc.logmoniter.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author oszhugc
 * @Date 2019\4\26 0026 0:37
 **/
public class FileUtil {

    //最大重试的次数
    private static int MAX_RETRY = 5;

    //获取几天前的时间,默认获取当天
    private static int BEFORE_DAYS = 0;

    /**
     * 获取最新日志的路径
     *
     * @return
     */
    public static String getLogFilePath(){

        if(MAX_RETRY > 0){
            String path = "/home/work/apache-tomcat-7.0.77/logs/catalina.out." + getDateStr();
            System.out.println("logfile: " + path);
            if(new File(path).exists())
                return path;
            //当日没有日志就往前面找
            MAX_RETRY--;
            BEFORE_DAYS--;
            getLogFilePath();
        }else {
            return null;
        }

        return null;
    }

    private static String getDateStr() {
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,BEFORE_DAYS);
        date = calendar.getTime();
        return  new SimpleDateFormat("yyyyMMdd").format(date);
    }

    public static void init(){
        MAX_RETRY = 5;
        BEFORE_DAYS = 0;
    }


}
