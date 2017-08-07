<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${ctxstatic}/train/css/train.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctxstatic}/train/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript">
	function radn() {
		$(".touclick-hov").remove();
		$(".touclick-image").attr("src","${ctx}/train/getPassCodeNew?module=${moduled}&"+Math.random());
	}
	function remove(obj){
		$(obj).remove();
	}
/* 	 window.onload=function(){
		var obj=document.getElementById("touclick-image");	
		obj.onclick=function(e){
			oEvent=e||event;
			obj.mouseStart={};
			obj.mouseStart.x=oEvent.clientX-10;
			obj.mouseStart.y=oEvent.clientY-10;
			if(obj.mouseStart.x>4&&obj.mouseStart.y>11){
				$(".touclick-img-par").append("<div class='touclick-hov touclick-bgimg' index='0' left="+obj.mouseStart.x+"  top="+obj.mouseStart.y+" style='position: absolute; width: 32px; height: 32px; border-radius: 32px; left:"+obj.mouseStart.x+"px; top: "+obj.mouseStart.y+"px;' onclick='remove(this)'></div>");
			}
		};
	}; */
	$(function(){
		 $(".touclick-image").click(function(t){
			var a = $(this).offset(), n = 30, i = 0, s = 0;
			if (i = Math.floor(t.pageX - a.left), s = Math.floor(t.pageY - a.top - n), !(0 > s || 0 > i)) {
				s=s+ 15;
				$(".touclick-img-par").append("<div class='touclick-hov touclick-bgimg' index='0' left="+i+"  top="+s+" style='left:"+i+"px; top: "+s+"px;' onclick='remove(this)'></div>");
			}
		}); 
	});
</script>
</head>
<body>
<div class="touclick-wrapper" style="height: 0px">
	<div class="touclick-bgimg touclick-reload touclick-reload-normal" onclick="radn()"></div>
	<div class="touclick-bgimg touclick-arrow"></div>
</div>
<div class="touclick-img-par touclick-bgimg">
	<img id="touclick-image" class="touclick-image" alt="" src="${ctx}/train/getPassCodeNew?module=${moduled}" style="display: block; visibility: visible;">
	<div id="touclick-wait" class="touclick-wait" style="left: 121.5px; top: 84px; display: none;">
		<div class="touclick-bgimg" id="touclick-bgimg" style="width: 10px; height: 10px; position: absolute; left: 20px; top: 0px; font-size: 0px; background-position: 0px -193px;"></div>
	</div>
</div>

</body>
</html>



