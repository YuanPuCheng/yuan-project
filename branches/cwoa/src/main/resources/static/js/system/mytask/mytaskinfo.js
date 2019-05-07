layui.extend({
	setter: "../../../../static/layui/config",
	formSelects: "../../../../static/layui/formSelects-v4"

}).define(["form", "setter", "layer", "jquery", "upload","formSelects"], function(e) {
	var form = layui.form, //只有执行了这一步，部分表单元素才会自动修饰成功
		o = layui.$,
		layer = layui.layer,
		formSelects = layui.formSelects,
		index = parent.layer.getFrameIndex(window.name), //获取窗口索引
		$ = layui.jquery,
		upload = layui.upload,
		a = o("body");
		var taskBId = $("#taskbId").val();
		var attachment = "";
		
		var fileName = $("#filename").val();
		var fileRename = $("#filerename").val();
		$("#attachment").html(fileRename);
		if(fileRename!=''&&fileRename!=null){
			$("#down").css("display","inline-block");
		}
		$("#down").attr("href",layui.setter.project+'/file/down?filename='+fileName);
		
		var task_fileName = $("#taskb_filename").val();
		var task_fileRename = $("#taskb_filerename").val();
		if(task_fileRename!=''&&task_fileRename!=null){
			$("#down1").css("display","inline-block");
		}
		$("#attachment1").html(task_fileRename);
		$("#down1").attr("href",layui.setter.project+'/file/down?filename='+task_fileName);
		
			a.on('click', "#addqx", function(o) {

		parent.layer.close(index);
		//parent.location.reload();

	})
	
	e("mytaskinfo", {})
})