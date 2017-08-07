 $(function(){
	$("#stratTimeSort").click(function(){
		var div = $("#tbody tr").toArray().sort(function(a,b){
		       return parseInt($(a).find("#lishi").html().replace(":",""))- parseInt($(b).find("#lishi").html().replace(":",""));
		});
		$("#tbody").html(div);
	});
	var tbody="";
	var trainSize="";
	$("#avail_ticket").click(function(){
		if(tbody!=""){
			$("#tbody").html(tbody);
			$(".trainSize").html(trainSize);
		}
		tbody=$("#tbody").html();
		trainSize=$(".trainSize").html();
		var html="";
		var size=0;
		if($(this).is(':checked')){
			$("#tbody tr").each(function(){
				if($(this).find("#secretStr").attr("secretStr").trim()!=""){
					html+="<tr align='center'>"+$(this).html()+"</tr>";
					size++;
				}
			})
		}else{
			html=tbody;
			size=trainSize;
		}
		$("#tbody").html(html);
		$(".trainSize").html(size);
	});
})
