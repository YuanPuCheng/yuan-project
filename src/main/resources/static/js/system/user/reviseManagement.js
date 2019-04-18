layui.extend({
	setter: "../../../../static/layui/config",
	inputverify: "../../common/inputverify",
	formSelects: "../../../../static/layui/formSelects-v4"

}).define(["setter", "jquery","formSelects","inputverify","form"], function(e) {
	
	var $ = layui.jquery,
		formSelects = layui.formSelects,
	    form=layui.form;
	

	var data = JSON.parse(sessionStorage.getItem('key'));
		$('#usercode').val(data[0].userCode);
		$('#username').val(data[0].userName);
		$('#email').val(data[0].email);
		$('#phone').val(data[0].phone);
	if(data[0].departments!=null){
	var depArr=[];
	var dep="";
	for(var i=0;i<data[0].departments.length;i++){
		dep=dep+data[0].departments[i].departmentId+",";
		depArr.push(data[0].departments[i].departmentId);
	}
		if(dep!=null||dep!=""){
			dep=dep.substring(0,dep.length-1);
		}
		console.log(dep)
	}
	setTimeout(function(){
		
		formSelects.value('select1',depArr, true);
	},50)

	//多选
		layui.formSelects.config('select1', {
			keyName: 'departmentName',            //自定义返回数据中name的key, 默认 name
			keyVal: 'departmentId',
			beforeSuccess: function(id, url, searchVal, result) {
				//我要把数据外层的code, msg, data去掉
				result = result.data;
				//然后返回数据
				return result;
			}
		}).data('select1', 'server', {
			url: layui.setter.project+'/department/getdepartment'
		});
		
		//部门下拉框加载
		 $.get(layui.setter.project+'/department/getdepartment', {}, function (data) {
               var $html = "<option>请选择</option>";
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
               var $html = "<option>请选择</option>";
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
		
		//提交
			form.on('submit(LAY-user-login-submit)', function(obj) {
				console.log(formSelects.value('select1', 'valStr'))
					var usercode = $("#usercode").val();
					var username = $("#username").val();
					var email = $("#email").val();
					var phone = $("#phone").val();
					var departments =formSelects.value('select1', 'valStr');
					var projects = $("#project").val();
					var idNum = $('#idNum').val();
					var bankCardNum = $('#bankCardNum').val();
					var state = $('input[name="state"]:checked').val();
					
					$.ajax({
						url: layui.setter.project+"/user/edit",
						type: "post",
						xhrFields: {
							withCredentials: true
						},
						data: {
							"userId": data[0].userId,
							"userCode": usercode,
							"userName": username,
							"userPassword": "A!123456",
							"email": email,
							"phone": phone,
							"tempVar3":dep,
							"tempVar2": departments,
							"tempInt1": projects,
							"idNum": idNum,
							"bankCardNum": bankCardNum,
							"status": state,
							
						},
						dataType: "json",
						success: function(data) {
							console.log(data)
							if(data.result==400){
								layer.msg(data.message);
								var index = parent.layer.getFrameIndex(window.name); 
								//再执行关闭 
								parent.layer.close(index);  
								//关闭父级页面的表格
								parent.layui.table.reload('table');
							}else{
								layer.msg(data.message);
							}
							
						}
					})
					form.render();
					return false;
				});
		
	
	e("reviseManagement", {})

});