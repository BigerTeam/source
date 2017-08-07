package com.source.train.conf;

import java.util.Properties;

import com.source.utils.train.HttpsRequestNg;
/**
 * 12306 访问路径配置中心
 * @author lijintao
 *
 */
public class TrainConf {
	public static Properties prop;

	public static Properties getProperties() {
		if (prop == null) {
			prop = new Properties();
			try {
				String res =new String(HttpsRequestNg.getHttpClient().doGet("https://kyfw.12306.cn/otn/leftTicket/init"), "UTF-8");
				prop.setProperty("query_url", getHtmlValue(res, "CLeftTicketUrl", "'"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return prop;
	}
	 private static String getHtmlValue(String html, String key, String start) {
	        String value = "";
	        if (html.contains(key)) {
	            try {
	                String temp = html.split(key)[1];
	                temp = temp.substring(temp.indexOf(start) + 1);
	                temp = temp.substring(0, temp.indexOf(start));
	                value = temp.trim();
	            }
	            catch (Exception e) {
	            }
	        }
	        return value;
	    }

	
	/**
	 * 主路径
	 */
	public final static String base_url = "https://kyfw.12306.cn/otn/";
	// 登录
	public final static String loginAysnSuggestUrl = base_url + "login/loginAysnSuggest?loginUserDTO.user_name=";
	// 下单获取签名
	public final static String initDc = base_url + "confirmPassenger/initDc";
	// 查询火车票列表
	public final static String queryUrl = base_url + getProperties().getProperty("query_url", "leftTicket/query") + "?leftTicketDTO.train_date=";
	// 获取验证码
	public final static String getPassCodeNewUrl = base_url + "passcodeNew/getPassCodeNew?module=";
	// 校验验证码
	public final static String checkRandCodeAnsynUrl = base_url + "passcodeNew/checkRandCodeAnsyn?randCode=";
	// 验证是否登录状态
	public final static String checkUser = base_url + "login/checkUser";
	// 查询常用联系人
	public final static String passengersUrl = base_url + "confirmPassenger/getPassengerDTOs";
	// 查询未完成订单
	public final static String queryMyOrderNoComplete = base_url + "queryOrder/queryMyOrderNoComplete?_json_att=";
	// 查询未完成订单
	public static String queryMyOrder = base_url + "queryOrder/queryMyOrder?queryType=1&queryStartDate=%queryStartDate%&queryEndDate=%queryEndDate%&come_from_flag=my_order&pageSize=8&pageIndex=0&query_where=%query_where%&sequeue_train_name=";
	// 取消订单
	public final static String cancelNoCompleteMyOrderUrl = base_url + "queryOrder/cancelNoCompleteMyOrder?sequence_no=";
	// 查询订单余票
	public final static String queryOrderWaitTimeUrl = base_url + "confirmPassenger/queryOrderWaitTime?random=";
	// 提交订单 结束
	public final static String confirmSingleForQueueUrl = base_url + "confirmPassenger/confirmSingleForQueue?passengerTicketStr=";
	// 获取余票
	public final static String getQueueCountUrl = base_url + "confirmPassenger/getQueueCount?module=cmgp&purpose_codes=00&_json_att=&train_date=";
	// 检查订单有效
	public final static String checkOrderInfoUrl = base_url + "confirmPassenger/checkOrderInfo?cancel_flag=2&bed_level_order_num=000000000000000000000000000000&tour_flag=dc&_json_att=&REPEAT_SUBMIT_TOKEN=";
	// 预提交订单
	public final static String submitOrderRequestUrl = base_url + "leftTicket/submitOrderRequest?secretStr=";
	// 退出登录
	public final static String loginOutUrl = base_url + "login/loginOut";
	// 查询经停站
	public final static String queryByTrainNoUrl = base_url + "czxx/queryByTrainNo?train_no=";

}
