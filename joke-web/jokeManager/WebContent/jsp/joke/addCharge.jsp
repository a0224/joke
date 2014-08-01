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
					<div class="title">计费项编号:</div>
					<div class="wrapper clearfix">
						<div class="form">
							<input type="hidden" class="input formitem" maxLength="31"
								id="id" name="id" value="" /> <input type="text" value='<s:property value="code" />'
								class="input formitem" maxLength="60" id="payCode" 
								disabled="disabled" name="payCode" />
						</div>
						<div class="tip textshadow">该项由系统自动生成</div>
					</div>
				</li>
				<li>
					<div class="title">
						计费项名:<font class="red">*</font>
					</div>
					<div class="wrapper clearfix">
						<div class="form">
							<input type="text" class="input formitem screeninput" maxLength="31"
								id="chargeName" name="chargeName" verify="计费项名|NotNull|Length<30" />
						</div>
						<div class="tip textshadow"></div>
					</div>
				</li>
				<li>
					<div class="title">
						价格(元):<font class="red">*</font>
					</div>
					<div class="wrapper clearfix">
						<div class="form">
							<input type="text" class="input formitem screeninput" maxLength="31"
								id="price" name="price"
								verify="价格|NotNull|regexEnum=num3" />
						</div>
						<div class="tip textshadow">精确到小数点后两位（最多2位小数），例如1.75</div>
					</div>
				</li>
				<li>
					<div class="title">
						支付方式:<font class="red">*</font>
					</div>
					<div class="wrapper clearfix">
						<div class="form">
							<input type="radio" name="payType" value="1" checked="checked" /><label for="pt1">话费</label>
							<!-- <input type="radio" name="payType" value="2"  /><label for="pt2">短信</label>
							<input type="radio" name="payType" value="3"  /><label for="rd1">支付宝</label>
							<input type="radio" name="payType"  value="4" /><label for="rd2">充值卡</label>
							<input type="radio" name="payType" value="5" /><label for="rd3">点卡</label>
							<input type="radio" name="payType"  value="6" /><label for="rd4">余额支付</label> -->
						</div>
						<div class="tip textshadow"></div>
					</div>
				</li>
				<li>
					<div class="title">
						支付规则:<font class="red">*</font>
					</div>
					<div class="wrapper clearfix">
						<div class="form">
							<input type="radio" name="type" id="rd1" value="1" checked="checked" /><label for="rd1">单次</label>
							<input type="radio" name="type" id="rd2" value="2" /><label for="rd2">包月</label>
						</div>
						<div class="tip textshadow"></div>
					</div>
				</li>
				<li>
					<div class="title">计费提示:</div>
					<div class="wrapper clearfix">
						<div class="form">
							<textarea class="textarea formItem"
								style="width: 260px; height: 70px;" maxlength="105" id="chaMsg"
								name="chaMsg" verify="计费提示|NotNull|Length&lt;=100"></textarea>
						</div>
						<div class="tip textshadow"></div>
					</div>
				</li>
				<li>
					<div class="title">计费描述:</div>
					<div class="wrapper clearfix">
						<div class="form">
							<textarea class="textarea formItem"
								style="width: 260px; height: 70px;" maxlength="105" id="chaMemo"
								name="chaMemo" verify="Length&lt;=100"></textarea>
						</div>
						<div class="tip textshadow"></div>
					</div>
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
		CMS.page.appCode =CMS.util.getQueryStr('appCode');
		CMS.page.verifyEle = A('.formItem, .screeninput');
		var gridrow = "";
		CMS.page.id=0;

		A(document).ready(function() {
			CMS.util.Form.verify(CMS.page.verifyEle);
		});

		CMS.page.setFormData = function(row) {
// 			CMS.util.Form.setData(CMS.page.verifyEle, row || {});
			CMS.page.id = row.id;
			CMS.page.appId = row.app_id;
			A('#payCode').val(row.pay_code);
			A('#chargeName').val(row.chargeName);
			A('#price').val(row.price);
			A("input[name='type']").each(function(){
				if($(this).val() == row.type){
					$(this).attr('checked','checked');
					return;
				}
			});
			A("input[name='payType']").each(function(){
				if($(this).val() == row.charge){
					$(this).attr('checked','checked');
					return;
				}
			});
			A('#chaMsg').val(row.cha_msg);
			A('#chaMemo').val(row.cha_memo);
		};
		
		CMS.page.getFormData = function(){
			if(!CMS.util.Form.verifyAll(CMS.page.verifyEle)){
				CMS.page.formData = new CMS.util.Form.getData(A('.formwrapper'), 'formItem', []);
				CMS.page.formData.insert('payCode', A('#payCode').val());
				CMS.page.formData.insert('chargeName', A('#chargeName').val());
				CMS.page.formData.insert('price', A('#price').val());
				CMS.page.formData.insert('type', A("input[name='type']:checked").val());
				CMS.page.formData.insert('payType', A("input[name='payType']:checked").val());
				CMS.page.formData.insert('appCode', CMS.page.appCode);
				CMS.page.formData.insert('chaMsg', A('#chaMsg').val());
				CMS.page.formData.insert('chaMemo', A('#chaMemo').val());
				CMS.page.formData.insert('id', CMS.page.id);
				return {
					object : CMS.page.formData.toObject(),
					json : CMS.page.formData.toJson()
				};
			}else{
				Dialog.alert('还有未正确填写的项，请参照提示修改!');
				return false;
			}
		};
		
	</script>
</body>
</html>