layui.extend({
	inputverify: "../../common/inputverify",
	setter: "../../../../static/layui/config"

}).define(["form", "inputverify", "setter", "layer", "jquery", "laydate", "table", "upload"], function(e) {
	var form = layui.form, //只有执行了这一步，部分表单元素才会自动修饰成功
		o = layui.$,
		layer = layui.layer,
		index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	$ = layui.jquery,
		a = o("body"),
		laydate = layui.laydate,
		table = layui.table,
		upload = layui.upload;

    var widthMax = "70%",
        heightMax = "80%";
    if($(window).width() < 768) {
        widthMax = "100%";
        heightMax = "80%";
    }

	table.render({
		elem: '#test',
		url: layui.setter.project + '/project/projectfile',
		toolbar: '#toolbarDemo',
        cellMinWidth: 120, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
		where: {
			projectId: $('#projectId').val()

		},
		title: '合同',
		cols: [
			[{
					type: 'checkbox'
				},
				{
					field: 'fileRename',
					title: '文件名称',
					sort: true
				},
				{
					field: 'ts',
					title: '上传日期'
				},
				{
					field: 'name',
					title: '操作',
					event: 'img',
					templet: function(d) {
						var name = "";
						name = "<a>点击查看</a>";
						return name;
					}
				}, {

					field: 'name',
					title: '操作',
					templet: function(d) {
						var name = "";
						name = "<a href='" + layui.setter.project + "/file/down?filename=" + d.fileName + "'>点击下载</a>";
						return name;
					}

				}, {
					//fixed: 'right',
					title: '操作',
					toolbar: '#barDemo'
				}
			]
		],
		page: true
	});

	function down(name) {
		alert(name);
	}

	a.on('click', "#close", function(o) {
		parent.layer.close(index);
		//parent.location.reload();
	})

	table.on('toolbar(dataTable)', function(obj) {
		var checkStatus = table.checkStatus(obj.config.id);
		switch(obj.event) {

			case 'deletes':
				var data = checkStatus.data;
				if(data.length == 0) {
					layer.alert("未选中数据");
					return false;
				}
				var name = "";
				var id = "";
				$.each(data, function(index, value) {
					name = name + value.fileRename + "、";
					id = id + value.fileId + ","
				});
				if(name != "" || name != null) {
					name = name.substring(0, name.length - 1);
					id = id.substring(0, id.length - 1);
				}
				layer.confirm('已选择' + name + "，是否删除？", function(index) {
					$.ajax({
						url: layui.setter.project + "/file/deletes",
						type: "post",
						xhrFields: {
							withCredentials: true
						},
						data: {
							"fileIds": id
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

	$("#add").click(function() {

		layer.open({ //弹框
			//id: 'insert-form',
			type: 2,
			//skin: 'layui-layer-demo', //样式类名
			title: '上传合同',
			maxmin: true,
			btn: [],
			yes: function(index, layero) {

			},
			closeBtn: 1, //不显示关闭按钮
			anim: 2,
            area: [widthMax,  heightMax],
			shadeClose: false, //开启遮罩关闭
			content: layui.setter.project + '/sys/addhetong',
			success: function(layero, index) {

				var body = layer.getChildFrame('body', index);
				body.find("#projectId").val($("#projectId").val());

			},
			end: function() {

			},

		});

	});

	function renderTable() {
		table.reload('test', {
			url: layui.setter.project + '/project/projectfile',
			//toolbar: '#toolbarDemo',
			where: {
				projectId: $('#projectId').val()

			}
		});
	}
	table.on('tool(dataTable)', function(obj) {
		var data = obj.data;

		if(obj.event === 'del') {
			layer.confirm('真的删除行么', function(index) {
				$.ajax({
					url: layui.setter.project + "/file/del",
					type: "post",
					xhrFields: {
						withCredentials: true
					},
					data: {
						"fileId": data.fileId
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

		} else if(obj.event === 'img') {
			console.log(data);
			var type = "";
			if(data.tempVar1 != null) {
				type = data.tempVar1.toLocaleLowerCase()
			}
			console.log(type)
			if(type != "gif" &&
				type != "jpg" &&
				type != "jpeg" &&
				type != "jpe" &&
				type != "png" &&
				type != "bmp") {

				layer.alert("不是图片格式不能浏览");
				return;
			}

			layer.open({ //弹框
				//id: 'insert-form',
				//type: 2,
				//skin: 'layui-layer-demo', //样式类名
				title: '附件信息',
				maxmin: true,
				btn: [],
				yes: function(index, layero) {
				 //layer.close(index);
				},
				closeBtn: 1, //不显示关闭按钮
				anim: 2,
                area: [widthMax,  heightMax],
				shadeClose: false, //开启遮罩关闭
				content: '<img style="width:'+widthMax+';height: '+heightMax+'" src="'+layui.setter.project+'/file/show?filename='+data.fileName+'" />',
				success: function(layero, index) {
				//
					var body = layer.getChildFrame('body', index);

				},
				end: function() {

				},

			});

		}

	})

	e("projecthetong", {})
})