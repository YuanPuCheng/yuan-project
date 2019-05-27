var canvas = document.getElementById('canvas');
var ctx = canvas.getContext('2d');
var $ = layui.jquery;
var layer = layui.layer;
var form = layui.form;
var widthMax = "70%",
	heightMax = "80%";
if($(window).width() < 768) {
	widthMax = "99%";
	heightMax = "85%";
}
var circleNumberList = []; //数组,用于储存代号
var linePointList = []; //数组,用于储存方向线
var nowIndex = -1; //存储被选择的元素在数组中的下标
var nowType = -1; //存储被选择的元素类型
var oldPlanName = ""; //网络计划的名称

//画计划图代号
function drawCircleNumber(x, y, num) {
	ctx.beginPath();
	ctx.arc(x, y, 20, 0, 2 * 2 * Math.PI);
	ctx.stroke();
	ctx.font = "28px Arial";
	ctx.fillText(num, x - 15, y + 10);
	var circleNumber = {
		"x": x,
		"y": y,
		"num": num,
		"nextNumList": []
	};
	circleNumberList.push(circleNumber);
}

//画方向线
function drawLinePoint(num1, num2, type, work, time) {
	var len = circleNumberList.length;
	for(var i = 0; i < len; i++) {
		if(circleNumberList[i].num === num1) {
			var c1x = circleNumberList[i].x;
			var c1y = circleNumberList[i].y;
		}
		if(circleNumberList[i].num === num2) {
			var c2x = circleNumberList[i].x;
			var c2y = circleNumberList[i].y;
		}
	}
	if(type === 1) {
		ctx.setLineDash([5]);
		work = "虚拟工作";
		time = 0;
	} else {
		ctx.setLineDash([]);
	}
	var weightTag = c2x - c1x;
	var heightTag = c2y - c1y;
	var startX = 0;
	var startY = 0;
	var midX = 0;
	var midY = 0;
	var endX = 0;
	var endY = 0;
	var pointX1 = 0;
	var pointY1 = 0;
	var pointX2 = 0;
	var pointY2 = 0;
	if(weightTag === 0 && heightTag <= -30) {
		startX = c1x;
		startY = c1y - 20;
		midX = startX;
		midY = startY;
		endX = c2x;
		endY = c2y + 20;
		pointX1 = endX - 5;
		pointY1 = endY + 10;
		pointX2 = endX + 5;
		pointY2 = endY + 10;
	} else if(weightTag >= 30 && heightTag <= -30) {
		startX = c1x;
		startY = c1y - 20;
		midX = c1x;
		midY = c2y;
		endX = c2x - 20;
		endY = c2y;
		pointX1 = endX - 10;
		pointY1 = endY - 5;
		pointX2 = endX - 10;
		pointY2 = endY + 5;
	} else if(weightTag >= 30 && heightTag === 0) {
		startX = c1x + 20;
		startY = c1y;
		midX = startX;
		midY = startY;
		endX = c2x - 20;
		endY = c2y;
		pointX1 = endX - 10;
		pointY1 = endY - 5;
		pointX2 = endX - 10;
		pointY2 = endY + 5;
	} else if(weightTag >= 30 && heightTag >= 30) {
		startX = c1x;
		startY = c1y + 20;
		midX = c1x;
		midY = c2y;
		endX = c2x - 20;
		endY = c2y;
		pointX1 = endX - 10;
		pointY1 = endY - 5;
		pointX2 = endX - 10;
		pointY2 = endY + 5;
	} else if(weightTag === 0 && heightTag >= 30) {
		startX = c1x;
		startY = c1y + 20;
		midX = startX;
		midY = startY;
		endX = c2x;
		endY = c2y - 20;
		pointX1 = endX - 5;
		pointY1 = endY - 10;
		pointX2 = endX + 5;
		pointY2 = endY - 10;
	} else if(weightTag < -30 && heightTag >= 30) {
		startX = c1x;
		startY = c1y + 20;
		midX = c1x;
		midY = c2y;
		endX = c2x + 20;
		endY = c2y;
		pointX1 = endX + 10;
		pointY1 = endY - 5;
		pointX2 = endX + 10;
		pointY2 = endY + 5;
	} else if(weightTag < -30 && heightTag === 0) {
		startX = c1x - 20;
		startY = c1y;
		midX = startX;
		midY = startY;
		endX = c2x + 20;
		endY = c2y;
		pointX1 = endX + 10;
		pointY1 = endY - 5;
		pointX2 = endX + 10;
		pointY2 = endY + 5;
	} else if(weightTag < -30 && heightTag <= -30) {
		startX = c1x;
		startY = c1y - 20;
		midX = c1x;
		midY = c2y;
		endX = c2x + 20;
		endY = c2y;
		pointX1 = endX + 10;
		pointY1 = endY - 5;
		pointX2 = endX + 10;
		pointY2 = endY + 5;
	}
	ctx.beginPath();
	ctx.moveTo(startX, startY);
	ctx.lineTo(midX, midY);
	ctx.lineTo((pointX1 + pointX2) / 2, ((pointY1 + pointY2) / 2));
	ctx.stroke();
	ctx.setLineDash([]);
	ctx.beginPath();
	ctx.moveTo(pointX1, pointY1);
	ctx.lineTo(pointX2, pointY2);
	ctx.lineTo(endX, endY);
	ctx.fill();
	ctx.stroke();
	if(type != 1) {
		ctx.beginPath();
		ctx.font = "14px Arial";
		if(weightTag === 0) {
			ctx.fillText(work, midX - 14 * (work.length / 2), (midY + endY) / 2 - 5);
			ctx.fillText(time + "天", midX + 2, ((midY + endY) / 2 + 15));
		} else {
			ctx.fillText(work, (midX + endX) / 2 - 14 * (work.length / 2), midY - 5);
			ctx.fillText(time + "天", (midX + endX) / 2 - 14, midY + 15);
		}

	}
	var linePoint = {
		"num1": num1,
		"c1x": c1x,
		"c1y": c1y,
		"num2": num2,
		"c2x": c2x,
		"c2y": c2y,
		"type": type,
		"work": work,
		"time": time
	};
	linePointList.push(linePoint);
	for(var j = 0; j < len; j++) {
		if(num1 === circleNumberList[j].num) {
			circleNumberList[j].nextNumList.push(num2);
			break;
		}
	}
}

//点击获取计划图元素
canvas.onclick = function(ev) {
	nowIndex = -1;
	nowType = -1;
	//鼠标按下的位置
	var clickX = ev.offsetX;
	var clickY = ev.offsetY;
	var len = circleNumberList.length;
	for(var i = 0; i < len; i++) {
		var clickSpace = Math.pow((clickX - circleNumberList[i].x), 2) + Math.pow((clickY - circleNumberList[i].y), 2);
		if(clickSpace <= 400) {
			showCircleNumber(i);
			return;
		}
	}
	var len2 = linePointList.length;
	for(var j = 0; j < len2; j++) {
		if(Math.abs(linePointList[j].c1x - clickX) < 15) {
			if(linePointList[j].c1y < linePointList[j].c2y) {
				if(clickY > linePointList[j].c1y && clickY < linePointList[j].c2y) {
					showLinePoint(j);
					return;
				}
			} else {
				if(clickY < linePointList[j].c1y && clickY > linePointList[j].c2y) {
					showLinePoint(j);
					return;
				}
			}

		}
		if(Math.abs(linePointList[j].c2y - clickY) < 15 && linePointList[j].c1x < clickX && linePointList[j].c2x > clickX) {
			showLinePoint(j);
			return;
		}
	}
};

//展示代号信息
function showCircleNumber(i) {
	nowIndex = i;
	nowType = 1;
	layer.msg('您选择了代号:' + circleNumberList[i].num);
}

//展示方向线信息
function showLinePoint(i) {
	nowIndex = i;
	nowType = 2;
	layer.msg('您选择了 ' + linePointList[i].num1 + '->' + linePointList[i].num2 + ' 方向线!');
}

//清空画布
function deletePlanPic() {
	var width = canvas.offsetWidth;
	var height = canvas.offsetHeight;
	ctx.clearRect(0, 0, width, height);
}

//画网络计划图
function drawPlanPic(list1, list2) {
	circleNumberList = [];
	linePointList = [];
	var len1 = list1.length;
	for(var i = 0; i < len1; i++) {
		var nowCircleNumber = list1[i];
		drawCircleNumber(nowCircleNumber.x, nowCircleNumber.y, nowCircleNumber.num);
	}
	var len2 = list2.length;
	for(var i = 0; i < len2; i++) {
		var nowLinePoint = list2[i];
		drawLinePoint(nowLinePoint.num1, nowLinePoint.num2, nowLinePoint.type, nowLinePoint.work, nowLinePoint.time);
	}
}

//重生成
function reDrawPlanPic() {
	deletePlanPic();
	drawPlanPic(circleNumberList, linePointList);
	console.log(circleNumberList);
	console.log(linePointList);
}

//删除代号
function deleteCircleNumber() {
	var nowNum = circleNumberList[nowIndex].num;
	circleNumberList.splice(nowIndex, 1);
	var delArray = [];
	var len = linePointList.length;
	for(var i = 0; i < len; i++) {
		if(linePointList[i].num1 === nowNum || linePointList[i].num2 === nowNum) {
			delArray.push(i);
		}
	}
	for(var i = 0; i < delArray.length; i++) {
		linePointList.splice(delArray[i], 1);
	}
	nowIndex = -1;
	nowType = -1;
	reDrawPlanPic();
}

//删除方向线
function deleteLinePoint() {
	var frontNum = linePointList[nowIndex].num1;
	var nextNum = linePointList[nowIndex].num2;
	var len = circleNumberList.length;
	var delIndex = -1;
	for(var i = 0; i < len; i++) {
		if(circleNumberList[i].num === frontNum) {
			var list = circleNumberList[i].nextNumList;
			for(var j = 0; j < list.length; j++) {
				if(list[j] === nextNum) {
					delIndex = j;
				}
			}
			list.splice(delIndex, 1);
		}
	}
	linePointList.splice(nowIndex, 1);
	nowIndex = -1;
	nowType = -1;
	reDrawPlanPic();
}

//操作成功提示
function successTip() {
	nowIndex = -1;
	nowType = -1;
	layer.msg('操作成功！');
	setTimeout(function() {
		layer.closeAll()
	}, 1000);
}

//创建代号按钮点击事件
$('#createNum').click(function() {
	$('#createNumTitle').text("创建代号");
	layer.open({
		type: 1,
		shadeClose: true,
		area: [widthMax, heightMax], //宽高
		content: $('#addCircleBox')
	});
});

//创建(编辑)代号页面确定按钮点击事件
$('#addCircle').click(function() {
	var circleX = parseInt($('#circleX').val());
	var circleY = parseInt($('#circleY').val());
	var circleNum = parseInt($('#circleNum').val());
	var createNumTitle = $('#createNumTitle').text();
	if(!/^\+?[1-9][0-9]*$/.test(circleX)) {
		layer.msg('X坐标必须为50的正倍数！');
		return;
	}
	if(circleX % 50 != 0) {
		layer.msg('X坐标必须为50的正倍数！');
		return;
	}
	if(!/^\+?[1-9][0-9]*$/.test(circleY)) {
		layer.msg('Y坐标必须为50的正倍数！');
		return;
	}
	if(circleY % 50 != 0) {
		layer.msg('Y坐标必须为50的正倍数！');
		return;
	}
	if(!/^\+?[1-9][0-9]*$/.test(circleNum)) {
		layer.msg('代号必须为大于0的正整数！');
		return;
	}
	var nowCircleNumber = circleNumberList[nowIndex];
	var hasDelete = false;
	if(createNumTitle === '编辑代号') {
		circleNumberList.splice(nowIndex, 1);
		hasDelete = true;
	}
	for(var i = 0; i < circleNumberList.length; i++) {
		if(circleNumberList[i].num === circleNum) {
			layer.msg('代号不能重复！');
			if(hasDelete) {
				circleNumberList.push(nowCircleNumber);
			}
			return;
		}
	}
	if(createNumTitle === '编辑代号') {
		var circleNumber = {
			"x": circleX,
			"y": circleY,
			"num": circleNum,
			"nextNumList": nowCircleNumber.nextNumList
		};
		circleNumberList.push(circleNumber);
		reDrawPlanPic();
	} else {
		drawCircleNumber(circleX, circleY, circleNum);
	}
	successTip();
});

//取消按钮点击事件
$('.goNot').click(function() {
	nowIndex = -1;
	nowType = -1;
	layer.closeAll();
});

//创建方向线按钮点击事件
$('#createPoint').click(function() {
	$('#createPointTitle').text("创建方向线");
	var len = circleNumberList.length;
	if(len < 2) {
		layer.msg('请至少创建两个代号！');
		return;
	}
	$("#smallNum").html("");
	$("#largeNum").html("");
	for(var i = 0; i < len; i++) {
		$("#smallNum").append('<option value="' + circleNumberList[i].num + '">' + circleNumberList[i].num + '</option>');
		$("#largeNum").append('<option value="' + circleNumberList[i].num + '">' + circleNumberList[i].num + '</option>');
	}
	form.render();
	layer.open({
		type: 1,
		shadeClose: true,
		area: [widthMax, heightMax], //宽高
		content: $('#addPointBox')
	});
});

//创建(编辑)方向线页面确定按钮点击事件
$('#addPoint').click(function() {
	var createPointTitle = $('#createPointTitle').text();
	var smallNum = parseInt($('#smallNum option:selected').prop("value"));
	var largeNum = parseInt($('#largeNum option:selected').prop("value"));
	var lineType = parseInt($('#lineType option:selected').prop("value"));
	var workText = $('#workText').val();
	var workDays = parseInt($('#workDays').val());
	if(smallNum >= largeNum) {
		layer.msg('小代号必须小于大代号！');
		return;
	}
	if(lineType === 0) {
		if(!/^\+?[1-9][0-9]*$/.test(workDays)) {
			layer.msg('工作天数必须为大于0的正整数！');
			return;
		}
		if(workText === "") {
			layer.msg('工作内容不能为空！');
			return;
		}
	}
	var len = linePointList.length;
	for(var i = 0; i < len; i++) {
		if(linePointList[i].num1 === smallNum && linePointList[i].num2 === largeNum) {
			layer.msg('已经存在的方向线！');
			return;
		}
	}
	if(createPointTitle === "编辑方向线") {
		deleteLinePoint();
	}
	drawLinePoint(smallNum, largeNum, lineType, workText, workDays);
	successTip();
});

//重生成按钮点击事件
$('#reDraw').click(function() {
	reDrawPlanPic();
});

//新建按钮点击事件
$('#add').click(function() {
	deletePlanPic();
});

//删除按钮点击事件
$('#delete').click(function() {
	if(nowType === 1) {
		deleteCircleNumber();
	} else if(nowType === 2) {
		deleteLinePoint();
	}
});

//编辑按钮点击事件
$('#edit').click(function() {
	if(nowType === 1) {
		$('#createNumTitle').text("编辑代号");
		$('#circleX').val(circleNumberList[nowIndex].x);
		$('#circleY').val(circleNumberList[nowIndex].y);
		$('#circleNum').val(circleNumberList[nowIndex].num);
		layer.open({
			type: 1,
			shadeClose: true,
			area: [widthMax, heightMax], //宽高
			content: $('#addCircleBox')
		});
	} else if(nowType === 2) {
		$('#createPointTitle').text("编辑方向线");
		var len = circleNumberList.length;
		$("#smallNum").html("");
		$("#largeNum").html("");
		for(var i = 0; i < len; i++) {
			if(linePointList[nowIndex].num1 === circleNumberList[i].num) {
				$("#smallNum").append('<option value="' + circleNumberList[i].num + '" selected="">' + circleNumberList[i].num + '</option>');
			} else {
				$("#smallNum").append('<option value="' + circleNumberList[i].num + '">' + circleNumberList[i].num + '</option>');
			}
			if(linePointList[nowIndex].num2 === circleNumberList[i].num) {
				$("#largeNum").append('<option value="' + circleNumberList[i].num + '" selected="">' + circleNumberList[i].num + '</option>');
			} else {
				$("#largeNum").append('<option value="' + circleNumberList[i].num + '">' + circleNumberList[i].num + '</option>');
			}
		}
		$('#lineType').html("");
		if(linePointList[nowIndex].type === 1) {
			$('#lineType').append('<option value="0">实线</option>');
			$('#lineType').append('<option value="1" selected="">虚线</option>');
		} else {
			$('#lineType').append('<option value="0" selected="">实线</option>');
			$('#lineType').append('<option value="1">虚线</option>');
		}
		$('#workText').val(linePointList[nowIndex].work);
		$('#workDays').val(linePointList[nowIndex].time);
		form.render();
		layer.open({
			type: 1,
			shadeClose: true,
			area: [widthMax, heightMax], //宽高
			content: $('#addPointBox')
		});
	}
});

//平移按钮点击事件
$('#move').click(function() {
	$('#moveX').val('0');
	$('#moveY').val('0');
	layer.open({
		type: 1,
		shadeClose: true,
		area: [widthMax, heightMax], //宽高
		content: $('#moveBox')
	});
});

//平移页面确定按钮点击事件
$('#moveAll').click(function() {
	var moveX = parseInt($('#moveX').val());
	var moveY = parseInt($('#moveY').val());
	if(!/^\d+$/.test(moveX)) {
		layer.msg('X坐标增量必须为50的正倍数！');
		return;
	}
	if(moveX % 50 != 0) {
		layer.msg('X坐标增量必须为50的正倍数！');
		return;
	}
	if(!/^\d+$/.test(moveY)) {
		layer.msg('Y坐标增量必须为50的正倍数！');
		return;
	}
	if(moveY % 50 != 0) {
		layer.msg('Y坐标增量必须为50的正倍数！');
		return;
	}
	var len = circleNumberList.length;
	for(var i = 0; i < len; i++) {
		circleNumberList[i].x += moveX;
		circleNumberList[i].y += moveY;
	}
	reDrawPlanPic();
	successTip();
});

//保存按钮的点击事件
$('#save').click(function() {
	$('#workMan').val($("#userName", parent.document).attr("name"));
	$('#planName').val(oldPlanName);
	layer.open({
		type: 1,
		shadeClose: true,
		area: [widthMax, heightMax], //宽高
		content: $('#saveBox')
	});
});

//保存页面确定按钮
$('#savePlan').click(function() {
	var workMan = $('#workMan').val();
	var planName = $('#planName').val();
	if(planName === "") {
		layer.msg('名称不能为空！');
		return;
	}
	var dataUrl = '';
	if(oldPlanName === "") {
		dataUrl = '/plan/insertPlan';
	} else {
		dataUrl = '/plan/updatePlan';
	}
	$.ajax({
		url: dataUrl,
		type: "get",
		data: {
			"workMan": workMan,
			"planName": planName,
			"circleList": JSON.stringify(circleNumberList),
			"pointList": JSON.stringify(linePointList)
		},
		dataType: "text", //预测服务端返回的数据类型
		success: function(data) { //请求成功的回调
			if(data === 'true') {
				successTip();
			} else {
				this.error();
			}
		},
		error: function() { //请求失败的回调
			layer.msg('发生了未知错误！', {
				icon: 2
			});
		}
	});
});