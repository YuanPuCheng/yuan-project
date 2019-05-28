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
		
		//$("input[name=status][value=0]").attr("checked", 0 == 0 ? true : false);
		//$("input[name='status']").val(1)
		form.render();
		
		
		
	a.on('click', "#editqx", function(o) {
		parent.layer.close(index);
		//parent.location.reload();
		
	});
	var flag = false;
	form.on('submit(editmenu)', function(obj) {
		if(!flag){
			flag = true;

		var parentId = $("#parentId").val();
		var menuName = $("#menuName").val();
		var menuCode = $("#menuCode").val();
		var menuUrl = $("#menuUrl").val();
		var menuType = $("#menuType").val();
		var status = $("input[name='status']:checked").val();
		console.log(menuType+status)
		$.ajax({
			url: layui.setter.project+"/menu/edit",
			type: "post",
			xhrFields: {
				withCredentials: true
			},
			data: {
				"menuId": parentId,
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
        }
	})


	e("editmenu", {})
})