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
		url: layui.setter.project + '/role/getrolePage',
		toolbar: '#toolbarDemo',
		title: '角色',
		cols: [
			[{
					type: 'checkbox'
				}
			,
				{
					field: 'roleId',
					title: 'id',
					sort: true
				},
				{
					field: 'roleName',
					title: '角色名称',
					sort: true
				},
				{
					field: 'roleLevel',
					title: '级别'
				}
				,{
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
		table.reload('test', {
			url: layui.setter.project + '/role/getrolePage'
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
					content: layui.setter.project +'/sys/addrole',
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
					name = name + value.roleName + "、";
					id = id + value.roleId + ","
				});
				if(name != "" || name != null) {
					name = name.substring(0, name.length - 1);
					id = id.substring(0, id.length - 1);
				}
				layer.confirm('已选择' + name + "，是否删除？", function(index) {
					$.ajax({
						url: layui.setter.project + "/role/deletes",
						type: "post",
						xhrFields: {
							withCredentials: true
						},
						data: {
							"roleIds": id
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
					url: layui.setter.project + "/role/del",
					type: "post",
					xhrFields: {
						withCredentials: true
					},
					data: {
						"roleId": data.roleId
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
			title: '修改角色',
			maxmin: true,
			btn: [],
			yes: function(index, layero) {

			},
			closeBtn: 1, //不显示关闭按钮
			anim: 2,
			area: [widthMax + "px", heightMax + "px"],
			shadeClose: false, //开启遮罩关闭
			content:  layui.setter.project +'sys/editrole',
			success: function(layero, index) {
				var body = layer.getChildFrame('body', index);
				body.find("#roleName").val(data.roleName);
				body.find("#roleLevel").val(data.roleLevel);
				body.find("#roleId").val(data.roleId);
				

			},
			end: function() {

			},

		});
		}
	});
	e("role", {})
});