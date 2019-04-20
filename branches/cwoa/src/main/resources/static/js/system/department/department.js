layui.extend({
	setter: "../../../../static/layui/config"

}).define(["setter", "jquery", 'form', 'laypage', 'table'], function(e) {

	var table = layui.table,
		laypage = layui.laypage,
		layer = layui.layer,
		form = layui.form,
		$ = layui.jquery;
	var widthMax = 850,
		heightMax = 500;
	if($(window).width() < 768) {
		widthMax = 280;
		heightMax = 350
	}
	table.render({
		elem: '#test',
		url: layui.setter.project + '/department/getdepartmentPage',
		toolbar: '#toolbarDemo',
		title: '部门',
		cols: [
			[{
					type: 'checkbox'
				},
				{
					field: 'departmentName',
					title: '部门名称',
					sort: true
				},
				{
					field: 'departmentCode',
					title: '部门编码'
				},
				{
					field: 'ts',
					title: '时间'
				},

				{
					field: 'status',
					title: '部门',
					templet: function(d) {
						var name = ""
						if(d.status == 0) {
							name = "正常"
						} else {
							name = "禁用"
						}
						return name;
					}
				}, {
					//fixed: 'right',
					title: '操作',
					toolbar: '#barDemo',
					width: 120
				}
			]
		],
		page: true
	});

	$('#Search').on('click', function() {
		renderTable();
	});

	function renderTable() {
		var derNAme = $('#derNAme').val();
		//				console.log(name1+sex1);
		table.reload('test', {
			url: layui.setter.project + '//department/getdepartmentPage',
			where: {
				departmentName: $('#derNAme').val()

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
					title: '新增菜单',
					maxmin: true,
					btn: [],
					yes: function(index, layero) {

					},
					closeBtn: 1, //不显示关闭按钮
					anim: 2,
					area: [widthMax + "px", heightMax + "px"],
					shadeClose: false, //开启遮罩关闭
					content: 'adddepartment.html',
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
					name = name + value.departmentName + "、";
					id = id + value.departmentId + ","
				});
				if(name != "" || name != null) {
					name = name.substring(0, name.length - 1);
					id = id.substring(0, id.length - 1);
				}
				layer.confirm('已选择' + name + "，是否删除？", function(index) {
					$.ajax({
						url: layui.setter.project + "/department/deletes",
						type: "post",
						xhrFields: {
							withCredentials: true
						},
						data: {
							"departmentIds": id
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
					url: layui.setter.project + "/department/del",
					type: "post",
					xhrFields: {
						withCredentials: true
					},
					data: {
						"departmentId": data.departmentId
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
			title: '修改部门',
			maxmin: true,
			btn: [],
			yes: function(index, layero) {

			},
			closeBtn: 1, //不显示关闭按钮
			anim: 2,
			area: [widthMax + "px", heightMax + "px"],
			shadeClose: false, //开启遮罩关闭
			content: 'editdepartment.html',
			success: function(layero, index) {
				var body = layer.getChildFrame('body', index);
				body.find("#departmentName").val(data.departmentName);
				body.find("#departmentCode").val(data.departmentCode);
				body.find("input[name=status][value="+data.status+"]").attr("checked","checked");
				console.log(data.status)
				body.find("#departmentId").val(data.departmentId);
				

			},
			end: function() {

			},

		});
		}
	});
	e("department", {})
});