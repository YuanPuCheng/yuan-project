layui.extend({
	inputverify: "../../common/inputverify",
	setter: "../../../../static/layui/config"

}).define(["form", "inputverify", "setter", "layer", "jquery","laydate"], function(e) {
	var form = layui.form, //只有执行了这一步，部分表单元素才会自动修饰成功
		o = layui.$,
		layer = layui.layer,
		index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		$ = layui.jquery,
		a = o("body"),
		laydate = layui.laydate;

	
laydate.render({
    elem: '#startTime'
    
  });
  laydate.render({
    elem: '#endTime'
   
  });
	
	a.on('click', "#addqx", function(o) {

		parent.layer.close(index);
		//parent.location.reload();

	})
	form.on('submit(addproject)', function(obj) {

		var projectName = $("#projectName").val();
		var projectIntroduction = $("#projectIntroduction").val();
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		var status = $("input[name='status']:checked").val();
		var projectAddress = $("#projectAddress").val();
		
		console.log(projectName+projectIntroduction+startTime+endTime+projectAddress+status);
		$.ajax({
			url: layui.setter.project + "/project/add",
			type: "post",
			xhrFields: {
				withCredentials: true
			},
			data: {
				"projectName": projectName,
				"projectIntroduction": projectIntroduction,
				"startTime": startTime,
				"endTime": endTime,
				"status": status,
				"projectAddress": projectAddress
			},
			//contentType:"application/json",
			dataType: "json",
			success: function(data) {
				console.log(data)
				if(data.result == 200) {
					parent.layer.alert(data.message, function() {
						console.log(index);
						parent.layer.close(index);
						
						parent.location.reload();

					});

				} else {
					parent.layer.alert(data.message);
				}
			}
		});

	})

	e("addproject", {})
})