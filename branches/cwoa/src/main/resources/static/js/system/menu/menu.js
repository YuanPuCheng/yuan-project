layui.extend({
	inputverify: "../../common/inputverify",
	setter: "../../../../static/layui/config",
	treeTable: "../../../../static/js/treeTable"
}).define(["form", "inputverify", "setter", 'treeTable', 'jquery'], function(e) {
	var o = layui.$,
		$ = layui.jquery,
		form = layui.form,
		layer = layui.layer,
		treeTable = layui.treeTable;

    var widthMax = "70%",
        heightMax = "80%";
    if($(window).width() < 768) {
        widthMax = "100%";
        heightMax = "80%";
    }

	// 直接下载后url: './data/table-tree.json',这个配置可能看不到数据，改为data:[],获取自己的实际链接返回json数组
	var re = treeTable.render({
		elem: '#tree-table',
		url: layui.setter.project+'/menu/getmenu',
		primary_key: 'menuId',
		parent_key: 'parentId',
		icon_key: 'menuName', //展示下级列表
		is_checkbox: true,
		top_value: 0,
		end: function(e) {
			form.render();
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
			},
            {
                key: 'status',
                title: '状态',
                width: '30px',
                align: 'center',
                template: function(item) {
                    if(item.status == 0) {
                        return '<div class="layui-btn layui-btn-normal layui-btn-xs">正常</div>';
                    } else {
                        return '<div class="layui-btn layui-btn-xs layui-btn-danger">禁用</div>';
                    }

                }
            },
			{
				title: '操作',
				align: 'center',
				width: '100px',
				template: function(item) {
					if(item.menuType == 3) {
						return '<button class="layui-btn layui-btn-sm layui-btn-normal" lay-filter="edit">编辑</button><button class="layui-btn layui-btn-sm layui-btn-normal" lay-filter="delete">删除</button>';
					}

					return '<button class="layui-btn layui-btn-sm layui-btn-normal" lay-filter="add" >添加</button><button class="layui-btn layui-btn-sm layui-btn-normal" lay-filter="edit">编辑</button> <button class="layui-btn layui-btn-sm layui-btn-normal" lay-filter="delete">删除</button>';
				}
			}
		]
	});
	//新增根目录
	o('.addgmenu').click(function() {
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
            area: [widthMax,  heightMax],
			shadeClose: false, //开启遮罩关闭
			content: 'addmenu.html',
			success: function(layero, index) {
				var body = layer.getChildFrame('body', index);
				body.find("#parName").val("根目录");
				body.find("#parentId").val("0");
				
				body.find("#menuType").val(1);
				body.find("#type2").attr("disabled", "disabled");
				body.find("#type3").attr("disabled", "disabled");
				

			},
			end: function() {

			},

		});
	});
	//批量删除
	o('.deletes').click(function() {
		var str = treeTable.checked(re).join(',');
		if(str==null||str==""){
			layer.alert("请选择数据");
			return false;
			
		}
	    layer.confirm('确定批量删除？', {icon: 3, title:'提示'}, function(index){
  			$.ajax({
			url: layui.setter.project+"/menu/deletes",
			type: "post",
			xhrFields: {
				withCredentials: true
			},
			data: {
				"menuIds": str
			},
			dataType: "json",
			success: function(data) {
				if(data.result == 200) {
					
					layer.msg(data.message);
					treeTable.render(re);
					console.log(re)
					
				} else {
					layer.msg(data.message);
				}
			}
		});
		
	});
	})
	// 全部展开
	o('.open-all').click(function() {
		treeTable.openAll(re);
	})
	// 全部关闭
	o('.close-all').click(function() {
		treeTable.closeAll(re);
	});
	//监听删除方法
	treeTable.on('tree(delete)', function(data) {
		console.log(data);
		layer.confirm('确定删除？', {icon: 3, title:'提示'}, function(index){
  			$.ajax({
			url: layui.setter.project+"/menu/delete",
			type: "post",
			xhrFields: {
				withCredentials: true
			},
			data: {
				"menuId": data.item.menuId
			},
			dataType: "json",
			success: function(data) {
				if(data.result == 200) {
					
					layer.msg(data.message);
					treeTable.render(re);
					console.log(re)
					
				} else {
					layer.msg(data.message);
				}
			}
		});
  
  			
		});
		
		
	})
	
	// 监听新增方法
	treeTable.on('tree(add)', function(data) {
		//sessionStorage.setItem('menu',JSON.stringify(data));

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
            area: [widthMax,  heightMax],
			shadeClose: false, //开启遮罩关闭
			content: layui.setter.project+'/sys/addmenu',
			success: function(layero, index) {
				var body = layer.getChildFrame('body', index);
				body.find("#parName").val(data.item.menuName);
				body.find("#parentId").val(data.item.menuId);
				if(data.item.menuType == 1) {
					body.find("#menuType").val(2);
					body.find("#type1").attr("disabled", "disabled");
					body.find("#type3").attr("disabled", "disabled");
				} else if(data.item.menuType == 2) {
					body.find("#menuType").val(3);
					body.find("#type1").attr("disabled", "disabled");
					body.find("#type2").attr("disabled", "disabled");
				} else if(data.item.menuType == 3) {
					body.find("#type1").attr("disabled", "disabled");
					body.find("#type2").attr("disabled", "disabled");
				}

			},
			end: function() {

			},

		});

		//layer.msg(JSON.stringify(data));
	});
	//监听修改方法
	treeTable.on('tree(edit)', function(data) {
		layer.open({ //弹框
			//id: 'insert-form',
			type: 2,
			//skin: 'layui-layer-demo', //样式类名
			title: '修改菜单',
			maxmin: true,
			btn: [],
			yes: function(index, layero) {

			},
			closeBtn: 1, //不显示关闭按钮
			anim: 2,
            area: [widthMax,  heightMax],
			shadeClose: false, //开启遮罩关闭
			content: layui.setter.project+'/sys/editmenu',
			success: function(layero, index) {
				var body = layer.getChildFrame('body', index);
				body.find("#menuName").val(data.item.menuName);
				body.find("#menuCode").val(data.item.menuCode);
				body.find("#menuUrl").val(data.item.menuUrl);
				body.find("input[name=status][value="+data.item.status+"]").attr("checked","checked");
				console.log(data.item.status)
				body.find("#parentId").val(data.item.menuId);
				if(data.item.menuType == 1) {
					body.find("#menuType").val(1);
					body.find("#type2").attr("disabled", "disabled");
					body.find("#type3").attr("disabled", "disabled");
				} else if(data.item.menuType == 2) {
					body.find("#menuType").val(2);
					body.find("#type1").attr("disabled", "disabled");
					body.find("#type3").attr("disabled", "disabled");
				} else if(data.item.menuType == 3) {
					body.find("#menuType").val(3);
					body.find("#type1").attr("disabled", "disabled");
					body.find("#type2").attr("disabled", "disabled");
				}

			},
			end: function() {

			},

		});
	});

	e("menu", {})
})