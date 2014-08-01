<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String context = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ context + "/";
	String contextPath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ context;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="x-ua-compatible" content="ie=edge" />
<meta name="language" content="en" />
<base href="<%=basePath%>" />
<title>计费项管理</title>

<script>
	contextPath="<%=request.getContextPath()%>";
	jsessionid="<%=session.getId()%>";
</script>

<link rel="stylesheet" type="text/css" href="skin/main.css" />
</head>
<body>
	<div class="barwrapper" id="filterItems"
		style="border-bottom: 1px solid #c3c3c3; padding: 6px 10px 5px; text-indent: 5e;">
		<p>当前位置: 应用管理 >> 计费项管理</p>
	</div>
	<div class="bodywarpper">
		<div class="butbarlayout2 clearfix">
			<a href="javascript:void(0);" class="zbutton"
				onclick="CMS.page.addCharge()"> <span>新增计费项</span></a> </a><a
				href="javascript:void(0);" class="zbutton"
				onClick="CMS.page.remove()"> <span>删除</span>
			</a><a href="javascript:void(0);" class="zbutton"
				onClick="CMS.page.back()"> <span>返回</span>
				<div class="right"></div>
			</a>
		</div>
		<div class="gridwarpper">
			<table id="table" width="100%" border="0" cellpadding="0"
				cellspacing="0">
				<thead>
					<tr>
						<th class="gridcheckbox"><input type="checkbox" /></th>
						<th name="id" align="center" class="gridcolumn hidden">编号</th>
						<th width="100px" name="appName" class="gridcolumn">App名称</th>
						<th width="200px" name="package" class="gridcolumn">App包名</th>
						<th width="150px" name="app_code" class="gridcolumn">App编号</th>
						<th width="150px" name="pay_code" class="gridcolumn">计费项编号</th>
						<th width="120px" name="chargeName" class="gridcolumn">计费项名</th>
						<th width="100px" name="type" class="gridcolumn">支付规则</th>
						<th width="80px" name="price" class="gridcolumn">价格(元)</th>
						<th width="80px" align="center">
							<div align="center" class="c">操作</div>
						</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
	</div>

	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/json2.js"></script>
	<script type="text/javascript" src="js/jquery-migrate.min.js"></script>
	<script type="text/javascript" src="js/common.js"></script>
	<script type="text/javascript" src="js/dialog/Drag.js"></script>
	<script type="text/javascript" src="js/dialog/Dialog.js"></script>
	<script type="text/javascript" src="js/jquery.placeholder.js"></script>
	<script type="text/javascript">
		CMS.setting.twoDataGridPage = false;
		CMS.page.appCode = CMS.util.getQueryStr('appCode');
		CMS.page.gridPostUrl = contextPath + "/app/listChargeInfo.html";
		CMS.page.gridData = "";

		A(document).ready(function() {
			CMS.page.postData = {
				appCode : CMS.page.appCode
			};
			CMS.page.grid = new CMS.util.DataGrid({
				elem : "#table",
				url : CMS.page.gridPostUrl,
				currentpage : 0,
				postData : CMS.page.postData,
				colModelItem : [ {
					name : 'appName',
					xtype : 'user-defined',
					renderer : CMS.page.layouyAppName
				}, {
					name : 'package',
					xtype : 'user-defined',
					renderer : CMS.page.layouyPackageName
				}, {
					name : 'app_code',
					xtype : 'user-defined',
					renderer : CMS.page.layoutAppCode
				}, {
					name : 'pay_code',
					xtype : 'user-defined',
					renderer : CMS.page.layoutPayCode
				}, {
					name : 'chargeName',
					xtype : 'user-defined',
					renderer : CMS.page.layoutName
				}, {
					name : 'type',
					xtype : 'user-defined',
					renderer : CMS.page.layoutPayRule
				}, {
					name : 'price',
					xtype : 'user-defined',
					renderer : CMS.page.layoutPrice
				} ],
				operateFunItem : [ {
					type : "link",
					text : "修改",
					fun : CMS.page.editCharge
				} ],
				afterEvent : function(data, maps, rows) {
				}
			});
		});

		CMS.page.stringXZ = function(length, col) {
			col = (col || CMS.args.EMPTY).toString();
			if (col.length > length) {
				return '<span title="' + (col).html() + '">'
						+ (col.substring(0, length) + '…') + '</span>';
			}
			return col;
		};
		CMS.page.layouyAppName = function(col, row, trs, index) {
			return CMS.util.stringXZ(100, row.appName);
		};
		CMS.page.layouyPackageName = function(col, row, trs, index) {
			return CMS.util.stringXZ(60, row.package);
		};
		CMS.page.layoutAppCode = function(col, row, trs, index) {
			return CMS.util.stringXZ(100, row.app_code);
		};
		CMS.page.layoutPayCode = function(col, row, trs, index) {
			return CMS.util.stringXZ(100, row.pay_code);
		};
		CMS.page.layoutName = function(col, row, trs, index) {
			return CMS.util.stringXZ(100, row.chargeName);
		};
		CMS.page.layoutPayRule = function(col, row, trs, index) {
			var type = row.type;
			switch(type){
				case 1:
				type= "单次";	
				break;
				case 2:
				type= "包月";	
				break;
			}
			return  CMS.util.stringXZ(100,type);
		};
		CMS.page.layoutPrice = function(col, row, trs, index) {
			return CMS.util.stringXZ(100, row.price);
		};
		CMS.page.addCharge = function() {
			var title = "新增计费项";
			CMS.page.addChargeWin = new Dialog();
			CMS.page.addChargeWin.Width = 800;
			CMS.page.addChargeWin.Height = 400;
			CMS.page.addChargeWin.Title = title;
			CMS.page.addChargeWin.URL = contextPath
					+ "/app/addChargePage.html?appCode=" + CMS.page.appCode;
			CMS.page.addChargeWin.Message = "添加新计费项";
			CMS.page.addChargeWin.okButton = "disabled";
			CMS.page.addChargeWin.OKEvent = function(button) {
				var inner = CMS.page.addChargeWin.innerFrame.contentWindow;
				var postData = inner.CMS.page.getFormData();
				if (postData) {
					CMS.util.HttpAjax(contextPath + '/app/addCharge.html',
							postData.json, function(data) {
								if (data.result == "success") {
									Dialog.alert('添加成功!');
									CMS.page.addChargeWin.close();
									CMS.page.grid.reload();
								} else {
									Dialog.alert('添加失败!');
								}
							});
				}
				;
			};
			CMS.page.addChargeWin.show();
		};
		CMS.page.editCharge = function(row) {
			row = row.getValue('rowData');
			var title = "修改计费项";
			CMS.page.editAppWin = new Dialog();
			CMS.page.editAppWin.Width = 800;
			CMS.page.editAppWin.Height = 400;
			CMS.page.editAppWin.Title = title;
			CMS.page.editAppWin.URL = contextPath + "/app/addChargePage.html";
			CMS.page.editAppWin.Message = "修改计费项信息";
			CMS.page.editAppWin.okButton = "disabled";
			CMS.page.editAppWin.OKEvent = function(button) {
				var inner = CMS.page.editAppWin.innerFrame.contentWindow;
				var postData = inner.CMS.page.getFormData();
				if (postData) {
					CMS.util.HttpAjax(contextPath
							+ '/app/updateChargeInfo.html', postData.json,
							function(data) {
								if (data.result == "success") {
									CMS.page.editAppWin.close();
									CMS.page.grid.reload();
									Dialog.alert('操作成功!');
								} else {
									Dialog.alert('操作失败!');
								}
							});
				}
			};
			CMS.page.editAppWin.OnLoad = function() {
				var inner = CMS.page.editAppWin.innerFrame.contentWindow;
				if (CMS.page.grid.getSelectRow) {
					inner.CMS.page.setFormData(row);
				}
			}
			CMS.page.editAppWin.show();
		};

		CMS.page.back = function() {
			window.location.replace(contextPath + '/app/appManage.html');
		};

		CMS.page.remove = function() {
			if (CMS.page.grid.hasSelect()) {
				Dialog.confirm('提示：您确认要删除选中的应用吗？', function() {
					CMS.util.HttpAjax(contextPath + '/app/delCharge.html', JSON.stringify({
								ids : CMS.page.grid.getSelectRowIds("id"),
								status : '-1'
							}), function(data) {
						if (data.result == 'success') {
							Dialog.alert("成功删除应用!", function() {
								CMS.page.grid.reload();
							});
						} else {
							Dialog.alert(data.message);
						}
					})
				})
			} else {
				Dialog.alert("请至少选中一个应用再删除");
			}
		};
	</script>
</body>
</html>