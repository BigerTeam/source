 $(function(){
	$("#getPassengers").click(function(){
		dialog.tip.work({type:'loading',  content : '正在努力加载数据，请稍后...', lock : true, timer:0});
		if($(".modal-title").html()!="用户信息"){
			$.ajax({
				type : "POST",
				url : path+"/train/passengers",
				dataType : "html",
				success : function(data) {
					dialog.tip.remove();
					$(".modal-title").html("用户信息");
					$(".modal-body").html(data);
					$('#myModal').modal('show');
				}
			});
		}else{
			dialog.tip.remove();
			$('#myModal').modal('show');
		}
	});
	$("#loginbutton").click(function(){
		login();
	});
	$("#search").click(function(){
		var fromStation=$("#fromStation").val();
		if(fromStation==""){
			$("#fromStationText").css({"border": "1px solid red","color":"#999"});
			return;
		}
		var toStation=$("#toStation").val();
		if(toStation==""){
			$("#toStation").css({"border": "1px solid red","color":"#999"});
			return;
		}
		var startDate=$("#startDate").val();
		setCookie("fromStation",fromStation,60);
		setCookie("fromStationText",$("#fromStationText").val(),60);
		setCookie("toStation",toStation,60);
		setCookie("toStationText",$("#toStationText").val(),60);
		var data={toStation:toStation,fromStation:fromStation,startDate:startDate};
		dialog.tip.work({type:'loading',  content : '正在努力加载数据，请稍后...', lock : true, timer:0});
		$.ajax({
			type : "POST",
			url : path+"/train/query",
			dataType : "html",
			data : data,
			success : function(data) {
				dialog.tip.remove();
				$("#train").html(data);
			}
		});
	});
	$("#checkbox_All").click(function(){
		if($(this).is(':checked')){
			$("input[name=cc_type]").prop("checked","checked");
		}else{
			$("input[name=cc_type]").removeProp("checked");
		}
	});
	var tbody="";
	$("input[name=cc_type]").click(function(){
		if(tbody!="")
			$("#tbody").html(tbody);
		tbody=$("#tbody").html();
		var cc_type="";
		$("input[name=cc_type]:checkbox:checked").each(function(){
			cc_type+=$(this).val();
		});
		var html="";
		$("#tbody tr").each(function(){
			if(cc_type.indexOf($(this).find(".trainCode").val().substring(0,1))>=0){
				html+="<tr align='center'>"+$(this).html()+"</tr>";
			}
		})
		if(html==""){
			html=tbody;
		}
		$("#tbody").html(html);
	});
	$("#myModal").on("hide.bs.modal", function () {
		flag=true;
	});
	var tbody="";
	$("#autoPlay").click(function(){
		var myAuto =document.getElementById('myaudio');
		if($(this).val()=='播放'){
			myAuto.play();
			$(this).val("停止")
		}else{
			myAuto.pause();
			myAuto.currentTime = 0;
			$(this).val("播放")
		}
	})
})