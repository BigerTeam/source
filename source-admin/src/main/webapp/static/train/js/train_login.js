 $(function(){
	 $("#user_name").val(getCookie("user_name"));
	 $("#password").val(getCookie("password"));
	 $("#login").click(function(){
		 dialog.tip.work({type:'loading',  content : '正在努力加载数据，请稍后...', lock : true, timer:0});
		var code="";
		var obj= $(document.getElementById('myiframe').contentWindow.document.body).find(".touclick-hov");
		obj.each(function(){
			var left=parseInt($(this).attr("left"))+3;
			var top=parseInt($(this).attr("top"))-16;
			code+=left+","+top+","
		})
		
		if(code==null||code==false){
			dialog.tip.remove();
			dialog.tip.work({type:'error', content :'验证码错误', timer : 2000});
			$('#myModal').modal('show');
		}
		code=code.substring(0,code.length-1);
		
		
		var data={randCode:code};
		 $.ajax({
			type : "GET",
			isTakeParam: false,
			dataType:"json",
			crossDomain:true,
			beforeSend: function(k) {
                k.setRequestHeader("If-Modified-Since", "0");
                k.setRequestHeader("Cache-Control", "no-cache")
            },
        	data:data,
			url : path+"/train/checkRandCodeAnsyn",
			
			success : function(data) {
				if(data.body.data.msg=='TRUE'&&data.body.data.result==1){
					var user_name=$("#user_name").val();
					var password=$("#password").val();
					 $.ajax({
							type : "GET",
							isTakeParam: false,
							dataType:"json",
							crossDomain:true,
			            	data:{randCode:code,user_name:user_name,password:password},
							url : path+"/train/loginAysnSuggest",
							success : function(data) {
								dialog.tip.remove();
								if(data.flag==true){
									$("#loginbutton").hide();
									$("#user").html("<a class=\"btn btn-link\" href=\"https://kyfw.12306.cn/otn/index/initMy12306\" target=\"_blank\">您好,"+data.username+"</a>");
									setCookie("user_name",user_name,60);
									setCookie("password",password,60);
									var myAuto =document.getElementById('myaudio');
									myAuto.pause();
									myAuto.currentTime = 0;
									flag=true;
								}
								else{
									dialog.tip.work({type:'error', content :data.messages, timer : 2000});
									$('#myModal').modal('show');
									document.getElementById('myiframe').src=path+"/index/img";
								}
							}
						});
				}else{
					dialog.tip.remove();
					dialog.tip.work({type:'error', content :'验证码错误', timer : 2000});
					$('#myModal').modal('show');
					document.getElementById('myiframe').src=path+"/train/img";
				}
			}
		});
			
	});
})
