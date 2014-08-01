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
.listul li {
	padding: 2px
}

textarea.textarea {
	width: 350px;
	height:200px;
}

label.selected {
	color: red
}
</style>
</head>
<body class="body">
	<div class="bodywarpper" style="overflow: hidden; * zoom: 1;">
		<ul class="formwrapper">
			<li>
				<div class="title">
						Jar包选择<font class="red">*</font>
					</div>
					<div class="wrapper clearfix">
						<div class="form">
							<div id="categoryCombox" keyName="id"
								valueName="name" vtype="combox" style="width: 300px;">选择Jar包</div>
						</div>
						<div class="tip textshadow">Jar包选择。</div>
					</div> <b></b>
				</li>
		</ul>
	</div>
	<script type="text/javascript" src="js/json2.js"></script>
	<script type="text/javascript" src="js/ajaxupload.js"></script>
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/common.js"></script>
	<script type="text/javascript" src="js/dialog/Drag.js"></script>
	<script type="text/javascript" src="js/dialog/Dialog.js"></script>
	<script type="text/javascript">
		CMS.page.verifyEle = A('.formItem, .screeninput'); 
		CMS.page.lastFloder = CMS.args.EMPTY;

		CMS.page.initPage = function() {
			CMS.util.Form.verify(A('.formItem'));
			CMS.page.initForm();
			
		};

		CMS.core.document.ready(CMS.page.initPage);
		
		CMS.page.setFormData = function (row){
			data = row[0].o.getValue('rowData');
//			alert(JSON.stringify(data));
			CMS.util.Form.setData(CMS.page.verifyEle, data || {});
			CMS.page.id=data.id;

			if(data.img){
				A('#picUrl').val(data.img);
				CMS.page.setPicImage.call(A("#picUrl"), "${session.down}" + data.img, data);
				dataResult = data;
				A("#picUrl").removeClass('borderred');
			}
		}
		
		CMS.page.getFormData = function() {
			if (!CMS.util.Form.verifyAll(A('.formItem'))) {
				
				if(!CMS.page.categoryCombox.getValue()){
					Dialog.alert("请选择工具包!");
				}
				
				var postData = {
					type : CMS.page.categoryCombox.getValue()||0,
				};
				return postData;
			}
			return false;
		};

		CMS.page.initForm = function(){
			
			var arr;
			CMS.util.HttpAjax(contextPath + '/jar/list.html',"{}", function(data){
				CMS.page.resTypeArr = (data.result == 'success') ? data.root : []; 
				arr = CMS.page.resTypeArr;
			}, {}, true);
			CMS.page.categoryCombox = new CMS.util.Edit.Combox(A('#categoryCombox'),arr);
		};
	</script>
</body>
</html>

