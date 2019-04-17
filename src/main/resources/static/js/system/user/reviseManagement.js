layui.extend({
	setter: "../../../../static/layui/config"

}).define(["setter", "jquery"], function(e) {
	
	var $ = layui.jquery;
	layui.use(['form'],function (){
		var form=layui.form;
		
		
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
	})
	
	e("reviseManagement", {})

});