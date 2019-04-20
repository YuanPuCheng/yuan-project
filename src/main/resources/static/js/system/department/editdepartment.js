layui.extend({
	inputverify: "../../common/inputverify",
	setter: "../../../../static/layui/config",
	treeTable: "../../../../static/js/treeTable"

}).define(["form", "inputverify",  "layer", "jquery", "treeTable","setter"], function(e) {
	var form = layui.form, //只有执行了这一步，部分表单元素才会自动修饰成功
		o = layui.$,
		layer = layui.layer,
		index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		$ = layui.jquery,
		a = o("body"),
		treeTable = layui.treeTable;
		var list =[];
		var oldmenuIds="";
		var re;
		
		
			var departmentId = $("#departmentId").val();
			$.ajax({
				type:"get",
				url:layui.setter.project+"/department/menubydepar",
				data:{"departmentId":departmentId},
				//async:false,
				success: function(data) {
				list=data.list;
				$.each(data.list, function(index,value) {
					oldmenuIds = oldmenuIds+value+","
				});
				if(data.list.length!=0){
					oldmenuIds = oldmenuIds.substring(0,oldmenuIds.length-1);
				}
				re=	treeTable.render({

		elem: '#tree-table1',
		url: layui.setter.project+'/menu/getmenu',
		primary_key: 'menuId',
		parent_key: 'parentId',
		icon_key: 'menuName', //展示下级列表
		is_checkbox: true,
		top_value: 0,
		end: function(e) {
			form.render();
		},
		checked: {
			key: 'menuId',
			data: list,
		},
		cols: [{
				key: 'menuName',
				title: '名称',
				width: '80px',
				template: function(item) {
					if(item.level == 0) {
						return '<span style="color:black;">' + item.menuName + '</span>';
					} else if(item.level == 1) {
						return '<span style="color:#666;">' + item.menuName + '</span>';
					} else if(item.level == 2) {
						return '<span style="color:#aaa;">' + item.menuName + '</span>';
					}
				}
			},
			{
				key: 'menuId',
				title: 'ID',
				width: '30px',
				align: 'center',
			},
			{
				key: 'parentId',
				title: '父ID',
				width: '30px',
				align: 'center',
			},
			{
				key: 'status',
				title: '状态',
				width: '30px',
				align: 'center',
				template: function(item) {
					if(item.status == 0) {
						return '正常';
					} else {
						return '失效';
					}

				}
			},
			{
				key: 'menuCode',
				title: '权限编码',
				width: '30px',
				align: 'center',
			},
			{
				key: 'menuType',
				title: '菜单类型',
				width: '60px',
				align: 'center',
				template: function(item) {
					if(item.menuType == 1) {
						return '功能菜单';
					} else if(item.menuType == 2) {
						return '虚拟节点';
					} else if(item.menuType == 3) {
						return '操作权限';
					}

				}
			}

		]
	});
				console.log(list);
				treeTable.render(re);
				
			}
			});
		
		
		
	/*var re = treeTable.render({

		elem: '#tree-table1',
		url: layui.setter.project+'/menu/getmenu',
		primary_key: 'menuId',
		parent_key: 'parentId',
		icon_key: 'menuName', //展示下级列表
		is_checkbox: true,
		top_value: 0,
		end: function(e) {
			form.render();
		},
		checked: {
			key: 'menuId',
			data: [list],
		},
		cols: [{
				key: 'menuName',
				title: '名称',
				width: '80px',
				template: function(item) {
					if(item.level == 0) {
						return '<span style="color:black;">' + item.menuName + '</span>';
					} else if(item.level == 1) {
						return '<span style="color:#666;">' + item.menuName + '</span>';
					} else if(item.level == 2) {
						return '<span style="color:#aaa;">' + item.menuName + '</span>';
					}
				}
			},
			{
				key: 'menuId',
				title: 'ID',
				width: '30px',
				align: 'center',
			},
			{
				key: 'parentId',
				title: '父ID',
				width: '30px',
				align: 'center',
			},
			{
				key: 'status',
				title: '状态',
				width: '30px',
				align: 'center',
				template: function(item) {
					if(item.status == 0) {
						return '正常';
					} else {
						return '失效';
					}

				}
			},
			{
				key: 'menuCode',
				title: '权限编码',
				width: '30px',
				align: 'center',
			},
			{
				key: 'menuType',
				title: '菜单类型',
				width: '60px',
				align: 'center',
				template: function(item) {
					if(item.menuType == 1) {
						return '功能菜单';
					} else if(item.menuType == 2) {
						return '虚拟节点';
					} else if(item.menuType == 3) {
						return '操作权限';
					}

				}
			}

		]
	});*/

	o('.layui-select-title').click(function() {
		o(this).parent().hasClass('layui-form-selected') ? o(this).next().hide() : o(this).next().show(), o(this).parent().toggleClass('layui-form-selected');
	})
	o(document).on("click", function(i) {
		!o(i.target).parent().hasClass('layui-select-title') && !o(i.target).parents('table').length && !(!o(i.target).parents('table').length && o(i.target).hasClass('layui-icon')) && o(".layui-form-select").removeClass("layui-form-selected").find('.layui-anim').hide();
	})
	a.on('click', "#addqx", function(o) {

		parent.layer.close(index);
		//parent.location.reload();

	})
	form.on('submit(editdepartment)', function(obj) {
		var departmentId = $("#departmentId").val();
		var departmentName = $("#departmentName").val();
		var departmentCode = $("#departmentCode").val();
		var status = $("input[name='status']").val();
		var menuId = treeTable.checked(re).join(',');
		console.log(departmentId)
		console.log(menuId)
		console.log(oldmenuIds)
		$.ajax({
			url: layui.setter.project + "/department/edit",
			type: "post",
			xhrFields: {
				withCredentials: true
			},
			data: {
				"departmentId": departmentId,
				"departmentName": departmentName,
				"departmentCode": departmentCode,
				"status": status,
				"menuIds": menuId,
				"oldmenuIds": oldmenuIds
			},
			//contentType:"application/json",
			dataType: "json",
			success: function(data) {
				if(data.result == 200) {
					parent.layer.alert(data.message, function() {
						
						parent.location.reload();
						//parent.layer.
						parent.layer.close(index);

					});

				} else {
					parent.layer.alert(data.message);
				}
			}
		});

	})

	e("editdepartment", {})
})