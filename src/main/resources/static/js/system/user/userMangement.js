layui.extend({
	setter: "../../../../static/layui/config"

}).define(["setter", "jquery"], function(e) {
	var $ = layui.jquery;

	layui.use(['form', 'layer', 'table'], function() {
		var table = layui.table,
			layer = layui.layer,
			form = layui.form,
			$ = layui.jquery;

		table.render({
			elem: '#test',
			url: layui.setter.project + '/user/getuserPage',
			toolbar: "#toolbarDemo",
			method: 'post',
			cellMinWidth: 80, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
			cols: [
				[{
						type: 'checkbox'
					},
					{
						field: 'userCode',
						title: '用户工号',
						sort: true
					},
					{
						field: 'userName',
						title: '用户名称'
					},
					{
						field: 'sex',
						title: '性别'
					},
					{
						field: 'email',
						title: '邮箱'
					},
					{
						field: 'lastTime',
						title: '上次登录时间'
					},
					{
						field: 'phone',
						title: '电话'
					},
					{
						field: 'ts',
						title: '登陆时间',
						sort: true
					},
					{
						field: 'ip',
						title: 'IP'
					},
					{
						field: 'name',
						title: '部门',
						templet: function(d) {
							console.log(d)
							var a = eval(d.departments);
							var name = "";
							if(a != null) {
								$.each(a, function(index, value) {
									name = value.departmentName + "、" + name;
								});
							} else {
								name = ""
							}

							//var ddd=a.departments[0].departmentCode
							name = name.substring(0, name.length - 1);
							return name;
						}
					},
					{
						field: 'projects',
						title: '所在项目',
						templet: function(d) {
							var a = d.project;
							var name1 = "";
							if(a != null) {
								name1 = d.project.projectName;
							}
							return name1;
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
		//搜索(方法重载)
		function renderTable() {
			var name2 = $('#SearchDepartments').val();
			var name1 = names(name2);
			var sex1 = names($('#SearchSex').val());
			var project1 = names($('#SearchProjects').val());

			function names(obj) {
				var n = "";
				if(obj == "不限") {
					return n;
				}
				return obj;
			}
			//				console.log(name1+sex1);
			table.reload('test', {
				url: layui.setter.project + '/user/getuserPage',
				where: {
					userCode: $('#SearchUserCode').val(),
					userName: $('#SearchUserName').val(),
					tempVar3: name1,
					sex: sex1,
					tempInt1: project1
				}
			});
		}

		$('#Search').on('click', function() {
			renderTable();
		});

		var widthMax = 750,
			heightMax = 600;
		if($(window).width() < 768) {
			widthMax = 280;
			heightMax = 350
		}
		//头工具栏事件
		table.on('toolbar(test)', function(obj) {
			var checkStatus = table.checkStatus(obj.config.id);
			switch(obj.event) {
				case 'add':
					//用户添加
					var widthMax = 750,
						heightMax = 600;
					if($(window).width() < 768) {
						widthMax = 280;
						heightMax = 350
					}

					layer.open({
						type: 2,
						//skin: 'layui-layer-demo', //样式类名
						title: '用户添加',
						closeBtn: 1, //不显示关闭按钮
						anim: 2,
						fixed: false,
						maxmin: true,

						success: function() {},
						btn: [],
						area: [widthMax + 'px', heightMax + 'px'],
						shadeClose: false, //开启遮罩关闭
						content: layui.setter.project +'/sys/addMangement'
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
					name = name + value.userName + "、";
					id = id + value.userId + ","
				});
				if(name != "" || name != null) {
					name = name.substring(0, name.length - 1);
					id = id.substring(0, id.length - 1);
				}
				layer.confirm('已选择' + name + "，是否删除？", function(index) {
					$.ajax({
						url: layui.setter.project + "/user/deletes",
						type: "post",
						xhrFields: {
							withCredentials: true
						},
						data: {
							"userIds": id
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
						url: layui.setter.project + "/user/del",
						type: "post",
						xhrFields: {
							withCredentials: true
						},
						data: {
							"userId": data.userId
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
					title: '修改用户',
					maxmin: true,
					btn: [],
					yes: function(index, layero) {

					},
					closeBtn: 1, //不显示关闭按钮
					anim: 2,
					area: [widthMax + "px", heightMax + "px"],
					shadeClose: false, //开启遮罩关闭
					content: layui.setter.project +'/sys/reviseManagement',
					success: function(layero, index) {
						var body = layer.getChildFrame('body', index);
						body.find("#usercode").val(data.userCode);
						body.find("#username").val(data.userName);
						body.find("#email").val(data.email);
						body.find("#phone").val(data.phone);
						var id = "";
						if(data.departments != null) {
							$.each(data.departments, function(index, value) {
								id = id + value.departmentId + ",";
							});
							id = id.substring(0, id.length - 1);

						}
						console.log(id)
						body.find("#departmentId").val(id);

						if(data.project != null) {
							body.find("#projectId").val(data.project.projectId);
						}
						body.find("#idNum").val(data.idNum);
						body.find("#bankCardNum").val(data.bankCardNum);
						body.find("input[name=status][value=" + data.status + "]").attr("checked", "checked");
						console.log(data.status)
						body.find("#userId").val(data.userId);
					},
					end: function() {

					},

				});
			}
		});

		//部门下拉框加载
		$.get(layui.setter.project + '/department/getdepartment', {}, function(data) {
			var $html = "<option  value=''>全部</option>";
			if(data.data != null) {
				$.each(data.data, function(index, item) {
					if(item.proType) {
						$html += "<option class='generate' value=''>全部</option>";
					} else {
						$html += "<option value='" + item.departmentId + "'>" + item.departmentName + "</option>";
					}
				});
				$("select[name='departments']").append($html);
				//反选
				$("select[name='departments']").val($("#SearchDepartments").val());
				//append后必须从新渲染
				form.render('select');
			}
		});

		//项目下拉框加载
		$.get(layui.setter.project + '/project/getprojecttoselect', {}, function(data) {
			var $html = "<option  value=''>全部</option>";
			if(data != null) {
				$.each(data, function(index, item) {
					if(item.proType) {
						$html += "<option class='generate' value=''>全部</option>";
					} else {
						$html += "<option value='" + item.projectId + "'>" + item.projectName + "</option>";
					}
				});
				$("select[name='projects']").append($html);
				//反选
				$("select[name='projects']").val($("#SearchProjects").val());
				//append后必须从新渲染
				form.render('select');
			}
		});
	});

	e("userMangement", {})
});