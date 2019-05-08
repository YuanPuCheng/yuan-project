layui.extend({
	inputverify: "../../common/inputverify",
	setter: "../../../../static/layui/config"

}).define(["form", "inputverify", "setter", "layer", "jquery", "laydate","table"], function(e) {
	var form = layui.form, //只有执行了这一步，部分表单元素才会自动修饰成功
		o = layui.$,
		layer = layui.layer,
		index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		$ = layui.jquery,
		a = o("body"),
		table = layui.table,
		laydate = layui.laydate;
		
		window.viewObj = {
		tbData: [{
			schStartId: null,
			projectId: null,
			schStartTime: null,
			schEndTime: null,
			schContent: null
		}],

	};
		$(".layui-input").attr("disabled", "disabled");
		$(".layui-textarea").attr("disabled", "disabled");
		$("#userName1").val($("#userId").val());
		$("input[name='status']").attr("disabled", "disabled");
		form.render();
	
		var schedules = $("#schedules").val();
		//console.log(JSON.stringify(value))
		var obj = JSON.parse(schedules);
		
		
	var layTableId = "layTable";
	var tableIns = table.render({
		elem: '#dataTable',
		id: layTableId,
		data: obj,
        cellMinWidth: 120, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
		//width: tbWidth,
		page: true,
		loading: true,
		even: false, //不开启隔行背景
		cols: [
			[{
					title: '序号',
					type: 'numbers'
				},
				{
					field: 'schStartTime',
					title: '开始时间'

				},
				{
					field: 'schEndTime',
					title: '结束时间'
				},
				{
					field: 'schContent',
					title: '进度'
				}
			]
		],
		done: function(res, curr, count) {
			console.log(res)
		}
	});
	


	a.on('click', "#addqx", function(o) {

		parent.layer.close(index);
		//parent.location.reload();

	})


	e("detailedproject", {})
})