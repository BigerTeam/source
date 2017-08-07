package com.source.app.controller.train;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.source.app.shiro.ShiroKit;
import com.source.train.entity.NewTrain;
import com.source.train.entity.Passenger;
import com.source.train.service.TrainService;
import com.source.utils.train.Cookie;
import com.source.utils.train.CookieUtil;
import com.source.utils.train.DateUtils;
import com.source.utils.train.HttpsRequestNg;

@Controller
@RequestMapping("/train")
public class TrainController extends BaseController {
	
	private final static Logger logger = Logger.getLogger(TrainController.class);

	// 首页
	@RequestMapping("/index")
	public String index(Model model) {
		model.addAttribute("startDate", DateUtils.format(new Date()));
		model.addAttribute("minDate", DateUtils.format(new Date()));
		model.addAttribute("maxDate", DateUtils.format(DateUtils.addDateTime(new Date(), 59)));
		return "train/index";
	}

	// 查询列车经停站
	@RequestMapping("/queryMyOrder")
	public String queryMyOrder(Model model) throws IOException {
		model.addAttribute("orderNoComplete", TrainService.queryMyOrderNoComplete());
		model.addAttribute("gOrderList", TrainService.queryMyOrder("G"));
		model.addAttribute("hOrderList", TrainService.queryMyOrder("H"));
		return "train/orderList";
	}

	// 查询列车经停站
	@ResponseBody
	@RequestMapping("/queryByTrainNo")
	public JSONObject queryByTrainNo(HttpServletRequest request, String trainNo, String fromStation, String toStation,
			String startDate) throws IOException {
		JSONObject result = TrainService.queryByTrainNo(trainNo, fromStation, toStation, startDate);
		return result;
	}

	// 登录
	@ResponseBody
	@RequestMapping("/loginAysnSuggest")
	public JSONObject loginAysnSuggest(HttpServletRequest request, HttpServletResponse response, String user_name,
			String password, String randCode) throws IOException {
		Boolean loginAysnSuggest = TrainService.loginAysnSuggest(user_name, password, randCode);
		Map<String, Cookie> cookies = HttpsRequestNg.getHttpClient().cookies;
		Set<Map.Entry<String, Cookie>> entrys = cookies.entrySet();
		for (Map.Entry<String, Cookie> entry : entrys) {
			Cookie cookie = entry.getValue();
			CookieUtil.setCookie(response, cookie.getName(), cookie.getValue());
		}
		String string = new String(HttpsRequestNg.getHttpClient().doPost("https://kyfw.12306.cn/otn/login/userLogin"));
		String reg = "<span style=\"width:50px;\">[\u4e00-\u9fa5]{0,100}</span>";
		Matcher m = Pattern.compile(reg).matcher(string);
		String username = "";
		while (m.find()) {
			String r = m.group().trim();
			username = r.trim() != "" ? r.split("\">")[1] : "";
		}
		Session session =ShiroKit.getSession();
		if(username!=null&&username.length()>7){
			username = username.substring(0, username.length() - 7);
			session.setAttribute("username", username);
		}
		JSONObject fromObject = new JSONObject();
		fromObject.put("username", username);
		fromObject.put("flag", loginAysnSuggest);
		fromObject.put("messages", loginAysnSuggest?"登录成功":"登录失败");
		return fromObject;
	}

	// 退出登录
	@ResponseBody
	@RequestMapping("/loginOut")
	public String loginOut(HttpServletRequest request) throws IOException {
		clearSession();
		String result = new String(HttpsRequestNg.getHttpClient().doPost("https://kyfw.12306.cn/otn/login/loginOut"));
		logger.info("loginOut----" + result);
		return "1";
	}

	@RequestMapping("/login")
	public String login(HttpServletRequest request) throws IOException {
		clearSession();
		return "train/login";
	}

	@RequestMapping("/img")
	public String img(Model model, @RequestParam(defaultValue = "login") String moduled) throws IOException {
		model.addAttribute("moduled", moduled);
		return "train/img";
	}

	// 预提交订单
	@ResponseBody
	@RequestMapping("/submitOrderRequest")
	public JSONObject submitOrderRequest(HttpServletRequest request, String secretStr, String train_date,
			String query_from_station_name, String query_to_station_name) throws IOException {
		JSONObject submitOrderRequest = TrainService.submitOrderRequest(secretStr, train_date, query_from_station_name,
				query_to_station_name);
		return submitOrderRequest;
	}

	// 检查订单有效
	@ResponseBody
	@RequestMapping("/checkOrderInfo")
	public String checkOrderInfo(HttpServletRequest request, String randCode, String oldPassengerStr,
			String passengerTicketStr, String repeat_submit_token, String train_date, String train_no,
			String stationTrainCode, String seatType, String fromStationTelecode, String toStationTelecode,
			String leftTicket, String key_check_isChange, String train_location) throws IOException {
		String result = TrainService.checkOrderInfo(randCode, oldPassengerStr, passengerTicketStr, repeat_submit_token, train_date, train_no, stationTrainCode, seatType, fromStationTelecode, toStationTelecode, leftTicket, key_check_isChange, train_location);
		
		JSONObject res = new JSONObject();
		JSONObject data = new JSONObject();
		result =result == null ? "下单出错" : result;
		if(result.equals("下单出错")){
			data.put("submitStatus", false);
			data.put("errMsg", "下单出错");
		}
		res.put("data", data);
		return result == null ? res.toString() : result;
	}

	@ResponseBody
	@RequestMapping("/queryOrderWaitTime")
	public String queryOrderWaitTime(HttpServletRequest request, String repeat_submit_token) throws IOException {
		String result = TrainService.queryOrderWaitTime(repeat_submit_token);
		return JSON.parseObject(result).toString();
	}

	// 取消订单
	@ResponseBody
	@RequestMapping("/cancelNoCompleteMyOrder")
	public String cancelNoCompleteMyOrder(HttpServletRequest request, String orderId) throws IOException {
		Boolean result = TrainService.cancelNoCompleteMyOrder(orderId);
		return result.toString();
	}

	// 查询未完成订单
	@ResponseBody
	@RequestMapping("/queryMyOrderNoComplete")
	public JSONArray queryMyOrderNoComplete(HttpServletRequest request) throws IOException {
		JSONArray queryMyOrderNoComplete = TrainService.queryMyOrderNoComplete();
		return queryMyOrderNoComplete;
	}

	// 获取乘客信息
	@RequestMapping("/passengers")
	public String passengers(HttpServletRequest request, Model model) throws IOException {
		List<Passenger> passengers = TrainService.passengers();
		model.addAttribute("list", passengers);
		return "train/passengers";
	}

	// 检查用户登录是否有效
	@ResponseBody
	@RequestMapping("/checkUser")
	public String checkUser(HttpServletRequest request, Model model) throws IOException {
		Boolean checkUser = TrainService.checkUser();
		return checkUser.toString();
	}

	// 校验验证码
	@ResponseBody
	@RequestMapping("/checkRandCodeAnsyn")
	public JSONObject checkRandCodeAnsyn(HttpServletRequest request, String randCode,
			@RequestParam(value = "repeat_submit_token", required = false) String repeat_submit_token)
			throws IOException {
		JSONObject checkRandCodeAnsyn = TrainService.checkRandCodeAnsyn(randCode, repeat_submit_token);
		return checkRandCodeAnsyn;
	}

	// 获取验证码
	@ResponseBody
	@RequestMapping("/getPassCodeNew")
	public void getPassCodeNew(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(defaultValue = "login") String module) throws IOException {
		byte[] doGet = TrainService.getPassCodeNew(module);
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		InputStream buffin = new ByteArrayInputStream(doGet);
		BufferedImage img = ImageIO.read(buffin);
		// 将图像输出到Servlet输出流中。
		ServletOutputStream sos = response.getOutputStream();
		ImageIO.write(img, "jpeg", sos);
		sos.close();
	}

	// 查询余票
	@RequestMapping("/query")
	public ModelAndView train(HttpServletRequest request, Model model, String fromStation, String toStation,
			String startDate) {
		ModelAndView modelAndView = new ModelAndView("train/train");
		try {
			List<NewTrain> queryTrain = TrainService.queryTrain(fromStation, toStation, startDate);
			modelAndView.addObject("list", queryTrain);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return modelAndView;
	}

}
