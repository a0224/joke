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
<title>应用管理</title>

<script>
	contextPath="<%=request.getContextPath()%>";
	jsessionid="<%=session.getId()%>";
</script>

<link rel="stylesheet" type="text/css" href="skin/main.css" />
<style>
</style>
</head>
<body>
	<div class="barwrapper" id="filterItems"
		style="border-bottom: 1px solid #c3c3c3; padding: 6px 10px 5px; text-indent: 5e;">
		<p>当前位置: 应用管理 >> 应用管理</p>
	</div>
	<div class="bodywarpper">
		<div class="butbarlayout2 clearfix">
			<a href="javascript:void(0);" class="zbutton"
				onclick="CMS.page.addApp()"> <span>添加</span></a> <a
				href="javascript:void(0);" class="zbutton"
				onClick="CMS.page.remove()"> <span>删除</span>
			</a>
		</div>
		<div class="gridwarpper">
			<table id="table" width="100%" border="0" cellpadding="0"
				cellspacing="0">
				<thead>
					<tr>
						<th class="gridcheckbox"><input type="checkbox" /></th>
						<th name="id" align="center" class="gridcolumn hidden">编号</th>
						<th width="100px" name="name" class="gridcolumn">应用名称</th>
						<th width="100px" name="descrip" class="gridcolumn">应用描述</th>
						<th width="100px" name="price" class="gridcolumn">价格</th>
						<th width="200px" align="center">
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
		CMS.page.gridPostUrl = contextPath + "/app/list.html";
		CMS.page.gridData = "";

		A(document).ready(function() {
			CMS.page.grid = new CMS.util.DataGrid({
				elem : "#table",
				url : CMS.page.gridPostUrl,
				currentpage : 0,
				postData : CMS.page.gridData,
				colModelItem : [ {
					name : 'app_code',
					xtype : 'user-defined',
					renderer : CMS.page.layouyappCode
				}, {
					name : 'name',
					xtype : 'user-defined',
					renderer : CMS.page.layouyAppName
				}, {
					name : 'package',
					xtype : 'user-defined',
					renderer : CMS.page.layoutAppPkName
				} ],
				operateFunItem : [ {
					type : "link",
					text : "修改",
					fun : CMS.page.editApp
				}],
				afterEvent : function(data, maps, rows) {
				}
			});
		});

		CMS.page.stringXZ = function(length, col) {
			if(col){
				col = (col.html()|| CMS.args.EMPTY).toString();
				if (col.length > length) {
					return '<span title="' + (col).html() + '">'
							+ (col.substring(0, length) + '…') + '</span>';
				}
			return col;
			}
		};
		CMS.page.layouyappCode = function(col, row, trs, index) {
			return CMS.util.stringXZ(60, col);
		};
		CMS.page.layouyAppName = function(col, row, trs, index) {
			return CMS.util.stringXZ(60, col);
		};
		CMS.page.layoutAppPkName = function(col, row, trs, index) {
			return CMS.util.stringXZ(100, col);
		};
		// 		CMS.page.layouyBillCount = function(col, row, trs, index) {
		// 			return CMS.util.stringXZ(5, col.html());
		// 		};
		CMS.page.addApp = function() {
			var title = "新增应用";
			CMS.page.addAppWin = new Dialog();
			CMS.page.addAppWin.Width = 800;
			CMS.page.addAppWin.Height = 400;
			CMS.page.addAppWin.Title = title;
			CMS.page.addAppWin.URL = contextPath + "/page/addApp.html";
			CMS.page.addAppWin.Message = "添加新的应用";
			CMS.page.addAppWin.okButton = "disabled";
			CMS.page.addAppWin.OKEvent = function(button) {
				var inner = CMS.page.addAppWin.innerFrame.contentWindow;
				var postData = inner.CMS.page.getFormData();
				if (postData) {
					CMS.util.HttpAjax(contextPath + '/app/add.html',
							postData.json, function(data) {
								if (data.result == "success") {
									Dialog.alert('添加成功!');
									CMS.page.addAppWin.close();
									CMS.page.grid.reload();
								} else {
									Dialog.alert('添加失败!');
								}
							});
				}
				;
			};
			CMS.page.addAppWin.show();
		};
		CMS.page.editApp = function(row) {
			row = row.getValue('rowData');
			var title = "修改应用";
			CMS.page.editAppWin = new Dialog();
			CMS.page.editAppWin.Width = 800;
			CMS.page.editAppWin.Height = 400;
			CMS.page.editAppWin.Title = title;
			CMS.page.editAppWin.URL = contextPath + "/page/addApp.html";
			CMS.page.editAppWin.Message = "修改应用信息";
			CMS.page.editAppWin.okButton = "disabled";
			CMS.page.editAppWin.OKEvent = function(button) {
				var inner = CMS.page.editAppWin.innerFrame.contentWindow;
				var postData = inner.CMS.page.getFormData();
				if (postData) {
					CMS.util.HttpAjax(contextPath + '/app/update.html',
							postData.json, function(data) {
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
		CMS.page.manageCharge = function(row) {
			row = row.getValue('rowData');
			window.location.replace(contextPath
					+ '/app/manageCharge.html?appCode=' + row.id);
		};

		CMS.page.remove = function() {
			if (CMS.page.grid.hasSelect()) {
				Dialog.confirm('提示：您确认要删除选中的应用吗？', function() {
					CMS.util.HttpAjax(contextPath + '/app/delById.html', JSON.stringify({
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