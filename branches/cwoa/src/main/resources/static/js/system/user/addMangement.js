layui.extend({
	setter: "../../../../static/layui/config",
	inputverify: "../../common/inputverify",

}).define(["setter","inputverify", "jquery"], function(e) {
	
	var $ = layui.jquery,
	    form=layui.form;
	layui.use(['form'],function (){
		
		
		 //部门下拉框加载
		 $.get(layui.setter.project+'/department/getdepartment', {}, function (data) {
               var $html = "<option class='generate'>请选择</option>";
               if(data.data != null){
                   $.each(data.data, function (index, item) {
                       if (item.proType){
                           $html += "<option class='generate'>请选择</option>";
                       }else{
                           $html += "<option value='" + item.departmentId + "'>" + item.departmentName + "</option>";
                       }
                   });
                $("select[name='departments']").append($html);
                //反选
                $("select[name='departments']").val($("#SearchDepartments").val());
                //append后必须从新渲染
                form.render('select');
            }
		});
		 
		//项目下拉框加载
		 $.get(layui.setter.project+'/project/getproject', {}, function (data) {
               var $html = "<option class='generate'>请选择</option>";
               if(data.data != null){
                   $.each(data.data, function (index, item) {
                       if (item.proType){
                           $html += "<option class='generate'>请选择</option>";
                       }else{
                           $html += "<option value='" + item.projectId + "'>" + item.projectName + "</option>";
                       }
                   });
                $("select[name='projects']").append($html);
                //反选
                $("select[name='projects']").val($("#SearchProjects").val());
                //append后必须从新渲染
                form.render('select');
            }
		});
	})
	$('#save').on("click", function() {
		form.render();
	})
	
	//保存
	form.on('submit(save)', function(data) {

		setTimeout(function() {
			var usercode = $("#userCode").val();
			var username = $("#userName").val();
			var email = $("#email").val();
			var cellphone = $("#cellphone").val();
			var departments = $("#departments").val();//部门
			var projects = $("#projects").val();//项目
			var bankCardNum = $("#bankCardNum").val();//银行卡
			var idNum = $("#idNum").val();//身份证
			var item = $("input[name='sex']:checked").val();
			var state = $("input[name='state']:checked").val();//状态
			
			console.log("进入ajax");
			$.ajax({
				url: layui.setter.project + "/user/userinfo",
				type: "post",
				xhrFields: {
					withCredentials: true
				},
				data: {
					"userCode": usercode,
					"userName": username,
					"email":email,
					"phone": cellphone,
					"departments": departments,
					"projects": projects,
					"sex": item,
					"bankCardNum":bankCardNum,
					"idNum":idNum,
					"state": state
				},
				//contentType:"application/json",
				dataType: "json",
				success: function(data) {
					if(data.result == 200) {
						layer.msg(data.message);
						
						form.render();
					} else {
						layer.msg(data.message);
						form.render();
					}
				}
			});
		}, 500);

	});
	e("addMangement", {})

});