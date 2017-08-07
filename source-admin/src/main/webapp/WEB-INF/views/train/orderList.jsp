<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${ctxstatic}/train/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="${ctxstatic}/train/css/bootstrap-theme.min.css" />
<script type="text/javascript">
$(function(){
	$("#choicePassenger").click(function(){
		var passengerTicketStr="";
		var oldPassengerStr="";
		var html="";
		var seatType=$("input[name='seatType']:checkbox:checked").val();
		$("input[name='passenger']:checkbox:checked").each(function(){
			passengerTicketStr+=seatType+",0,"+$(this).val()+","+$(this).attr("mobile_no")+",N_";
			oldPassengerStr+=$(this).val().substring(2)+",1_";
			var type=$("input[name='seatType']:checkbox:checked").attr("types")
			html+="<label for='avail_ticket' style='cursor: pointer;'>乘客名："+$(this).attr("passenger_name")+"   座位类型："+type+"</label>  ";
		});
		passengerTicketStr=passengerTicketStr.substring(0,passengerTicketStr.length-1);
		$("#passengerTicketStr").val(passengerTicketStr);
		$("#oldPassengerStr").val(oldPassengerStr);
		$('#myModal').modal('hide');
		$(".fr").html(html);
	})
});
</script>
</head>
<body>

<table width="80%" style="margin: 0 auto;" class="table table-bordered" id="passenger">
	<tr align="center">
		<td>订单号 <td>
		<td>订单时间<td>
		<td>发车时间<td>
		<td>车次   <td>
		<td>出发地 <td>
		<td>目的地 <td>
		<td>乘客   <td>
		<td>票种   <td>
		<td>席别   <td>
		<td>车厢   <td>
		<td>座位   <td>
		<td>票价   <td>
		<td>状态   <td>
	</tr>
	
	 <c:forEach items="${list}" var="passenger">
		<tr align="center">
			<td><input type="checkbox" name="passenger" passenger_name="${passenger.passenger_name}" value="${passenger.passenger_type},${passenger.passenger_name},${passenger.passenger_id_type_code},${passenger.passenger_id_no}" mobile_no="${passenger.mobile_no}"/>${passenger_index+1}</td>
			<td>${passenger.passenger_name }</td>
			<td>${passenger.passenger_id_type_name}</td>
			<td>${passenger.passenger_id_no }</td>
			<td>${passenger.mobile_no }${passenger.phone_no }</td>
			<td>${passenger.passenger_type_name}</td>
			<td>
				<c:if test="${passenger.passenger_flag=='0'}">已通过</c:if>
				<c:if test="${passenger.passenger_flag!='0'}">未通过</c:if>
			</td>
		</tr>
	</c:forEach>
</table>
</body>
</html>


