<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
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
	jsessionid="<%=session.getId()%>";
	contextPath="<%=request.getContextPath()%>";
</script>
<link rel="stylesheet" type="text/css" href="skin/main.css" />
</head>
<body class="body">
	<div class="barwrapper" id="filterItems"
		style="border-bottom: 1px solid #c3c3c3; padding: 6px 10px 5px; text-indent: 5e;">
		<p>当前位置: 信息管理 >> 修改通知地址</p>
	</div>
	<div class="bodywarpper">
		<div>
			<ul class="formwrapper">
				<li>
					<div class="title">请选择应用</div>
					<div class="wrapper clearfix">
						<div class="form" id="chargeName">
							<div id="type" class="w100 filterItem combox" keyName="id"
								valueName="name" name="type" vtype="combox" setvalue="CMS.page.setTypeComboxVal">---请选择---</div>
						</div>
						<div class="tip textshadow"></div>
					</div> <b></b>
				</li>
				<li>
					<div class="title">
						原通知地址: <font class="red"> <!-- * -->
						</font>
					</div>
					<div class="wrapper clearfix">
						<div class="form">
							<input type="text" class="input formItem" maxlength="31"
								id="oldUrl" name="oldUrl" verify="通知地址|Length<=30"
								disabled="disabled" />
						</div>
						<div class="tip textshadow">该项不能修改</div>
					</div> <b></b>
				</li>
				<li>
					<div class="title">
						新通知地址: <font class="red"> <!-- * -->
						</font>
					</div>
					<div class="wrapper clearfix">
						<div class="form">
							<input type="text" class="input formItem screeninput"
								maxlength="31" id="newUrl" name="newUrl"
								verify="通知地址|NotNull|Length<=30" />
						</div>
						<div class="tip textshadow">
							<font class="red">如:http://www.joy.cn/</font>
						</div>
					</div> <b></b>
				</li>
				<li>
					<div class="title">
						是否需要通知开发者服务器: <font class="red"> <!-- * -->
						</font>
					</div>
					<div class="wrapper clearfix">
						<div class="form">
							<input type="radio" id="ds1" name="devserver" value="2"
								checked="checked" /><label for="ds1">不通知</label> <input
								type="radio" id="ds2" name="devserver" value="1" /><label
								for="ds2">通知</label>
						</div>
						<div class="tip textshadow"></div>
					</div> <b></b>
				</li>
			</ul>
		</div>
		<table width="100%" id="submitFormId" style="display: none">
			<tr>
				<td width="20%"></td>
				<td><a href="javascript:void(0);" class="zbutton"
					onclick="submitForm()"> <span>&nbsp;提 交&nbsp;</span>
						<div class="right"></div>
				</a></td>
			</tr>
		</table>
	</div>
	<script type="text/javascript" src="js/ajaxupload.js"></script>
	<script type="text/javascript" src="js/json2.js"></script>
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/common.js"></script>
	<script type="text/javascript" src="js/dialog/Drag.js"></script>
	<script type="text/javascript" src="js/dialog/Dialog.js"></script>
	<script type="text/javascript" src="js/jquery-migrate.min.js"></script>
	<script type="text/javascript">
		CMS.page.id = "";

		//初始化计费项
		CMS.page.layoutCharge = function() {
			CMS.page.chargeCombox = new CMS.util.Edit.Combox(A('#type'),
					contextPath + "/app/listAppInfo.html", 'click',
					function(value, text) {
						if (value == '0') {
							A('#oldUrl').val('');
							A('#submitFormId').hide();
							A('#ds1').attr('checked','checked');
							return;
						}
						CMS.page.formData = new CMS.util.Form.getData(
								A('.formwrapper'), 'formItem', []);
						CMS.page.formData.insert('id', "" + value);
						CMS.util.HttpAjax(contextPath + '/app/getAppInfoById.html',
								CMS.page.formData.toJson(), function(data) {
									if (data.result == "success") {
										CMS.page.id = data.data.id;
										A('#oldUrl').val(data.data.sendUrl);
										data.data.isDevServer == 1 ? A('#ds2').attr('checked', 'checked')
												: A('#ds1').attr('checked','checked');
										A('#submitFormId').show();
									} else {
										Dialog.alert("获取数据失败");
									}
								});
					});
		};

		A(document).ready(function() {
			CMS.page.layoutCharge();
		});
		
		CMS.page.setTypeComboxVal = function (category) {
			return CMS.page.stringXZ(10, category.name);
		};

		CMS.page.stringXZ = function (length, col) {
			col = (col || CMS.args.EMPTY).toString();
			if (col.length > length) {
				return '<span title="' + (col).html() + '">' + (col.substring(0, length) + '…') + '</span>';
			}
			return col
		};

		submitForm = function() {
			if (!CMS.util.Form
					.verifyAll(A('.formItem, .screeninput,.innerformItem'))) {
				CMS.page.formData = new CMS.util.Form.getData(
						A('.formwrapper'), 'formItem', []);
				CMS.page.formData.insert('id', CMS.page.id);
				CMS.page.formData.insert('sendUrl', A('#newUrl').val().trim());
				CMS.page.formData.insert('isDevServer', A(
						"input[name='devserver']:checked").val());
				Dialog.alert("警告：您确认要修改通知地址吗？", function() {
					CMS.util.HttpAjax(contextPath + "/app/updateUrl.html",
							CMS.page.formData.toJson(), function(data) {
								if (data.result == 'success') {
									Dialog.alert('修改成功!');
									A('#oldUrl').val(A('#newUrl').val());
									A('#newUrl').val('');
									var checkInput = A(
											"input[name='devserver']:checked")
											.val();
									checkInput == 2 ? A('#ds1').attr('checked',
										'checked'): A('#ds2').attr('checked', 'checked');
								} else {
									Dialog.alert('修改失败!');
								}
							});
				});
			} else {
				Dialog.alert('还有未正确填写的项，请参照提示修改!');
			}
		};
	</script>
</body>
</html>