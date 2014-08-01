<%@ page  contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
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
						用户登陆名 <font class="red"> *
						</font>
					</div>
					<div class="wrapper clearfix">
						<div class="form">
							<input type="hidden" class="input formItem" maxlength="31"
								id="id" name="id" value="" verify="软件名称|NotNull|Length<=30" />
							<input type="text" class="input formItem" maxlength="31"
								id="loginName" name="loginName" verify="用户名称|NotNull|Length<=30|regexEnum=username" />
						</div>
						<div class="tip textshadow">用户名称(4-16位字母  数字组成)</div>
					</div> <b></b>
				</li>
				<li>
					<div class="title">
						渠道号<font class="red"> *
						</font>
					</div>
					<div class="wrapper clearfix">
						<div class="form">
							<input type="text" value = "<s:property value="code"/>" disabled='disabled' class="input formItem" maxlength="128"
								id="channel" name="channel"
								verify="渠道号|NotNull|Length<=128" />
						</div>
						<div class="tip textshadow">渠道号(由系统生成)</div>
					</div> <b></b>
				</li>
				<li>
					<div class="title">
						昵称<font class="red"> *
						</font>
					</div>
					<div class="wrapper clearfix">
						<div class="form">
							<input type="text" class="input formItem" maxlength="31"
								id="nickName" name="nickName"
								verify="昵称|NotNull|Length<=30" />
						</div>
						<div class="tip textshadow">昵称</div>
					</div> <b></b>
				</li>
				<li id="role">
					<div class="title">选择用户角色<font class="red"> *
						</font></div>
					<div class="wrapper clearfix">
						<div class="form">
							<div id="resCombox" class="w100 c filterItem combox"
								keyName="key" style="text-align: left" valueName="value"
								name="categoryParentId" vtype="combox"></div>
						</div>
						<div class="tip textshadow">选择用户权限</div>
					</div> <b></b>
				</li>
				<li>
					<div class="title">
						地址<font class="red"> <!-- * -->
						</font>
					</div>
					<div class="wrapper clearfix">
						<div class="form">
							<input type="text" class="input formItem" maxlength="31"
								id="address" name="address"
								verify="地址|Length<=30" />
						</div>
						<div class="tip textshadow">地址</div>
					</div> <b></b>
				</li>
				<li>
					<div class="title">
						公司<font class="red"> <!-- * -->
						</font>
					</div>
					<div class="wrapper clearfix">
						<div class="form">
							<input type="text" class="input formItem" maxlength="31"
								id="company" name="company"
								verify="公司|Length<=30" />
						</div>
						<div class="tip textshadow">公司</div>
					</div> <b></b>
				</li>
				<li>
					<div class="title">
						部门<font class="red"> <!-- * -->
						</font>
					</div>
					<div class="wrapper clearfix">
						<div class="form">
							<input type="text" class="input formItem" maxlength="31"
								id="department" name="department"
								verify="部门|Length<=30" />
						</div>
						<div class="tip textshadow">部门</div>
					</div> <b></b>
				</li>
				<li>
					<div class="title">
					联系人<font class="red"> <!-- * -->
						</font>
					</div>
					<div class="wrapper clearfix">
						<div class="form">
							<input type="text" class="input formItem" maxlength="31"
								id="contact" name="contact"
								verify="联系人|Length<=30" />
						</div>
						<div class="tip textshadow">联系人</div>
					</div> <b></b>
				</li>
				<li>
					<div class="title">
						电话号码<font class="red"> <!-- * -->
						</font>
					</div>
					<div class="wrapper clearfix">
						<div class="form">
							<input type="text" class="input formItem" maxlength="31"
								id="phone" name="phone"
								verify="电话号码|Length<=30" />
						</div>
						<div class="tip textshadow">电话号码</div>
					</div> <b></b>
				</li>
				<li>
					<div class="title">
						邮箱<font class="red"> <!-- * -->
						</font>
					</div>
					<div class="wrapper clearfix">
						<div class="form">
							<input type="text" class="input formItem" maxlength="31"
								id="email" name="email"
								verify="邮箱|Length<=30|regexEnum=email" />
						</div>
						<div class="tip textshadow">邮箱</div>
					</div> <b></b>
				</li>
				<li>
					<div class="title">
						QQ<font class="red"> <!-- * -->
						</font>
					</div>
					<div class="wrapper clearfix">
						<div class="form">
							<input type="text" class="input formItem" maxlength="31"
								id="qqnum" name="qqnum"
								verify="QQ|Length<=30|regexEnum=qq" />
						</div>
						<div class="tip textshadow">QQ</div>
					</div> <b></b>
				</li>
				<li>
					<div class="title">
						银行账户<font class="red"> <!-- * -->
						</font>
					</div>
					<div class="wrapper clearfix">
						<div class="form">
							<input type="text" class="input formItem" maxlength="31"
								id="account" name="account"
								verify="银行账户|Length<=30|regexEnum=bankAccount" />
						</div>
						<div class="tip textshadow">银行账户</div>
					</div> <b></b>
				</li>
				<li>
					<div class="title">
						所属银行<font class="red"> <!-- * -->
						</font>
					</div>
					<div class="wrapper clearfix">
						<div class="form">
							<input type="text" class="input formItem" maxlength="31"
								id="whichBank" name="whichBank"
								verify="所属银行|Length<=30" />
						</div>
						<div class="tip textshadow">所属银行</div>
					</div> <b></b>
				</li>
				<li>
					<div class="title">
						接入网络名称<font class="red"> <!-- * -->
						</font>
					</div>
					<div class="wrapper clearfix">
						<div class="form">
							<input type="text" class="input formItem" maxlength="31"
								id="joinNetName" name="joinNetName"
								verify="接入网络地址|Length<=30" />
						</div>
						<div class="tip textshadow">接入网络地址</div>
					</div> <b></b>
				</li>
				<li>
					<div class="title">
						接入网络地址<font class="red"> <!-- * -->
						</font>
					</div>
					<div class="wrapper clearfix">
						<div class="form">
							<input type="text" class="input formItem" maxlength="31"
								id="joinNetUrl" name="joinNetUrl"
								verify="接入网络地址|Length<=30" />
						</div>
						<div class="tip textshadow">接入网络地址</div>
					</div> <b></b>
				</li>
				<li>
					<div class="title">
						经营范围<font class="red"> <!-- * -->
						</font>
					</div>
					<div class="wrapper clearfix">
						<div class="form">
							<input type="text" class="input formItem" maxlength="31"
								id="busScope" name="busScope"
								verify="经营范围|Length<=30" />
						</div>
						<div class="tip textshadow">经营范围</div>
					</div> <b></b>
				</li>
				<li>
					<div class="title">
						用户状态<font class="red"> <!-- * -->
						</font>
					</div>
					<div class="wrapper clearfix">
						<div class="form">
							<div id="statusCombox" class="w100 c filterItem combox"
								keyName="key" style="text-align: left" valueName="value"
								name="status" vtype="combox"></div>
						</div>
						<div class="tip textshadow">用户状态</div>
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
//						A.extend(rowData, row); 
						CMS.util.Form.setData(CMS.page.verifyEle, rowData || {});
						CMS.page.resCombox.setValue(rowData.userRole);
						CMS.page.statusCombox.setValue(rowData.status);
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
						CMS.page.formData.insert('status', CMS.page.statusCombox.getValue());
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
					
					CMS.page.statusCombox = new CMS.util.Edit.Combox(A('#statusCombox'), 
							[{key:1, value:'正常'},
							 {key:2, value:'测试'},
							 {key:3, value:'冻结'}
							], 
						function(value, text){ 
							//CMS.page.getResType(value)
						}); 
					
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

