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
		url: layui.setter.project + "/mail/getDrafts",
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
					title: '时间',
					width:160,
					templet: function(d) {
						console.log(d);
						var newDate = new Date(d.sendTime);
						var datas= newDate.format('yyyy-MM-dd hh:mm');
						return datas;
					},					
					sort: true
				}
				
			]
		],
		done: function(res, curr, count){
		 $('tr').css({'background-color': '#FFFFFF'});
		},
		page: true
	});
	table.on('tool(test)', function(obj){
		var result = obj.data;
    	if(obj.event === 'zcontent'){
    		
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
					content: layui.setter.project + "/rw/draftsmail",
					success: function(layero, index) {
					
    				var data = "";	//详情数据
					var cuserIds="";//抄送人Id
    				var suserIds="";//收件人Id
    				var muserIds="";//密送人id
    				var starStateMailflag =1;
    				$.each(result.mailUsers, function(index, value) {
    					if(value.mailUser==userId){
    						if(value.starState==2){
    							starStateMailflag=2;
    						}
    					}
    					if(value.status==1){
    						suserIds = suserIds+value.userss.userId + ",";
    					}else if(value.status==2){
    						cuserIds = cuserIds+value.userss.userId+ ",";
    					}else if(value.status==3){
    						muserIds = muserIds+value.userss.userId+ ",";
    					}
					});
    				if(suserIds!=""){
    					suserIds = suserIds.substring(0, suserIds.length - 1);
    				}
    			
    				if(cuserIds!=""){
    					cuserIds = cuserIds.substring(0, cuserIds.length - 1);
    				}
    				if(muserIds!=""){
    					muserIds = muserIds.substring(0, muserIds.length - 1);
    				}
						var body = layer.getChildFrame('body', index);
						body.find("#mailId").val(result.mailId);
						body.find("#state").val(2);
						body.find("#contents").val(result.mailContent);
						body.find("#suserIds").val(suserIds);
						body.find("#cuserIds").val(cuserIds);
						body.find("#muserIds").val(muserIds);
						body.find("#zhuti").val(result.mailTheme);
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
			url: layui.setter.project + "/mail/getDrafts",
			where:{userId: userId,content:content}
		});
	};

	$("#qxin").click(function(){
		var checkStatus = table.checkStatus('test');
		
		if(checkStatus.data.length == 0) {
			layer.alert("未选中数据");
			return false;
		}
		var mailIds="";//
		$.each(checkStatus.data, function(index, value) {
				mailIds = mailIds+value.mailId+",";
			});
		
		$.ajax({
    			type:"post",
    			url:layui.setter.project + "/mail/delMail",
    			async:true,
				xhrFields: {
					withCredentials: true
				},
			
				//contentType:"application/json;charset=utf-8",
				dataType: "json",
    			data:{"mailIds":mailIds},
    			success:function(result){
    				if(result.result==200){
    					renderTable();
    				}
    			}
    		});
		
	})

	
	e("drafts", {})
});