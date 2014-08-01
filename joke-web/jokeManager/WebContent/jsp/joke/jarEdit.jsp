<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
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
<title>添加应用</title>
<script>
	contextPath="<%=request.getContextPath()%>";
	jsessionid="<%=session.getId()%>";
</script>
<link rel="stylesheet" type="text/css" href="skin/main.css" />
</head>
<body class="body">
	<div class="bodywarpper">
		<div>
			<ul class="formwrapper">
				<li>
					<div class="title">
						JAR包 <font class="red"> <!-- * -->
						</font>
					</div>
					<div class="wrapper clearfix">
						<div class="form">
							<input type="text" class="input formItem uploadinput" vtype="upload"
								id="url" name="url"
								setDataFun="CMS.page.setPackage"
								verify="软件包|NotNull|Length<=500" /> <input type="button"
								value="选择" class="button radius4" id="urlBut" />
						</div>
						<div class="tip textshadow">jar文件</div>
					</div> <b></b>
				</li>
				
				<li>
					<div class="title">
						名称:<font class="red">*</font>
					</div>
					<div class="wrapper clearfix">
						<div class="form">
							<input type="text" class="input formitem screeninput"
								maxLength="31" id="name" name="name"
								verify="名称|NotNull|Length<30" />
						</div>
						<div class="tip textshadow"></div>
					</div>
				</li>
				<li>
					<div class="title">
						类名:<font class="red">*</font>
					</div>
					<div class="wrapper clearfix">
						<div class="form">
							<input type="text" class="input formitem screeninput"
								maxLength="31" id="className" name="className"
								verify="类名|NotNull|Length<30" />
						</div>
						<div class="tip textshadow">包名.类名</div>
					</div>
				</li>
				<li>
					<div class="title">
						Url地址:<font class="red">*</font>
					</div>
					<div class="wrapper clearfix">
						<div class="form">
							<input type="text" class="input formItem" maxlength="1000"
									id="link" name="link"
									verify="Url地址|NotNull" />
						</div>
						<div class="tip textshadow">必填，长度1000个字符以内，一个汉字占两个字符。</div>
					</div> <b></b>
				</li>
				<li>
					<div class="title">
						作者:<font class="red">*</font>
					</div>
					<div class="wrapper clearfix">
						<div class="form">
							<input type="text" class="input formitem screeninput"
								maxLength="31" id="author" name="author"
								verify="作者|Length<30" />
						</div>
						<div class="tip textshadow">写上你 的名字</div>
					</div>
				</li>
			</ul>
		</div>
	</div>
	<script type="text/javascript" src="js/ajaxupload.js"></script>
	<script type="text/javascript" src="js/json2.js"></script>
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/common.js"></script>
	<script type="text/javascript" src="js/dialog/Drag.js"></script>
	<script type="text/javascript" src="js/dialog/Dialog.js"></script>
	<script type="text/javascript" src="js/jquery-migrate.min.js"></script>
	<script type="text/javascript">
		CMS.page.verifyEle = A('.formItem, .screeninput');
		var gridrow = "";
		CMS.page.id = 0;
		CMS.page.uploadArray = [];
		CMS.page.uploadCount = 0;
		CMS.page.packageMes = A('.packageMes');
		CMS.page.lastFloder = CMS.args.EMPTY;
		CMS.page.packageName = "";
		CMS.page.size = "";
		CMS.page.versionCode = "";

		A(document).ready(
				function() {
					CMS.util.Form.verify(CMS.page.verifyEle);

					CMS.util.Edit.AjaxUpload('#urlBut', contextPath
							+ '//upload/fileUpload.html', CMS.page.lastFloder,
							'#url', function(data, element, input) {
								A('#url').val(data.url);
							}, function(data, element, input) {
							}, [ 'jar' ]);

				});
		
		CMS.page.setImg = function(val, obj, width) {
			if (CMS.util.isEmpty(val))
				return;
			var imgwapper = this.nextAll('div').find('img');
			if (imgwapper.size() > 0) {
				imgwapper.attr('src', val)
			} else {
				imgwapper.end().append(
				'<img class="radius4" onerror="this.src=contextPath + \'/images/uu_icon.png\'" style="height:50px;width:'
						+ (width || 50)+ 'px;" src='+ val+ '>')
			}
		};

		CMS.page.setIconImg = function(val, obj) {
			CMS.page.setImg.call(this, val, obj, 50)
		};

		CMS.page.setFormData = function(row) {
			// 			CMS.util.Form.setData(CMS.page.verifyEle, row || {});
			row = row[0].o.getValue('rowData');
			CMS.page.id = row.id;
			A('#name').val(row.name);
			A('#url').val(row.jarUrl);
			A('#className').val(row.className);
			A('#author').val(row.author);
			A('#link').val(row.link);
		};

		CMS.page.getFormData = function() {
			if (!CMS.util.Form.verifyAll(CMS.page.verifyEle)) {
				CMS.page.formData = new CMS.util.Form.getData(
						A('.formwrapper'), 'formItem', []);
				CMS.page.formData.insert('id', CMS.page.id);
				CMS.page.formData.insert('name',A('#name').val());
				alert(A('#name').val());
				CMS.page.formData.insert('url',A('#url').val());
				CMS.page.formData.insert('className', A('#className').val());
				CMS.page.formData.insert('author', A('#author').val());
				CMS.page.formData.insert('link', A('#link').val());
				return {
					object : CMS.page.formData.toObject(),
					json : CMS.page.formData.toJson()
				};
			} else {
				Dialog.alert('还有未正确填写的项，请参照提示修改!');
				return false;
			}
		};
		
	</script>
</body>
</html>