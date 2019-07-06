layui.extend({
	setter: "../../static/layui/config"

}).define(["setter", "jquery", 'form', 'laypage', 'table'], function(e) {

	var table = layui.table,
		laypage = layui.laypage,
		layer = layui.layer,
		form = layui.form,
		$ = layui.jquery;

    var sessionData = sessionStorage.getItem('user');//取出数据
    var user = JSON.parse(sessionData);
    var userId = user.userId;
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
})
	
	table.render({
		elem: '#test',
		url: layui.setter.project + "/mail/getStarMail",
		title: '任务',
		where: {userId: userId},
		skin: "line",
        //cellMinWidth: 100,
		cols: [
			[{
					type: 'checkbox'
				}
			,{
					field: 'state',
					title: '<svg class="icon" aria-hidden="true"><use xlink:href="#icon-youjian"></use></svg>',
					templet: function(d) {
						
							var name='<svg class="icon" aria-hidden="true"><use xlink:href="#icon-wodexiaoxiyidu"></use></svg>';
						return name;
					},width:55
			},
				
				{
					field: 'mailTheme',
					title: '主题',
					event: 'zcontent',				
					sort: true,
					style:'cursor: pointer'
					
				}
				,
				{
					field: 'user',
					title: '发件人',
					templet: function(d) {
						var u = d.users;
						var name = "";
						if(u!=null){
							name = u.userName;
						}
						return name;
					},					
					sort: true
					,width:240
				},
				{
					field: 'sendTime',
					title: '发布时间',
					width:160,
					templet: function(d) {
						console.log(d.sendTime);
						var newDate = new Date(d.sendTime);
						var datas= newDate.format('yyyy-MM-dd hh:mm');
						return datas;
					},					
					sort: true
				},
				{
					field: 'taskStatus',
					title: '<i class="layui-icon layui-icon-rate"></i>',
					templet: function(d) {
						var name = "";
						var user = d.mailUsers;
						console.log(d)
						if(d.starStatus==2){
							name='<i class="layui-icon layui-icon-rate-solid"></i>';
							return name;
						}
						
						if(user[0].starState==2){
								name='<i class="layui-icon layui-icon-rate-solid"></i>';
								return name;
							}
						return name;
					}
				,width:55,
				event: 'setSign',
				 style:'cursor: pointer'
				}
				
			]
		],
		done: function(res, curr, count){
		 $('tr').css({'background-color': '#FFFFFF'});
		},
		page: true
	});


	table.on('tool(test)', function(obj){
		var data = obj.data;
    	if(obj.event === 'setSign'){
    		
    		var state = data.starStatus;
    		var user = data.mailUsers;
    		var mailIdkeys = "";
    		var mailUserIdkeys="";
    		var shuang="";
    		if(state==2){
    			if(user[0]!=null){
    				if(user[0].starState==state){
    					shuang = data.mailId;
    				}else{
    					mailIdkeys=data.mailId;
    				}
    			}else{
    				mailIdkeys=data.mailId;
    			}
    			
    			
    		}else{
    			mailUserIdkeys=data.mailId;
    		}
    		//console.log(JSON.stringify(data))
    		$.ajax({
    			type:"post",
    			url:layui.setter.project + "/mail/starmail",
    			async:true,
				xhrFields: {
					withCredentials: true
				},
			
				//contentType:"application/json;charset=utf-8",
				dataType: "json",
    			data:{"userId":userId,"mailIdkeys":mailIdkeys,"mailUserIdkeys":mailUserIdkeys,"shuang":shuang},
    			success:function(result){
    				if(result.result==200){
    					renderTable();
    				}
    			}
    		});
    	}else if(obj.event === 'zcontent'){
    		
    		layer.open({ //弹框
					id: 'insert-form',
					type: 2,
					//skin: 'layui-layer-demo', //样式类名
					title: '查看邮件',
					maxmin: true,
					btn: [],
					yes: function(index, layero) {

					},
					closeBtn: 1, //不显示关闭按钮
					anim: 7,
                    area: ['100%',  '100%'],
					shadeClose: false, //开启遮罩关闭
					content: layui.setter.project + "/rw/starmailinfo",
					success: function(layero, index) {
						var body = layer.getChildFrame('body', index);
						body.find("#mailId").val(data.mailId);
					},
					end: function() {

					},

				});

    	}
    })
    $('#Search').on('click', function() {
    	var content = $("#content").val();
		renderTable(content);
	});		
    		
	function renderTable(content) {
		table.reload('test', {
			url: layui.setter.project + "/mail/getStarMail",
			where:{userId: userId,content:content}
		});
	};

	$("#qxin").click(function(){
		var checkStatus = table.checkStatus('test');
		
		if(checkStatus.data.length == 0) {
			layer.alert("未选中数据");
			return false;
		}
		var mailIdkeys="";//发件箱
		var mailUserIdkeys="";//收件箱
		var state="1";//取消标星
		var shuang="";
		$.each(checkStatus.data, function(index, value) {
			var user = value.mailUsers;
			if(value.starStatus==2){
				if(user[0]!=null){
					if(user[0].starState==value.starStatus){
						shuang = shuang+value.mailId+",";
					}else{
						mailIdkeys=mailIdkeys+value.mailId+",";//发件箱
					}
				}else{
					mailIdkeys=mailIdkeys+value.mailId+",";//发件箱
				}
				
				
			}else{
					mailUserIdkeys = mailUserIdkeys + value.mailId + ","//收件箱
				}
			});
		if(mailIdkeys != "" || mailIdkeys!= null) {
			mailIdkeys = mailIdkeys.substring(0, mailIdkeys.length - 1);
		}	
		if(mailUserIdkeys != "" || mailUserIdkeys!= null) {
			mailUserIdkeys = mailUserIdkeys.substring(0, mailUserIdkeys.length - 1);
		}
		
		
		$.ajax({
    			type:"post",
    			url:layui.setter.project + "/mail/starmail",
    			async:true,
				xhrFields: {
					withCredentials: true
				},
			
				//contentType:"application/json;charset=utf-8",
				dataType: "json",
    			data:{"userId":userId,"mailIdkeys":mailIdkeys,"mailUserIdkeys":mailUserIdkeys,"shuang":shuang},
    			success:function(result){
    				console.log(result)
    				if(result.result==200){
    					renderTable();
    				}
    			}
    		});
		
	})

	
	e("starmail", {})
});