<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String contextPath = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ contextPath + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
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
</head>
<body class="body">
	<div class="barwrapper" id="filterItems"
		style="border-bottom: 1px solid #c3c3c3; padding: 6px 10px 5px; text-indent: 5e;">
		<p>当前位置: 用户管理 >> 权限管理</p>
	</div>
	<div class="bodywarpper">

		<div class="butbarlayout2 clearfix">

			<a href="javascript:void(0);" class="zbutton"
				onClick="CMS.page.editUser(true)"> <span>添加</span>
				<div class="right"></div>
			</a> <a href="javascript:void(0);" class="zbutton"
				onClick="CMS.page.editUser(CMS.page.grid)"> <span>修改</span>
				<div class="right"></div>
			</a> <a href="javascript:void(0);" class="zbutton"
				onClick="CMS.page.remove()"> <span>删除</span>
				<div class="right"></div>
			</a>
		</div>

		<div class="gridwarpper ">
			<table id="table" width="100%" border="0" cellpadding="0"
				cellspacing="0">
				<thead>
					<tr>
						<th class="gridcheckbox"><input type="checkbox" /></th>
						<th name="id" align="center" class="gridcolumn hidden">编号</th>
						<th width="100px" name="name" class="gridcolumn" align="left">
							名称</th>
						<th width="100px" name="code" class="gridcolumn" align="left">
							角色编码</th>
						<th width="150px" name="memo" class="gridcolumn"
							align="left">详细</th>
						<th width="180px" name="status" class="gridcolumn"
							align="center">状态</th>
						<th width="150px" align="center">
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
	<script type="text/javascript" src="js/jquery-migrate.min.js"></script>
	<script type="text/javascript" src="js/common.js"></script>
	<script type="text/javascript" src="js/dialog/Drag.js"></script>
	<script type="text/javascript" src="js/dialog/Dialog.js"></script>
	<script type="text/javascript" src="js/jquery.placeholder.js"></script>
	<script type="text/javascript" src="js/json2.js"></script>
	<script type="text/javascript">
		CMS.setting.twoDataGridPage = false;
		CMS.page.gridPostUrl = contextPath + "/menu/roleInfoList.html"; //table17.json 
		CMS.page.status = A('#resCombox');
		CMS.page.gridData = "";

		A(document).ready(function() {

			CMS.page.grid = new CMS.util.DataGrid({
				elem : "#table",
				url : CMS.page.gridPostUrl,
				currentPage : 0,
				postData : CMS.page.gridData,
				sorts : [ 'id' ],
				initSort : 'id',
				colModelItem : [ {
					name : "userName",
					xtype : "user-defined",
					renderer : CMS.page.userName
				}, {
					name : "userPaWord",
					xtype : "user-defined",
					renderer : CMS.page.userPaWord
				}, {
					name : "userRole",
					xtype : "user-defined",
					renderer : CMS.page.userRole
				}, {
					name : "phone",
					xtype : "user-defined",
					renderer : CMS.page.phone
				}, {
					name : "lastLoginTime",
					xtype : "user-defined",
					renderer : CMS.page.lastLoginTime
				} ],
				 operateFunItem:[{
					type:"text",
					text:"功能管理",
					fun:CMS.page.getAppList
				}
				],
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

		CMS.page.userName = function(col, row, trs, index) {
			return CMS.page.stringXZ(50, col);
		};

		CMS.page.lastLoginTime = function(col, row, trs, index) {
			return row.lastLoginTime;
		};

		CMS.page.phone = function(col, row, trs, index) {
			return col;
		};
		CMS.page.userRole = function(col, row, trs, index) {
			if (row.userRole == 1) {
				return "管理员";
			} else if (row.userRole == 2) {
				return "运维";
			} else if (row.userRole == 3) {
				return "客户";
			} else if (row.userRole == 4) {
				return "财务";
			} else {
				return "其他";
			}
		};

		CMS.page.userPaWord = function(col, row, trs, index) {
			return "********";
		};

		CMS.page.remove = function() {
			if (CMS.page.grid.hasSelect()) {
				Dialog.confirm('提示：您确认要删除选中的吗？', function() {
					CMS.page.formData = new CMS.util.Form.getData(
							A('.formwrapper'), 'formItem', []);
					CMS.page.formData.insert('ids', CMS.page.grid
							.getSelectRowIds("id"));
					CMS.page.formData.insert('status', -1);
					CMS.util.HttpAjax(contextPath + '/menu/delRole.html',
							CMS.page.formData.toJson(), function(data) {
								if (data.result == 'success') {
									Dialog.alert("成功删除!", function() {
										CMS.page.grid.reload()
									})
								} else {
									Dialog.alert(data.message);
								}
							})
				})
			} else {
				Dialog.alert("请至少选中一个再删除");
			}
		};
		
		
		CMS.page.getAppList = function(row) {
			var rowData = row.getValue('rowData');
			window.location.href = contextPath
					+ '/menu/roleMenuPage.html?roleCode='
					+ rowData.code + '&name=' + rowData.name
					+ '&type=' + (rowData.type || 0);
		};

		CMS.page.details = function(row) {
			
			var rowData = row.getValue('rowData');
			
			CMS.page.detailWin = new Dialog();
			CMS.page.detailWin.Width = 700;
			CMS.page.detailWin.Height = 600;
			CMS.page.detailWin.Title = '查看信息';
			CMS.page.detailWin.Message = "<b>["+ rowData.loginName +"]</b>信息如下：";
			CMS.page.detailWin.URL = contextPath + "/menu/roleMenuPage.html";  
			CMS.page.detailWin.OnLoad = function(){
				var inner = CMS.page.detailWin.innerFrame.contentWindow, 
				innerDoc = inner.document; 
				inner.CMS.page.setFormData(rowData.code); 
			};
			CMS.page.detailWin.show(); 
		};
		
		
		CMS.page.editUser = function(row) {
			
			CMS.page.createWin = new Dialog();
			CMS.page.createWin.Width = 1050;
			CMS.page.createWin.Height = 400;
			
			
			if (typeof row != 'boolean') {
				if (row.hasSelect()) {
					if (CMS.page.grid.getSelectRow().length>1) {
						Dialog.alert("只能选择一个!");
						return;
					}
				}else{
					Dialog.alert("请选中一个再编辑");
					return
				}
				//属于修改功能
				isEdit = true;
				editUrl = 'ad_edit';
				postUrl = "/menu/updateRole.html";
				CMS.page.createWin.URL = contextPath + "/menu/roleEdit.html";
				title = '编辑<b>[' + row.getSelectRowIds("loginName") + ']</b>信息';
			} else {
				//添加功能
				title = '添加', 
				isEdit = false, 
				editUrl = 'ad_create';
				postUrl = "/menu/addRole.html", 
				CMS.page.createWin.URL = contextPath + "/menu/roleEdit.html";
			}
			
			CMS.page.createWin.Title = title;
			CMS.page.createWin.MessageTitle = title;
			CMS.page.createWin.Width = 800;
			CMS.page.createWin.Height = 380;
			CMS.page.createWin.Message = "请按照字段提示来填写下面软件详细内容!";
			CMS.page.createWin.OKEvent = function(button) {
				button.disabled = 'disabled';
				var inner = CMS.page.createWin.innerFrame.contentWindow;
				CMS.page.formData = inner.CMS.page.getFormData();
				if (CMS.page.formData) {
					CMS.util.HttpAjax(contextPath + postUrl,
							CMS.page.formData.json, function(data) {
								if (data.result == 'success') {
									CMS.page.editDAO(title, isEdit,
											CMS.page.formData.object)
								} else {
									button.disabled = '';
									Dialog.alert(data.message);
								}
							})
				} else {
					button.disabled = '';
				}
			};
			CMS.page.createWin.OnLoad = function() {
				var inner = CMS.page.createWin.innerFrame.contentWindow;
				if (isEdit) {
					inner.CMS.page.setFormData(row.getSelectRowIds("id"));
				}
			};
			CMS.page.createWin.show();
		};
		
		CMS.page.editDAO = function (title, isEdit, object) {
			Dialog.alert(title + "成功!", function () {
				CMS.page.createWin.close();
				if (!isEdit) {
					setTimeout(function () {
					}, 100);

					CMS.page.grid.reload(CMS.page.gridPostUrl, {
					});
					return;
				}
				CMS.page.grid.reload();
			})
		};
	</script>
</body>
</html>
