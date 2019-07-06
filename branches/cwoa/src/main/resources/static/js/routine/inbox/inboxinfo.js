layui.extend({
	setter: "../../static/layui/config"

}).define(["setter", "jquery", 'form', 'laypage', 'table'], function(e) {

	var table = layui.table,
		laypage = layui.laypage,
		layer = layui.layer,
		form = layui.form,
		$ = layui.jquery;
	var data = "";	//详情数据
	var cuserIds="";//抄送人Id
    var suserIds="";//收件人Id
	var contents = "";
    var sessionData = sessionStorage.getItem('user');//取出数据
    var user = JSON.parse(sessionData);
    var userId = user.userId;
	var mailId = $("#mailId").val();
	var cuserFlag =false;
	var widthMax = "70%",
		heightMax = "80%";
	if($(window).width() < 768) {
		widthMax = "100%";
		heightMax = "80%";
	};
	$("#div2").click(function(event){
		event.stopPropagation();
		$(".d1").show();
	});
	$(document).click(function(e){
    	var target = $(e.target);
     	if(target.closest(".d1").length != 0) return;
   		 $(".d1").hide();
	});
	$("#wz").click(function(){
		$("#wz").hide();
		$("#jj").css("display","inline");
		$("#jjcontent").hide();
		$(".wzcontent").show();
		if(cuserFlag){
			$(".cuserf").show();
		}
		
	});
	$("#jj").click(function(){
		$("#jj").hide();
		$("#wz").css("display","inline");
		$(".wzcontent").hide();
		$("#jjcontent").show();
		$(".cuserf").hide();
	});
	$(".layui-icon-rate").click(function(){
		$(".layui-icon-rate").hide();
		$(".layui-icon-rate-solid").show();
		var state=2;//星标
		$.ajax({
    			type:"post",
    			url:layui.setter.project + "/inbox/starmail",
    			async:true,
				xhrFields: {
					withCredentials: true
				},
			
				//contentType:"application/json;charset=utf-8",
				dataType: "json",
    			data:{"mailIds":mailId,"userId":userId,"starState":state},
    			success:function(result){
    				console.log(result)
    				if(result.result==200){
    				}
    			}
    		});
	})
	$(".layui-icon-rate-solid").click(function(){
		$(".layui-icon-rate-solid").hide();
		$(".layui-icon-rate").show();
		var state=1;//取消星标
		$.ajax({
    			type:"post",
    			url:layui.setter.project + "/inbox/starmail",
    			async:true,
				xhrFields: {
					withCredentials: true
				},
			
				//contentType:"application/json;charset=utf-8",
				dataType: "json",
    			data:{"mailIds":mailId,"userId":userId,"starState":state},
    			success:function(result){
    				console.log(result)
    				if(result.result==200){
    				
    				}
    			}
    		});
	});
	//回复
	$("#hf").click(function(){
		layer.open({ //弹框
					id: 'insert-form',
					type: 2,
					//skin: 'layui-layer-demo', //样式类名
					title: '回复邮件',
					maxmin: true,
					btn: [],
					yes: function(index, layero) {

					},
					closeBtn: 1, //不显示关闭按钮
					anim: 7,
                    area: ['100%',  '100%'],
					shadeClose: false, //开启遮罩关闭
					content: layui.setter.project + "/rw/replymail",
					success: function(layero, index) {
						var body = layer.getChildFrame('body', index);
						body.find("#data").val(JSON.stringify(data));
						body.find("#state").val(1);
						body.find("#contents").val(contents);
					},
					end: function() {

					},

				});
	});
	//回复全部
	$("#hfqb").click(function(){
		layer.open({ //弹框
					id: 'insert-form',
					type: 2,
					//skin: 'layui-layer-demo', //样式类名
					title: '回复邮件',
					maxmin: true,
					btn: [],
					yes: function(index, layero) {

					},
					closeBtn: 1, //不显示关闭按钮
					anim: 7,
                    area: ['100%',  '100%'],
					shadeClose: false, //开启遮罩关闭
					content: 'replymail.html',
					success: function(layero, index) {
						var body = layer.getChildFrame('body', index);
						body.find("#data").val(JSON.stringify(data));
						body.find("#state").val(2);
						body.find("#contents").val(contents);
						body.find("#suserIds").val(suserIds);
						body.find("#cuserIds").val(cuserIds);
					},
					end: function() {

					},

				});
	});
	//转发
	$("#zf").click(function(){
		layer.open({ //弹框
					id: 'insert-form',
					type: 2,
					//skin: 'layui-layer-demo', //样式类名
					title: '回复邮件',
					maxmin: true,
					btn: [],
					yes: function(index, layero) {

					},
					closeBtn: 1, //不显示关闭按钮
					anim: 7,
                    area: ['100%',  '100%'],
					shadeClose: false, //开启遮罩关闭
					content: 'replymail.html',
					success: function(layero, index) {
						var body = layer.getChildFrame('body', index);
						body.find("#data").val(JSON.stringify(data));
						body.find("#state").val(3);
						body.find("#contents").val(contents);
						//body.find("#suserIds").val(suserIds);
						//body.find("#cuserIds").val(cuserIds);
					},
					end: function() {

					},

				});
	})
	
		$.ajax({
    			type:"post",
    			url:layui.setter.project + "/inbox/getInboxInfo",
    			async:true,
				xhrFields: {
					withCredentials: true
				},
			
				//contentType:"application/json;charset=utf-8",
				dataType: "json",
    			data:{"mailId":mailId},
    			success:function(result){
    				contents = "发件人："+result.users.userName+"</br></br>";
    				data = result;
    				console.log(result);
    				var dd = new Date(result.sendTime).format("yyyy年MM月dd日 hh:mm")
    				console.log(dd);
    				var suser1 ="";//精简收件人姓名
    				var suser2 ="";//完整收件人姓名
    				var cuser = "";//完整抄送人姓名
    				
    				var sflag = 0;
    				var flag=0;
    				var starStateMailflag =1;
    				$.each(result.mailUsers, function(index, value) {
    					if(value.mailUser==userId){
    						if(value.starState==2){
    							starStateMailflag=2;
    						}
    					}
    					console.log(value.status);
    					if(value.status==1){
    						suser2 = suser2+ value.userss.userName + "、";
    						suserIds = suserIds+value.userss.userId + ",";
    						console.log(value.userss.userName);
    						if(sflag<3){
    							suser1 = suser1+ value.userss.userName + "、";
    							sflag++;
    						}
    						flag++;
    					}else if(value.status==2){
    						cuser = cuser+ value.userss.userName + "、";
    						cuserIds = cuserIds+value.userss.userId+ ",";
    					}
					});
    				if(suser1!=""){
    					suser1 = suser1.substring(0, suser1.length - 1);
    					suser2 = suser2.substring(0, suser2.length - 1);
    					suserIds = suserIds.substring(0, suserIds.length - 1);
    				}
    				contents += "收件人："+suser2+"</br></br>"
    				if(cuser!=""){
    					cuserFlag =true;
    					cuserIds = cuserIds.substring(0, cuserIds.length - 1);
    					cuser = cuser.substring(0, cuser.length - 1);
    					contents += "抄件人："+cuser+"</br></br>";
    				}
    				contents +="发送日期："+dd+"</br></br>";
    				contents +="主题："+result.mailTheme+"</br></br>";
    				var jjcontent="";
    				if(flag==1){
    					
    					jjcontent = "<span style='color:#0070c1;'>"+result.users.userName+"</span> ：于"+dd+" 发送给 "+suser1;
    				}else{
    					jjcontent = "<span style='color:#0070c1;'>"+result.users.userName+"</span> ：于"+dd+" 发送给 "+suser1+" 等";
    				}
    				if(starStateMailflag==1){//详细星标
    					$(".layui-icon-rate").show();
    				}else{
    					$(".layui-icon-rate-solid").show();
    				}
    				$("#theme").html(result.mailTheme);
    				$("#jjcontent").html(jjcontent);
    				$("#suser1").html(suser2);
    				$("#cuser1").html(cuser);
    				$("#mail_content").html(result.mailContent);
    				$("#sendUser").html(result.users.userName);
    			}
    		});
		

	//标星
	$("#xin").click(function(){		
		$(".layui-icon-rate").hide();
		$(".layui-icon-rate-solid").show();
		var state=2;//星标
		$.ajax({
    			type:"post",
    			url:layui.setter.project + "/inbox/starmail",
    			async:true,
				xhrFields: {
					withCredentials: true
				},
			
				//contentType:"application/json;charset=utf-8",
				dataType: "json",
    			data:{"mailIds":mailId,"userId":userId,"starState":state},
    			success:function(result){
    				console.log(result)
    				if(result.result==200){
    				}
    			}
    		});
		
	});

	$("#qxin").click(function(){
		$(".layui-icon-rate-solid").hide();
		$(".layui-icon-rate").show();
		var state=1;//取消星标
		$.ajax({
    			type:"post",
    			url:layui.setter.project + "/inbox/starmail",
    			async:true,
				xhrFields: {
					withCredentials: true
				},
			
				//contentType:"application/json;charset=utf-8",
				dataType: "json",
    			data:{"mailIds":mailId,"userId":userId,"starState":state},
    			success:function(result){
    				console.log(result)
    				if(result.result==200){
    				
    				}
    			}
    		});
		
	})

	
	e("inboxinfo", {})
});