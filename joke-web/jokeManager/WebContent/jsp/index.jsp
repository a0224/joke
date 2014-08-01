<!DOCTYPE html>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ page import="com.joke.pojo.UserPojo"%>
<%@ page import="com.joke.pojo.MenuPojo"%>
<%
	String contextPath = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ contextPath + "/";
%>
<html style="overflow-x:auto;">
<head>
<meta charset="utf-8">
<meta http-equiv="x-ua-compatible" content="ie=edge" />
<title>管理平台</title>
<script>
	contextPath="<%=request.getContextPath()%>";
	jsessionid="<%=session.getId()%>";
</script>
<link type="text/css" rel="stylesheet" href="skin/main.css" />
<style type="text/css">
.headerTop{
	height: 50px;
	padding-top: 0px;
	font:bold 20px/22px "黑体";
	color: #fff;
	background:#bbdbef url('pictures/headerTopBg.png') repeat-x;
	
	}
#header_relative{
	position: relative;
	background: url(pictures/headerBottomBg.png) repeat-x;
	height: 70px;
	text-align: center
}
.welcomeText{
	padding-top: 0px;
	padding-left: 15px;
	float:left;
	font:25px/70px "黑体";
	color: #545454;
}
</style>
</head>
<body style="background:url('pictures/bodyBg.png')  repeat bottom ;height:100%;min-width: 1280px;_width:1270px;position: relative;">
	<!--top-->

	<!--系统初始化进度开始-->
	<div id="apploading" class="apploading show"></div>
	<!--系统初始化进度结束-->

	<div class="header shadow6CCC appMain hidden" id="header">
		<div class="headerTop">
		<img alt="" src="">
		</div>
		<div id="header_relative">
			<!-- <div class="clearfix">
				<span class="headerlogo" title="激动"> 激动云管理平台 </span>

			</div> -->
			<div class="welcomeText">
				<img alt="" src="pictures/xiaoren.png">
				<span id="timeCode"></span><span style="color: #f05500">${session.user_inf["loginName"]}</span>&nbsp;
				欢迎登陆管理后台
			</div>
			<div class="msglogin">
				<p>
					<a href="javascript:CMS.page.reSetPassword()">修改密码</a>&nbsp;| <a
						href="javascript:CMS.page.loginOut()">注销账户</a>
				</p>
				<div class="heardtime">
					<script type="text/javascript">
						var today = new Date(), year = today.getFullYear(), month = (parseInt(today
								.getMonth() + 1) < 10) ? ("0" + parseInt(today
								.getMonth() + 1))
								: parseInt(today.getMonth() + 1), day = (parseInt(today
								.getDate()) < 10) ? ("0" + today.getDate())
								: today.getDate();
						document.write(
								"今天是:&nbsp;"
										+ year
										+ "年"
										+ month
										+ "月"
										+ day
										+ "日  星期"
										+ ('日一二三四五六'
												.charAt(new Date().getDay())))
					</script>

				</div>
			</div>
			<div class="serviceset"></div>
			<ul class="menu" id="meunwarpper"></ul>
		</div>
	</div>
	
	<!--top-->
	<%
		UserPojo user = (UserPojo) session.getAttribute("user_inf");
	%>
	<!--left-->
	<div class="leftbox appMain hidden" id="leftbox">
		<div id="PARENT">
			<ul id="leftbox-submenu" class="submenu">
				
				<s:iterator  value="menulist" id="menu">
				 <li><a href="javascript:void(0);"
					onClick="DoMenu('ChildMenu<s:property value="#menu.id"/>')"><s:property value="#menu.title"/></a>
					<ul id="ChildMenu<s:property value="#menu.id"/>" class="collapsed">
					<s:iterator  value="#menu.child" id="child">
						<li><a href="<s:property value="#child.url"/>" target="mainIframe"
							onClick="DoMenuChild(this)"><s:property value="#child.title"/></a></li>
					 </s:iterator>
					</ul></li>
			    </s:iterator>

			</ul>
		</div>
		<!-- <div class="shrink" style="display: none" id="shrink"
			href="javascript:void(0);" onClick="openMenu()">
			<div class="shrink-icon shadow4CCC"></div>
			<span>收起菜单</span>
		</div> -->

	</div>
	<!--/left-->

	<!--right-->
	<div class="rightbox appMain hidden" id="rightbox">
		<div class="maincontent" id="maincontent">
			<iframe id="mainIframe" name="mainIframe" align="center" width="100%"
				height="auto" src="" frameborder="0"
				scrolling="auto"></iframe>
		</div>
	</div>

	<div id="ajaxLoading" class="dialogwarpper padding10 c"
		style="display: none;">
		<span>正在跳转页面，请稍后......</span> <br /> <br /> <img
			src="images/ajax-loader.gif" /> <br /> <br /> <span>跳转页面过程需要一段时间，请您耐心等待！</span>
	</div>

	<div id="tipDialog"
		style="display: none; position: absolute; right: 0; bottom: 0; z-index: 9001;">
		<table border="0" width="276px" cellspacing="0" cellpadding="0"
			style="-moz-user-select: none; font-size: 12px; line-height: 1.4; width: 276px;"
			onselectstart="return false;" id="">
			<tbody>
				<tr style="" id="">
					<td width="13" height="33" style="" class="dialog_lt">
						<div style="width: 13px;"></div>
					</td>
					<td height="33" style="" class="dialog_ct">
						<div
							style="padding: 9px 0 0 4px; float: left; font-weight: bold; color: #fff;">
							<img align="absmiddle" src="js/dialog/images/icon_dialog.gif" />
							<span id="">最新热门软件</span>
						</div>
						<div onclick="CMS.page.closeDialog();"
							style="margin: 6px 0 0; position: relative; cursor: pointer; float: right; height: 17px; width: 28px; background-image: url(js/dialog/images/dialog_closebtn.gif);"></div>
					</td>
					<td width="13" height="33" style="" class="dialog_rt">
						<div style="width: 13px;">
							<a href="javascript:void(0);" id=""></a>
						</div>
					</td>
				</tr>
				<tr valign="top">
					<td width="13" style="" class="dialog_mlm"></td>
					<td align="center">
						<table border="0" bgcolor="#ffffff" width="100%" cellspacing="0"
							cellpadding="0">
							<tbody>
								<tr style="display: none" id="_MessageRow_0">
									<td valign="top" height="50">
										<table border="0" width="100%" cellspacing="0" cellpadding="0"
											id=""
											style="background: #eaece9 url(js/dialog/images/dialog_bg.jpg) no-repeat scroll right top;">
											<tbody>
												<tr>
													<td align="center" width="50" height="50"><img
														width="32" height="32" id=""
														src="js/dialog/images/window.gif" /></td>
													<td align="left" style="line-height: 16px;">
														<div style="font-weight: bold" id=""></div>
														<div id=""></div>
													</td>
												</tr>
											</tbody>
										</table>
									</td>
								</tr>
								<tr>
									<td align="center" valign="top">
										<div style="position: relative; width: 250px; height: 100px;">
											<div
												style="position: absolute; height: 100%; width: 100%; display: none; background-color: #FFF; -moz-opacity: 0.5; opacity: 0.5; filter: alpha(opacity =                  50);">
												&nbsp;</div>
											<div style="text-align: left;">
												<p
													style="padding: 7px 5px 8px 5px; text-indent: 2em; line-height: 20px;">
													最新上市"<a href="javascript:CMS.page.getNews();" id="dfb">10</a>"款软件处于"待发布"状态，您可以通过"投放"或"推荐"按钮将这些软件发布到您的客户端！
												</p>
												<div
													style="height: 25px; background-color: #EFEFEF; line-height: 25px; text-align: right; padding: 0 5px;">
													<a style="float: right;" href="javascript:CMS.page.zshl();">暂时忽略</a>
													<a style="float: left;"
														href="javascript:CMS.page.getNews();">查看软件</a>
												</div>
											</div>
										</div>
									</td>
								</tr>

							</tbody>
						</table>
					</td>
					<td width="13" style="" class="dialog_mrm"></td>
				</tr>
				<tr>
					<td width="13" height="13" style="" class="dialog_lb"></td>
					<td style="" class="dialog_cb"></td>
					<td width="13" height="13" style="" class="dialog_rb"><a
						href="#;"></a></td>
				</tr>
			</tbody>
		</table>
	</div>
	
	<div id="uploadingDiv" class="c padding10" style="display: none;">
		<span>正在上传中,请勿执行任何操作......</span>
		<br/><br/>
		<img src="images/ajax-loader.gif"/>
		<br/><br/>
		<span>根据网络情况和文件大小不同,上传过程需要几分钟<br/>请您耐心等待！</span>
	</div>
	<script type="text/javascript" src="js/json2.js"></script>
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/jquery-migrate.min.js"></script>
	<script type="text/javascript" src="js/common.js"></script>
	<script type="text/javascript" src="js/dialog/Drag.js"></script>
	<script type="text/javascript" src="js/dialog/Dialog.js"></script>
	<script type="text/javascript" src="js/leftmenu.js"></script>
	<!--[if lte IE 6]> 
			<script type="text/javascript" src="js/fixPng.js"></script>
	<![endif]-->
	<script type="text/javascript" src="js/index.js"></script>
	<script>
	 	//var role = ${sessionScope.user_inf.userRole};
	 	//if(role == 1 || role == 2){
	 		document.getElementById("mainIframe").src="user/personInfo.html";
	 	//}else{
	 	//	document.getElementById("mainIframe").src="statAnaly/resAdvertAnalysis.html";
	 	//}
	</script>

	  <%!  
	     synchronized void countPeople()//串行化计数函数
	        { ServletContext application=getServletContext();
	           Integer number=(Integer)application.getAttribute("Count");
	           if(number==null) //如果是第1个访问本站
	              { number=new Integer(1);
	                application.setAttribute("Count",number);
	              }
	           else
	              { number=new Integer(number.intValue()+1);
	                application.setAttribute("Count",number);
	              }
	        }
	    %>
	    <% if(session.isNew())//如果是一个新的会话
	          countPeople();
	       Integer yourNumber=(Integer)application.getAttribute("Count");
	    %>
	    <P><P>欢迎访问本站，您是第
   		 <%=yourNumber%>
		个访问用户。
</body>
<!--/right-->
</html>