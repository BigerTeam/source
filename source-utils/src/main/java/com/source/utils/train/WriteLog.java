package com.source.utils.train;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.TimeZone;

public class WriteLog {
  
	/**
	 * 写logString字符串到./log目录下的文件中
	 * @author zhuyangxu 
	 * @data 2017年8月3日 上午11:39:00
	 * @param fileNameHead
	 * @param logString
	 */
    public static void write(String fileNameHead, String logString) {
        try {
            String logFilePathName = null;
            Calendar cd = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));// 日志文件时间
            int year = cd.get(Calendar.YEAR);
            String month = addZero(cd.get(Calendar.MONTH) + 1);
            String day = addZero(cd.get(Calendar.DAY_OF_MONTH));
            String hour = addZero(cd.get(Calendar.HOUR_OF_DAY));
            String min = addZero(cd.get(Calendar.MINUTE));
            String sec = addZero(cd.get(Calendar.SECOND));
            String millisecond = addZero(cd.get(Calendar.MILLISECOND));
            String osname = System.getProperty("os.name");
            String pathString = "D:/userlog/" + year + month + "/" + day + "/" + hour + "/log";
            if (osname != null && osname.contains("Linux")) {
                pathString = "/userlog/" + year + month + "/" + day + "/" + hour + "/log";
            }
            File fileParentDir = new File(pathString);
            // 判断log目录是否存在
            if (!fileParentDir.exists()) {
               fileParentDir.mkdirs();
            }
            if (fileNameHead == null || fileNameHead.equals("")) {
                logFilePathName = pathString + "/" + year + month + day + ".log";
            }
            else {
                logFilePathName = pathString + "/" + fileNameHead + ".log";
            }

            PrintWriter printWriter = new PrintWriter(new FileOutputStream(logFilePathName, true));// 紧接文件尾写入日志字符串
            String time = "[" + year + "-" + month + "-" + day + " " + hour + ":" + min + ":" + sec + "." + millisecond
                    + "] ";
            printWriter.println(time + logString);
            printWriter.flush();
            printWriter.close();
        }
        catch (FileNotFoundException e) {
                        e.getMessage();
        }
        catch (Exception e) {
                        e.getMessage();
        }

    }

    /**
     * 整数i小于10则前面补0
     * 
     * @param i
     * 
     * @return
     * 
     * @author tower
     * 
     */
    public static String addZero(int i) {
        if (i < 10) {
            String tmpString = "0" + i;
            return tmpString;
        }
        else {
            return String.valueOf(i);
        }
    }
}
