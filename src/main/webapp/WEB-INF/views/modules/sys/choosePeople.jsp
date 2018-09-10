<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>代理人操作管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	function page(n,s){
		if(n) $("#pageNo").val(n);
		if(s) $("#pageSize").val(s);
		$("#searchForm").attr("action","${ctx}/sys/user/choosePeople");
		$("#searchForm").submit();
// 		$("#pageForm").attr("action","${ctx}/recheck/PolicyRecheck/choosePeople");
// 		$("#pageForm").submit();
    	return false;
    }
		function back(){
			window.close();
	    }
		
		function chk(){
			$("#inputForm").submit();
		}
		function search(){
			var btn =  document.getElementById("btn1");
			var iTop = (window.screen.availHeight - 30 - 600) / 2; 
	           //获得窗口的水平位置 
	        var iLeft = (window.screen.availWidth - 10 - 800) / 2; 
			window.opener.open("${ctx}/sys/user/choosePeople",'','width=800,height=600,top='+iTop+",left="+iLeft);
			self.close();
			
		}
		
		function upload(){
			var table = document.getElementById("contentTable");
			var rows = table.rows;
			
			obj		 = document.getElementsByName("proxyid"); //30
			nameList = document.getElementsByName("proxyName");
			
			for(var i=0;i<obj.length;i++){
				if(obj[i].checked){
					/* alert(document.getElementById("contentTable").rows[i+1].cells[1].innerText);
					alert(document.getElementById("contentTable").rows[i+1].cells[2].innerText); */
					window.opener.$("#proxy").val(document.getElementById("contentTable").rows[i+1].cells[1].innerText);
					window.opener.$("#proxyName").val(document.getElementById("contentTable").rows[i+1].cells[2].innerText);
					break;
					
				}
			}		
            self.close();
		}
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			}); 
		});
		
		
		$(document).ready(function() {
			$("#closeWindow").click(function(){
				
				var table = document.getElementById("contentTable");
				var rows = table.rows;
				obj = document.getElementsByName("studnetid")
				
				for(var i=0;i<obj.length;i++){
					if(obj[i].checked){
						window.opener.$("teacher").val(rows[i].cells[2].innerHTML);
						window.opener.$("curUser.number").val(rows[i].cells[1].innerHTML);
						window.opener.$("curUser.username").val(rows[i].cells[2].innerHTML);
					}
				}
				
				
				
				
				
	            //self.close();
			});
		});
	</script>
	<style type="text/css">
			th{
				background: #C7C7E2;
			}
	</style>
</head>
<body>	
<br/>
<br/>
		<form:form id="searchForm" action="${ctx}/sys/user/choosePeople" modelAttribute="user" method="post">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		
			<label>姓名:</label><form:input path="name" id="searchName" htmlEscape="false" maxlength="50" class="input-small"/>
			<%-- <label>手机号码:</label><form:input path="mobile" id="searchPhone" htmlEscape="false" maxlength="50" class="input-small"/> --%>
			<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
			<%-- <input type="hidden" name="pageNum" id="pageNum" value="${pageNum }"/> --%>
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
			<input class="btn btn-primary" type="button" value="提 交" onclick="upload()"/>
			<input class="btn btn-primary" type="button" value="取消" onclick="back()">
		
	</form:form>
	
		
							<table id="contentTable" class="table table-striped table-bordered table-condensed">
								<thead>
									<tr><th></th>
										<th>姓名</th>
										<!-- <th>手机</th> -->
										<th>所属楼层</th>
										<!-- <th>代理人组类</th> -->
									</tr>
								</thead>
								<tbody>
								<c:forEach items="${page.list}" var="a" >
									<tr>
										<td><input type="radio"  name="proxyid" id="proxyid"  value="${a.id }" class="required"/></td>
										<td id="proxyName" style="display:none">${a.id}</td>
										<td id="proxyName">${a.name}</td>
										<%-- <td>${a.mobile}</td> --%>
										<td>${a.dept.name}</td>
										<%-- <td>${a.type}</td> --%>
									</tr>
								</c:forEach>
								</tbody>
							</table>
					
							
		<div class="pagination">${page}</div>	
</body>
</html>