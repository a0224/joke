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
						标题 <font class="red"> *
						</font>
					</div>
					<div class="wrapper clearfix">
						<div class="form">
							<input type="hidden" class="input formItem" maxlength="31"
								id="id" name="id" value="" verify="软件名称|NotNull|Length<=30" />
							<input type="text" class="input formItem" maxlength="31"
								id="name" name="name" verify="用户名称|NotNull|Length<=30" />
						</div>
						<div class="tip textshadow">用户名称(4-16位字母  数字组成)</div>
					</div> <b></b>
				</li>
				<li>
					<div class="title">
						编码<font class="red"> *
						</font>
					</div>
					<div class="wrapper clearfix">
						<div class="form">
							<input type="text" class="input formItem" maxlength="31"
								id="code" name="code"
								verify="昵称|NotNull" />
						</div>
						<div class="tip textshadow">昵称</div>
					</div> <b></b>
				</li>
				<li>
					<div class="title">
						介绍<font class="red"> * 
						</font>
					</div>
					<div class="wrapper clearfix">
						<div class="form">
							<input type="text" class="input formItem" maxlength="31"
								id="memo" name="memo"
								verify="NotNull" />
						</div>
						<div class="tip textshadow">密码(字母 _ 数字 18位以内)</div>
					</div> <b></b>
				</li>
				
				<!-- <li>
					<div class="title">
						父节点<font class="red">*</font>
					</div>
					<div class="wrapper clearfix">
						<div class="form">
							<div id="categoryCombox" keyName="id"
								valueName="title" vtype="combox" style="width: 200px;">最上级分类</div>
						</div>
						<div class="tip textshadow">选择壁纸分类。</div>
					</div> <b></b>
				</li> 
				<li>
					<div class="title">
						排序<font class="red"> * 
						</font>
					</div>
					<div class="wrapper clearfix">
						<div class="form">
							<input type="text" value='99' class="input formItem" maxlength="2"
								style="width: 100px;" id="order" name="order"
								verify="NotNull" />
						</div>
						<div class="tip textshadow">排序(两位数字)</div>
					</div> <b></b>
				</li>-->
			
				<li>
					<div class="title">
						状态<font class="red"> <!-- * -->
						</font>
					</div>
					<div class="wrapper clearfix">
						<div class="form">
							<div id="statusCombox" class="w100 c filterItem combox"
								keyName="key" style="text-align: left" valueName="value"
								name="status" vtype="combox"></div>
						</div>
						<div class="tip textshadow">状态</div>
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
					CMS.util.HttpAjax(contextPath + '/menu/roleInfoById.html', 
							'{"id": "'+ id +'"}', function(data){
						var rowData = {};
						if(data.result == 'success'){   
						   rowData = data.data.length != 0 ? data.data : {};
						} 
//						A.extend(rowData, row); 
						CMS.util.Form.setData(CMS.page.verifyEle, rowData || {});
						CMS.page.statusCombox.setValue(rowData.status);
						CMS.core.hideMask();
					})
				}; 
				
				CMS.page.getFormData = function(){
					
					if(!CMS.util.Form.verifyAll(CMS.page.verifyEle) ){
						CMS.page.formData = new CMS.util.Form.getData(A('.formwrapper'), 'formItem', []);
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
							 {key:2, value:'关闭'}
							], 
						function(value, text){ 
							//CMS.page.getResType(value)
						}); 
					
				};
				
				
					
			</script>
</body>
</html>

