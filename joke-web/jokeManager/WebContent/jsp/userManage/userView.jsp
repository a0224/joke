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

	<div class="bodywarpper">
		<div>
			<ul class="formwrapper">
				<li>
					<div class="title">
						用户名 
					</div>
					<div class="wrapper clearfix">
						<div class="form" id="loginName" name="loginName">&nbsp;</div>
					</div> <b></b>
				</li>
				<li>
					<div class="title">
						渠道号
					</div>
					<div class="wrapper clearfix">
						<div class="form" id="channel" name="channel">&nbsp;</div>
					</div> <b></b>
				</li>
				<li>
					<div class="title">
						昵称
					</div>
					<div class="wrapper clearfix">
						<div class="form" id="nickName" name="nickName">&nbsp;</div>
					</div> <b></b>
				</li>
				<li>
					<div class="title">
						密码
					</div>
					<div class="wrapper clearfix">
						<div class="form" id="userPaWord" name="loginName">&nbsp;</div>
					</div> <b></b>
				</li>
				<li id="role">
					<div class="title">用户角色</div>
						<div class="wrapper clearfix">
						<div class="form" id="userRole" name="loginName">&nbsp;</div>
					</div> <b></b>
				</li>
				<li>
					<div class="title">
						地址<font class="red"> <!-- * -->
						</font>
					</div>
					<div class="wrapper clearfix">
						<div class="form" id="address" name="loginName">&nbsp;</div>
					</div> <b></b>
				</li>
				<li>
					<div class="title">
						公司<font class="red"> <!-- * -->
						</font>
					</div>
					<div class="wrapper clearfix">
						<div class="form" id="company" name="loginName">&nbsp;</div>
					</div> <b></b>
				</li>
				<li>
					<div class="title">
						部门<font class="red"> <!-- * -->
						</font>
					</div>
					<div class="wrapper clearfix">
						<div class="form" id="department" name="loginName">&nbsp;</div>
					</div> <b></b>
				</li>
				<li>
					<div class="title">
						联系人<font class="red"> <!-- * -->
						</font>
					</div>
					<div class="wrapper clearfix">
						<div class="form" id="contact" name="loginName">&nbsp;</div>
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
						银行账户<font class="red"> <!-- * -->
						</font>
					</div>
					<div class="wrapper clearfix">
						<div class="form" id="account" name="loginName">&nbsp;</div>
					</div> <b></b>
				</li>
				<li>
					<div class="title">
						所属银行<font class="red"> <!-- * -->
						</font>
					</div>
					<div class="wrapper clearfix">
						<div class="form" id="whichBank" name="whichBank">&nbsp;</div>
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
						经营范围<font class="red"> <!-- * -->
						</font>
					</div>
					<div class="wrapper clearfix">
						<div class="form" id="busScope" name="busScope">&nbsp;</div>
					</div> <b></b>
				</li>
				
				<li>
					<div class="title">
						创建人<font class="red"> <!-- * -->
						</font>
					</div>
					<div class="wrapper clearfix">
						<div class="form" id="createUser" name="loginName">&nbsp;</div>
					</div> <b></b>
				</li>
				
				<li>
					<div class="title">
						创建时间<font class="red"> <!-- * -->
						</font>
					</div>
					<div class="wrapper clearfix">
						<div class="form" id="createTime" name="loginName">&nbsp;</div>
					</div> <b></b>
				</li>
				
				<li>
					<div class="title">
						更新人<font class="red"> <!-- * -->
						</font>
					</div>
					<div class="wrapper clearfix">
						<div class="form" id="modifyUser" name="loginName">&nbsp;</div>
					</div> <b></b>
				</li>
				
				<li>
					<div class="title">
						更新时间<font class="red"> <!-- * -->
						</font>
					</div>
					<div class="wrapper clearfix">
						<div class="form" id="updateTime" name="loginName">&nbsp;</div>
					</div> <b></b>
				</li>
				
				<li>
					<div class="title">
						最后登录时间<font class="red"> <!-- * -->
						</font>
					</div>
					<div class="wrapper clearfix">
						<div class="form" id="lastLoginTime" name="loginName">&nbsp;</div>
					</div> <b></b>
				</li>
				
				<li>
					<div class="title">
						用户状态<font class="red"> <!-- * -->
						</font>
					</div>
					<div class="wrapper clearfix">
						<div class="form" id="status" name="loginName">&nbsp;</div>
					</div> <b></b>
				</li>

			</ul>
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
						   rowData = data.data.length != 0 ? data.data : {}
						} 
						
						A.each(rowData, function (name, value) {
							var element = A('#' + name);
							
							if (name == "status") {
								if(value==1){
									element.html("正常账户" || '&nbsp;')
								}else if(value==2){
									element.html("测试账户" || '&nbsp;')
								}else if(value==3){
									element.html("冻结账户" || '&nbsp;')
								}
								
							}else if (name == "userRole") {
								if(value==1){
									element.html("管理员" || '&nbsp;')
								}else if(value==2){
									element.html("运维" || '&nbsp;')
								}else if(value==3){
									element.html("开发者" || '&nbsp;')
								}else if(value==4){
									element.html("通道商" || '&nbsp;')
								}else if(value==5){
									element.html("渠道商" || '&nbsp;')
								}else if(value==6){
									element.html("商务" || '&nbsp;')
								}else if(value==7){
									element.html("财务" || '&nbsp;')
								}else if(value==8){
									element.html("子渠道商" || '&nbsp;')
								}
								
							} else if (name == 'userPaWord') {
								element.html("********");
							}else {
								element.html(value || '&nbsp;')
							}
						});
//						A.extend(rowData, row); 
//						CMS.util.Form.setData(CMS.page.verifyEle, rowData || {});
//						CMS.page.resCombox.setValue(rowData.userRole);
						CMS.core.hideMask();
					})
				}; 
				
				CMS.page.getFormData = function(){
//					$("#reuserPaWord").value()
					if($("#reuserPaWord").val() != $("#userPaWord").val()){
						Dialog.alert('两次密码不一致 请重新输入!');
						return false
					}
					
					if(!CMS.util.Form.verifyAll(CMS.page.verifyEle) ){
						CMS.page.formData = new CMS.util.Form.getData(A('.formwrapper'), 'formItem', []);
						CMS.page.formData.insert('userRole', CMS.page.resCombox.getValue());
						CMS.page.formData.insert('id', gridrow);
						
						return {
							object: CMS.page.formData.toObject(),
							json: CMS.page.formData.toJson()
						} 
					} else {
						Dialog.alert('还有未正确填写的项，请参照提示修改!');
						return false
					}
				};
				
				CMS.page.initForm = function(){
					var role = ${sessionScope.user_inf.userRole};
					if(role == 1){
						CMS.page.resCombox = new CMS.util.Edit.Combox(A('#resCombox'), 
								[{key:1, value:'管理员'},
								 {key:2, value:'运维'},
								 {key:3, value:'开发者'},
								 {key:4, value:'通道商'},
								 {key:5, value:'渠道商'},
								 {key:6, value:'商务'},
								 {key:7, value:'财务'},
								 {key:8, value:'子渠道商'}
								], 
							function(value, text){ 
								//CMS.page.getResType(value)
							}); 
					}else{
						$("#role").hide();
						CMS.page.resCombox = new CMS.util.Edit.Combox(A('#resCombox'), 
								[
								 {key:3, value:'客户'},
								
								], 
							function(value, text){ 
								//CMS.page.getResType(value)
							}); 
					}
					
				};
				
				
					
			</script>
</body>
</html>

