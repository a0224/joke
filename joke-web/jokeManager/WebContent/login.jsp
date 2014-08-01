<!DOCTYPE html>
<%@ page  contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String contextPath = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ contextPath + "/";
%>
<html>
<head>
<meta property="qc:admins" content="3502271265161752634754627163672735516175271645060454" />
<meta charset="utf-8">
<meta http-equiv="x-ua-compatible" content="ie=7" />
<base href="<%=basePath%>" />
<title>管理平台</title>
<script>
	contextPath="<%=request.getContextPath()%>";
	jsessionid="<%=session.getId()%>";
</script>
<link type="text/css" rel="stylesheet" href="skin/main.css" />
<style type="text/css">
body{
		overflow-x: visible !important;
		background:#bbdbef;
	}
.loginsubmit {
	border: none;
	height: 49px;
	line-height: 49px;
	width: 247px;
	color: #545454;
	font:bold 20px "黑体";
	background: url("pictures/loginbtn.png");
}

.loginicon {
	width: 250px;
	height: 180px;
	border-right: 1px dotted #CCCCCC;
	background: #FFFFFF url('images/logoimg.gif') no-repeat left center;
}
.loginDiv{
	width: 900px;
	height: 425px;
	background:#bbdbef url('pictures/loginDivBg.png') no-repeat left center;
}
.logindt{
	width: 430px;
	height: 400px;
}
.logindd{
	width: 400px;
	height: 400px;
	padding-top: 150px;
	font:bold 20px/22px "黑体";
	color: #fff;
	
}
</style>
<script type="text/javascript">
 function cen(){  
  var divname = document.all("testdiv");  
//居中高度等于页面内容高度减去DIV的高度 除以2  
var topvalue = (document.body.clientHeight - divname.clientHeight)/2;  
divname.style.marginTop = topvalue;  
}  
//页面大小发生变化时触发  
window.onresize = cen;  
</script>  
</head>
<body >
	<!--系统初始化进度开始-->
	<div id="apploading" class="apploading hidden" style="display: none"></div>
	<!--系统初始化进度结束-->
	<div id="cententWapper">
		<dl class="loginDiv center c padding10 clearfix relative"
			style="width: 900px; height: 425px; top: 130px;">
			<dt class="logindt fl c overhide" title="厂商版CMS"></dt>
			<dd class="logindd fl c overhide" style="">
				<table cellpadding="5" cellspacing="0">
					<tbody>
						<tr>
							<td class="r" width="90">帐号：</td>
							<td><input class="input formItem" type="text" id="loginName"
								name="loginName"
								style="height: 23px; line-height: 23px;" value="" /></td>
						</tr>
						<tr>
							<td class="r">密码：</td>
							<td><input class="input formItem" type="password"
								id="userPaWord" name="userPaWord"
								style="height: 23px; line-height: 23px;" value="" /></td>
						</tr>
						<tr>
							<td class="r">验证码：</td>
							<td class="l"><input class="input veralign formItem"
								size="4" maxlength="4" type="text" id="validate"
								name="validate"
								style="width: 80px; height: 23px; line-height: 23px;" /> <img
								class="veralign pointer"
								src="validateCode.html" id="validateImage"
								alt="验证码" title="点击刷新"
								onclick="this.src='validateCode.html?a='+new Date().getTime()" />
							</td>
						</tr>
						<tr>
							<td colspan="2" class="loginerror"></td>
						</tr>
					</tbody>
					<tfoot>
						<tr>
							<td colspan="2" class="btn"><input
								class="loginsubmit pointer" id="loginsubmit" value="登录"
								type="submit" onclick='CMS.page.loginSubmit()' /></td>

						</tr>
					</tfoot>
				</table>
			</dd>
		</dl>
		<div class="center c absolute" style="bottom: 30px; width: 100%">
			Powered by <a href="http://www.joy.cn" target="_blank">joy</a>! ©
			2013
		</div>


		<div id="ajaxLoading" class="dialogwarpper padding10 c"
			style="display: none;">
			<span>正在登录，请稍后......</span> <br /> <br /> <img
				src="images/ajax-loader.gif" /> <br /> <br /> <span>登录过程需要一段时间，请您耐心等待！</span>
		</div>
	</div>


	<script type="text/javascript" src="js/json2.js"></script>
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/jquery-migrate.min.js"></script>
	<script type="text/javascript" src="js/common.js"></script>
	<script type="text/javascript" src="js/dialog/Drag.js"></script>
	<script type="text/javascript" src="js/dialog/Dialog.js"></script>

	<script type="text/javascript">
		CMS.page.hasDialog = false;

		CMS.page.initPage = function() {
			A('input:first').focus();
			//if (typeof window.event != 'undefined') {
			//	CMS.core.document.bind('keydown', CMS.page.keyPress)
			//} else {
			CMS.core.document.bind('keypress', CMS.page.keyPress);
			//}
		};

		CMS.page.keyPress = function(e) {
			if (!CMS.page.hasDialog) {
				if (e.which == 13)
					CMS.page.loginSubmit();
			} else {
				if (CMS.page.Dailog) {
					CMS.page.Dailog.close();
					CMS.page.Dailog = null;
					CMS.page.hasDialog = false;
				}
			}
		};

		CMS.core.document.ready(CMS.page.initPage);

		CMS.page.loginSubmit = function() {
			var loginName = A('#loginName').val(), password = A('#userPaWord')
					.val(), validate = A('#validate').val();

			if (CMS.util.isEmpty(loginName)) {
				CMS.page.hasDialog = true;
				setTimeout(function() {
					CMS.page.Dailog = Dialog.alert('登录名不能为空',
							CMS.page.hasDialogFun);
				}, 100);
				return;
			}

			if (CMS.util.isEmpty(password)) {
				CMS.page.hasDialog = true;
				setTimeout(function() {
					CMS.page.Dailog = Dialog.alert('登录密码不能为空',
							CMS.page.hasDialogFun);
				}, 100);
				return;
			}

			if (CMS.util.isEmpty(validate)) {
				CMS.page.hasDialog = true;
				setTimeout(function() {
					CMS.page.Dailog = Dialog.alert('验证码不能为空',
							CMS.page.hasDialogFun);
				}, 100);
				return;
			}

			CMS.page.formData = new CMS.util.Form.getData(A('table'),
					'formItem', [ 'loginName' ]);
			CMS.page.formData.insert('userPaWord', A('#userPaWord').val());
			CMS.page.hasDialog = true;
			CMS.page.ajaloadWin = new Dialog();
			CMS.page.ajaloadWin.Width = 380;
			CMS.page.ajaloadWin.Height = 140;
			CMS.page.ajaloadWin.Title = '正在登录，请稍后......';
			CMS.page.ajaloadWin.InvokeElementId = "ajaxLoading";
			CMS.page.ajaloadWin.show();
			
			CMS.util.HttpAjax(contextPath+'/login.html', CMS.page.formData
					.toJson(), function(data) {
				if (data.result == 'success') {
					window.parent.document.location
							.replace(contextPath+"/main.html");
				} else {
					CMS.page.ajaloadWin.close();
					A('#validate').val(CMS.args.EMPTY);
					A('#validateImage').attr(
							'src',contextPath+'/validateCode.html?a='
									+ (+new Date));
					CMS.page.Dailog = Dialog.alert(data.message,
							CMS.page.hasDialogFun);
					CMS.page.hasDialog = true;
				}
			});
		};

		CMS.page.hasDialogFun = function() {
			CMS.page.hasDialog = false;
		};
	</script>
</body>
<!--/right-->
</html>