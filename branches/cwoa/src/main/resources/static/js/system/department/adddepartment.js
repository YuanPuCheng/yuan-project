layui.extend({
	inputverify: "../../common/inputverify",
	setter: "../../../../static/layui/config"


}).define(["form", "inputverify", "setter", "layer", "jquery"], function(e) {
	var form = layui.form, //只有执行了这一步，部分表单元素才会自动修饰成功
		o = layui.$,
		layer = layui.layer,
		index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		$ = layui.jquery,
		a = o("body");



	a.on('click', "#addqx", function(o) {

		parent.layer.close(index);
		//parent.location.reload();

	});
	var flag = false;
	form.on('submit(adddepartment)', function(obj) {
		if(!flag){
            flag = true;
		var departmentName = $("#departmentName").val();
		var departmentCode = $("#departmentCode").val();
		var status = $("input[name='status']:checked").val();


		$.ajax({
			url: layui.setter.project + "/department/add",
			type: "post",
			xhrFields: {
				withCredentials: true
			},
			data: {
				"departmentName": departmentName,
				"departmentCode": departmentCode,
				"status": status

			},
			//contentType:"application/json",
			dataType: "json",
			success: function(data) {
				if(data.result == 200) {
					parent.layer.alert(data.message, function() {
						//parent.layer.renderTable();
						console.log(parent);
						//window.parent.renderTable()
						//var parentMethodValue=parent.layer.getMethodValue();
						parent.location.reload();
						//parent.layui.department.renderTable();

						//parent.layer.
						parent.layer.close(index);

					});

				} else {
					parent.layer.alert(data.message);
				}
			}
		});
        }
	})

	e("adddepartment", {})
})