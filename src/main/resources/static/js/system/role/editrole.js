

layui.extend({
	setter: "../../../../static/layui/config"

}).define(["form",  "setter", "layer", "jquery"], function(e) {
	var form = layui.form, //只有执行了这一步，部分表单元素才会自动修饰成功
		o = layui.$,
		layer = layui.layer,
		index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		$ = layui.jquery,
		a = o("body");




    var role = $("#roleParentId").val();
    setTimeout(function(){
        $("#roleParent").val(role);
        form.render('select');
    },100);




	a.on('click', "#addqx", function(o) {

		parent.layer.close(index);
		//parent.location.reload();

	})
	
	form.on('submit(editrole)', function(obj) {
		var roleId = $("#roleId").val();
		var roleParent =$("#roleParent").val();
		var roleName = $("#roleName").val();
		var roleLevel = $("#roleLevel").val();
		
		$.ajax({
			url: layui.setter.project + "/role/edit",
			type: "post",
			xhrFields: {
				withCredentials: true
			},
			data:{
				"roleId":roleId,
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
    var roleId = $("#roleId").val();
//上级角色下拉框加载
    $.get(layui.setter.project + '/role/roleselect', {"roleId":roleId}, function(data) {
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
		

	e("editrole", {})
})