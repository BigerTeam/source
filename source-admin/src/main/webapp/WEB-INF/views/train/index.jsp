<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>火车票列表</title>
</head>
<script type="text/javascript">
	var path='${ctx}';
</script>
<link rel="shortcut icon" type="image/x-icon" href="${ctxstatic}/train/img/favicon.ico">
<link rel="stylesheet" type="text/css" href="${ctxstatic}/train/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="${ctxstatic}/train/css/bootstrap-theme.min.css" />
<link rel="stylesheet" type="text/css" href="${ctxstatic}/train/css/train.css" />
<link rel="stylesheet" type="text/css" href="${ctxstatic}/train/css/dialog/dialog.css" />
<script type="text/javascript" src="${ctxstatic}/train/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctxstatic}/train/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${ctxstatic}/train/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctxstatic}/train/js/dialog.js" ></script>
<script type="text/javascript" src="${ctxstatic}/train/js/train.js"></script>
<script type="text/javascript" src="${ctxstatic}/train/js/train_utils.js"></script>
<script type="text/javascript" src="${ctxstatic}/train/js/favorite_name.js"></script>
<script type="text/javascript" src="${ctxstatic}/train/js/station_name.js"></script>
<script type="text/javascript" src="${ctxstatic}/train/js/city_name.js"></script>
<script type="text/javascript">
	var flag = true;
	function checkUser(){
		if(flag){
			$.ajax({
				type : "POST",
				url : path+"/train/checkUser",
				dataType : "json",
				success : function(data) {
					if(data==false){
						login();
					//	var myAuto =document.getElementById('myaudio');
					//	myAuto.play();
					}
					flag=data;
				},error:function(){
					flag=false;
				}
			});
		}
	}
	$(function() {
		$("#fromStation").val(getCookie("fromStation"));
		$("#fromStationText").val(getCookie("fromStationText"));
		$("#toStation").val(getCookie("toStation"));
		$("#toStationText").val(getCookie("toStationText"));
// 		setInterval(checkUser, 10000);
	})
	
	
	
// 	<#if !login??>
// 		login();
// 	</#if>		
</script>
<style>
input.error {
	border: 1px solid red;
	color: #999
}



#train td {
	vertical-align: middle;
	text-align: center;
}


</style>

<body>
	<audio id="myaudio" src="${ctxstatic}/train/music/message.wav" controls="controls"
		loop="false" hidden="true">
	</audio>
	<span id="user">
	
	
	<c:if test="${empty username}">
			<input type="button" id="loginbutton" value="登录" class="btn btn-default" />
	</c:if>
	
	<c:if test="${not empty username}">
		<a class="btn btn-link" href="https://kyfw.12306.cn/otn/index/initMy12306" target="_blank">您好,${username}</a>
	</c:if>
	</span>
	<input type="button" id="getPassengers" class="btn btn-default" value="获取联系人"/>
	<input type="button" id="" class="btn btn-default" value="退出登录"/>
	<div class="fr"> </div>
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">登录</h4>
				</div>
				<div class="modal-body"></div>
			</div>
		</div>
	</div>
	<form action="${ctx}/train/train" method="post" id="trainParme">
		<table style="margin: 0 auto;" class="table table-bordered">
			<tr>
				<td>出发城市： <input name="fromStation" id="fromStation" type="hidden" /> <input name="fromStationText"
					maxlength="15" placeholder="简码/汉字" id="fromStationText" style="width: 100px;" class="input_20txt_gray" type="text" />
				</td>
				<td>目的城市： <input name="toStation" id="toStation" type="hidden" /> <input name="toStationText" maxlength="15"
					placeholder="简码/汉字" id="toStationText" style="width: 100px;" class="input_20txt_gray" type="text" />
				</td>
				<td>出发时间： <input type="text" value="${startDate}" name="startDate" id="startDate" class="input_20txt_gray"
					onclick="WdatePicker({doubleCalendar:true,minDate:'${minDate}',maxDate:'${maxDate }'})">
				</td>
				<td rowspan="2"><input type="button" class="btn btn-primary btn-fixed-lg" role="button" id="search" value="查询"></td>
			</tr>
			<tr>
				<td colspan="3">车次类型： 
					<input name="cc_type" checked="checked" type="checkbox" class="check" id="checkbox_All"/>全部
					<input name="cc_type" checked="checked" value="G" type="checkbox" class="check" id="checkbox_OFp57OqnbA"/>GC-高铁/城际
					<input name="cc_type" checked="checked" value="D" type="checkbox" class="check" id="checkbox_DlEf7bRWEW"/>D-动车
					<input name="cc_type" checked="checked" value="Z" type="checkbox" class="check" id="checkbox_XCOTEvuC4i"/>Z-直达
					<input name="cc_type" checked="checked" value="T" type="checkbox" class="check" id="checkbox_P6cOKO6IXJ"/>T-特快
					<input name="cc_type" checked="checked" value="K" type="checkbox" class="check" id="checkbox_tVKVvd2fgX"/>K-快速
					<input name="cc_type" checked="checked" value="QT" type="checkbox" class="check" id="checkbox_YWciZnHrsj"/>其他
				</td>
			</tr>
		</table>
	</form>
	<%@include file="train.jsp" %>
	<input type="hidden" id="passengerTicketStr" />
	<input type="hidden" id="oldPassengerStr" />
	<input type="hidden" id="globalRepeatSubmitToken" />
	<input type="hidden" id="key_check_isChange" />
	<input type="hidden" id="leftTicketStr" />
	<input type="hidden" id="secretStr" />
	<input type="hidden" id="start_time" />
	<input type="hidden" id="train_no" />
	<input type="hidden" id="from_station_telecode" />
	<input type="hidden" id="to_station_telecode" />
	<input type="hidden" id="yp_info" />
	<input type="hidden" id="from_station_name" />
	<input type="hidden" id="to_station_name" />
	<input type="hidden" id="location_code" />
	<input type="hidden" id="station_train_code" />
	<div style="top: 0; left: 0; z-index: 1000; POSITION: absolute;">
		<div style="overflow: hidden; left: 323px; top: 90.5px; display: none;" id="form_cities">
			<div id="top_cities">简码/汉字或↑↓</div>
			<div id="panel_cities"></div>
			<div style="display: block;" id="flip_cities">
				<a href="" class="cityflip" onclick="city_showlist(1);return false;">向后&nbsp;»</a>
			</div>
		</div>
	</div>

	<div style="top: 0; left: 0; z-index: 1000; POSITION: absolute;">
		<div style="overflow: hidden; display: none; left: 323px; top: 90.5px;" id="form_cities2">
			<div id="top_cities1"></div>
			<div id="panel_cities2"></div>
		</div>
	</div>
</body>
<script src="https://s11.cnzz.com/z_stat.php?id=1261804647&web_id=1261804647" language="JavaScript"></script>

</html>