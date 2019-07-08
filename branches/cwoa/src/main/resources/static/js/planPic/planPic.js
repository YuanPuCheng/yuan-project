var canvas = document.getElementById('canvas');
var ctx = canvas.getContext('2d');
var $ = layui.jquery;
var layer = layui.layer;
var form = layui.form;
var laydate = layui.laydate;
var widthMax = "70%",
	heightMax = "80%";
if($(window).width() < 768) {
	widthMax = "99%";
	heightMax = "85%";
}
var ajaxClick = false; //用以控制用户多次点击多次请求服务器
var circleNumberList = []; //数组,用于储存代号
var linePointList = []; //数组,用于储存方向线
var nowIndex = -1; //存储被选择的元素在数组中的下标
var nowType = -1; //存储被选择的元素类型
var oldPlanName = ""; //网络计划的名称
var planTimeLimit = 0; //计划工期
var planStartTime = new Date().getTime(); //计划开始时间
var haveChange = false; //用于修改计划后重置计算结果
var haveNew = false; //判断是否点击新建按钮
var dayWidth = 100; //单位时间宽度
var ifchange = false; //是否转换成时标网络计划
var ifGrid = false; //是否开启时间线
var ifManLine = false; //是否开启劳动力曲线

//画计划图代号
function drawCircleNumber(x, y, num) {
	ctx.strokeStyle = "black";
	ctx.fillStyle = "#000000";
	ctx.beginPath();
	ctx.arc(x, y, 20, 0, 2 * 2 * Math.PI);
	ctx.stroke();
	ctx.font = "28px Arial";
	ctx.fillText(num, x - 15, y + 10);
	var circleNumber = {
		"x": x,
		"y": y,
		"num": num,
		"nextNumList": [],
		"frontNumList": []
	};
	circleNumberList.push(circleNumber);
}

//画方向线
function drawLinePoint(num1, num2, type, dec, work, time, man, start, end, es, ls, ef, lf, tf, ff) {
	ctx.strokeStyle = "black";
	ctx.fillStyle = "#000000";
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
	if(typeof c1x === 'undefined' || typeof c2x === 'undefined') {
		return;
	}
	if(type === 1) {
		ctx.setLineDash([5]);
		work = "虚拟工作";
		time = 0;
		man = 0;
		if(ifchange) {
			dec = 3;
		}
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
	var loc = 0;
	if(weightTag === 0 && heightTag <= -30) {
		loc = 5;
		startX = c1x;
		startY = c1y - 20;
		endX = c2x;
		endY = c2y + 20;
		midX = startX;
		midY = startY;
		pointX1 = endX - 5;
		pointY1 = endY + 10;
		pointX2 = endX + 5;
		pointY2 = endY + 10;
	} else if(weightTag >= 30 && heightTag <= -30) {
		loc = 1;
		if(dec === 0 || dec === 1) {
			dec = 1;
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
		} else if(dec === 2) {
			startX = c1x + 20;
			startY = c1y;
			midX = c2x;
			midY = c1y;
			endX = c2x;
			endY = c2y + 20;
			pointX1 = endX - 5;
			pointY1 = endY + 10;
			pointX2 = endX + 5;
			pointY2 = endY + 10;
		} else {
			startX = c1x + 14.142;
			startY = c1y - 14.142;
			endX = c2x - 14.142;
			endY = c2y + 14.142;
			midX = (startX + endX) / 2;
			midY = (startY + endY) / 2;
			pointX1 = endX - 10.607;
			pointY1 = endY + 3.536;
			pointX2 = endX - 3.536;
			pointY2 = endY + 10.607;
		}
	} else if(weightTag >= 30 && heightTag === 0) {
		loc = 6;
		startX = c1x + 20;
		startY = c1y;
		endX = c2x - 20;
		endY = c2y;
		midX = startX;
		midY = startY;
		pointX1 = endX - 10;
		pointY1 = endY - 5;
		pointX2 = endX - 10;
		pointY2 = endY + 5;
	} else if(weightTag >= 30 && heightTag >= 30) {
		loc = 2;
		if(dec === 0 || dec === 2) {
			dec = 2;
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
		} else if(dec === 1) {
			startX = c1x + 20;
			startY = c1y;
			midX = c2x;
			midY = c1y;
			endX = c2x;
			endY = c2y - 20;
			pointX1 = endX - 5;
			pointY1 = endY - 10;
			pointX2 = endX + 5;
			pointY2 = endY - 10;
		} else {
			startX = c1x + 14.142;
			startY = c1y + 14.142;
			endX = c2x - 14.142;
			endY = c2y - 14.142;
			midX = (startX + endX) / 2;
			midY = (startY + endY) / 2;
			pointX1 = endX - 10.607;
			pointY1 = endY - 3.536;
			pointX2 = endX - 3.536;
			pointY2 = endY - 10.607;
		}
	} else if(weightTag === 0 && heightTag >= 30) {
		loc = 7;
		startX = c1x;
		startY = c1y + 20;
		endX = c2x;
		endY = c2y - 20;
		midX = startX;
		midY = startY;
		pointX1 = endX - 5;
		pointY1 = endY - 10;
		pointX2 = endX + 5;
		pointY2 = endY - 10;
	} else if(weightTag < -30 && heightTag >= 30) {
		loc = 3;
		if(dec === 0 || dec === 1) {
			dec = 1;
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
		} else if(dec === 2) {
			startX = c1x - 20;
			startY = c1y;
			midX = c2x;
			midY = c1y;
			endX = c2x;
			endY = c2y - 20;
			pointX1 = endX - 5;
			pointY1 = endY - 10;
			pointX2 = endX + 5;
			pointY2 = endY - 10;
		} else {
			startX = c1x - 14.142;
			startY = c1y + 14.142;
			endX = c2x + 14.142;
			endY = c2y - 14.142;
			midX = (startX + endX) / 2;
			midY = (startY + endY) / 2;
			pointX1 = endX + 10.607;
			pointY1 = endY - 3.536;
			pointX2 = endX + 3.536;
			pointY2 = endY - 10.607;
		}
	} else if(weightTag < -30 && heightTag === 0) {
		loc = 8;
		startX = c1x - 20;
		startY = c1y;
		endX = c2x + 20;
		endY = c2y;
		midX = startX;
		midY = startY;
		pointX1 = endX + 10;
		pointY1 = endY - 5;
		pointX2 = endX + 10;
		pointY2 = endY + 5;
	} else if(weightTag < -30 && heightTag <= -30) {
		loc = 4;
		if(dec === 0 || dec === 2) {
			dec = 2;
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
		} else if(dec === 1) {
			startX = c1x - 20;
			startY = c1y;
			midX = c2x;
			midY = c1y;
			endX = c2x;
			endY = c2y + 20;
			pointX1 = endX - 5;
			pointY1 = endY + 10;
			pointX2 = endX + 5;
			pointY2 = endY + 10;
		} else {
			startX = c1x - 14.142;
			startY = c1y - 14.142;
			endX = c2x + 14.142;
			endY = c2y + 14.142;
			midX = (startX + endX) / 2;
			midY = (startY + endY) / 2;
			pointX1 = endX + 10.607;
			pointY1 = endY + 3.536;
			pointX2 = endX + 3.536;
			pointY2 = endY + 10.607;
		}
	}
	if(typeof es === 'undefined' || haveChange) {
		es = -1;
	}
	if(typeof ls === 'undefined' || haveChange) {
		ls = -1;
	}
	if(typeof ef === 'undefined' || haveChange) {
		ef = -1;
	}
	if(typeof lf === 'undefined' || haveChange) {
		lf = -1;
	}
	if(typeof ff === 'undefined' || haveChange) {
		ff = -1;
	}
	if(typeof tf === 'undefined' || haveChange) {
		tf = -1;
	}
	if(typeof start === 'undefined' || start===null) {
		start = 0;
	}
	if(typeof end === 'undefined' || haveChange) {
		end = 0;
	}
	var linePoint = {
		"num1": num1, //小代号
		"c1x": c1x, //小代号X坐标
		"c1y": c1y, //小代号Y坐标
		"num2": num2, //大代号
		"c2x": c2x, //大代号X坐标
		"c2y": c2y, //大代号Y坐标
		"type": type, //线型，0是实线，1是虚线
		"dec": dec, //方向
		"work": work, //工作描述
		"time": time, //工作天数
		"loc": loc, //象限
		"ES": es, //最早开始时间
		"LS": ls, //最晚开始时间
		"EF": ef, //最早完成时间
		"LF": lf, //最晚完成时间
		"TF": tf, //总时差
		"FF": ff, //自由时差
		"start": start, //实际开始时间
		"end": end, //实际结束时间
		"man": man //工作所需人数
	};
	ctx.beginPath();
	if(tf === 0) {
		ctx.strokeStyle = "red";
		ctx.fillStyle = "red";
	}
	ctx.moveTo(startX, startY);
	if(heightTag !== 0) {
		ctx.lineTo(midX, midY);
	}
	if(!haveChange && ifchange && type !== 1) {
		var tsx = 100 + start * 1 * dayWidth;
		var tex = 100 + end * 1 * dayWidth;
		if(heightTag === 0 || (loc === 1 && dec === 1) || (loc === 2 && dec === 2)) {
			if(end !== 0 && tsx === c1x && c2x > tex) { //只有后面有弹簧
				ctx.lineTo(tex, midY);
				var tx = tex + 5;
				var ty = midY + 5;
				ctx.lineTo(tx, ty);
				var td = 1;
				while(c2x - tx > 35) {
					ctx.lineTo(tx, ty);
					if(td === 0) {
						tx += 5;
						ty += 10;
						td = 1;
					} else {
						tx += 5;
						ty -= 10;
						td = 0;
					}
				}
			} else if(tex === c2x && tsx > c1x) { //只有前面有弹簧
				var tx = midX + 5;
				var ty = midY;
				ctx.lineTo(tx, ty);
				var td = 0; //0向上
				tx += 5;
				ty += 5;
				td = 1; //1向下
				while(tsx - tx > 0) {
					ctx.lineTo(tx, ty);
					if(td === 0) {
						tx += 5;
						ty += 10;
						td = 1;
					} else {
						tx += 5;
						ty -= 10;
						td = 0;
					}
				}
				ctx.lineTo(tsx, midY);
			} else if(tsx > c1x && tex < c2x) { //前后都有弹簧
				var tx = midX + 5;
				var ty = midY;
				ctx.lineTo(tx, ty);
				var td = 0; //0向上
				tx += 5;
				ty += 5;
				td = 1; //1向下
				while(tsx - tx > 0) {
					ctx.lineTo(tx, ty);
					if(td === 0) {
						tx += 5;
						ty += 10;
						td = 1;
					} else {
						tx += 5;
						ty -= 10;
						td = 0;
					}
				}
				ctx.lineTo(tsx, midY);
				ctx.lineTo(tex, midY);
				tx = tex + 5;
				ty = midY + 5;
				td = 1;
				while(c2x - tx > 35) {
					ctx.lineTo(tx, ty);
					if(td === 0) {
						tx += 5;
						ty += 10;
						td = 1;
					} else {
						tx += 5;
						ty -= 10;
						td = 0;
					}
				}
			}
		} else if((loc === 1 && dec === 2) || (loc === 2 && dec === 1)) {
			if(end !== 0 && tsx === c1x && c2x > tex) { //只有后面有弹簧
				ctx.beginPath();
				ctx.moveTo(startX, startY);
				ctx.lineTo(tex, midY);
				var tx = tex + 5;
				var ty = midY + 5;
				ctx.lineTo(tx, ty);
				var td = 1;
				while(c2x - tx > 5) {
					ctx.lineTo(tx, ty);
					if(td === 0) {
						tx += 5;
						ty += 10;
						td = 1;
					} else {
						tx += 5;
						ty -= 10;
						td = 0;
					}
				}
				ctx.lineTo(midX - 5, midY);
				ctx.lineTo(midX, midY);
			} else if(tex === c2x && tsx > c1x) { //只有前面有弹簧
				ctx.beginPath();
				ctx.moveTo(startX, startY);
				var tx = startX + 5;
				var ty = startY;
				ctx.lineTo(tx, ty);
				var td = 0; //0向上
				tx += 5;
				ty += 5;
				td = 1; //1向下
				while(tsx - tx > 0) {
					ctx.lineTo(tx, ty);
					if(td === 0) {
						tx += 5;
						ty += 10;
						td = 1;
					} else {
						tx += 5;
						ty -= 10;
						td = 0;
					}
				}
				ctx.lineTo(tsx, midY);
                ctx.lineTo(midX, midY);
			} else if(tsx > c1x && tex < c2x) { //前后都有弹簧
				ctx.beginPath();
				ctx.moveTo(startX, startY);
				var tx = startX + 5;
				var ty = startY;
				ctx.lineTo(tx, ty);
				var td = 0; //0向上
				tx += 5;
				ty += 5;
				td = 1; //1向下
				while(tsx - tx > 0) {
					ctx.lineTo(tx, ty);
					if(td === 0) {
						tx += 5;
						ty += 10;
						td = 1;
					} else {
						tx += 5;
						ty -= 10;
						td = 0;
					}
				}
				ctx.lineTo(tsx, midY);
				ctx.lineTo(tex, midY);
				tx = tex + 5;
				ty = midY + 5;
				td = 1;
				while(c2x - tx > 5) {
					ctx.lineTo(tx, ty);
					if(td === 0) {
						tx += 5;
						ty += 10;
						td = 1;
					} else {
						tx += 5;
						ty -= 10;
						td = 0;
					}
				}
				ctx.lineTo(midX - 5, midY);
				ctx.lineTo(midX, midY);
			}
		} else if(loc === 1 && dec === 3) {
			var c3x = (pointX1 + pointX2) / 2;
			var c3y = (pointY1 + pointY2) / 2;
			var slope = (c3x - startX) / (c3y - startY);
			var inter = startX - slope * startY;
            var inter1 = inter + weightTag / 50;
			var tb = 0;
			if(end !== 0 && tsx === c1x && c2x > tex) { //只有后面有弹簧 1象限斜向
				ctx.beginPath();
				ctx.moveTo(startX, startY);
				var tx = tex;
				var ty = (tx - inter) / slope;
				var td = 1;
				while(c3x - tx > 12) {
					ctx.lineTo(tx, ty);
					if(td === 0) {
						ty = ty - 10;
						td = 1;
					} else {
						tb = tx - ty;
						ty = (tb - inter1) / (slope - 1);
						tx = ty + tb;
						td = 0;
					}
				}
			} else if(tex === c2x && tsx > c1x) { //只有前面有弹簧 1象限斜向
				ctx.beginPath();
				ctx.moveTo(startX, startY);
				var tx = startX + 10;
				var ty = (tx - inter) / slope;
				var td = 1;
				while(tsx - tx > 0) {
					ctx.lineTo(tx, ty);
					if(td === 0) {
						ty = ty - 10;
						td = 1;
					} else {
						tb = tx - ty;
						ty = (tb - inter1) / (slope - 1);
						tx = ty + tb;
						td = 0;
					}
				}
				ctx.lineTo(tsx, (tsx - inter) / slope);
			} else if(tsx > c1x && tex < c2x) { //前后都有弹簧 1象限斜向
				ctx.beginPath();
				ctx.moveTo(startX, startY);
				var tx = startX + 10;
				var ty = (tx - inter) / slope;
				var td = 1;
				while(tsx - tx > 0) {
					ctx.lineTo(tx, ty);
					if(td === 0) {
						ty = ty - 10;
						td = 1;
					} else {
						tb = tx - ty;
						ty = (tb - inter1) / (slope - 1);
						tx = ty + tb;
						td = 0;
					}
				}
				tx = tsx;
				ty = (tx - inter) / slope;
				ctx.lineTo(tx, ty);
				tx = tex;
				ty = (tx - inter) / slope;
				td = 1;
				while(c3x - tx > 12) {
					ctx.lineTo(tx, ty);
					if(td === 0) {
						ty = ty - 10;
						td = 1;
					} else {
						tb = tx - ty;
						ty = (tb - inter1) / (slope - 1);
						tx = ty + tb;
						td = 0;
					}
				}
			}
		} else if(loc === 2 && dec === 3) {
			var c3x = (pointX1 + pointX2) / 2;
			var c3y = (pointY1 + pointY2) / 2;
			var slope = (c3x - startX) / (c3y - startY);
			var inter = startX - slope * startY;
            var inter1 = inter + weightTag / 50;
			var tb = 0;
			if(end !== 0 && tsx === c1x && c2x > tex) { //只有后面有弹簧 2象限斜向
				ctx.beginPath();
				ctx.moveTo(startX, startY);
				var tx = tex;
				var ty = (tx - inter) / slope;
				var td = 1;
				while(c3x - tx > 12) {
					ctx.lineTo(tx, ty);
					if(td === 0) {
						ty = ty + 10;
						td = 1;
					} else {
						tb = tx + ty;
						ty = (tb - inter1) / (slope + 1);
						tx = tb - ty;
						td = 0;
					}
				}
			} else if(tex === c2x && tsx > c1x) { //只有前面有弹簧 2象限斜向
				ctx.beginPath();
				ctx.moveTo(startX, startY);
				var tx = startX + 10;
				var ty = (tx - inter) / slope;
				var td = 1;
				while(tsx - tx > 0) {
					ctx.lineTo(tx, ty);
					if(td === 0) {
						ty = ty + 10;
						td = 1;
					} else {
						tb = tx + ty;
						ty = (tb - inter1) / (slope + 1);
						tx = tb - ty;
						td = 0;
					}
				}
				ctx.lineTo(tsx, (tsx - inter) / slope);
			} else if(tsx > c1x && tex < c2x) { //前后都有弹簧 2象限斜向
				ctx.beginPath();
				ctx.moveTo(startX, startY);
				var tx = startX + 10;
				var ty = (tx - inter) / slope;
				var td = 1;
				while(tsx - tx > 0) {
					ctx.lineTo(tx, ty);
					if(td === 0) {
						ty = ty + 10;
						td = 1;
					} else {
						tb = tx + ty;
						ty = (tb - inter1) / (slope + 1);
						tx = tb - ty;
						td = 0;
					}
				}
				tx = tsx;
				ty = (tx - inter) / slope;
				ctx.lineTo(tx, ty);
				tx = tex;
				ty = (tx - inter) / slope;
				td = 1;
				while(c3x - tx > 12) {
					ctx.lineTo(tx, ty);
					if(td === 0) {
						ty = ty + 10;
						td = 1;
					} else {
						tb = tx + ty;
						ty = (tb - inter1) / (slope + 1);
						tx = tb - ty;
						td = 0;
					}
				}
			}
		}
	}
	ctx.lineTo((pointX1 + pointX2) / 2, ((pointY1 + pointY2) / 2));
	ctx.stroke();
	ctx.setLineDash([]);
	ctx.beginPath();
	ctx.moveTo(pointX1, pointY1);
	ctx.lineTo(pointX2, pointY2);
	ctx.lineTo(endX, endY);
	ctx.fill();
	ctx.stroke();
	if(type !== 1) {
		ctx.beginPath();
		ctx.font = "14px Arial";
		if(weightTag === 0) {
			ctx.fillText(work, startX - 14 * (work.length / 2), (endY + startY) / 2 - 8);
			ctx.fillText(time + "天", startX + 2, ((endY + startY) / 2 + 18));
		} else {
			ctx.fillText(work, (c1x + c2x) / 2 - 14 * (work.length / 2), midY - 8);
			ctx.fillText(time + "天", (c1x + c2x) / 2 - 14, midY + 18);
		}
	}
	ctx.strokeStyle = "black";
	ctx.fillStyle = "black";
	linePointList.push(linePoint);
	for(var j = 0; j < len; j++) {
		if(num1 === circleNumberList[j].num) {
			circleNumberList[j].nextNumList.push(num2);
		}
	}
}

//点击获取计划图元素
canvas.onclick = function(ev) {
	blockClick();
	//鼠标按下的位置
	var clickX = ev.offsetX;
	var clickY = ev.offsetY;
	var len = circleNumberList.length;
	for(var i = len - 1; i >= 0; i--) {
		var clickSpace = Math.pow((clickX - circleNumberList[i].x), 2) + Math.pow((clickY - circleNumberList[i].y), 2);
		if(clickSpace <= 400) {
			showCircleNumber(i);
			return;
		}
	}
	var len2 = linePointList.length;
	for(var j = len2 - 1; j >= 0; j--) {
		if(linePointList[j].loc === 1) {
			if(linePointList[j].dec === 1) {
				if(Math.abs(clickX - linePointList[j].c1x) <= 15) {
					if(clickY <= linePointList[j].c1y && clickY >= linePointList[j].c2y) {
						showLinePoint(j);
						return;
					}
				}
				if(Math.abs(clickY - linePointList[j].c2y) <= 15) {
					if(clickX <= linePointList[j].c2x && clickX >= linePointList[j].c1x) {
						showLinePoint(j);
						return;
					}
				}
			} else if(linePointList[j].dec === 2) {
				if(Math.abs(clickX - linePointList[j].c2x) <= 15) {
					if(clickY <= linePointList[j].c1y && clickY >= linePointList[j].c2y) {
						showLinePoint(j);
						return;
					}
				}
				if(Math.abs(clickY - linePointList[j].c1y) <= 15) {
					if(clickX <= linePointList[j].c2x && clickX >= linePointList[j].c1x) {
						showLinePoint(j);
						return;
					}
				}
			} else {
				if(clickX <= linePointList[j].c2x && clickX >= linePointList[j].c1x && clickY <= linePointList[j].c1y && clickY >= linePointList[j].c2y) {
					var c1x = linePointList[j].c1x;
					var c1y = linePointList[j].c1y;
					var c2x = linePointList[j].c2x;
					var c2y = linePointList[j].c2y;
					var a = Math.sqrt((clickX - c1x) * (clickX - c1x) + (clickY - c1y) * (clickY - c1y));
					var b = Math.sqrt((clickX - c2x) * (clickX - c2x) + (clickY - c2y) * (clickY - c2y));
					var c = Math.sqrt((c2x - c1x) * (c2x - c1x) + (c2y - c1y) * (c2y - c1y));
					var p = (a + b + c) / 2;
					var d = Math.sqrt(p * (p - a) * (p - b) * (p - c)) * 2 / c;
					if(d < 15) {
						showLinePoint(j);
						return;
					}
				}
			}
		} else if(linePointList[j].loc === 2) {
			if(linePointList[j].dec === 1) {
				if(Math.abs(clickX - linePointList[j].c2x) <= 15) {
					if(clickY <= linePointList[j].c2y && clickY >= linePointList[j].c1y) {
						showLinePoint(j);
						return;
					}
				}
				if(Math.abs(clickY - linePointList[j].c1y) <= 15) {
					if(clickX <= linePointList[j].c2x && clickX >= linePointList[j].c1x) {
						showLinePoint(j);
						return;
					}
				}
			} else if(linePointList[j].dec === 2) {
				if(Math.abs(clickX - linePointList[j].c1x) <= 15) {
					if(clickY <= linePointList[j].c2y && clickY >= linePointList[j].c1y) {
						showLinePoint(j);
						return;
					}
				}
				if(Math.abs(clickY - linePointList[j].c2y) <= 15) {
					if(clickX <= linePointList[j].c2x && clickX >= linePointList[j].c1x) {
						showLinePoint(j);
						return;
					}
				}
			} else {
				if(clickX <= linePointList[j].c2x && clickX >= linePointList[j].c1x && clickY <= linePointList[j].c2y && clickY >= linePointList[j].c1y) {
					var c1x = linePointList[j].c1x;
					var c1y = linePointList[j].c1y;
					var c2x = linePointList[j].c2x;
					var c2y = linePointList[j].c2y;
					var a = Math.sqrt((clickX - c1x) * (clickX - c1x) + (clickY - c1y) * (clickY - c1y));
					var b = Math.sqrt((clickX - c2x) * (clickX - c2x) + (clickY - c2y) * (clickY - c2y));
					var c = Math.sqrt((c2x - c1x) * (c2x - c1x) + (c2y - c1y) * (c2y - c1y));
					var p = (a + b + c) / 2;
					var d = Math.sqrt(p * (p - a) * (p - b) * (p - c)) * 2 / c;
					if(d < 15) {
						showLinePoint(j);
						return;
					}
				}
			}
		} else if(linePointList[j].loc === 3) {
			if(linePointList[j].dec === 1) {
				if(Math.abs(clickX - linePointList[j].c1x) <= 15) {
					if(clickY <= linePointList[j].c2y && clickY >= linePointList[j].c1y) {
						showLinePoint(j);
						return;
					}
				}
				if(Math.abs(clickY - linePointList[j].c2y) <= 15) {
					if(clickX <= linePointList[j].c1x && clickX >= linePointList[j].c2x) {
						showLinePoint(j);
						return;
					}
				}
			} else if(linePointList[j].dec === 2) {
				if(Math.abs(clickX - linePointList[j].c2x) <= 15) {
					if(clickY <= linePointList[j].c2y && clickY >= linePointList[j].c1y) {
						showLinePoint(j);
						return;
					}
				}
				if(Math.abs(clickY - linePointList[j].c1y) <= 15) {
					if(clickX <= linePointList[j].c1x && clickX >= linePointList[j].c2x) {
						showLinePoint(j);
						return;
					}
				}
			} else {
				if(clickX <= linePointList[j].c1x && clickX >= linePointList[j].c2x && clickY <= linePointList[j].c2y && clickY >= linePointList[j].c1y) {
					var c1x = linePointList[j].c1x;
					var c1y = linePointList[j].c1y;
					var c2x = linePointList[j].c2x;
					var c2y = linePointList[j].c2y;
					var a = Math.sqrt((clickX - c1x) * (clickX - c1x) + (clickY - c1y) * (clickY - c1y));
					var b = Math.sqrt((clickX - c2x) * (clickX - c2x) + (clickY - c2y) * (clickY - c2y));
					var c = Math.sqrt((c2x - c1x) * (c2x - c1x) + (c2y - c1y) * (c2y - c1y));
					var p = (a + b + c) / 2;
					var d = Math.sqrt(p * (p - a) * (p - b) * (p - c)) * 2 / c;
					if(d < 15) {
						showLinePoint(j);
						return;
					}
				}
			}
		} else if(linePointList[j].loc === 4) {
			if(linePointList[j].dec === 1) {
				if(Math.abs(clickX - linePointList[j].c2x) <= 15) {
					if(clickY <= linePointList[j].c1y && clickY >= linePointList[j].c2y) {
						showLinePoint(j);
						return;
					}
				}
				if(Math.abs(clickY - linePointList[j].c1y) <= 15) {
					if(clickX <= linePointList[j].c1x && clickX >= linePointList[j].c2x) {
						showLinePoint(j);
						return;
					}
				}
			} else if(linePointList[j].dec === 2) {
				if(Math.abs(clickX - linePointList[j].c1x) <= 15) {
					if(clickY <= linePointList[j].c1y && clickY >= linePointList[j].c2y) {
						showLinePoint(j);
						return;
					}
				}
				if(Math.abs(clickY - linePointList[j].c2y) <= 15) {
					if(clickX <= linePointList[j].c1x && clickX >= linePointList[j].c2x) {
						showLinePoint(j);
						return;
					}
				}
			} else {
				if(clickX <= linePointList[j].c1x && clickX >= linePointList[j].c2x && clickY <= linePointList[j].c1y && clickY >= linePointList[j].c2y) {
					var c1x = linePointList[j].c1x;
					var c1y = linePointList[j].c1y;
					var c2x = linePointList[j].c2x;
					var c2y = linePointList[j].c2y;
					var a = Math.sqrt((clickX - c1x) * (clickX - c1x) + (clickY - c1y) * (clickY - c1y));
					var b = Math.sqrt((clickX - c2x) * (clickX - c2x) + (clickY - c2y) * (clickY - c2y));
					var c = Math.sqrt((c2x - c1x) * (c2x - c1x) + (c2y - c1y) * (c2y - c1y));
					var p = (a + b + c) / 2;
					var d = Math.sqrt(p * (p - a) * (p - b) * (p - c)) * 2 / c;
					if(d < 15) {
						showLinePoint(j);
						return;
					}
				}
			}
		} else if(linePointList[j].loc === 5) {
			if(Math.abs(clickX - linePointList[j].c1x) <= 15) {
				if(clickY <= linePointList[j].c1y && clickY >= linePointList[j].c2y) {
					showLinePoint(j);
					return;
				}
			}
		} else if(linePointList[j].loc === 6) {
			if(Math.abs(clickY - linePointList[j].c1y) <= 15) {
				if(clickX <= linePointList[j].c2x && clickX >= linePointList[j].c1x) {
					showLinePoint(j);
					return;
				}
			}
		} else if(linePointList[j].loc === 7) {
			if(Math.abs(clickX - linePointList[j].c1x) <= 15) {
				if(clickY <= linePointList[j].c2y && clickY >= linePointList[j].c1y) {
					showLinePoint(j);
					return;
				}
			}
		} else if(linePointList[j].loc === 8) {
			if(Math.abs(clickY - linePointList[j].c1y) <= 15) {
				if(clickX <= linePointList[j].c1x && clickX >= linePointList[j].c2x) {
					showLinePoint(j);
					return;
				}
			}
		}
	}
};

//空白点击
function blockClick() {
	nowIndex = -1;
	nowType = -1;
	$('#messageBox').html('<tr><td>当前元素</td><td>无</td></tr>');
}

//展示代号信息
function showCircleNumber(i) {
	nowIndex = i;
	nowType = 1;
	layer.msg('您选择了代号:' + circleNumberList[i].num);
	var txt = '<tr><td>代号</td><td>' + circleNumberList[i].num + '</td></tr>';
	txt += '<tr><td>X坐标</td><td>' + circleNumberList[i].x + '</td></tr>';
	txt += '<tr><td>Y坐标</td><td>' + circleNumberList[i].y + '</td></tr>';
	$('#messageBox').html(txt);
}

//展示方向线信息
function showLinePoint(i) {
	nowIndex = i;
	nowType = 2;
	layer.msg('您选择了 ' + linePointList[i].num1 + '->' + linePointList[i].num2 + ' 方向线');
	var txt = '<tr><td>方向线</td><td>' + linePointList[i].num1 + ' --> ' + linePointList[i].num2 + '</td></tr>';
	txt += '<tr><td>工作描述</td><td>' + linePointList[i].work + '</td></tr>';
	txt += '<tr><td>工作天数</td><td>' + linePointList[i].time + '天</td></tr>';
	txt += '<tr><td>工作人数</td><td>' + linePointList[i].man + '人</td></tr>';
	var start = linePointList[i].ES === -1 ? '未计算' : new Date(planStartTime + 86400000 * linePointList[i].start).format("yyyy-MM-dd");
	txt += '<tr><td>实际开始时间</td><td>' + start + '</td></tr>';
	var end = linePointList[i].ES === -1 ? '未计算' : new Date(planStartTime + 86400000 * linePointList[i].end).format("yyyy-MM-dd");
	txt += '<tr><td>实际结束时间</td><td>' + end + '</td></tr>';
	var es = linePointList[i].ES === -1 ? '未计算' : new Date(planStartTime + 86400000 * linePointList[i].ES).format("yyyy-MM-dd");
	txt += '<tr><td>最早开始时间</td><td>' + es + '</td></tr>';
	var ef = linePointList[i].EF === -1 ? '未计算' : new Date(planStartTime + 86400000 * linePointList[i].EF).format("yyyy-MM-dd");
	txt += '<tr><td>最早完成时间</td><td>' + ef + '</td></tr>';
	var ls = linePointList[i].LS === -1 ? '未计算' : new Date(planStartTime + 86400000 * linePointList[i].LS).format("yyyy-MM-dd");
	txt += '<tr><td>最晚开始时间</td><td>' + ls + '</td></tr>';
	var lf = linePointList[i].LF === -1 ? '未计算' : new Date(planStartTime + 86400000 * linePointList[i].LF).format("yyyy-MM-dd");
	txt += '<tr><td>最晚完成时间</td><td>' + lf + '</td></tr>';
	var tf = linePointList[i].TF === -1 ? '未计算' : linePointList[i].TF + '天';
	txt += '<tr><td>总时差</td><td>' + tf + '</td></tr>';
	var ff = linePointList[i].FF === -1 ? '未计算' : linePointList[i].FF + '天';
	txt += '<tr><td>自由时差</td><td>' + ff + '</td></tr>';
	$('#messageBox').html(txt);
}

//清空画布
function deletePlanPic() {
	var width = canvas.offsetWidth;
	var height = canvas.offsetHeight;
	ctx.clearRect(0, 0, width, height);
}

//画时间线
function drawGrid() {
	ctx.fillStyle = "#000000";
	ctx.beginPath();
	ctx.moveTo(100, 50);
	ctx.lineTo(200 + planTimeLimit * dayWidth, 50);
	ctx.stroke();
	ctx.beginPath();
	ctx.moveTo(200 + planTimeLimit * dayWidth, 50 - 5);
	ctx.lineTo(200 + planTimeLimit * dayWidth, 50 + 5);
	ctx.lineTo(210 + planTimeLimit * dayWidth, 50);
	ctx.fill();
	ctx.stroke();
	ctx.font = "14px Arial";
	ctx.fillStyle = "#007DDB";
	ctx.strokeStyle = "#DDDDDD";
	var fontY = 40;
	for(var i = 0; i < planTimeLimit * 1 + 1; i++) {
		ctx.beginPath();
		ctx.moveTo(100 + i * dayWidth, 50);
		ctx.lineTo(100 + i * dayWidth, canvas.offsetHeight);
		ctx.stroke();
		if(dayWidth === 50) {
			if(i % 2 === 0) {
				fontY = 45;
			} else {
				fontY = 30;
			}
		}
		ctx.fillText(new Date(planStartTime + 86400000 * i).format("yyyy/MM/dd"), 65 + i * dayWidth, fontY);
	}
}

//画劳动力曲线
function drawManLine() {
	//计算劳动力曲线
	var manList = [];
	for(var i = 0; i < planTimeLimit; i++) {
		var manCount = {
			"num": i,
			"count": 0
		};
		manList.push(manCount);
	}
	var len = linePointList.length;
	for(var j = 0; j < len; j++) {
		var temp = linePointList[j].start * 1;
		while(temp < linePointList[j].end * 1) {
			for(var k = 0; k < planTimeLimit; k++) {
				if(manList[k].num === temp) {
					manList[k].count += linePointList[j].man;
				}
			}
			temp++;
		}
	}
	//画劳动力曲线
	var len1 = circleNumberList.length;
	var lineY = 0;
	for(var k = 0; k < len1; k++) {
		if(lineY < circleNumberList[k].y) {
			lineY = circleNumberList[k].y * 1;
		}
	}
	lineY = lineY + 100;
	var maxManCount = 0;
	var minManCount = 10000;
	var tagHeight = 0.2;
	for(var n = 0; n < planTimeLimit; n++) {
		if(maxManCount < manList[n].count) {
			maxManCount = manList[n].count;
		}
		if(minManCount > manList[n].count) {
			minManCount = manList[n].count;
		}
	}
	if(maxManCount < 10) {
		tagHeight = 10;
	} else if(maxManCount < 20) {
		tagHeight = 5;
	} else if(maxManCount < 50) {
		tagHeight = 3;
	} else if(maxManCount < 100) {
		tagHeight = 2;
	} else if(maxManCount < 200) {
		if(minManCount > 100) {
			tagHeight = 0.5;
		} else {
			tagHeight = 1;
		}

	} else if(minManCount > 100) {
		tagHeight = 0.2;
	} else if(minManCount > 50) {
		tagHeight = 0.3;
	}
	ctx.strokeStyle = "#000000";
	ctx.font = "14px Arial";
	ctx.fillStyle = "#000000";
	ctx.beginPath();
	ctx.moveTo(100, lineY);
	for(var m = 0; m < planTimeLimit; m++) {
		ctx.lineTo(100 + manList[m].num * dayWidth, lineY + tagHeight * manList[m].count);
		ctx.lineTo(100 + (manList[m].num * 1 + 1) * dayWidth, lineY + tagHeight * manList[m].count);
		ctx.fillText(manList[m].count, 90 + (manList[m].num * 1 + 0.5) * dayWidth, lineY + tagHeight * manList[m].count + 15);

	}
	ctx.lineTo(100 + planTimeLimit * dayWidth, lineY);
	ctx.lineTo(100, lineY);
	ctx.stroke();
}

//画网络计划图
function drawPlanPic(list1, list2) {
	if(ifGrid) {
		drawGrid();
		if(ifManLine) {
			drawManLine();
		}
	}
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
		drawLinePoint(nowLinePoint.num1, nowLinePoint.num2, nowLinePoint.type, nowLinePoint.dec, nowLinePoint.work, nowLinePoint.time, nowLinePoint.man,
			nowLinePoint.start, nowLinePoint.end, nowLinePoint.ES, nowLinePoint.LS, nowLinePoint.EF, nowLinePoint.LF, nowLinePoint.TF, nowLinePoint.FF);
	}
}

//重生成
function reDrawPlanPic() {
	blockClick();
	deletePlanPic();
	drawPlanPic(circleNumberList, linePointList);
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
	reDrawPlanPic();
}

//操作成功提示
function successTip() {
	blockClick();
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
	haveChange = true;
	var circleX = parseInt($('#circleX').val());
	var circleY = parseInt($('#circleY').val());
	var circleNum = parseInt($('#circleNum').val());
	var createNumTitle = $('#createNumTitle').text();
	if(!/^\+?[1-9][0-9]*$/.test(circleX)) {
		layer.msg('X坐标必须为50的正倍数！');
		return;
	}
	if(circleX % 50 !== 0) {
		layer.msg('X坐标必须为50的正倍数！');
		return;
	}
	if(!/^\+?[1-9][0-9]*$/.test(circleY)) {
		layer.msg('Y坐标必须为50的正倍数！');
		return;
	}
	if(circleY % 50 !== 0) {
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
                nowIndex=circleNumberList.length-1;
			}
			return;
		}
	}
	if(createNumTitle === '编辑代号') {
		var circleNumber = {
			"x": circleX,
			"y": circleY,
			"num": circleNum,
			"nextNumList": [],
            "frontNumList": []
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
	haveNew = false;
	blockClick();
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
	$('#needPeople').val(0);
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
	haveChange = true;
	var smallNum = parseInt($('#smallNum option:selected').prop("value"));
	var largeNum = parseInt($('#largeNum option:selected').prop("value"));
	if(smallNum >= largeNum) {
		layer.msg('小代号必须小于大代号！');
		return;
	}
	var lineType = parseInt($('#lineType option:selected').prop("value"));
	if(ifchange) {
		var len1 = circleNumberList.length;
		var tempSx = 0;
		var tempSy = 0;
		var tempLx = 0;
		var tempLy = 0;
		for(var k = 0; k < len1; k++) {
			if(circleNumberList[k].num === smallNum) {
				tempSx = circleNumberList[k].x;
				tempSy = circleNumberList[k].y;
			}
			if(circleNumberList[k].num === largeNum) {
				tempLx = circleNumberList[k].x;
				tempLy = circleNumberList[k].y;
			}
		}
		if(lineType === 1) {
			if(tempLy - tempSy === 0) {
				layer.msg('时标网络计划中虚线的起始代号不能位于同一高度！');
				return;
			}
		} else {
			if(tempLx - tempSx === 0) {
				layer.msg('时标网络计划中实线的起始代号X坐标不能相同！');
				return;
			}
		}
	}
	var createPointTitle = $('#createPointTitle').text();
	var lineDirection = parseInt($('#lineDirection option:selected').prop("value"));
	var workText = $('#workText').val();
	var workDays = parseInt($('#workDays').val());
	var needPeople = parseInt($('#needPeople').val());
	if(lineType === 0) {
		if(!/^\+?[1-9][0-9]*$/.test(workDays)) {
			layer.msg('工作天数必须为大于0的正整数！');
			return;
		}
		if(workText === "") {
			layer.msg('工作内容不能为空！');
			return;
		}
		if(!/^\d+$/.test(needPeople)) {
			layer.msg('人数必须为正整数');
			return;
		}
	}
	var workStartTime = new Date($('#workStartTime').val()).getTime();
	var start = (workStartTime - planStartTime) / 86400000;
	if(start < 0) {
		start = 0;
	}
	var nowLinePoint = linePointList[nowIndex];
	if(createPointTitle === "编辑方向线") {
		deleteLinePoint();
	}
	var len = linePointList.length;
	for(var i = 0; i < len; i++) {
		if(linePointList[i].num1 === smallNum && linePointList[i].num2 === largeNum) {
			linePointList.push(nowLinePoint);
			layer.msg('已经存在的方向线！');
			return;
		}
	}
	drawLinePoint(smallNum, largeNum, lineType, lineDirection, workText, workDays, needPeople, start);
	successTip();
});

//重生成按钮点击事件
$('#reDraw').click(function() {
	$('#drawWidth').val(canvas.offsetWidth);
	$('#drawHeight').val(canvas.offsetHeight);
	layer.open({
		type: 1,
		shadeClose: true,
		area: [widthMax, heightMax], //宽高
		content: $('#redrawBox')
	});
});

//重生成页面确定按钮点击事件
$('#confirmRedraw').click(function() {
	var moveX = parseInt($('#drawWidth').val());
	var moveY = parseInt($('#drawHeight').val());
	if(!/^\d+$/.test(moveX)) {
		layer.msg('画布宽度必须为正整数!');
		return;
	}
	if(!/^\d+$/.test(moveY)) {
		layer.msg('画布高度必须为正整数!');
		return;
	}
	canvas.setAttribute("width", moveX);
	canvas.setAttribute("height", moveY);
	reDrawPlanPic();
	successTip();
});

//新建方法
function newPlan() {
	$('#newPlanLegend').text('新建计划');
	$('#planTimeLimit').val(0);
	$('#planIfChange').html("");
	$('#planIfChange').append('<option value="0" selected="">普通网络计划</option>');
	$('#planIfChange').append('<option value="1">时标网络计划</option>');
	$('#planTimeWidth').html("");
	$('#planTimeWidth').append('<option value="50">50</option>');
	$('#planTimeWidth').append('<option value="100" selected="">100</option>');
	$('#planTimeWidth').append('<option value="150">150</option>');
	form.render();
	laydate.render({
		elem: '#startTimeDate',
		type: 'date',
		value: new Date().format("yyyy-MM-dd")
	});
	layer.open({
		type: 1,
		shadeClose: true,
		area: [widthMax, heightMax], //宽高
		content: $('#writeNewPlan')
	});
}

//新建(计算)页面确定按钮点击事件
$('#confirmNewPlan').click(function() {
	var temp = $('#planTimeLimit').val();
	if(!/^\d+$/.test(temp)) {
		layer.msg('计划工期必须为正整数!');
		return;
	}
	var timeTemp = $('#startTimeDate').val();
	if(timeTemp === '') {
		layer.msg('开始时期不能为空!');
		return;
	}
	var dateTemp = new Date(timeTemp);
	planStartTime = dateTemp.getTime();
	oldPlanName = $('#newPlanName').val();
	planTimeLimit = temp;
	ifchange = ($('#planIfChange option:selected').prop("value") * 1 === 1);
	if(!ifchange) {
		$('#timeFlow').hide();
		ifGrid = false;
		$('#showManLine').hide();
		ifManLine = false;
	} else {
		$('#timeFlow').show();
		$('#timeFlow').text('开启时间线');
		ifGrid = false;
		$('#showManLine').show();
		$('#showManLine').text('开启劳动力曲线');
		ifManLine = false;
	}
	dayWidth = $('#planTimeWidth option:selected').prop("value") * 1;
	var flag = true;
	if($('#newPlanLegend').text() === '新建计划') {
		deletePlanPic();
		ajaxClick = false;
		circleNumberList = [];
		linePointList = [];
		nowIndex = -1;
		nowType = -1;
		haveChange = false;
		haveNew = false;
		dayWidth = 100;
	} else {
		flag = calculateAll();
	}
	if(flag) {
		showPlanMessage();
		successTip();
	}
});

//新建按钮点击事件
$('#add').click(function() {
	if(circleNumberList.length !== 0) {
		layer.confirm('需要保存当前的网络计划吗？', {
			btn: ['保存', '不保存', '取消'] //按钮
		}, function() {
			haveNew = true;
			$('#save').click();
		}, function() {
			newPlan();
		}, function() {
			$('.goNot').click();
		});
	} else {
		newPlan();
	}
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
		laydate.render({
			elem: '#workStartTime',
			type: 'date',
			value: new Date(linePointList[nowIndex].start * 1 * 86400000 + planStartTime).format("yyyy-MM-dd")
		});
		$('#needPeople').val(linePointList[nowIndex].man);
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
		$('#lineDirection option[value=' + linePointList[nowIndex].dec + ']').prop("selected", true);
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
	if(moveX % 50 !== 0) {
		layer.msg('X坐标增量必须为50的正倍数！');
		return;
	}
	if(!/^\d+$/.test(moveY)) {
		layer.msg('Y坐标增量必须为50的正倍数！');
		return;
	}
	if(moveY % 50 !== 0) {
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
var haveGetProject=false;
$('#save').click(function() {
	var workManName=$("#userName", parent.document).attr("name");
    if(workManName===null || workManName==='' || workManName==='undefined' || typeof workManName==='undefined'){
        layer.msg('您没有登陆,无权使用保存功能！');
        return;
    }
	$('#workMan').val(workManName);
	$('#planName').val(oldPlanName);
	$('#savePlanTimeLimit').val(planTimeLimit);
	$('#saveStartTimeDate').val(new Date(planStartTime).format("yyyy-MM-dd"));
	if(!haveGetProject){
        $.ajax({
            url: "/project/getprojecttoselect",
            type: "get",
            dataType: "json", //预测服务端返回的数据类型
            success: function(data) { //请求成功的回调
                var proList = data;
                var proListLen = proList.length;
                for(var i = 0; i < proListLen; i++) {
                    $("#userProject").append('<option value="' + proList[i].projectId + '">' + proList[i].projectName + '</option>');
                }
                form.render('select');
                haveGetProject=true;
            },
            error: function() { //请求失败的回调
                layer.msg('发生了未知错误！', {
                    icon: 2
                });
            }
        });
	}
	layer.open({
		type: 1,
		shadeClose: true,
		area: [widthMax, heightMax], //宽高
		content: $('#saveBox')
	});
});

//保存页面确定按钮
$('#savePlan').click(function() {
	if(!ajaxClick) {
		ajaxClick = true;
		var workMan = $('#workMan').val();
		var planName = $('#planName').val();
		var timeLimit = $('#savePlanTimeLimit').val();
		var startTime = $('#saveStartTimeDate').val();
		var userProject=$('#userProject option:selected').prop("value");
		if(planName === "") {
			layer.msg('计划名称不能为空！');
			return;
		}
		if(userProject===''){
            layer.msg('项目名称不能为空！');
            return;
		}
		$.ajax({
			url: '/plan/insertPlan',
			type: "get",
			data: {
				"workMan": workMan,
				"planName": planName,
				"timeLimit": timeLimit,
				"startTime": startTime,
				"planType": ifchange,
				"timeWidth": dayWidth,
				"circleList": JSON.stringify(circleNumberList),
				"pointList": JSON.stringify(linePointList),
				"project":userProject
			},
			dataType: "text", //预测服务端返回的数据类型
			success: function(data) { //请求成功的回调
				if(data === 'true') {
					oldPlanName = planName;
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
		ajaxClick = false;
	}
	if(haveNew) {
		newPlan();
	}
	showPlanMessage();
});

//打开按钮的点击事件
$('#open').click(function() {
	if(!ajaxClick) {
		ajaxClick = true;
        $.ajax({
            url: "/project/getprojecttoselect",
            type: "get",
            dataType: "json", //预测服务端返回的数据类型
            success: function(data) { //请求成功的回调
                var proList = data;
                var proListLen = proList.length;
                $('#project').html('<option value="">直接选择或搜索选择项目</option>');
                for(var i = 0; i < proListLen; i++) {
                    $("#project").append('<option value="' + proList[i].projectId + '">' + proList[i].projectName + '</option>');
                }
                form.render('select');
            },
            error: function() { //请求失败的回调
                layer.msg('发生了未知错误！', {
                    icon: 2
                });
            }
        });
		layer.open({
			type: 1,
			shadeClose: true,
			area: [widthMax, heightMax], //宽高
			content: $('#openBox')
		});
		ajaxClick = false;
	}
});

//打开页面确定按钮的点击事件
$('#openPlan').click(function() {
	var id = $('#selectPlan option:selected').prop("value");
	if(id === "") {
		layer.msg('请选择要打开的网络计划!');
		return;
	}
	if(!ajaxClick) {
		ajaxClick = true;
		$.ajax({
			url: '/plan/selectPlanText',
			type: "get",
			data: {
				"id": id
			},
			dataType: "json", //预测服务端返回的数据类型
			success: function(data) { //请求成功的回调
				var cList = JSON.parse(data.circle_list);
				var pList = JSON.parse(data.point_list);
				oldPlanName = data.plan_name;
				planStartTime = new Date(data.start_time).getTime();
				planTimeLimit = data.time_limit;
				ifchange = data.plan_type === 'true';
				if(!ifchange) {
					$('#timeFlow').hide();
					ifGrid = false;
					$('#showManLine').hide();
					ifManLine = false;
				} else {
					$('#timeFlow').show();
					$('#timeFlow').text('开启时间线');
					ifGrid = false;
					$('#showManLine').show();
					$('#showManLine').text('开启劳动力曲线');
					ifManLine = false;
				}
				dayWidth = data.time_width * 1;
				showPlanMessage();
				deletePlanPic();
				drawPlanPic(cList, pList);
				successTip();
			},
			error: function() { //请求失败的回调
				layer.msg('发生了未知错误！', {
					icon: 2
				});
			}
		});
		ajaxClick = false;
	}
});

//计算每一个代号以这个代号开始的工作的最早开始时间ES及最早结束时间EF
function calculateEarlyStartEnd(s) {
	var nowNum = circleNumberList[s].num;
	var len = linePointList.length;
	for(var i = 0; i < len; i++) {
		if(linePointList[i].num1 === nowNum) { //if通过，说明linePointList[i]是以当前代号开始的工作
			for(var j = 0; j < len; j++) {
				if(linePointList[j].num2 === nowNum) { //if通过，说明linePointList[j]是以当前代号结束的工作
					if(linePointList[i].ES < linePointList[j].EF) {
						linePointList[i].ES = linePointList[j].EF;
					}
				}
			}
			if(linePointList[i].ES === -1) {
				linePointList[i].ES = 0;
			}
			linePointList[i].EF = linePointList[i].ES + linePointList[i].time;
		}
	}
}

//计算网络计划图所有工作的最早开始时间ES及最早结束时间EF
function calculateAllEarlyStartEnd() {
	var len = circleNumberList.length;
	for(var i = 0; i < len; i++) {
		calculateEarlyStartEnd(i);
	}
}

//计算每一个代号以这个代号结束的工作的最晚开始时间LS及最晚结束时间LF及自由时差FF
function calculateLastStartEnd(s) {
	var nowNum = circleNumberList[s].num;
	var len = linePointList.length;
	for(var i = 0; i < len; i++) {
		if(linePointList[i].num2 === nowNum) { //if通过，说明linePointList[i]是以当前代号结束的工作
			if(nowNum === circleNumberList[circleNumberList.length - 1].num) {
				if(planTimeLimit < linePointList[i].EF) {
					planTimeLimit = linePointList[i].EF * 1;
				}
				linePointList[i].LF = planTimeLimit*1;
			}
			var freeTime = 0;
			for(var j = 0; j < len; j++) {
				if(linePointList[j].num1 === nowNum) { //if通过，说明linePointList[j]是以当前代号开始的工作
					var temp = linePointList[j].ES - linePointList[i].EF;
					if(freeTime === 0) {
						freeTime = temp;
					} else if(freeTime > temp) {
						freeTime = temp;
					}
					if(linePointList[i].LF === -1) {
						linePointList[i].LF = linePointList[j].LS*1;
					} else if(linePointList[j].LS < linePointList[i].LF) {
						linePointList[i].LF = linePointList[j].LS*1;
					}
				}
			}
			linePointList[i].LS = linePointList[i].LF - linePointList[i].time;
			linePointList[i].FF = freeTime;
		}
	}
}

//计算网络计划图所有工作的最晚开始时间LS及最晚结束时间LF
function calculateAllLastStartEnd() {
	var len = circleNumberList.length;
	for(var i = len - 1; i >= 0; i--) {
		calculateLastStartEnd(i);
	}
}

//重新定义代号X坐标
function reWriteCircleX() {
	var len1 = circleNumberList.length;
	var len2 = linePointList.length;
	for(var i = 0; i < len1; i++) {
		var x = 100;
		for(var j = 0; j < len2; j++) {
			if(linePointList[j].num2 === circleNumberList[i].num) {
				var temp1 = 100 + linePointList[j].end * dayWidth;
				if(temp1 > x) {
					x = temp1;
				}
			}
		}
		circleNumberList[i].x = x;
	}
	if(len1 > 0) {
        circleNumberList[0].x=1000000;
		for(var k = 0; k < len2; k++) {
			if(linePointList[k].num1 === circleNumberList[0].num) {
				var temp = 100 + linePointList[k].start * dayWidth;
				if(temp < circleNumberList[0].x) {
					circleNumberList[0].x = temp;
				}
			}
		}
	}
}

//将代号从小到大进行排序
function sortCircleNumberList() {
	var list = [];
	var temp = [];
	var index = -1;
	var flag = 0;
	var len = circleNumberList.length;
	for(var i = 0; i < len; i++) {
		temp = list;
		var len2 = temp.length;
		if(len2 !== 0) {
			for(var j = 0; j < len2; j++) {
				if(circleNumberList[i].num < temp[j].num) {
					index = j;
					break;
				}
			}
			list = [];
			for(var j = 0; j < len2; j++) {
				if(j === index) {
					list.push(circleNumberList[i]);
					index = -1;
					flag = 1;
				}
				list.push(temp[j]);
			}
			if(index === -1 && flag === 0) {
				list.push(circleNumberList[i]);
			}
			flag = 0;
		} else {
			list.push(circleNumberList[i]);
		}
	}
	circleNumberList = list;
}

//计算frontNumList
function fullFrontNumList() {
	var len = circleNumberList.length;
	for(var i = 0; i < len; i++) {
		var nextNumList = circleNumberList[i].nextNumList;
		var nextLen = nextNumList.length;
		for(var j = 0; j < nextLen; j++) {
			for(var k = 0; k < len; k++) {
				if(circleNumberList[k].num === nextNumList[j]) {
					circleNumberList[k].frontNumList.push(circleNumberList[i].num);
				}
			}
		}
	}
}

//计算总时差TF与实际开始时间与实际结束时间
function calculateTotalFloat() {
	var len = linePointList.length;
	var len1 = circleNumberList.length;
	for(var k = 0; k < len1; k++) {
		for(var i = 0; i < len; i++) {
			if(circleNumberList[k].num === linePointList[i].num1) {
				linePointList[i].TF = linePointList[i].LF - linePointList[i].EF;
				if(linePointList[i].start < linePointList[i].ES) {
					linePointList[i].start = linePointList[i].ES*1;
				}
				if(linePointList[i].start > linePointList[i].LS) {
					linePointList[i].start = linePointList[i].LS*1;
				}
				linePointList[i].end = linePointList[i].start * 1 + linePointList[i].time * 1;
				if(linePointList[i].TF * 1 !== 0) {
					for(var j = 0; j < len; j++) {
						if(linePointList[i].num2 === linePointList[j].num1) {
							if(linePointList[j].start < linePointList[i].end) {
								linePointList[j].start = linePointList[i].end * 1;
							}
							linePointList[j].end = linePointList[j].start * 1 + linePointList[j].time * 1;
						}
					}
				}
			}
		}
	}
}

//计算按钮的点击事件
$('#calculate').click(function() {
	$('#newPlanLegend').text('请确认计算参数!');
	$('#planTimeLimit').val(planTimeLimit);
	$('#newPlanName').val(oldPlanName);
	$('#planIfChange').html("");
	if(ifchange) {
		$('#planIfChange').append('<option value="0">普通网络计划</option>');
		$('#planIfChange').append('<option value="1" selected="">时标网络计划</option>');
	} else {
		$('#planIfChange').append('<option value="0" selected="">普通网络计划</option>');
		$('#planIfChange').append('<option value="1">时标网络计划</option>');
	}
	$('#planTimeWidth').html("");
	if(dayWidth === 100) {
		$('#planTimeWidth').append('<option value="50">50</option>');
		$('#planTimeWidth').append('<option value="100" selected="">100</option>');
		$('#planTimeWidth').append('<option value="150">150</option>');
	} else if(dayWidth === 50) {
		$('#planTimeWidth').append('<option value="50" selected="">50</option>');
		$('#planTimeWidth').append('<option value="100">100</option>');
		$('#planTimeWidth').append('<option value="150">150</option>');
	} else {
		$('#planTimeWidth').append('<option value="50">50</option>');
		$('#planTimeWidth').append('<option value="100">100</option>');
		$('#planTimeWidth').append('<option value="150" selected="">150</option>');
	}
	form.render();
	laydate.render({
		elem: '#startTimeDate',
		type: 'date',
		value: new Date(planStartTime).format("yyyy-MM-dd")
	});
	layer.open({
		type: 1,
		shadeClose: true,
		area: [widthMax, heightMax], //宽高
		content: $('#writeNewPlan')
	});
});

//计算网络计划的所有参数
var testCalculate=true;
function calculateAll() {
	haveChange = true;
	reDrawPlanPic();
	fullFrontNumList();
	var start = 0;
	var end = 0;
	var len = circleNumberList.length;
	for(var i = 0; i < len; i++) {
		if(circleNumberList[i].frontNumList.length === 0) {
			start++;
		}
		if(circleNumberList[i].nextNumList.length === 0) {
			end++;
		}
	}
	if(start > 1) {
		layer.msg('计算失败,开始代号必须唯一!');
		return false;
	}
	if(end > 1) {
		layer.msg('计算失败,结束代号必须唯一!');
		return false;
	}
	sortCircleNumberList();
	calculateAllEarlyStartEnd();
	calculateAllLastStartEnd();
	calculateTotalFloat();
	if(ifchange) {
		reWriteCircleX();
	}
	haveChange = false;
	reDrawPlanPic();
	if (testCalculate){
		testCalculate=false;
        calculateAll();
	}else{
		testCalculate=true;
	}
	return true;
}

//展示计划信息
function showPlanMessage() {
	$('.planText').eq(0).text(oldPlanName);
	$('.planText').eq(1).text(planTimeLimit + '天');
	$('.planText').eq(2).text(new Date(planStartTime).format("yyyy-MM-dd"));
}

//开启时间线按钮的点击事件
$('#timeFlow').click(function() {
	if(ifGrid) {
		$('#timeFlow').text('开启时间线');
		ifGrid = false;
		$('#showManLine').text('开启劳动力曲线');
		ifManLine = false;
	} else {
		$('#timeFlow').text('关闭时间线');
		ifGrid = true;
	}
	reDrawPlanPic();
});

//开启劳动力曲线按钮点击事件
$('#showManLine').click(function() {
	ifGrid = true;
	if(ifManLine) {
		$('#showManLine').text('开启劳动力曲线');
		ifManLine = false;
	} else {
		$('#showManLine').text('关闭劳动力曲线');
        $('#timeFlow').text('关闭时间线');
		ifManLine = true;
	}
	reDrawPlanPic();
});

//插入代号按钮点击事件
$('#insertNum').click(function () {
    $("#nowHaveNum").html("");
	var len=circleNumberList.length;
    for(var i = 0; i < len; i++) {
        $("#nowHaveNum").append('<option value="' + circleNumberList[i].num + '">' + circleNumberList[i].num + '</option>');
    }
    form.render();
    layer.open({
        type: 1,
        shadeClose: true,
        area: [widthMax, heightMax], //宽高
        content: $('#insertCircleBox')
    });
});

//插入代号页面确定按钮点击事件
$('#insertCircle').click(function () {
    haveChange = true;
    var numX = parseInt($('#numX').val());
    var numY = parseInt($('#numY').val());
    var num = parseInt($('#nowHaveNum option:selected').prop("value"));
    if(!/^\+?[1-9][0-9]*$/.test(numX)) {
        layer.msg('X坐标必须为50的正倍数！');
        return;
    }
    if(numX % 50 !== 0) {
        layer.msg('X坐标必须为50的正倍数！');
        return;
    }
    if(!/^\+?[1-9][0-9]*$/.test(numY)) {
        layer.msg('Y坐标必须为50的正倍数！');
        return;
    }
    if(numY % 50 !== 0) {
        layer.msg('Y坐标必须为50的正倍数！');
        return;
    }
    if(num===''){
        layer.msg('必须选择前置代号！');
        return;
	}
    var len=circleNumberList.length;
    for(var i = 0; i < len; i++) {
		if(circleNumberList[i].num>num){
            circleNumberList[i].num=circleNumberList[i].num*1+1;
		}
    }
    var len2=linePointList.length;
    for(var i = 0; i < len2; i++) {
		if(linePointList[i].num1>num){
            linePointList[i].num1=linePointList[i].num1*1+1;
		}
        if(linePointList[i].num2>num){
            linePointList[i].num2=linePointList[i].num2*1+1;
        }
    }
    reDrawPlanPic();
	drawCircleNumber(numX,numY,num+1);
	successTip();
});


$('#openHelp').click(function () {
	if($('#openWorkPlanHelp',parent.document).length===0){
        var addHtmlTxt='<a id="openWorkPlanHelp" lay-href="/plan/workPlanHelpHtml">网络计划使用说明</a>';
        $("body", parent.document).append(addHtmlTxt);
	}
    window.parent.openbq('openWorkPlanHelp');
});

form.on('select(project)', function(data){
    $.ajax({
        url: '/plan/selectPlanName',
        type: "get",
		data:{"project":data.value},
        dataType: "json", //预测服务端返回的数据类型
        success: function(data) { //请求成功的回调
            $('#selectPlan').html('<option value="">直接选择或搜索选择计划</option>');
            var len = data.length;
            for(var i = 0; i < len; i++) {
                $('#selectPlan').append('<option value="' + data[i].plan_id + '">' + data[i].plan_name + '</option>');
            }
            form.render();
        },
        error: function() { //请求失败的回调
            layer.msg('发生了未知错误！', {
                icon: 2
            });
        }
    });
});

$(document).ready(function() {
	showPlanMessage();
});