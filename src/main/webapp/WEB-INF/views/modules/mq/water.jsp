<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
	<title> waterFlow WebSocket</title>
	<script type="text/javascript"src="http://echarts.baidu.com/gallery/vendors/echarts/echarts-all-3.js"></script>
</head>
<body>
	<div class="page-header" id="tou">水表数据</div>
	<div id="chart" style="width:400px;height:400px;"></div>
	<div id="message"></div>
</body>
<script type="text/javascript">
	var websocket = null;

	//判断当前浏览器是否支持WebSocket
	if ('WebSocket' in window) {
		websocket = new WebSocket("ws://127.0.0.1:8080/Lafite/webSocket/water");
	} else {
		alert('当前浏览器 不支持WebSocket')
	}

	//连接发生错误的回调方法
	websocket.onerror = function() {
		$("#tou").html("发生错误")
	};

	//连接成功建立的回调方法
	websocket.onopen = function() {
		$("#tou").html("连接服务器成功!")
	}

	//接收到消息的回调方法，此处添加处理接收消息方法，当前是将接收到的信息显示在网页上
	websocket.onmessage = function(event) {
		//setMessageInnerHTML(event.data);
		option.series[0].data[0].value = event.data;
		myChart.setOption(option);
	}

	//连接关闭的回调方法
	websocket.onclose = function() {
		$("#tou").html("与服务器断开了链接!")
	}
	//将消息显示在网页上，如果不需要显示在网页上，则不调用该方法
	function setMessageInnerHTML(innerHTML) {
		document.getElementById('message').innerHTML += innerHTML + '<br/>';
	}
	// 初始化图表标签
    var myChart = echarts.init(document.getElementById("chart"));
    var option = {
        tooltip : {
            formatter: "{a} <br/>{b} : {c}%"
        },
        toolbox: {
            feature: {
                restore: {},
                saveAsImage: {}
            }
        },
        series: [
            {
                name: "用水量",
                type: "gauge",
                detail: {formatter:"{value}m³"},
                data: [{value: 0, name: "用水量"}]
            }
        ]
    };
    myChart.setOption(option);
	//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
	window.onbeforeunload = function() {
		closeWebSocket();
	}
	//关闭WebSocket连接
	function closeWebSocket() {
		websocket.close();
	}
</script>
</html>
