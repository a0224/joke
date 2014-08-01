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
<title></title>
<script>
	contextPath="<%=request.getContextPath()%>";
	jsessionid="<%=session.getId()%>";
</script>
<link rel="stylesheet" type="text/css" href="skin/main.css" />

<style type="text/css">
.textarea {
	width: 700px;
}

.screeninput,.uploadinput {
	width: 204px
}

.comboxlist .c {
	text-align: left
}

.formwrapper li {
	position: relative;
}

#fileUploadCent div {
	margin-bottom: 5px
}

#fileUploadList {
	overflow: visible;
}

#fileUploadList li {
	list-style: none;
	float: left;
	width: 200px;
	height: 330px;
	border: 1px #FFFFFF solid;
	margin: 0 5px 5px 0;
}

.packageMes li {
	border: 0
}

div.star-rating {
	float: left;
	width: 17px;
	height: 15px;
	text-indent: -999em;
	cursor: pointer;
	display: block;
	background: transparent;
	overflow: hidden
}

div.star-rating,div.star-rating a {
	background: url(js/img/star.gif) no-repeat 0 0px
}

div.star-rating a {
	display: block;
	width: 16px;
	height: 100%;
	background-position: 0 0px;
	border: 0
}

div.star-rating-on a {
	background-position: 0 -16px !important
}

div.star-rating-hover a {
	background-position: 0 -32px
}

div.star-rating-readonly a {
	cursor: default !important
}

div.star-rating {
	background: transparent !important;
	overflow: hidden !important
}
</style>
</head>
<body class="body">
	<div class="barwrapper" id="filterItems"
		style="border-bottom: 1px solid #c3c3c3; padding: 6px 10px 5px; text-indent: 5e;">
		<p>当前位置: 信息管理 >> 个人信息</p>
	</div>
	<div class="bodywarpper">
		<div style="width:400px;float:left">
			<ul class="formwrapper">
				<li>
					<div class="title">
						用户名称
					</div>
					<div class="wrapper clearfix">
						<div class="form" id="loginName" name="loginName">&nbsp;</div>
					</div> <b></b>
				</li>
				<li>
					<div class="title">
						用户编号
					</div>
					<div class="wrapper clearfix">
						<div class="form" id="channel" name="channel">&nbsp;</div>
					</div> <b></b>
				</li>
				<li>
					<div class="title">
						经营范围
					</div>
					<div class="wrapper clearfix">
						<div class="form" id="busScope" name="busScope">&nbsp;</div>
					</div> <b></b>
				</li>
				<li>
					<div class="title">
						接入网站地址<font class="red"> <!-- * -->
						</font>
					</div>
					<div class="wrapper clearfix">
						<div class="form" id="joinNetUrl" name="joinNetUrl">&nbsp;</div>
					</div> <b></b>
				</li>
				<li>
					<div class="title">
						接入网站名称<font class="red"> <!-- * -->
						</font>
					</div>
					<div class="wrapper clearfix">
						<div class="form" id="joinNetName" name="joinNetName">&nbsp;</div>
					</div> <b></b>
				</li>
			</ul>
		</div>
		<div style="width:400px;left:-300px;float:left">
			<ul class="formwrapper">
				<li>
					<div class="title">
						联系人<font class="red"> <!-- * -->
						</font>
					</div>
					<div class="wrapper clearfix">
						<div class="form" id="contact" name="contact">&nbsp;</div>
					</div> <b></b>
				</li>
				<li>
					<div class="title">
						电话号码<font class="red"> <!-- * -->
						</font>
					</div>
					<div class="wrapper clearfix">
						<div class="form" id="phone" name="loginName">&nbsp;</div>
					</div> <b></b>
				</li>
				<li>
					<div class="title">
						邮箱<font class="red"> <!-- * -->
						</font>
					</div>
					<div class="wrapper clearfix">
						<div class="form" id="email" name="email">&nbsp;</div>
					</div> <b></b>
				</li>
				<li>
					<div class="title">
						QQ<font class="red"> <!-- * -->
						</font>
					</div>
					<div class="wrapper clearfix">
						<div class="form" id="qqnum" name="qqnum">&nbsp;</div>
					</div> <b></b>
				</li>
				<li>
					<div class="title">
						地址<font class="red"> <!-- * -->
						</font>
					</div>
					<div class="wrapper clearfix">
						<div class="form" id="address" name="address">&nbsp;</div>
					</div> <b></b>
				</li>
			</ul>
		</div>
		</div>
		<script type="text/javascript" src="js/json2.js"></script>
		<script type="text/javascript" src="js/jquery.min.js"></script>
		<script type="text/javascript" src="js/common.js"></script>
		<script type="text/javascript" src="js/dialog/Drag.js"></script>
		<script type="text/javascript" src="js/dialog/Dialog.js"></script>
		<script type="text/javascript" src="js/jquery-migrate.min.js"></script>
		<script type="text/javascript">

				CMS.page.verifyEle = A('.formItem, .screeninput'); 
				var gridrow = "";

				A(document).ready(function(){  
					CMS.util.Form.verify(CMS.page.verifyEle);
					CMS.page.initForm(); 
				});  
				
				
				CMS.page.setFormData = function(id){
					gridrow=id;
					CMS.core.showMask();
					CMS.util.HttpAjax(contextPath + '/user/getUserInfoById.html', 
							'{"id": "'+ id +'"}', function(data){
						var rowData = {};
						if(data.result == 'success'){   
						   rowData = data.data.length != 0 ? data.data : {};
						} 
						
						A.each(rowData, function (name, value) {
							var element = A('#' + name);
							element.html(value || '&nbsp;')
						});
//						A.extend(rowData, row); 
//						CMS.util.Form.setData(CMS.page.verifyEle, rowData || {});
//						CMS.page.resCombox.setValue(rowData.userRole);
						CMS.core.hideMask();
					})
				}; 
				
				
				CMS.page.initForm = function(){
					var id = ${sessionScope.user_inf.id};
					CMS.page.setFormData(id);
				};
				
				
					
			</script>
</body>
</html>

