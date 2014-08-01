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
				<!--<li>
					<div class="title">APP编号:</div>
					<div class="wrapper clearfix">
						<div class="form">
							<input type="hidden" class="input formitem" maxLength="31"
								id="id" name="id" value="" /> <input type="text" value='<s:property value="code" />'
								class="input formitem" maxLength="60" id="appCode"  name="appCode" />
						</div>
						<div class="tip textshadow">该项由系统自动生成</div>
					</div>
				</li>-->
				<li>
					<div class="title">
						软件包 <font class="red"> <!-- * -->
						</font>
					</div>
					<div class="wrapper clearfix">
						<div class="form">
							<input type="text" readonly="readonly"
								class="input formItem uploadinput" vtype="upload"
								id="appPackage" name="appPackage"
								setDataFun="CMS.page.setPackage"
								verify="软件包|NotNull|Length<=500" /> <input type="button"
								value="选择" class="button radius4" id="appPackageBut" />
							<div class="radius4 mt10 packageMes hidden">
								<ul>
									<li>包&#12288;名：<font style="font-size: 13px;">&nbsp;</font></li>
									<li>大&#12288;小：<font style="font-size: 13px;">&nbsp;</font>KB
									</li>
									<li>版&#12288;本：<font style="font-size: 13px;">&nbsp;</font></li>
<!-- 									<li>平&#12288;台：<label class="veralign"> -->
<!-- 											<div class="veralign" -->
<!-- 												style="display: inline-block; *display: inline; *zoom: 1; width: 120px; height: 25px; line-height: 25px" -->
<!-- 												id="apk" class="filterItem" keyName="key" valueName="value" -->
<!-- 												name="apk" vtype="combox"></div> -->
<!-- 									</label></li> -->
								</ul>
							</div>
						</div>
						<div class="tip textshadow">支持安卓系统的安装包，后缀格式为.apk</div>
					</div> <b></b>
				</li>
				<li>
					<div class="title">
						Icon图片 <font class="red"> <!-- * -->
						</font>
					</div>
					<div class="wrapper clearfix">
						<div class="form">
							<input type="text" readonly="readonly"
								class="input formItem uploadinput" vtype="upload" id="iconUrl"
								name="iconUrl" fileSize="100K" setDataFun="CMS.page.setIconImg"
								verify="Icon图片|NotNull|Length<=200" /> <input type="button"
								value="选择" class="button radius4" id="iconUrlBut" />
							<div class="radius4 mt10"></div>
						</div>
						<div class="tip textshadow">
							建议大小100KB以内，最佳尺寸96*96和72*72；后缀格式支持jpg、png（最佳格式png）</div>
					</div> <b></b>
				</li>
				<li>
					<div class="title">
						APP名称:<font class="red">*</font>
					</div>
					<div class="wrapper clearfix">
						<div class="form">
							<input type="text" class="input formitem screeninput"
								maxLength="31" id="name" name="name"
								verify="APP名称|NotNull|Length<30" />
						</div>
						<div class="tip textshadow"></div>
					</div>
				</li>
				<li>
					<div class="title">
						APP价格:<font class="red">*</font>
					</div>
					<div class="wrapper clearfix">
						<div class="form">
							<input type="text" class="input formitem screeninput"
								maxLength="31" id="price" name="price"
								verify="APP价格|Length<30" />
						</div>
						<div class="tip textshadow"></div>
					</div>
				</li>
				<li>
					<div class="title">应用描述:</div>
					<div class="wrapper clearfix">
						<div class="form">
							<textarea class="textarea formItem"
								style="width: 260px; height: 70px;" maxlength="105" id="descrip"
								name="descrip" verify="Length&lt;=100"></textarea>
						</div>
						<div class="tip textshadow"></div>
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
					CMS.util.Edit.AjaxUpload('#iconUrlBut', contextPath
							+ '//upload/uploadImg.html?size=100',
							CMS.page.lastFloder, '#iconUrl', function(data,
									element, input) {
								CMS.page.setIconImg.call(input,
										"${session.down}" + data.url, data);
								input.removeClass('borderred');
							}, function(data, element, input) {
							}, [ "png", "jpg" ]);

					CMS.util.Edit.AjaxUpload('#appPackageBut', contextPath
							+ '//upload/apkUpload.html', CMS.page.lastFloder,
							'#appPackage', function(data, element, input) {
								CMS.page.setPackage.call(input, data);
								input.removeClass('borderred');
								if (data.iconUrl != "") {
									input = CMS.util.toJquery("#iconUrl");
									input.val(data.iconUrl);
									CMS.page.setIconImg.call(input,
											"${session.down}" + data.iconUrl,
											data);
									input.removeClass('borderred');
								}

							}, function(data, element, input) {
								CMS.page.setPackage.call(input, {});
							}, [ 'apk' ]);

				});

		CMS.page.setPackage = function(data) {
			data = data || {};
			CMS.page.packageUrl = data.url;
			CMS.page.packageName = data.packageName;
			CMS.page.packageSize = data.size;
			CMS.page.versionName = data.versionName;
			CMS.page.versionCode = data.versionCode;
			CMS.page.resCloudId = data.resCloudId;
			CMS.page.appPackageId = data.appPackageId;
			CMS.page.remoteUrl = data.remoteUrl;
			CMS.page.remoteRealUrl = data.remoteRealUrl;
			delete CMS.page.coverFlag;
			var url = data.url;
			if (url) {
				if (CMS.page.UploadArray) {
					for (var i = CMS.page.UploadArray.length; i--;) {
						if (CMS.page.UploadArray[i]._settings.action
								.indexOf("uploadApk") >= 0) {
							CMS.page.UploadArray[i]._settings.action = contextPath
									+ '/app/uploadApk.html'
									+ (CMS.page.resCloudId ? "?resCloudId="
											+ CMS.page.resCloudId
											+ "&appPackageId="
											+ CMS.page.appPackageId : "");
						}
					}
				}
				if (data.bookFlag == 1) {
					Dialog.alert("该软件包正在被编辑,请稍候重试!");
					CMS.page.clearPackageMsg();
				} else {
					if (data.ExistsFlag == 1) {
						CMS.page.showPackageMsg();
						var tmp = '';
						Dialog.confirm("您上传的软件包与" + tmp + "[<b>"
								+ data.ExistsName + "</b>]软件包相同，确定要上传吗？上传后"
								+ tmp + "[<b>" + data.ExistsName + "</b>]将下线!",
								function() {
									CMS.page.confirmUploadApk();
								}, CMS.page.clearPackageMsg);
					} else {
						this.val(data.remoteUrl);
						CMS.page.showPackageMsg();
					}
				}
			} else {
				CMS.page.clearPackageMsg();
			}
		};
		
		CMS.page.clearPackageMsg = function(){
			CMS.page.packageUrl = undefined;
			CMS.page.packageName = undefined;
			CMS.page.packageSize = undefined;
			CMS.page.versionName = undefined;
			CMS.page.versionCode = undefined;
			CMS.page.resCloudId = undefined;
			CMS.page.appPackageId = undefined;
			CMS.page.remoteUrl = undefined;
			CMS.page.remoteRealUrl = undefined;
			CMS.page.packageMes.hide();
			A("#appPackage").val("");
			var label = CMS.page.packageMes.find('font');
			label.eq(0).text("");
			label.eq(1).text("");
			label.eq(2).text("");
		};

		CMS.page.showPackageMsg = function() {
			if (CMS.page.packageName) {
				CMS.page.packageMes.fadeIn('200');
				A("#appPackage").val(CMS.page.remoteUrl || CMS.page.packageUrl);
				var label = CMS.page.packageMes.find('font');
				label.eq(0).text(CMS.page.packageName);
				label.eq(1).text(CMS.page.packageSize || 0);
				label.eq(2).text(CMS.page.versionName);
			}
		};

		CMS.page.setImg = function(val, obj, width) {
			if (CMS.util.isEmpty(val))
				return;
			var imgwapper = this.nextAll('div').find('img');
			if (imgwapper.size() > 0) {
				imgwapper.attr('src', val)
			} else {
				imgwapper
						.end()
						.append(
								'<img class="radius4" onerror="this.src=contextPath + \'/images/uu_icon.png\'" style="height:50px;width:'
										+ (width || 50)
										+ 'px;" src='
										+ val
										+ '>')
			}
		};

		CMS.page.setIconImg = function(val, obj) {
			CMS.page.setImg.call(this, val, obj, 50)
		};

		CMS.page.setFormData = function(row) {
			// 			CMS.util.Form.setData(CMS.page.verifyEle, row || {});
			CMS.page.id = row.id;
			A('#appCode').val(row.app_code);
			CMS.page.remoteUrl = row.package;
			A('#appPackage').val(row.url);
			A('#iconUrl').val(row.img);
			A('#descrip').val(row.descrip);
			A('#price').val(row.price);
			
			CMS.page.packageName = row.package;
			CMS.page.packageSize = row.size;
			CMS.page.versionName = row.vcode;
			CMS.page.showPackageMsg();
			input = CMS.util.toJquery("#iconUrl");
			input.val(row.img);
			CMS.page.setIconImg.call(input, "${session.down}"+row.img, row);
			input.removeClass('borderred');
			
			A('#name').val(row.name);
			A('#memo').val(row.memo);
		};

		CMS.page.getFormData = function() {
			if (!CMS.util.Form.verifyAll(CMS.page.verifyEle)) {
				CMS.page.formData = new CMS.util.Form.getData(
						A('.formwrapper'), 'formItem', []);
				CMS.page.formData.insert('id', CMS.page.id);
				CMS.page.formData.insert('appCode',A('#appCode').val());
				CMS.page.formData.insert('img',A('#iconUrl').val());
				CMS.page.formData.insert('name', A('#name').val());
				CMS.page.formData.insert('price', A('#price').val());
				var label = CMS.page.packageMes.find('font');
				CMS.page.formData.insert('url', A('#appPackage').val());
				CMS.page.formData.insert('size',label.eq(1).text());
				CMS.page.formData.insert('versionCode',label.eq(2).text());
				CMS.page.formData.insert('memo', A('#memo').val());
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