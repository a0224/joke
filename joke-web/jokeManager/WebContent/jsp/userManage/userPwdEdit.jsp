<%@ page  contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String contextPath = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ contextPath + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="x-ua-compatible" content="ie=edge" />
<meta name="language" content="en" />
<base href="<%=basePath%>" />
<title>激动云管理系统</title>
<script>
	contextPath="<%=request.getContextPath()%>";
	jsessionid="<%=session.getId()%>";
</script>
<link rel="stylesheet" type="text/css" href="skin/main.css" />
<style>
.formwrapper {
	padding-left: 130px;
}

.formwrapper li .title {
	margin-left: -85px;
}
</style>
</head>
<body class="body">
	<div class="bodywarpper">

		<ul class="formwrapper">
			<li>
				<div class="title">用户名:</div>
				<div class="wrapper clearfix">
					<div class="form" id="SITE_NAME">
						<label id="loginName"></label>
					</div>
					<div class="tip textshadow"></div>
				</div> <b></b>
			</li>
			<li>
				<div class="title">原密码:</div>
				<div class="wrapper clearfix">
					<div class="form" id="SITE_NAME">
						<input type="password" class="inputsel formItem" name="oldpassword"
							id="oldpassword" verify="原密码|NotNull|Length<=10|regexEnum=password" /> <font
							class="red"> <!-- * --></font>
					</div>
					<div class="tip textshadow"></div>
				</div> <b></b>
			</li>
			<li>
				<div class="title">新密码:</div>
				<div class="wrapper clearfix">
					<div class="form" id="SITE_NAME">
						<input type="password" class="inputsel formItem" name="userPaWord"
							id="userPaWord" verify="重置密码|NotNull|Length<=10|regexEnum=password" /> <font
							class="red"> <!-- * --></font>
					</div>
					<div class="tip textshadow"></div>
				</div> <b></b>
			</li>
			<li>
				<div class="title">重新输入新密码:</div>
				<div class="wrapper clearfix">
					<div class="form" id="SITE_NAME">
						<input type="password" class="inputsel formItem" name="repassword"
							id="repassword" verify="重新输入新密码|NotNull|Length<=10|regexEnum=password" /> <font
							class="red"> <!-- * --></font>
					</div>
					<div class="tip textshadow"></div>
				</div> <b></b>
			</li>

		</ul>

	</div>

	<script type="text/javascript" src="js/json2.js"></script>
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/common.js"></script>
	<script type="text/javascript" src="js/dialog/Drag.js"></script>
	<script type="text/javascript" src="js/dialog/Dialog.js"></script>
	<script type="text/javascript">
		CMS.page.id = '';
		A(document).ready(function() {
			CMS.page.verifyEle = A('.formItem');
			CMS.util.Form.verify(CMS.page.verifyEle);
		});

		CMS.page.setFormData = function(rowData) {
			A('#loginName').text(rowData.loginName);
			CMS.page.id = rowData.id;
			CMS.util.Edit.focusVerify(CMS.page.verifyEle);
		};

		CMS.page.getFormData = function() {
			if (!CMS.util.Form.verifyAll(CMS.page.verifyEle)) {
				if($("#userPaWord").val() != $("#repassword").val()){
					Dialog.alert('两次密码不一致 请重新输入!');
					return false;
				}
				CMS.page.formData = new CMS.util.Form.getData(A('.formwrapper'), 'formItem', []);
				CMS.page.formData.insert('id', CMS.page.id);
				return CMS.page.formData.toJson();
			} else {
				Dialog.alert('还有未正确填写的项，请参照提示修改!');
				return false;
			}
		};
		
	</script>
</body>
</html>

