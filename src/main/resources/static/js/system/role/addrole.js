

layui.extend({
	setter: "../../../../static/layui/config"

}).define(["form",  "setter", "layer", "jquery"], function(e) {
	var form = layui.form, //只有执行了这一步，部分表单元素才会自动修饰成功
		o = layui.$,
		layer = layui.layer,
		index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		$ = layui.jquery,
		a = o("body");

    //用户下拉框加载
    $.get(layui.setter.project + '/role/roleselect', {}, function(data) {
        var $html = "<option  value=''>直接选择或搜索选择</option>";
        console.log(data)
        if(data != null) {
            $.each(data, function(index, item) {
                $html += "<option value='" + item.roleId + "'>" + item.roleName + "</option>";
            });
            $("select[name='roleParent']").append($html);
            //append后必须从新渲染
            form.render('select');
        }
    });




    a.on('click', "#addqx", function(o) {

		parent.layer.close(index);
		//parent.location.reload();

	})
	
	form.on('submit(add)', function(obj) {
		var roleName = $("#roleName").val();
		var roleLevel = $("#roleLevel").val();
        var roleParent = $("#roleParent").val();
		
		$.ajax({
			url: layui.setter.project + "/role/add",
			type: "post",
			xhrFields: {
				withCredentials: true
			},
			data:{
				"roleName":roleName,
				"roleLevel":roleLevel,
				"roleParentId":roleParent
			},
			//contentType:"application/json;charset=utf-8",
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

	});


	e("addrole", {})
})