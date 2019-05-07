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
		url: layui.setter.project + '/task/gettaskPage',
		toolbar: '#toolbarDemo',
		title: '任务',
		cols: [
			[{
					type: 'checkbox'
				}
			,
				
				{
					field: 'userName',
					title: '制单人',
					templet: function(d) {
						var u = d.users;
						console.log(d);
						var name = "";
						if(u!=null){
							name = u.userName;
						}
						return name;
					},					
					sort: true
				}
				,
				{
					field: 'ts',
					title: '发布时间'
				},
				{
					field: 'taskRemark',
					title: '描述'
				},
				{
					field: 'taskStatus',
					title: '状态',
					templet: function(d) {
						
						var name = "";
						if(d.taskStatus==1){
							name = "<span style='color:green'>制单</span>"
						}else if(d.taskStatus==2){
							name = "<span style='color:red'>已指派</span>"
						}else if(d.taskStatus==3){
                            name = "<span style='color:black'>已全部完成</span>"
                        }
						return name;
					}
				}
				,{
					//fixed: 'right',
					title: '操作',
					toolbar: '#barDemo',
					width:210,
					
				}
			]
		],
		done: function(res, curr, count){
    		console.log(res);
    		console.log(curr); 
    		console.log(count);
  	},
		page: true
	});

	$('#Search').on('click', function() {
		renderTable();
	});

	function renderTable() {
		table.reload('test', {
			url: layui.setter.project + '/task/gettaskPage'
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
					title: '指派任务',
					maxmin: true,
					btn: [],
					yes: function(index, layero) {

					},
					closeBtn: 1, //不显示关闭按钮
					anim: 2,
					area: [widthMax + "px", heightMax + "px"],
					shadeClose: false, //开启遮罩关闭
					content: layui.setter.project +'/sys/addtask',
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
				var id = "";
				$.each(data, function(index, value) {
					id = id + value.taskId + ","
				});
				if(name != "" || name != null) {
					id = id.substring(0, id.length - 1);
				}
				layer.confirm('已选择' + data.length + "数据，是否删除？", function(index) {
					$.ajax({
						url: layui.setter.project + "/task/deletes",
						type: "post",
						xhrFields: {
							withCredentials: true
						},
						data: {
							"taskIds": id
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

	//监听行工具事件
	table.on('tool(test)', function(obj) {
		var data = obj.data;
		console.log(data);
		if(obj.event === 'del') {
			layer.confirm('真的删除行么', function(index) {
				$.ajax({
					url: layui.setter.project + "/task/del",
					type: "post",
					xhrFields: {
						withCredentials: true
					},
					data: {
						"taskId": data.taskId
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
			layer.open({ //弹框
			//id: 'insert-form',
			type: 2,
			//skin: 'layui-layer-demo', //样式类名
			title: '查看任务',
			maxmin: true,
			btn: [],
			yes: function(index, layero) {

			},
			closeBtn: 1, //不显示关闭按钮
			anim: 2,
			area: [widthMax + "px", heightMax + "px"],
			shadeClose: false, //开启遮罩关闭
			content:  layui.setter.project +'/sys/edittask',
			success: function(layero, index) {
				var body = layer.getChildFrame('body', index);
				body.find("#taskb").val(JSON.stringify(data.taskBs));
				body.find("#taskId").val(data.taskId);
				body.find("#taskRemark").val(data.taskRemark);
				body.find("#userName").val(data.users.userName);
				if(data.file!=null){
					body.find("#filename").val(data.file.fileName);
					body.find("#filerename").val(data.file.fileRename);
				}
				
				
				

			},
			end: function() {

			},

		});
		}else if(obj.event === 'ti'){
			layer.confirm('确定指派任务吗？', function(index) {
				$.ajax({
					url: layui.setter.project + "/task/updatestatus",
					type: "post",
					xhrFields: {
						withCredentials: true
					},
					data: {
						"taskId": data.taskId
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
			
			
		}
	});
	e("task", {})
});