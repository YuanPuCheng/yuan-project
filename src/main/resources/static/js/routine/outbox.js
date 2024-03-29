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
		url: layui.setter.project + "/outbox/getoutbox",
		title: '任务',
		where: {userId: userId},
		skin: "line",
        cellMinWidth: 120,
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
						if(d.starStatus==1){
							name='<i class="layui-icon layui-icon-rate"></i>';
						}else if(d.starStatus==2){
							name='<i class="layui-icon layui-icon-rate-solid"></i>';
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
    		if(state==1){
    			state=2;
    		}else if(state == 2){
    			state=1;
    		}
    		//console.log(JSON.stringify(data))
    		$.ajax({
    			type:"post",
    			url:layui.setter.project + "/outbox/starmail",
    			async:true,
				xhrFields: {
					withCredentials: true
				},
			
				//contentType:"application/json;charset=utf-8",
				dataType: "json",
    			data:{"mailIds":data.mailId,"starStatus":state},
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
					content: layui.setter.project + "/rw/outboxinfo",
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
			url: layui.setter.project + "/outbox/getoutbox",
			where:{userId: userId,content:content}
		});
	};
	

	$("#xin").click(function(){
		var checkStatus = table.checkStatus('test');
		
		if(checkStatus.data.length == 0) {
			layer.alert("未选中数据");
			return false;
		}
		var id="";
		var state="2";//标星
		$.each(checkStatus.data, function(index, value) {
					id = id + value.mailId + ","
			});
		if(id != "" || id != null) {
			id = id.substring(0, id.length - 1);
		}			
		console.log(id);
		
		$.ajax({
    			type:"post",
    			url:layui.setter.project + "/outbox/starmail",
    			async:true,
				xhrFields: {
					withCredentials: true
				},
			
				//contentType:"application/json;charset=utf-8",
				dataType: "json",
    			data:{"mailIds":id,"starStatus":state},
    			success:function(result){
    				console.log(result)
    				if(result.result==200){
    					renderTable();
    				}
    			}
    		});
		
	});
	//标星
	$("#qxin").click(function(){
		var checkStatus = table.checkStatus('test');
		
		if(checkStatus.data.length == 0) {
			layer.alert("未选中数据");
			return false;
		}
		var id="";
		var state="1";//取消标星
		$.each(checkStatus.data, function(index, value) {
					id = id + value.mailId + ","
			});
		if(id != "" || id != null) {
			id = id.substring(0, id.length - 1);
		}			
		console.log(id);
		
		$.ajax({
    			type:"post",
    			url:layui.setter.project + "/outbox/starmail",
    			async:true,
				xhrFields: {
					withCredentials: true
				},
			
				//contentType:"application/json;charset=utf-8",
				dataType: "json",
    			data:{"mailIds":id,"starStatus":state},
    			success:function(result){
    				console.log(result)
    				if(result.result==200){
    					renderTable();
    				}
    			}
    		});
		
	})

	
	e("outbox", {})
});