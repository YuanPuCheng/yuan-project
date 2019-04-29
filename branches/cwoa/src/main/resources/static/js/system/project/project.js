layui.extend({
	setter: "../../../../static/layui/config"

}).define(["setter", "jquery", 'form', 'laypage', 'table'], function(e) {

	var table = layui.table,
		laypage = layui.laypage,
		layer = layui.layer,
		form = layui.form,
		$ = layui.jquery;
	var widthMax = 1050,
		heightMax = 570;
	if($(window).width() < 768) {
		widthMax = 280;
		heightMax = 350
	}
	table.render({
		elem: '#test',
		url: layui.setter.project + '/project/getproject',
		toolbar: '#toolbarDemo',
		title: '部门',
		cols: [
			[{
					type: 'checkbox'
				},
				{
					field: 'projectName',
					title: '项目名称',
					sort: true
				},
				{
					field: 'projectAddress',
					title: '项目地址'
				}
				,
				{
					field: 'projectIntroduction',
					title: '项目介绍'
				},
				{
					field: 'userName',
					title: '项目负责人',
					sort: true,
					templet: function(d) {
						var u = d.user;
						console.log(u);
						var name = "";
						if(u!=null){
							name = u.userName;
						}
						
						return name;
					}
				},
				{
					field: 'cooperationCorporate',
					title: '合作公司名称',
					sort: true
				}
				,
				{
					field: 'projectMoney',
					title: '合同总金额',
					sort: true
				}
				,
				{
					field: 'legalRepresentative',
					title: '法定代表人',
					sort: true
				},
				{
					field: 'startTime',
					title: '开始时间',
					sort: true
				},
				{
					field: 'endTime',
					title: '结束时间',
					sort: true
				},
				
				{
					field: 'status',
					title: '状态',
					sort: true,
					width:80,
					templet: function(d) {
						var name = ""
						if(d.status == 1) {
							name = "<span style='color:green'>未开始</span>"
						} else if(d.status==2){
							name = "<span style='color:red'>进行中</span>"
						}else if(d.status==3){
							name = "<span style='color:'black'>已结束</span>"
						}
						return name;
					}
				}, {
					//fixed: 'right',
					title: '操作',
					toolbar: '#barDemo',
					width:210,
					
				}
			]
		],
		page: true
	});

	$('#Search').on('click', function() {
		renderTable();
	});

	function renderTable() {
		var projectName = $('#projectName').val();
		var projectAddress = $('#projectAddress').val();
		var status = $('#status').val();
        var userid = $('#users').val();
						console.log(userid);
		table.reload('test', {
			url: layui.setter.project + '/project/getproject',
			where: {
                "projectUserId": userid,
				"projectName": projectName,
				"projectAddress": projectAddress,
				"status": status
				
			}
		});
	}

	//头工具栏事件
	table.on('toolbar(test)', function(obj) {
		var checkStatus = table.checkStatus(obj.config.id);
		switch(obj.event) {
			case 'add':
				layer.open({ //弹框
					id: 'insert-form',
					type: 2,
					//skin: 'layui-layer-demo', //样式类名
					title: '添加项目',
					maxmin: true,
					btn: [],
					yes: function(index, layero) {

					},
					closeBtn: 1, //不显示关闭按钮
					anim: 2,
					area: [widthMax + "px", heightMax + "px"],
					shadeClose: false, //开启遮罩关闭
					content: 'addproject.html',
					success: function(layero, index) {

					},
					end: function() {

					},

				});

				break;
			case 'deletes':
				var data = checkStatus.data;
				if(data.length == 0) {
					layer.alert("未选中数据");
					return false;
				}
				var name = "";
				var id = "";
				$.each(data, function(index, value) {
					name = name + value.projectName + "、";
					id = id + value.projectId + ","
				});
				if(name != "" || name != null) {
					name = name.substring(0, name.length - 1);
					id = id.substring(0, id.length - 1);
				}
				layer.confirm('已选择' + name + "，是否删除？", function(index) {
					$.ajax({
						url: layui.setter.project + "/project/deletes",
						type: "post",
						xhrFields: {
							withCredentials: true
						},
						data: {
							"projectIds": id
						},
						//contentType:"application/json",
						dataType: "json",
						success: function(data) {
							if(data.result == 200) {
								renderTable();
								layer.close(index);
							}
						}
					});

				})
				break;

		};
	});

    //项目下拉框加载
    $.get(layui.setter.project + '/user/getprouser', {}, function(data) {
        var $html = "<option  value=''  class='generate'>全部</option>";
        if(data != null) {
            $.each(data, function(index, item) {
                if(item.proType) {
                    $html += "<option value=''  class='generate'>全部</option>";
                } else {
                    $html += "<option value='" + item.userId + "'>" + item.userName + "</option>";
                }
            });
            $("select[name='users']").append($html);
            //反选
            $("select[name='users']").val($("#SearchProjects").val());
            //append后必须从新渲染
            form.render('select');
        }
    })

	//监听行工具事件
	table.on('tool(test)', function(obj) {
		var data = obj.data;
		console.log(data);
		if(obj.event === 'del') {
			layer.confirm('真的删除行么', function(index) {
				$.ajax({
					url: layui.setter.project + "/project/del",
					type: "post",
					xhrFields: {
						withCredentials: true
					},
					data: {
						"projectId": data.projectId
					},
					//contentType:"application/json",
					dataType: "json",
					success: function(data) {
						if(data.result == 200) {
							renderTable();
							layer.close(index);
						}
					}
				});

			});
		} else if(obj.event === 'edit') {
			console.log(data)
			layer.open({ //弹框
			//id: 'insert-form',
			type: 2,
			//skin: 'layui-layer-demo', //样式类名
			title: '修改项目',
			maxmin: true,
			btn: [],
			yes: function(index, layero) {

			},
			closeBtn: 1, //不显示关闭按钮
			anim: 2,
			area: [widthMax + "px", heightMax + "px"],
			shadeClose: false, //开启遮罩关闭
			content: layui.setter.project + '/sys/editproject',
			success: function(layero, index) {
				var body = layer.getChildFrame('body', index);
				body.find("#projectName").val(data.projectName);
				body.find("#projectIntroduction").val(data.projectIntroduction);
				body.find("#projectAddress").val(data.projectAddress);
				body.find("#cooperationCorporate").val(data.cooperationCorporate);
				body.find("#projectMoney").val(data.projectMoney);
				body.find("#legalRepresentative").val(data.legalRepresentative);
				body.find("#startTime").val(data.startTime);
				body.find("#endTime").val(data.endTime);
				body.find("input[name=status][value="+data.status+"]").attr("checked","checked");
				if(data.user!=null){
					console.log(data.user.userId)
					body.find("#userId").val(data.user.userId);
				}
				
				body.find("#schedules").val(JSON.stringify(data.schedules));
				body.find("#projectId").val(data.projectId);
				

			},
			end: function() {

			},

		});
		}else if(obj.event === 'hetong') {
			var widthMax1 = 80,
			heightMax1 = 80;
			/*if($(window).width() < 768) {
				widthMax = 280;
				heightMax = 350
			}*/
			
			layer.open({ //弹框
			//id: 'insert-form',
			type: 2,
			//skin: 'layui-layer-demo', //样式类名
			
			title: '合同信息',
			maxmin: true,
			btn: [],
			yes: function(index, layero) {

			},
			closeBtn: 1, //不显示关闭按钮
			anim: 2,
			area: [widthMax1 + "%", heightMax1 + "%"],
			shadeClose: false, //开启遮罩关闭
			content: layui.setter.project + '/sys/projecthetong',
			success: function(layero, index) {
				var body = layer.getChildFrame('body', index);
				body.find("#projectId").val(data.projectId);
			},
			end: function() {

			},

		});
			
		}else if(obj.event === 'xiangxi'){
			layer.open({ //弹框
			//id: 'insert-form',
			type: 2,
			//skin: 'layui-layer-demo', //样式类名
			title: '详细信息',
			maxmin: true,
			btn: [],
			yes: function(index, layero) {

			},
			closeBtn: 1, //不显示关闭按钮
			anim: 2,
			area: [widthMax + "px", heightMax + "px"],
			shadeClose: false, //开启遮罩关闭
			content: layui.setter.project + '/sys/detailedproject',
			success: function(layero, index) {
				var body = layer.getChildFrame('body', index);
				body.find("#projectName").val(data.projectName);
				body.find("#projectIntroduction").val(data.projectIntroduction);
				body.find("#projectAddress").val(data.projectAddress);
				body.find("#cooperationCorporate").val(data.cooperationCorporate);
				body.find("#projectMoney").val(data.projectMoney);
				body.find("#legalRepresentative").val(data.legalRepresentative);
				body.find("#startTime").val(data.startTime);
				body.find("#endTime").val(data.endTime);
				body.find("input[name=status][value="+data.status+"]").attr("checked","checked");
				if(data.user!=null){
					console.log(data.user.userId)
					body.find("#userId").val(data.user.userName);
					//body.find("#userId").val(data.user.userId);
				}
				
				body.find("#schedules").val(JSON.stringify(data.schedules));
				body.find("#projectId").val(data.projectId);
				

			},
			end: function() {

			},

		});
			
		}
	});
	e("project", {})
});