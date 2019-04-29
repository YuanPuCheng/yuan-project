layui.extend({
	inputverify: "../../common/inputverify",
	setter: "../../../../static/layui/config"

}).define(["form", "inputverify", "setter", "layer", "jquery", "laydate", "table", "upload"], function(e) {
	var form = layui.form, //只有执行了这一步，部分表单元素才会自动修饰成功
		o = layui.$,
		layer = layui.layer,
		index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	$ = layui.jquery,
		a = o("body"),
		laydate = layui.laydate,
		table = layui.table,
		upload = layui.upload;

	//多文件列表示例
	var demoListView = $('#demoList'),
		uploadListIns = upload.render({
			elem: '#testList',
			url: layui.setter.project + '/file/upload',
			accept: 'file',
			multiple: true,
			data: {
				filetype: "HT",
				id: $("#projectId").val()
			},
			auto: false,
			bindAction: '#testListAction',
			choose: function(obj) {
				var files = this.files = obj.pushFile(); //将每次选择的文件追加到文件队列
				//读取本地文件
				obj.preview(function(index, file, result) {
					var tr = $(['<tr id="upload-' + index + '">', '<td>' + file.name + '</td>', '<td>' + (file.size / 1014).toFixed(1) + 'kb</td>', '<td>等待上传</td>', '<td>', '<button class="layui-btn layui-btn-xs demo-reload layui-hide">重传</button>', '<button class="layui-btn layui-btn-xs layui-btn-danger demo-delete">删除</button>', '</td>', '</tr>'].join(''));

					//单个重传
					tr.find('.demo-reload').on('click', function() {
						obj.upload(index, file);
					});

					//删除
					tr.find('.demo-delete').on('click', function() {
						delete files[index]; //删除对应的文件
						tr.remove();
						uploadListIns.config.elem.next()[0].value = ''; //清空 input file 值，以免删除后出现同名文件不可选
					});

					demoListView.append(tr);
				});
			},
			done: function(res, index, upload) {
				if(res.result == 200) { //上传成功
					var tr = demoListView.find('tr#upload-' + index),
						tds = tr.children();
					tds.eq(2).html('<span style="color: #5FB878;">上传成功</span>');
					tds.eq(3).html(''); //清空操作
					parent.layer.alert(res.message, function() {
						parent.layer.close(index);

						parent.location.reload();

					});
					return delete this.files[index]; //删除文件队列已经上传成功的文件

				} else {
					var tr = demoListView.find('tr#upload-' + index),
						tds = tr.children();
					tds.eq(2).html('<span style="color: #FF5722;">上传失败</span>');
					tds.eq(3).find('.demo-reload').removeClass('layui-hide'); //显示重传
				}
				this.error(index, upload);
			},
			error: function(index, upload) {
				var tr = demoListView.find('tr#upload-' + index),
					tds = tr.children();
				tds.eq(2).html('<span style="color: #FF5722;">上传失败</span>');
				tds.eq(3).find('.demo-reload').removeClass('layui-hide'); //显示重传
			}
		});

	$("#fanhui").click(function() {
		parent.layer.close(index);

	})

	e("addhetong", {})
})