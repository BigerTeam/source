function login(){
	dialog.tip.work({type:'loading',  content : '正在努力加载数据，请稍后...', lock : true, timer:0});
	flag=false;
	 $.ajax({
		type : "POST",
		url : path+"/train/login",
		dataType : "html",
		success : function(data) {
			dialog.tip.remove();
			$(".modal-title").html("登录");
			$(".modal-body").html(data);
			$('#myModal').modal('show');
		},error:function(){
			dialog.tip.remove();
		}
	});
}
function checkCode(){
	dialog.tip.work({type:'loading',  content : '正在努力加载数据，请稍后...', lock : true, timer:0});
	var code="";
	var obj= $(document.getElementById('myiframe').contentWindow.document.body).find(".touclick-hov");
	obj.each(function(){
		var left=parseInt($(this).attr("left"))+3;
		var top=parseInt($(this).attr("top"))-16;
		code+=left+","+top+","
	})
	code=code.substring(0,code.length-1);
	var repeat_submit_token=$("#globalRepeatSubmitToken").val();
	var train_no=$("#train_no").val();
	var seatType=$("input[name='seatType']:checkbox:checked").val();
	var fromStationTelecode=$("#from_station_telecode").val();
	var toStationTelecode=$("#to_station_telecode").val();
	var stationTrainCode=$("#station_train_code").val();
	var leftTicket=$("#leftTicketStr").val();
	var key_check_isChange=$("#key_check_isChange").val();
	var train_location=$("#location_code").val();
	var passengerTicketStr=$("#passengerTicketStr").val();
	var oldPassengerStr=$("#oldPassengerStr").val();
	var data = {
		randCode : code,
		repeat_submit_token : repeat_submit_token,
	};
	$.ajax({
		type : "POST",
		isTakeParam: false,
		dataType:"json",
   		data:data,
		url : path+"/train/checkRandCodeAnsyn",
		success : function(data) {
			if(data.data.msg=='TRUE'&&data.data.result==1){
				data={
					randCode : code,
					repeat_submit_token : repeat_submit_token,
					train_date : new Date($("#startDate").val() + " 00:00:00"),
					train_no : train_no,
					stationTrainCode : stationTrainCode,
					seatType : seatType,
					fromStationTelecode : fromStationTelecode,
					toStationTelecode : toStationTelecode,
					leftTicket : leftTicket,
					oldPassengerStr : oldPassengerStr,
					passengerTicketStr : passengerTicketStr,
					key_check_isChange : key_check_isChange,
					train_location : train_location
				};
				$.ajax({
					type : "POST",
					isTakeParam: false,
					dataType:"json",
		       		data:data,
					url : path+"/train/checkOrderInfo",
					success : function(data) {
						if(data.data.submitStatus){
							data={repeat_submit_token : repeat_submit_token}
							queryOrderWaitTime(data);
						}else{
							dialog.tip.remove();
							dialog.tip.work({type:'error', content :data.data.errMsg, timer : 2000});
						}
							
					}
				});
			}else{
				dialog.tip.remove();
				var obj=$(document.getElementById('myiframe').contentWindow.document.body);
				obj.find(".touclick-hov").remove();
				obj.find("#touclick-image").attr("src",path+"/train/getPassCodeNew?module=passenger&"+Math.random());
			}
		}
	});
}
function queryOrderWaitTime(datas){
	$.ajax({
		type : "POST",
		isTakeParam: false,
		dataType:"json",
   		data:datas,
		url : path+"/train/queryOrderWaitTime",
		success : function(data) {
			dialog.tip.remove();
			if(data.data.waitTime=='-1'){
				$('#myModal').modal('hide');
				var win = dialog.win.work({
					title : "预定成功",
					width : 60,
					borderWidth : 8,
					effect : 2,
					content : '<div style="padding-top:13px; font-size:16px;">预定成功 订单号：'+data.data.orderId+'去支付</div>',
					icon : 'ico1',
					button : {
						'确认' : function() {
							window.open ("https://kyfw.12306.cn/otn/queryOrder/initNoComplete");
							win.close();
						},
						'取消' : function() {
						}
					}
				});
			}else if(data.data.waitTime=='-2'){
				dialog.tip.work({type:'error', content :data.data.msg, timer : 2000});
			}else{
				queryOrderWaitTime(datas);
			}
		},
		error : function(data) {
			dialog.tip.remove();
		}
	});
}
function submitOrderRequest(secretStr,start_time,train_no,from_station_telecode,to_station_telecode,yp_info,from_station_name,to_station_name,location_code,station_train_code){
	dialog.tip.work({type:'loading',  content : '正在努力加载数据，请稍后...', lock : true, timer:0});
	var train_date=$("#startDate").val();
	var data={train_date:train_date,secretStr:secretStr,query_from_station_name:from_station_name,query_to_station_name:to_station_name};
	$("#secretStr").val(secretStr);
	$("#start_time").val(start_time);
	$("#train_no").val(train_no);
	$("#from_station_telecode").val(from_station_telecode);
	$("#to_station_telecode").val(to_station_telecode);
	$("#yp_info").val(yp_info);
	$("#from_station_name").val(from_station_name);
	$("#to_station_name").val(to_station_name);
	$("#location_code").val(location_code);
	$("#station_train_code").val(station_train_code);
	$.ajax({
		type : "POST",
		url : path+"/train/submitOrderRequest",
		dataType : "json",
		data:data,
		success : function(data) {
			dialog.tip.remove();
			if(!data.status){
				dialog.tip.work({type:'error', content :data.messages, timer : 2000});
//				window.open ("https://kyfw.12306.cn/otn/queryOrder/initNoComplete");
				return;
			}
			$("#globalRepeatSubmitToken").val(data.globalRepeatSubmitToken);
			$("#key_check_isChange").val(data.key_check_isChange);
			$("#leftTicketStr").val(data.leftTicketStr);
			$(".modal-title").html("验证码");
			$(".modal-body").html("<iframe id=\"myiframe\"src=\""+ path+"/index/img?moduled=passenger\"width=\"500\"height=\"200\"frameborder=\"no\"border=\"0\"marginwidth=\"0\"marginheight=\"0\"scrolling=\"no\"allowtransparency=\"yes\"></iframe><input type=\"button\" id=\"checkCode\" onclick=\"checkCode()\" class=\"btn btn-default\" value=\"验证\"/>");
			$('#myModal').modal('show');
		},
		error : function(data) {
			dialog.tip.remove();
			dialog.tip.work({type:'error', content :'系统错误', timer : 2000});
		}
	});
}
function setCookie(name,value,Days) {
	var Days = 30;
	var exp = new Date();
	exp.setTime(exp.getTime() + Days*24*60*60*1000);
	document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
}
function getCookie(name) {
	var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
	if(arr=document.cookie.match(reg))
		return unescape(arr[2]);
	else
		return null;
}
