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
					信息<font class="red">*</font>
				</div>
				<div class="wrapper clearfix">
					<div class="form">
						<textarea class="textarea formItem" id="msg" name="msg"
							verify="NotNull|XLength<=1000"></textarea>
					</div>
					<div class="tip textshadow">必填，长度1000个字符以内，一个汉字占两个字符。</div>
				</div> <b></b>
			</li>

			<li class="local">
				<div class="title">
					图片 <font class="red"></font>
				</div>
				<div class="wrapper clearfix">
					<div class="form">
						<input type="text" readonly="readonly"
							class="input formItem uploadinput" vtype="upload" id="picUrl"
							name="picUrl" verify="图片url|Length<=200" /> <input
							type="button" value="选择" class="button radius4" id="picUrlBut" />

						<div id="picUrlWrite" class="radius4 mt10"></div>
					</div>

					<div class="tip textshadow">建议尺寸为 宽960*高800，最大不超过1M，格式jpg png。</div>
				</div> <b></b>
			</li>
			<!-- <li>
				<div class="title">
					显示等级<font class="red"></font>
				</div>
				<div class="wrapper clearfix">
					<div class="form">
						<input type="text" class="input formItem"  value="99" id="orderBy" name="orderBy"
							verify="显示等级|regexEnum=num1|XLength<=5" />
					</div>
					<div class="tip textshadow">填写要排序的编号 号码越小排列位置越靠前</div>
				</div> <b></b>
			</li> -->

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
		CMS.page.id=0;
		var dataResult='';
		//		CMS.page.type = CMS.util.getQueryStr('type');

		CMS.page.uploadImageUrl = contextPath+ '/upload/uploadImg.html?size=1024';
		CMS.page.initPage = function() {
			CMS.util.Form.verify(A('.formItem'));

			CMS.util.Edit.AjaxUpload('#picUrlBut', contextPath+ 
				'/upload/uploadImg.html?size=1024',
				CMS.page.lastFloder, '#picUrl', function(data, element,input) {
					CMS.page.setPicImage.call(input, CMS.page.url(data.url), data);
					dataResult = data;
					input.removeClass('borderred');
				}, function(data, element, input) {
					//					CMS.page.setPicImage.call(input, CMS.args.EMPTY)
				}, [ "jpg", "jpeg", "png" ]);
			
		};

		CMS.page.setPackage = function(data) {
			data = data || {};
			CMS.page.packageUrl = data.url;
			if (data.url) {
				this.value = data.url;
			} else {
				this.value = '';
			}
		}

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
				
				var postData = {
					id:CMS.page.id,
					msg : A('#msg').val().trim(),
					type : '3',
					orderBy : A('#orderBy').val()==""?'99':A('#orderBy').val(),
					status : '1',
					url:A('#picUrl').val(),
					size:dataResult.filesize
				};

				return postData;
			}
			return false;
		};

		CMS.page.setPicImage = function(val, obj) {
			CMS.page.setImg.call(this, val, obj, 50)
		};

		CMS.page.setImg = function(val, obj, width) {
			var imgwapper = this.nextAll('div').find('img');
			if (imgwapper.size() > 0) {
				imgwapper.attr('src', val)
			} else {
				imgwapper.end().append(
						'<img class="radius4" height="150" src=' + val + '>')
			}
		};
		
		CMS.page.url = function(url) {
			if (url.indexOf("http://") < 0) {
				return "${session.down}" + url;
			} else {
				return url;
			}
		}
	</script>
</body>
</html>

