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
		.td {
			background-color: #FFFFFF;
			border-bottom: 1px solid #DAF0FB;
			padding-bottom: 0px;
			width: 100%;
		}

		.pagewrapper {
			background-color: #FFFFFF;
			margin-top: -8px;
		}

		.pageformtitle {
			height: 32px;
			line-height: 32px;
			background-color: #F7FCFF;
			background-image: -moz-linear-gradient(center top, #FFFFFF, #EFF8FD);
			color: #ff8534;
			text-indent: 10px;
			border-top: 1px solid #DFDFDF;
			border-bottom: 1px dashed #DFDFDF;
			border-collapse: collapse;
			overflow: hidden;
			text-shadow: 0 1px 0 #FFFFFF
		}

		li.c {
			text-align: left;
		}
	</style>

</head>
<body class="body">
<div class="barwrapper" id="filterItems"
		style="border-bottom: 1px solid #c3c3c3; padding: 6px 10px 5px; text-indent: 5e;">
		<p>当前位置: 用户管理 >> 扣量管理</p>
</div>
<div class="bodywarpper">

<!-- 	<div class="pageformtitle"> -->
<!-- 		<table> -->
<!-- 			<tr> -->
<!-- 				<td><img src="images/airpush/sticky-note-pin.png"></td> -->
<!-- 				<td><span style="color:#ff8534">小贴士：参数修改后，1天后生效</span></td> -->
<!-- 			</tr> -->
<!-- 		</table> -->
<!-- 	</div> -->

	<div>
		<ul class="formwrapper">
			<li id="channelDivId">
				<div class="title">
					<span style="font-weight:bold">渠道:</span>
				</div>
				<div class="barwrapper">
					<div id="resCombox" class="w100 c formItem combox" keyName="id" style="width:150px;"
						valueName="nickName" name="categoryParentId" vtype="combox" setValue="CMS.page.setComboxVal">全局默认</div>

					<div id="resTypeCombox" class="w100 c formItem" keyName="channel" style="display:none;width: 150px;"
						valueName="deduct" name="categoryId" vtype="combox" setvalue="CMS.page.setResTypeComboxVal"></div>
					
					<div class="tip textshadow">
					</div>
				</div>
				<b></b>
			</li>
			
			<li id="paramsDivId">
				<div class="title">
					<span style="font-weight:bold">参数:</span>
				</div>
				<div class="barwrapper">
					<input type="text" class="inputsel w100 formItem" maxlength="50" name="deduct" id="deduct" verify="NotNull|regexEnum=num3"/>
				</div>
			</li>

		</ul>
		<table width="100%" id="submitFormId">
			<tr>
				<td width="20%"></td>
				<td><a href="javascript:void(0);" class="zbutton" onclick="submitForm()">
					<span>&nbsp;提   交&nbsp;</span>
					<div class="right"></div>
				</a></td>
			</tr>
		</table>
	</div>

</div>

<script type="text/javascript" src="js/json2.js"></script>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery-migrate.min.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/dialog/Drag.js"></script>
<script type="text/javascript" src="js/dialog/Dialog.js"></script>
<script type="text/javascript">

	CMS.page.id = '0';
	CMS.page.channel = '';
	CMS.page.channelId = 0;
	A(document).ready(function () {
// 		CMS.page.layoutChannel();
		
		CMS.page.resTypeArr_user = "";
		CMS.page.resTypeArr_channel = "";
		
// 		CMS.util.HttpAjax(contextPath + '/user/userlist.html',"{}", function(data){
// 			CMS.page.resTypeArr_user = (data.result == 'success') ? data.root : [];
// 			CMS.page.resTypeArr_user.by = [[]];
// 			A.each(CMS.page.resTypeArr_user, function(i,type){
// 				CMS.page.resTypeArr_user.by[0].push(type);
// 			});
// 		}, {}, true);
		
// 		CMS.util.HttpAjax(contextPath + '/user/getChannelList.html',"{}", function(data){[]
// 			CMS.page.resTypeArr_channel = (data.result == 'success') ? data.list : [];
// 			CMS.page.resTypeArr_channel.by = [[{channel:0, name:"--请选择--"}],[{channel:0, name:"--请选择--"}]];
// 		 		A.each(CMS.page.resTypeArr_channel, function(i,type){
// 		 			CMS.page.resTypeArr_channel.by[0].push(type.channel);
// 		 		});
// 	 	}, {}, true);
		
		CMS.page.setResTypeComboxVal = function (category) {
			return CMS.page.stringXZ(15, category.name||category.channel);
		};

		CMS.page.stringXZ = function (length, col) {
			col = (col || CMS.args.EMPTY).toString();
			if (col.length > length) {
				return '<span title="' + (col).html() + '">' + (col.substring(0, length) + '…') + '</span>';
			}
			return col;
		};

		CMS.page.resTypeCombox = new CMS.util.Edit.Combox(A('#resTypeCombox'), [], function (channel, text) {
			CMS.page.channelIdSelect(channel);
		});

		CMS.page.resCombox = new CMS.util.Edit.Combox(A('#resCombox'),contextPath + '/user/userlist.html',
			function(value, text){
			CMS.page.channelId = value;
			CMS.page.getChannel(value);
		});
		
		CMS.page.channelIdSelect(0);
		
	});
	
	CMS.page.getChannel = function(userId){
		if(userId == 0){
			CMS.page.channelId = '0';
			A('#deduct').val('');
			CMS.page.channel = '';
			A('#resTypeCombox').hide();
			return;
		}
		CMS.page.formData = new CMS.util.Form.getData(A('.formwrapper'), 'formItem', []);
		CMS.page.formData.insert('id', userId);
		CMS.util.HttpAjax(contextPath + '/user/getChannelListById.html',CMS.page.formData.toJson(), function(data){
		CMS.page.resTypeArr_channel = (data.result == 'success') ? data.list : [];
		CMS.page.resTypeArr_channel.by = [[{channel:0, name:"--请选择--"}],[{channel:0, name:"--请选择--"}]];
	 		A.each(CMS.page.resTypeArr_channel, function(i,type){
	 			CMS.page.resTypeArr_channel.by[0].push(type);
	 		});
 		}, {}, true);
		CMS.page.getResType(1);
	}
	
	CMS.page.channelIdSelect = function (channel) {
		if(channel == '0'){
			A('#deduct').val('');
			CMS.page.channel = '';
			return;
		}
		CMS.page.formData = new CMS.util.Form.getData(A('.formwrapper'), 'formItem', []);
		CMS.page.formData.insert('channel', channel);
		CMS.util.HttpAjax(contextPath + '/user/getDeductInfo.html', CMS.page.formData.toJson(),
				function (data) {
					CMS.page.channel = channel;
					A('#deduct').val(data.deduct);			
// 					if(data.deduct == null){
// 						Dialog.alert("未找到数据!");
// 					}else{
// 						A('#deduct').val(data.deduct);						
// 					}
		});
	}

	submitForm = function () {
		if(CMS.page.channelId == '0'){
			CMS.page.channel = '';	
		}
		if (!CMS.util.Form.verifyAll(A('.formItem'))) {
			CMS.page.formData = new CMS.util.Form.getData(A('.formwrapper'), 'formItem', []);
			CMS.page.formData.insert('parentId', CMS.page.channelId);
			CMS.page.formData.insert('deduct', A('#deduct').val().trim());
			CMS.page.formData.insert('channel', CMS.page.channel);
			Dialog.confirm('警告：您确认要修改扣量吗？', function () {
				CMS.util.HttpAjax(contextPath + '/user/updateDeduct.html', CMS.page.formData.toJson(),
						function (data) {
							if (data.result == 'success') {
//								if (data.ret && (data.ret.flag == 1)) {
									Dialog.alert("保存成功!");
									CMS.page.channelIdSelect(CMS.page.channel);
//								} else {
//									Dialog.alert(data.msg);
//								}
							} else if(data.result == 'failed'){
								Dialog.alert(data.message);
							} else {
								Dialog.alert("保存失败!");
							}
						}, CMS.util.ajaxProcess());
			})
		}else{
			Dialog.alert('请输入小数,且精确到小数点后两位');
			return;
		}
	}
	
	CMS.page.setComboxVal = function (apk) {
		return apk.nickName;
// 		if (!apk.id) {
// 			return apk.nickName;
// 		}
// 		return CMS.util.getSimpleApkName(apk.nickName) + " <font class=\"red\">(" + apk.id + ")</font>";
	};

	CMS.page.getResType = function(key){  
		if(key != 0){
			A('#resTypeCombox').show();
			CMS.page.resTypeCombox.reset(CMS.page.resTypeArr_channel.by[0]);
		} else {
			A('#resTypeCombox').hide();
		}
// 		if(key == 1){
// 			CMS.page.resTypeCombox.reset(CMS.page.resTypeArr_user.by[0]);
// 		}
// 		if(key == 2){
// 			CMS.page.resTypeCombox.reset(CMS.page.resTypeArr_channel.by[0]);
// 		}
	};
	
</script>
</body>
</html>