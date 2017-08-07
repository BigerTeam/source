package com.source.utils.train;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("rawtypes")
public class CheckUtils {
	
	public static boolean isNotNull(String input)
	{
		if( null != input && !input.trim().equals("") && !input.trim().equals("null") && !input.trim().equals("undefined") && !input.trim().equals("-1") )
		{
			return true;
		}
		return false;
	}
	
	public static boolean isNotNull(Long input)
	{
		if( null != input )
		{
			return true;
		}
		return false;
	}
	
	public static boolean isNotNull(Integer input)
	{
		if( null != input )
		{
			return true;
		}
		return false;
	}
	
	public static boolean isNotNull(Date input)
	{
		if( null != input )
		{
			return true;
		}
		return false;
	}
	
	
	public static boolean isNotNull(Object input)
	{
		if( null != input )
		{
			return true;
		}
		return false;
	}
	
	public static boolean isNotNull(StringBuffer input)
	{
		if( null != input && !input.toString().trim().equals("") )
		{
			return true;
		}
		return false;
	}
	
	public static boolean isNotNull(List input)
	{
		if( null != input && input.size() > 0 )
		{
			return true;
		}
		return false;
	}
	
	public static boolean isNotNull(Map input)
	{
		if( null != input && !input.isEmpty() )
		{
			return true;
		}
		return false;
	}
	
	public static boolean isNotNull(Set input)
	{
		if( null != input && !input.isEmpty() )
		{
			return true;
		}
		return false;
	}
	
	public static boolean isNotNull(String[] input)
	{
		if( null != input && input.length > 0 )
		{
			return true;
		}
		return false;
	}
	
	public static boolean isNotNull(Object[] input)
	{
		if( null != input && input.length > 0 )
		{
			return true;
		}
		return false;
	}
	
	public static boolean isNotNull(Class input)
	{
		if( null != input )
		{
			return true;
		}
		return false;
	}
	
	

	
	
	
	public static boolean isNull(String arg)
	{
		boolean isNull = true;
		if(arg != null && !"".equals(arg.trim()))
		{
			isNull = false;
		}
		return isNull;
	}
	
	public static boolean isNull(int arg) throws Exception
	{
		boolean isNull = true;
		if(arg != -1)
		{
			isNull = false;
		}
		return isNull;
	}
	
	public static boolean isNull(long arg) throws Exception
	{
		boolean isNull = true;
		if(arg != -1)
		{
			isNull = false;
		}
		return isNull;
	}
	
	public static boolean isNull(Map arg) throws Exception
	{
		boolean isNull = true;
		if(arg != null && !arg.isEmpty())
		{
			isNull = false;
		}
		return isNull;
	}
	
	public static boolean isNull(Object arg) throws Exception
	{
		boolean isNull = true;
		if(arg != null)
		{
			isNull = false;
		}
		return isNull;
	}

	public static boolean uniqueResult(Object uniqueResult ,int target) throws Exception
	{
		boolean isNull = false;
		if( null == uniqueResult || uniqueResult.toString().trim().equals("") || Integer.parseInt(uniqueResult.toString().trim()) == target )
		{
			isNull = true;
		}
		return isNull;
	}
	
	public static boolean uniqueResult(Object uniqueResult) throws Exception
	{
		boolean isNull = false;
		if( null == uniqueResult || uniqueResult.toString().trim().equals("") )
		{
			isNull = true;
		}
		return isNull;
	}

	/**
	 * 全角转半角
	 * @param input
	 * @return String
	 * @author zhaolu add
	 * @date 2012-04-20
	 */
	public static String QtoB(String input) {
		input = input.trim();
		char c[] = input.toCharArray();
		for(int i = 0; i < c.length; i++){
			if(c[i] == '\u3000'){
				c[i] = ' ';
			}
			else if(c[i] > '\uFF00' && c[i] < '\uFF5F'){
				c[i] = (char) (c[i] - 65248);
			}
		}
		return new String(c);
	}

	/**
	 * 半角转全角
	 * @param input
	 * @return String
	 * @author zhaolu add
	 * @date 2012-04-20
	 */
	public static String BtoQ(String input){
		input = input.trim();
		char c[] = input.toCharArray();
		for( int i=0; i<c.length;i++ ){
			if(c[i] ==' ') {
				c[i] = '\u3000';
			} 
			else if (c[i]<'\177') {
				c[i]= (char) (c[i]+65248);
			}
		}
			return new String(c);
	}
	
	/**
	 * 判断字符串长度是否在指定的数值范围内
	 * @param String str,int len 需判断的字符串和限制的长度
	 * @return boolean 布尔值
	 * @author zhaolu add
	 * @date 2012-04-20
	 */
	public static boolean checkStringLengrth(String str,int len){
		if(str.length()<=len){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 日期格式的字符串转换为日期
	 * @param dateStr 日期格式的字符串
	 * @param formatStr 需转换后的格式
	 * @return Date 日期
	 * @throws ParseException
	 * @author zhaolu add
	 * @date 2012-04-25
	 */
	public static Date formatDate(String dateStr, String formatStr) throws ParseException{
		if(isNotNull(dateStr) && isNotNull(formatStr)){
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat(formatStr);
			date = formatter.parse(dateStr);
			return date;
		}else{
			return null;
		}
		

	}
	
	/**
	 * 日期转换为日期格式的字符串
	 * @param dateStr 日期格式的字符串
	 * @param formatStr 需转换后的格式
	 * @return Date 日期
	 * @throws ParseException
	 * @author zhaolu add
	 * @date 2012-04-25
	 */
	public static String getStringDate(Date date, String formatStr){
		String dateStr = "";
		if(isNotNull(date) && isNotNull(formatStr)){
			SimpleDateFormat formatter = new SimpleDateFormat(formatStr);
			dateStr = formatter.format(date);
		}
		return dateStr;
	}
	
	public static boolean isDate(String date){
		boolean flag = false;
		try{
			if( date !=null && !date.equals("") ){
				SimpleDateFormat sdf   = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
				sdf.parse(date);
				flag = true;
			}
		}catch(Exception e){
			flag = false;
		}
		return flag;
	}
}
