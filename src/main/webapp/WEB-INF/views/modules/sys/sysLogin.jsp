<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<!DOCTYPE html>
<!-- saved from url=(0042)http://admin.sanjieke.cn/Public/login.html -->
<html lang="en">
<head>
<!-- <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 -->
<!--  <meta name="decorator" content="blank"/>
 --><title>登录页面</title>
 <link href="${ctxStatic}/common/jeesite.css" type="text/css" rel="stylesheet" />
<script src="${ctxStatic}/common/jeesite.js" type="text/javascript"></script>
<link rel="stylesheet" href="${ctxStatic}/sysLogin4/dome.css">
<link rel="stylesheet" href="${ctxStatic}/sysLogin4/bootstrap.min.css">  
	<script src="${ctxStatic}/sysLogin4/jquery.min.js"></script>
	<script src="${ctxStatic}/sysLogin4/bootstrap.min.js"></script>
	<link href="${ctxStatic}/jquery-validation/1.11.0/jquery.validate.min.css" type="text/css" rel="stylesheet" />
<script src="${ctxStatic}/jquery-validation/1.11.0/jquery.validate.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/jquery-jbox/2.3/Skins/Bootstrap/jbox.min.css" rel="stylesheet" />
<script src="${ctxStatic}/jquery-jbox/2.3/jquery.jBox-2.3.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#loginForm").validate({
				rules: {
					validateCode: {remote: "${pageContext.request.contextPath}/servlet/validateCodeServlet"}
				},
				messages: {
					username: {required: "请填写用户名."},password: {required: "请填写密码."},
					validateCode: {remote: "验证码不正确.", required: "请填写验证码."}
				},
				errorLabelContainer: "#messageBox",
				errorPlacement: function(error, element) {
					error.appendTo($("#loginError").parent());
				} 
			});
		});
		// 如果在框架或在对话框中，则弹出提示并跳转到首页
		if(self.frameElement && self.frameElement.tagName == "IFRAME" || $('#left').length > 0 || $('.jbox').length > 0){
			alert('未登录或登录超时。请重新登录，谢谢！');
			top.location = "{ctx}";
		}
	</script>
</head>
<style>
	
	.demo{
  padding: 25px;
  background-color: rgba(0,0,0,0.5);/* IE9、标准浏览器、IE6和部分IE7内核的浏览器(如QQ浏览器)会读懂 */
}
.demo p{
  color: #FFFFFF;
}
@media \0screen\,screen\9 {/* 只支持IE6、7、8 */
  .demo{
    background-color:#000000;
    filter:Alpha(opacity=50);
    position:static; /* IE6、7、8只能设置position:static(默认属性) ，否则会导致子元素继承Alpha值 */
    *zoom:1; /* 激活IE6、7的haslayout属性，让它读懂Alpha */
  }
  .demo p{
    position: relative;/* 设置子元素为相对定位，可让子元素不继承Alpha值 */
  }  
}
.share{
	width: 274px;
	margin:0 auto;
	overflow: hidden;
}
.share li{
	float: left;
	height: 50px;
	padding:5px 14px;
}
.share li img{
	width: 40px;
	height: 40px;
	cursor: pointer;
	border-radius: 20px;

}
ul{
	list-style: none;
}
.code_wrap{
	position: absolute;
	left:50%;
	top:-235px;
	margin-left: -450px;
	width:900px;
/* 	z-index:50;
 */}
.code{
	width: 100%;
	overflow: hidden;
	position: relative;
}
.code li{
	float: left;
	width: 200px;
	height:240px;
}
.code li img{
	width: 210px;
	height:240px;
	display: none;
}
.code li img.weibo{
	position: absolute;
	left: 244px;
}
.code li img.qq{
	position: absolute;
	left: 312px;

}
.code li img.twitter{
	position: absolute;
	left:382px;
}
.code li img.wechat{
	position:absolute;
	left:448px;
}
</style>
<body style="zoom: 1;">
	<%-- <div class="login">
		<div class="l-mark" style="height: 360px;"></div>
		<div class="logo" style="left: 120px; top: 110px;">
			<img src="${ctxStatic}/login2_files/logo.png" style="width: 200px;">
		</div>
		<div class="logo-text">${fns:getConfig('productName')}</div>
		<div class="login-w">
			<div class="lw-title">账号登录</div>
			<div class="lw-con">
				<form id="loginForm" class="form-signin" action="${ctx}/login"
					method="post" autocomplete="off">

					<ul>
						<li>
						 <label class="label-icon icon-username"></label>
						<input type="text" class="username"
							placeholder="Username" name="username" autocomplete="off"></li>
						<li>
						<label class="label-icon icon-pwd"></label>
						<input type="password" class="password"
							placeholder="Password" name="password" autocomplete="off"></li>
						<li><c:if test="${isValidateCodeLogin}">
								<div class="validateCode">
									<sys:validateCode name="validateCode"
										inputCssStyle="margin-bottom:0;" />
								</div>
							</c:if></li>
						<li><input type="submit" class="lw-submit" value="登录"></li>

					</ul>
				</form>
			</div>
		</div>
	</div>


	<div class="" style="clear:both">
		Copyright &copy; 2012-${fns:getConfig('copyrightYear')} <a
			href="${pageContext.request.contextPath}${fns:getFrontPath()}">${fns:getConfig('productName')}</a>
		- Powered By <a href="http://www.eooker.com" target="_blank">Eooker</a>
		${fns:getConfig('version')}
	</div>
 --%>
<input id="loginError" class="error" type="text" value="${message}">
 <div class="box" >
     <div class="cnt">
        <p id="huanying"><span id="cnt_one">后台管理系统</span></p>
         <form id="loginForm" class="bs-example bs-example-form" role="form" action="${ctx}/login" method="post" >
        
        <div>
		      <div class="input-group" style="margin-top: 20px;">
			      <span class="input-group-addon"><img src="${ctxStatic}/sysLogin4/未标题-1_03.png"></span>
			      <input type="text" class="form-control required" placeholder="请输入您的账号" id="username" name="number">
			      
		      </div><br>
		      <div class="input-group "style="margin-top: 20px;">
			      <span class="input-group-addon"><img src="${ctxStatic}/sysLogin4/suo.png"></span>
			      <input type="password" class="form-control required" placeholder="请输入您的密码" id="password" name="password" >
		      </div><br>
		      <c:if test="${isValidateCodeLogin}">
		      <div class="validateCode" style="margin-left: 20px;margin-bottom: 20px;">
			   <sys:validateCode name="validateCode" inputCssStyle="margin-bottom:0;"/>
		      </div>
		      </c:if>
		      <!--<div class="input-group" style="position:absolute;">
			      
			      <input type="text" class="form-control" placeholder="请输入验证码" style="position:relative;width:191px;height:33px;">
                  <img src="./sysLogin4/yanzhengma.png" id="oimg">
                  
		      </div>-->
		      <br>
        </div>
        <div >
           <input class="form-control btn btn-info" type="submit" value="登录" >
        </div>
        	       </form>
        
     </div> 
  </div>
  <div class="demo" style="position: absolute;bottom: 0px;width:100%;text-align: center; color: white;padding:0;">
		<ul class="share">
				<li>
					<img class="weibo_icon" src="${ctxStatic}/sysLogin4/androidLogo.png" alt="">
				</li>
				<li>
					<img class="qq_icon" src="${ctxStatic}/sysLogin4/appleLogo.png" alt="">
				</li>
				<%-- <li>
					<img class="twitter_icon" src="${ctxStatic}/sysLogin4/share4.png" alt="">
				</li> --%>
			</ul>
			<div class="code_wrap" style="display: none; z-index:0" >
				<ul class="code">
					<li><img class="weibo" src="${ctxStatic}/sysLogin4/androidShareApk.png" alt="" style="display: none; z-index:0"></li>
					<li><img class="qq" src="${ctxStatic}/sysLogin4/code.png" alt="" style="display: none; z-index:0"></li>
					<%-- <li><img class="twitter" src="${ctxStatic}/sysLogin4/code.png" alt="" style="display: none; z-index:0"></li> --%>
				</ul>
			</div>
		Copyright © 2012-2017 <a href="#">后台管理系统</a>
		- Powered By <a href="http://www.eooker.com/" target="_blank">Eooker</a>
		V1.0.0
	</div>


 	<script src="${ctxStatic}/flash/zoom.min.js" type="text/javascript"></script>
</body>
<script>
var width=document.documentElement.clientWidth;
/* alert(width);
 */	
 var t=$("#loginError").val();
 if (t !='')
	 alert($("#loginError").val())
 /*底部二维码*/

	$(".weibo_icon").hover(function(){
		$(".weibo").fadeIn(500);
		$(".qq,.twitter,.wechat").fadeOut(0);
		$(".code_wrap").css("display","block");
		$(".code_wrap").css("z-index","200");
	},function(){
		$(".weibo").fadeOut(0);
		$(".code_wrap").css("display","none");
		$(".code_wrap").css("z-index","0");
	});

	$(".qq_icon").hover(function(){
		$(".qq").fadeIn(500);
		$(".weibo,.twitter,.wechat").fadeOut(0);
		$(".code_wrap").css("display","block");
		$(".code_wrap").css("z-index","200");
	},function(){
		$(".qq").fadeOut(0);
		$(".code_wrap").css("display","none");
		$(".code_wrap").css("z-index","0");
	});
	$(".twitter_icon").hover(function(){
		$(".twitter").fadeIn(500);
		$(".weibo,.qq,.wechat").fadeOut(0);
		$(".code_wrap").css("display","block");
		$(".code_wrap").css("z-index","200");
	},function(){
		$(".twitter").fadeOut(0);
		$(".code_wrap").css("display","none");
		$(".code_wrap").css("z-index","0");
	});
	$(".wechat_icon").hover(function(){
		$(".wechat").fadeIn(500);
		$(".weibo,.qq,.twitter").fadeOut(0);
		$(".code_wrap").css("display","block");
		$(".code_wrap").css("z-index","200");
	},function(){
		$(".wechat").fadeOut(0);
		$(".code_wrap").css("display","none");
		$(".code_wrap").css("z-index","0");
	});
	$(".weibo,.twitter,.wechat").hover(function(){
		$(this).fadeIn(0);
		$(".code_wrap").css("display","block");
		$(".code_wrap").css("z-index","200");
	},function(){
		$(this).fadeOut(500);
		$(".code_wrap").css("display","none");
		$(".code_wrap").css("z-index","0");
	});

	</script>
</html>