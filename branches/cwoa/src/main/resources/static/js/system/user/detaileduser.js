layui.extend({
	setter: "../../../../static/layui/config",
	inputverify: "../../common/inputverify",
	formSelects: "../../../../static/layui/formSelects-v4"

}).define(["setter", "jquery","formSelects","inputverify","form"], function(e) {
	
	var $ = layui.jquery,
		formSelects = layui.formSelects,
	    form=layui.form,
	    o=layui.$,
	    index = parent.layer.getFrameIndex(window.name),
	    a = o("body");

    $("input[name='status']").attr("disabled", "disabled");




		a.on('click', "#addqx", function(o) {
				parent.layer.close(index);
			})




	
	e("reviseManagement", {})

});