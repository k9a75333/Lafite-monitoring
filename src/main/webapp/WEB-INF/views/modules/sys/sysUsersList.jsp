<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/sysUsers/">用户列表</a></li>
		<shiro:hasPermission name="sys:sysUsers:edit"><li><a href="${ctx}/sys/sysUsers/form">用户添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="sysUsers" action="${ctx}/sys/sysUsers/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>名字：</label>
				<form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>用户名</th>
				<th>姓名</th>
				<th>工号</th>
				<th>所在部门</th>
				<shiro:hasPermission name="sys:sysUsers:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="sysUsers">
			<tr>
				<td><a href="${ctx}/sys/sysUsers/form?id=${sysUsers.id}">
					${sysUsers.username}
				</a></td>
				<td><a href="${ctx}/sys/sysUsers/form?id=${sysUsers.id}">
					${sysUsers.name}
				</a></td>
				<td><a href="${ctx}/sys/sysUsers/form?id=${sysUsers.id}">
					${sysUsers.number}
				</a></td>
				<td>
				${sysUsers.office.name}
				</td>
				<shiro:hasPermission name="sys:sysUsers:edit"><td>
    				<a href="${ctx}/sys/sysUsers/form?id=${sysUsers.id}">修改</a>
					<a href="${ctx}/sys/sysUsers/delete?id=${sysUsers.id}" onclick="return confirmx('确认要删除该用户吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>