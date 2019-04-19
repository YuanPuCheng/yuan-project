layui.extend({
	inputverify: "../../common/inputverify",
	setter: "../../../../static/layui/config"

}).define(["form", "inputverify", "setter", "layer", "jquery"], function(e) {
	var form = layui.form, //只有执行了这一步，部分表单元素才会自动修饰成功
		s = layui.$,
		layer = layui.layer,
		index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		$ = layui.jquery,
		a=s("body");
	
	a.on('click', "#addqx", function(o) {
		
		parent.layer.close(index);
		//parent.location.reload();
		
	})
	form.on('submit(addmenu)', function(obj) {
		
		var parentId = $("#parentId").val();
		var menuName = $("#menuName").val();
		var menuCode = $("#menuCode").val();
		var menuUrl = $("#menuUrl").val();
		var menuType = $("#menuType").val();
		var status = $("input[name='status']").val();
		console.log(menuType+status)
		$.ajax({
			url: layui.setter.project+"/menu/add",
			type: "post",
			xhrFields: {
				withCredentials: true
			},
			data: {
				"parentId": parentId,
				"menuName": menuName,
				"menuCode": menuCode,
				"menuUrl": menuUrl,
				"menuType": menuType,
				"status": status,
			},
			//contentType:"application/json",
			dataType: "json",
			success: function(data) {
				if(data.result == 200) {
					parent.layer.alert(data.message,function(){
						parent.location.reload();
						parent.layer.close(index);
						
					});
					
				} else {
					parent.layer.alert(data.message);
				}
			}
		});

	})

	var active = {
		//让层自适应iframe

		//给父页面传值
		setParent: function() {
				var a = $("#menuName").val();

				//parent.layer.msg('您将标记 [ ' + val + ' ] 成功传送给了父窗口');
				parent.layui.$("#parent").text(a);
				parent.layer.close(index);
			}

			//在内部关闭iframe
	}

	e("addmenu", {})
})