package com.source.train.service;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.source.base.cache.EhcacheFactory;
import com.source.train.conf.TrainConf;
import com.source.train.entity.NewTrain;
import com.source.train.entity.Passenger;
import com.source.train.entity.TicketData;
import com.source.utils.train.DateUtils;
import com.source.utils.train.HttpsRequestNg;
import com.source.utils.train.ObjectToFile;

/**
 * 12306参数拼装和解析返回值
 */
public class TrainService {
	private static Logger logger = LoggerFactory.getLogger(TrainService.class);

	/**
	 * 查询列车经停站
	 * @param trainNo 车次
	 * @param fromStation 出发车站
	 * @param toStation 到达车站
	 * @param startDate 出发日期
	 * @return
	 * @throws IOException
	 */
	public static JSONObject queryByTrainNo(String trainNo, String fromStation, String toStation, String startDate)
			throws IOException {
		String urlStr = TrainConf.queryByTrainNoUrl + trainNo + "&from_station_telecode=" + fromStation
				+ "&to_station_telecode=" + toStation + "&depart_date=" + startDate;
		String result = new String(HttpsRequestNg.getHttpClient().doGet(urlStr), "UTF-8");
		return JSONObject.parseObject(result);
	}

	/**
	 * 登录
	 * @param user_name 用户名
	 * @param password 密码
	 * @param randCode 验证码
	 * @return
	 * @throws IOException
	 */
	public static Boolean loginAysnSuggest(String user_name, String password, String randCode) throws IOException {
		String urlStr = TrainConf.loginAysnSuggestUrl + user_name + "&userDTO.password=" + password + "&randCode="
				+ URLEncoder.encode(randCode,"UTF-8");
		String result = new String(HttpsRequestNg.getHttpClient().doPost(urlStr), "UTF-8");
		JSONObject parseObject = JSONObject.parseObject(result);
		if(!parseObject.getBoolean("status")){
			return parseObject.getBoolean("status");
		}
		JSONObject data = parseObject.getJSONObject("data");
		String loginCheck = data.getString("loginCheck");
		String ss = "Y".equals(loginCheck) ? "成功" : "失败";
		logger.info("登录结果" + ss);
		return "Y".equals(loginCheck);
	}

	// 退出登录
	public static String loginOut() throws IOException {
		String result = new String(HttpsRequestNg.getHttpClient().doPost(TrainConf.loginOutUrl), "UTF-8");
		logger.info("loginOut----" + result);
		return "1";
	}

	/**
	 * 预提交订单
	 * @param secretStr 
	 * @param train_date 出发日期
	 * @param query_from_station_name 出发车站
	 * @param query_to_station_name 到达车站
	 * @return
	 * @throws IOException
	 */
	public static JSONObject submitOrderRequest(String secretStr, String train_date, String query_from_station_name,
			String query_to_station_name) throws IOException {
		String urlStr = TrainConf.submitOrderRequestUrl + secretStr + "&train_date=" + train_date + "&back_train_date="
				+ DateUtils.format(new Date()) + "&tour_flag=dc&purpose_codes=ADULT&query_from_station_name="
				+ query_from_station_name + "&query_to_station_name=" + query_to_station_name + "&undefined=";
		String result = new String(HttpsRequestNg.getHttpClient().doPost(urlStr), "UTF-8");
		JSONObject parseObject = JSONObject.parseObject(result);
		if (parseObject.getBooleanValue("status")) {
			
			
			if(result.contains("未登录") ||result.contains("网络繁忙")
                        ||result.contains("该用户已在其他地点登录，本次登录已失效")){
				logger.info("提交订单失败");
				parseObject.put("status", false);
				return parseObject;
			}
			String ss = parseObject.getString("data").equals("N") ? "成功" : "失败";
			logger.info("提交订单" + ss);
			if (!parseObject.getBoolean("status") || !"[]".equals(parseObject.getString("messages"))) {
				return parseObject;
			}
		} else {
			logger.info("提交订单" + parseObject.getString("messages"));
			return parseObject;
		}
		return globalRepeatSubmitToken();
	}

	/**
	 * 检查订单有效
	 * @param randCode 验证码
	 * @param oldPassengerStr 乘车人信息(张三(乘客姓名),1(证件类型),320xxxxxx(身份证号),1_)
	 * @param passengerTicketStr乘车人信息(1(座位类型),0,1(车票类型),张三(乘客姓名),1(证件类型),320xxxxxx(身份证号),151xxxx(手机号),N)
	 * @param repeat_submit_token 提交订单token
	 * @param train_date 出发日期
	 * @param train_no 车次号code（240000K18311）
	 * @param stationTrainCode 车次号
	 * @param seatType 座位类型
	 * @param fromStationTelecode 出发站code
	 * @param toStationTelecode 到达站code
	 * @param leftTicket 签名
	 * @param key_check_isChange 签名
	 * @param train_location 暂不知（查询列车中获得）
	 * @return
	 * @throws IOException
	 */
	public static String checkOrderInfo(String randCode, String oldPassengerStr, String passengerTicketStr,
			String repeat_submit_token, String train_date, String train_no, String stationTrainCode, String seatType,
			String fromStationTelecode, String toStationTelecode, String leftTicket, String key_check_isChange,
			String train_location) throws IOException {
		JSONObject res = new JSONObject();
		JSONObject data = new JSONObject();
	
		
		res.put("data", data);
		if(oldPassengerStr==null||oldPassengerStr.equals("")
				||passengerTicketStr==null||passengerTicketStr.equals("")
				){
			data.put("submitStatus", false);
			data.put("errMsg", "请选择购买乘客以及坐席信息");
			return res.toString();
		}
		seatType="";
		String[] strings = passengerTicketStr.split("\\_");
		for (int i = 0; i < strings.length; i++) {
			seatType=strings[i].split(",")[0];
		}
		
		String urlStr = TrainConf.checkOrderInfoUrl + repeat_submit_token + "&randCode=" + randCode
				+ "&passengerTicketStr=" + passengerTicketStr + "&oldPassengerStr=" + oldPassengerStr;
		String result = new String(HttpsRequestNg.getHttpClient().doPost(urlStr), "UTF-8");
		System.out.println("checkOrderInfo：" + result);
		
		
		JSONObject json = JSONObject.parseObject(result);
		if(result.contains("未登录") ||result.contains("网络繁忙")
				||result.contains("该用户已在其他地点登录，本次登录已失效")){
			logger.info("提交订单失败");
			data.put("submitStatus", false);
			data.put("errMsg", json.getString("messages"));
			return res.toString();
		}
		JSONObject jsonObject = json.getJSONObject("data");
		logger.info("订单检查结果" + jsonObject.getBoolean("submitStatus"));
		if (!json.getBoolean("status") || !jsonObject.getBoolean("submitStatus")) {
			data.put("submitStatus", false);
			data.put("errMsg", jsonObject.getString("errMsg"));
			return res.toString();
		}
		result = getQueueCount(train_date, train_no, stationTrainCode, seatType, fromStationTelecode, toStationTelecode,
				leftTicket, repeat_submit_token, passengerTicketStr, oldPassengerStr, randCode, key_check_isChange,
				train_location);
		return  result;
	}

	/**
	 * 获取余票
	 * @param randCode 验证码
	 * @param oldPassengerStr 乘车人信息(张三(乘客姓名),1(证件类型),320xxxxxx(身份证号),1_)
	 * @param passengerTicketStr乘车人信息(1(座位类型),0,1(车票类型),张三(乘客姓名),1(证件类型),320xxxxxx(身份证号),151xxxx(手机号),N)
	 * @param repeat_submit_token 提交订单token
	 * @param train_date 出发日期
	 * @param train_no 车次号code（240000K18311）
	 * @param stationTrainCode 车次号
	 * @param seatType 座位类型
	 * @param fromStationTelecode 出发站code
	 * @param toStationTelecode 到达站code
	 * @param leftTicket 签名
	 * @param key_check_isChange 签名
	 * @param train_location 暂不知（查询列车中获得）
	 * @return
	 * @throws IOException
	 */
	public static String getQueueCount(String train_date, String train_no, String stationTrainCode, String seatType,
			String fromStationTelecode, String toStationTelecode, String leftTicket, String repeat_submit_token,
			String passengerTicketStr, String oldPassengerStr, String randCode, String key_check_isChange,
			String train_location) throws IOException {
		String urlStr = TrainConf.getQueueCountUrl + URLEncoder.encode(train_date, "UTF-8")
				+ "&train_no=" + train_no + "&stationTrainCode=" + stationTrainCode + "&seatType=" + seatType
				+ "&fromStationTelecode=" + fromStationTelecode + "&toStationTelecode=" + toStationTelecode
				+ "&leftTicket=" + leftTicket + "&REPEAT_SUBMIT_TOKEN=" + repeat_submit_token;
		String result = new String(HttpsRequestNg.getHttpClient().doPost(urlStr), "UTF-8");

		JSONObject json = JSONObject.parseObject(result).getJSONObject("data");
		String queue_note = "尊敬的旅客，本次列车您选择的席别尚有余票" + json.getString("ticket") + "，";
		if (json.getBoolean("op_2")) {
			queue_note += "目前排队人数已经超过余票张数，请您选择其他席别或车次，特此提醒。";
		} else if (json.getIntValue("countT") > 0) {
			queue_note += "目前排队人数" + json.getIntValue("countT") + "人，特此提醒。";
		}
		logger.info(queue_note);
		result = confirmSingleForQueue(passengerTicketStr, oldPassengerStr, randCode, key_check_isChange, leftTicket,
				repeat_submit_token, train_location);
		return result;
	}

	/**
	 * 提交订单 结束
	 * @param passengerTicketStr乘车人信息(1(座位类型),0,1(车票类型),张三(乘客姓名),1(证件类型),320xxxxxx(身份证号),151xxxx(手机号),N)
	 * @param oldPassengerStr乘车人信息(张三(乘客姓名),1(证件类型),320xxxxxx(身份证号),1_)
	 * @param randCode 验证码
	 * @param key_check_isChange 签名
	 * @param leftTicketStr 签名
	 * @param repeat_submit_token 提交订单token
	 * @param train_location 暂不知（查询列车中获得）
	 * @return
	 * @throws IOException
	 */
	public static String confirmSingleForQueue(String passengerTicketStr, String oldPassengerStr, String randCode,
			String key_check_isChange, String leftTicketStr, String repeat_submit_token, String train_location)
			throws IOException {
		String urlStr = TrainConf.confirmSingleForQueueUrl + passengerTicketStr + "&oldPassengerStr=" + oldPassengerStr
				+ "&randCode=" + randCode + "&purpose_codes=00&key_check_isChange=" + key_check_isChange
				+ "&leftTicketStr=" + leftTicketStr + "&train_location=" + train_location
				+ "&choose_seats=&seatDetailType=000&roomType=00&dwAll=N&_json_att=&REPEAT_SUBMIT_TOKEN="
				+ repeat_submit_token;
		String result = new String(HttpsRequestNg.getHttpClient().doPost(urlStr), "UTF-8");
		System.out.println("confirmSingleForQueue " + result);
		JSONObject json = JSONObject.parseObject(result);
		logger.info(" 提交订单结果" + json.getString("data"));
		
		JSONObject res = new JSONObject();
		JSONObject data = new JSONObject();
		res.put("data", data);
		if (!json.getBoolean("status")) {
			data.put("submitStatus", false);
			data.put("errMsg", json.getString("data"));
		}else{
			data.put("submitStatus", true);
			data.put("errMsg", json.getString("data"));
		}
		return res.toString();
	}
	/**
	 * 获取排队
	 * @param repeat_submit_token 提交订单token
	 * @return
	 * @throws IOException
	 */
	public static String queryOrderWaitTime(String repeat_submit_token) throws IOException {
		String urlStr = TrainConf.queryOrderWaitTimeUrl + System.currentTimeMillis()
				+ "&tourFlag=dc&_json_att=&REPEAT_SUBMIT_TOKEN=" + repeat_submit_token;
		String result = new String(HttpsRequestNg.getHttpClient().doPost(urlStr), "UTF-8");
		System.out.println("提交订单排队" + result);
		logger.info("订单排队成功");
		return result;
	}

	/**
	 * 取消订单
	 * @param orderId 订单id
	 * @return
	 */
	public static Boolean cancelNoCompleteMyOrder(String orderId){
		try {
			String urlStr = TrainConf.cancelNoCompleteMyOrderUrl + orderId+"&cancel_flag=cancel_order&_json_att=";
			String result = new String(HttpsRequestNg.getHttpClient().doPost(urlStr), "UTF-8");
			JSONObject json=JSONObject.parseObject(result);
			String string = json.getJSONObject("data").getString("existError");
			System.out.println("取消订单" + urlStr);
			System.out.println("取消订单" + result);
			return string.equals("N");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 *  查询订单
	 * @param query_where 查询类型 （G 未出行订单 ， H 历史订单）
	 * @return
	 */
	public static JSONArray queryMyOrder(String query_where) {
		try {
			String urlStr = TrainConf.queryMyOrder;
			Date now = new Date();
			urlStr = urlStr.replace("%queryStartDate%", DateUtils.format(DateUtils.addDateTime(now, -365)))
					.replace("%queryEndDate%", DateUtils.format(DateUtils.addDateTime(now, -1))).replace("%query_where%", query_where);
			String result = new String(HttpsRequestNg.getHttpClient().doPost(urlStr),"UTF-8");
			System.out.println(urlStr);
			System.out.println(result);
			JSONObject parseObject = JSONObject.parseObject(result);
			if(parseObject.containsKey("data"))
				return parseObject.getJSONObject("data").getJSONArray("OrderDTODataList");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new JSONArray();
	}

	/**
	 * 查询未完成订单
	 * @return
	 */
	public static JSONArray queryMyOrderNoComplete() {
		try {
			String urlStr = TrainConf.queryMyOrderNoComplete;
			String result = new String(HttpsRequestNg.getHttpClient().doPost(urlStr),"UTF-8");
			System.out.println("查询未完成订单" + result);
			JSONObject parseObject = JSONObject.parseObject(result);
			if(parseObject.containsKey("data")){
				return parseObject.getJSONObject("data").getJSONArray("orderDBList");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new JSONArray();
	}

	/**
	 *  获取乘客信息
	 * @return
	 */
	public static List<Passenger> passengers() {
		try {
			String urlStr = TrainConf.passengersUrl;
			String result = new String(HttpsRequestNg.getHttpClient().doPost(urlStr), "UTF-8");
			JSONObject parseObject = JSONObject.parseObject(result);
			boolean isExist = parseObject.getJSONObject("data").getBoolean("isExist");
			if (isExist) {
				logger.info("获取乘客信息成功");
				List<Passenger> list = JSONArray
						.parseArray(parseObject.getJSONObject("data").getString("normal_passengers"), Passenger.class);
				return list;
			}
			logger.info("获取乘客信息失败");
		} catch (Exception e) {
			logger.info("获取乘客信息失败");
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	/**
	 * 检查用户登录是否有效
	 * @return
	 */
	public static Boolean checkUserSwing() {
		try {
			Object readObject = ObjectToFile.readObject();
			if (readObject != null) {
				TicketData ticketData = (TicketData) readObject;
				HttpsRequestNg.getHttpClient().cookies = ticketData.getMap();
				Boolean boolean1 = checkUser();
				if (boolean1) {
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 检查用户登录是否有效
	 * 
	 * @return
	 */
	public static Boolean checkUser() {
		new Thread() {
			public void run() {
				TrainConf.getProperties();
			}
		}.start();
		try {
			String result = new String(HttpsRequestNg.getHttpClient().doPost(TrainConf.checkUser));
			boolean boolean1 = JSONObject.parseObject(result).getJSONObject("data").getBoolean("flag");
			System.out.println(DateUtils.longDate(new Date()) + "checkUser" + boolean1);
			if (boolean1) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 校验验证码
	 * @param randCode 验证码
	 * @param repeat_submit_token 提交订单token（提交订单验证时必传）
	 * @return
	 * @throws IOException
	 */
	public static JSONObject checkRandCodeAnsyn(String randCode, String repeat_submit_token) throws IOException {
		String urlStr = TrainConf.checkRandCodeAnsynUrl + randCode;
		if (repeat_submit_token != null && !"".equals(repeat_submit_token)) {
			urlStr += "&_json_att=&REPEAT_SUBMIT_TOKEN=" + repeat_submit_token + "&rand=randp";
		} else {
			urlStr += "&rand=sjrand";
		}
		String result = new String(HttpsRequestNg.getHttpClient().doGet(urlStr), "UTF-8");
		JSONObject jsonObject = JSONObject.parseObject(result);
		return jsonObject;
	}

	/**
	 * 获取验证码
	 * @param module 获取验证码类型(login 登录验证码，其他为提交订单验证码)
	 * @return
	 * @throws IOException
	 */
	public static byte[] getPassCodeNew(String module) throws IOException {
		String urlStr = TrainConf.getPassCodeNewUrl + module;
		urlStr += "login".equals(module) ? "&rand=sjrand&" : "&rand=randp&";
		urlStr += Math.random();
		byte[] doGet = HttpsRequestNg.getHttpClient().doGet(urlStr);
		return doGet;
	}

	/**
	 * 查询余票
	 * @param fromStation 出发站
	 * @param toStation 到达站
	 * @param startDate 出发日志
	 * @return
	 */
	public static List<NewTrain> queryTrain(String fromStation, String toStation, String startDate) {
		try {
			String urlStr = TrainConf.queryUrl + startDate + "&leftTicketDTO.from_station=" + fromStation
					+ "&leftTicketDTO.to_station=" + toStation + "&purpose_codes=ADULT";
			EhcacheFactory ehcacheFactory = new EhcacheFactory();
			String key = fromStation+toStation+startDate;
			JSONObject echcacheObject = ehcacheFactory.get("queryTrain", key);
			boolean isUserCache = false ;
			String result = "" ;
			if(echcacheObject!=null&&!echcacheObject.isEmpty()
					&&echcacheObject.containsKey("createtime")
					&&echcacheObject.containsKey("trainlist")){
				long createtime = echcacheObject.getLongValue("createtime");
				if(System.currentTimeMillis()-createtime<1000*10){
				  result  = echcacheObject.getString("trainlist");
					isUserCache = true;
				}
			}
			if(!isUserCache){
				long t1 = System.currentTimeMillis();
				result = new String(HttpsRequestNg.getHttpClient().doGet(urlStr), "UTF-8");
				System.out.println(System.currentTimeMillis()-t1);
			}
			System.out.println(result);
			JSONObject parseObject = JSONObject.parseObject(result);
			if (!result.equals("-1")) {
				if(parseObject.getBooleanValue("status")){
					JSONObject resultData = parseObject.getJSONObject("data");
					JSONArray result1 = resultData.getJSONArray("result");
					JSONObject sationMap=resultData.containsKey("map")?resultData.getJSONObject("map"):new JSONObject();
					//如果没走缓存 把结果放在缓存中
					if(!isUserCache){
						JSONObject echcacheTrain = new JSONObject();
						echcacheTrain.put("createtime", System.currentTimeMillis());
						echcacheTrain.put("trainlist", result);
						ehcacheFactory.put("queryTrain", key, echcacheTrain);
					}
					logger.info("车次查询成功共" + result1.size() + "车次");
					List<NewTrain> list=new ArrayList<>();
					for (int i=0;i<result1.size();i++) {
						String data = result1.getString(i);
						String[] datas = data.split("\\|");
						if(datas.length<32){
							continue;
						}
						NewTrain train=new NewTrain();
						train.setStationTrainCode(datas[3]);
						train.setTrainNo(datas[3]);
						train.setStartFlag(datas[6].equals(datas[4]));
						train.setEndFlag(datas[7].equals(datas[5]));
						train.setFromStationName(sationMap.getString(datas[6]));
						train.setFromStationTelecode(datas[6]);
						train.setToStationName(sationMap.getString(datas[7]));
						train.setToStationTelecode(datas[7]);
						train.setStartTime(datas[8]);
						train.setArriveTime(datas[9]);
						train.setTrainNo(datas[3]);
						train.setLishi(datas[10]);
						if(!"列车停运".equals(datas[1])) {
							train.setSwzNum(datas[32].equals("") ? "--" : datas[32]);
							train.setTzNum(datas[25].equals("") ? "--" : datas[25]);
							train.setZyNum(datas[31].equals("") ? "--" : datas[31]);
							train.setZeNum(datas[30].equals("") ? "--" : datas[30]);
							train.setGrNum(datas[21].equals("") ? "--" : datas[21]);
							train.setRwNum(datas[23].equals("") ? "--" : datas[23]);
							train.setYwNum(datas[28].equals("") ? "--" : datas[28]);
							train.setRzNum(datas[24].equals("") ? "--" : datas[24]);
							train.setYzNum(datas[29].equals("") ? "--" : datas[29]);
							train.setWzNum(datas[26].equals("") ? "--" : datas[26]);
							train.setQtNum(datas[22].equals("") ? "--" : datas[22]);
						}
						train.setSecretStr(datas[0]);
						train.setButtonTextInfo(datas[1]);
						train.setYpInfo(datas[12]);
						train.setLocation_code(datas[15]);
						list.add(train);
					}
					return list;
				}
				if(parseObject.containsKey("c_url")){
					TrainConf.getProperties().setProperty("query_url",parseObject.getString("c_url"));
					queryTrain(fromStation, toStation, startDate);
				}
			}
		} catch (Exception e) {
			logger.info("车次查询失败");
			e.printStackTrace();
		}
		return new ArrayList<NewTrain>();
	}

	/**
	 * 获取加密串(包含token，leftTicketStr和key_check_isChange )
	 * @return
	 */
	private static JSONObject globalRepeatSubmitToken() {
		String string = " ";
		JSONObject json = new JSONObject();
		try {
			String urlStr = TrainConf.initDc;
			String source = new String(HttpsRequestNg.getHttpClient().doPost(urlStr), "UTF-8");
			List<String> result = new ArrayList<String>();
			String reg = "globalRepeatSubmitToken = '[0-9a-zA-Z]{0,50}";
			Matcher m = Pattern.compile(reg).matcher(source);
			while (m.find()) {
				String r = m.group().trim();
				result.add(r);
			}
			string = result.size() > 0 ? result.get(0).split("=")[1].trim() : "";
			result = new ArrayList<String>();
			json.put("globalRepeatSubmitToken", string.substring(1));
			reg = "key_check_isChange':'[0-9a-zA-Z]{0,56}";
			m = Pattern.compile(reg).matcher(source);
			while (m.find()) {
				String r = m.group().trim();
				result.add(r);
			}
			string = result.size() > 0 ? result.get(0).split(":")[1].trim() : "";
			result = new ArrayList<String>();
			json.put("key_check_isChange", string.substring(1));
			reg = "leftTicketStr':'[0-9a-zA-Z%]{0,100}'";
			m = Pattern.compile(reg).matcher(source);
			while (m.find()) {
				String r = m.group().trim();
				result.add(r);
			}
			string = result.size() > 0 ? result.get(0).split(":")[1].trim() : "";
			json.put("leftTicketStr", string.substring(1, string.length() - 1));
			json.put("status", "true");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}
	
}
