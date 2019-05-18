layui.extend({
	inputverify: "../../common/inputverify",
	setter: "../../../../static/layui/config"

}).define(["form", "inputverify",  "layer", "jquery","setter"], function(e) {
	var form = layui.form, //只有执行了这一步，部分表单元素才会自动修饰成功
		o = layui.$,
		layer = layui.layer,
		index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		$ = layui.jquery,
		a = o("body");


		

	a.on('click', "#addqx", function(o) {

		parent.layer.close(index);
		//parent.location.reload();

	})
	form.on('submit(editdepartment)', function(obj) {
		var departmentId = $("#departmentId").val();
		var departmentName = $("#departmentName").val();
		var departmentCode = $("#departmentCode").val();
		var status = $("input[name='status']:checked").val();

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
				"status": status

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