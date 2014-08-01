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

<style type="text/css">
label.flagLabel {
	color: #CCCCCC;
	margin-left: 5px;
}

label.selected {
	color: red
}

.icon {
	display: block;
	width: 0px;
	height: 0px;
	margin: 0 auto
}

.icon img {
	width: 0px;
	height: 0px;
	margin: 1px
}

a.imgwapper {
	height: 100px;
	width:100px;
}

a.imgwapper img {
	height: 100px !important;
	width:100px;
	width: auto !important;
}

a.imgwapper div {
	position: absolute;
	top: 0px;
	left: 0px;
	height: 0px;
	width: 0px;
}
</style>
</head>
<body class="body">
	<div class="barwrapper" id="filterItems"
		style="border-bottom: 1px solid #c3c3c3; padding: 6px 10px 5px; text-indent: 5e;">
		<p>当前位置: 桌面管理 >> 管理</p>
	</div>
	<div class="bodywarpper">
		<div class="barwrapper clearfix">
			<a href="javascript:void(0);" class="zbutton"
				onClick="CMS.page.addWallpaper()"> <span>增 加</span>
			<a href="javascript:void(0);" class="zbutton"
				onClick="CMS.page.editWallpaper()"> <span>编辑</span>
				<div class="right"></div>
			</a> <a href="javascript:void(0);" class="zbutton"
				onClick="CMS.page.removeWallpaper()"> <span>删 除</span>
				<div class="right"></div>
			</a> 
			<!--  <a href="javascript:void(0);" class="zbutton"
				onClick="CMS.page.putRes()"> <span>上 架</span>

				<div class="right"></div>
			</a> <a href="javascript:void(0);" class="zbutton"
				onClick="CMS.page.downRes()"> <span>下 架</span>-->

				<div class="right"></div>
			</a>
		</div>

		<div class="gridwarpper" style="margin-bottom: 10px;">
			<table id="table" width="100%" border="0" cellpadding="0"
				cellspacing="0">
				<thead>
					<tr>
						<th class="gridcheckbox"><input type="checkbox" /></th>
						<th name="id" align="center" class="gridcolumn hidden">编号</th>
						<th name="name" width="100" class="gridcolumn"><label
							style="margin-left: 5px;">名称</label></th>
						<th name="className" align="left" width="200"  class="gridcolumn">类名</th>
						<th name="author" align="left" width="200"  class="gridcolumn">作者</th>
						<th name="motime" align="center" width="45"  class="gridcolumn">更新时间</th>
						<th name="mouser" align="center" width="50"  class="gridcolumn">更新人</th>
						<th width="100px" align="center" name="status" class="gridcolumn"></th>
					</tr>
				</thead>
				<tbody>

				</tbody>
			</table>
		</div>

		<script type="text/javascript" src="js/json2.js"></script>
		<script type="text/javascript" src="js/jquery.min.js"></script>
		<script type="text/javascript" src="js/jquery-migrate.min.js"></script>
		<script type="text/javascript" src="js/common.js"></script>
		<script type="text/javascript" src="js/dialog/Drag.js"></script>
		<script type="text/javascript" src="js/dialog/Dialog.js"></script>
		<script type="text/javascript">

		CMS.setting.twoDataGridPage = false;
		CMS.page.gridPostUrl = contextPath + "/jar/list.html";
		CMS.page.gridData = {};
		CMS.page.resTypeArr;
		
		CMS.page.postData = {
		};
		
		CMS.page.initPage = function () {
			
			CMS.page.grid = new CMS.util.DataGrid({
				elem: "#table",
				postData:CMS.page.postData,
				url: CMS.page.gridPostUrl,
				sorts: ["showOrder"],
				initSort: 'showOrder,asc',
				colModelItem: [
					{
						name: "img",
						xtype: "user-defined",
						renderer: CMS.page.layoutPic
					},
					{
						name: "status",
						xtype: "user-defined",
						renderer: CMS.page.layoutStatus
					}
				]
			});
			
		};
		
		CMS.core.document.ready(CMS.page.initPage);
		
		CMS.page.layoutPic = function (col, row, trs, index) {
			if(col && col!==' '){
				col = "${session.down}" + col || contextPath + 'images/uu_icon.png';
				return '<a href="javascript:;" style="border:none" class="imgwapper" ' +
						'onclick="CMS.page.showImg(this)">' +
						'<img class="radius2" onerror="this.src=contextPath + '+
						'\'/images/screenshot_default.png\'" class="radius4" src="' + col + '"/><div></div></a>';
			}else{
				return '空';
			}
			
		};
		
		CMS.page.title =  function (col, row, trs, index) {
			return row.modifyUser+"";
		};
		
		CMS.page.layoutStatus = function (col, row, trs, index) {
			if(col == 1){
				return '正常';
			}else if(col == 2){
				return	'待审核<br><br><a href="javascript:;" style="border:none" class="" ' +
				'onclick="CMS.page.jokecheck('+row.id+',1)">通过</a>&nbsp'
				+'<a href="javascript:;" style="border:none" class="" ' +
				'onclick="CMS.page.jokecheck('+row.id+',3)">不通过</a><br>' ;
			}else if(col == 3){
				return "未通过";
			}else{
				return "其他";
			}
		};
		
		CMS.page.layoutMemo = function (col, row, trs, index) {
			return CMS.util.stringXZ(65, col.html());
		};
		
		CMS.page.layoutOrder = function (col, row, trs, index) {
			return '<input onblur="CMS.page.OrderChange(this, ' + index + 
			')" type="text" maxlength="5" value="' + col + '" style="width:40px"/>'
			
		};
		
		CMS.page.showImg = function (img) {
			window.parent.CMS.util.showImage(A(img).find('img').attr('src'));
		};
		
		CMS.page.layoutIsFormal = function(col, row, trs, index) {
			return row.cloudType == 1 ? '云端' : (row.cloudType == 2 ? '我的' : '其他');
		};
		CMS.page.layoutCategory = function(col, row, trs, index) {
//			return row.cloudType == 1 ? '云端' : (col == 2 ? '我的' : '其他');
			var catename;
			A.each(CMS.page.resTypeArr, function(i, type){
				if(type.id == parseInt(row.categroy)) {
					catename = type.name;
					return type.name;
				}
			}) 
			return catename;
		};
		
		CMS.page.addWallpaper = function () {
			var title = '新增';
			CMS.page.addWallpaperWin = new Dialog();
			CMS.page.addWallpaperWin.Width = 800;
			CMS.page.addWallpaperWin.Height = 300;
			CMS.page.addWallpaperWin.Title = title;
			CMS.page.addWallpaperWin.URL = contextPath + "/page/jarEdit.html";
			CMS.page.addWallpaperWin.Message = '增加新的：';
			CMS.page.addWallpaperWin.okButton = "disabled";
			CMS.page.addWallpaperWin.OKEvent = function (button) {
				var inner = CMS.page.addWallpaperWin.innerFrame.contentWindow;
				var postData = inner.CMS.page.getFormData();
				if (postData) {
					CMS.util.HttpAjax(contextPath + '/jar/add.html', JSON.stringify(postData.object),
							function (data) {
								if (data.result == "success") {
									CMS.page.addWallpaperWin.close();
									CMS.page.grid.reload();
								} else {
									Dialog.alert(data.message);
								}
							});
				}
			};
			CMS.page.addWallpaperWin.show();
		};
		
		CMS.page.editWallpaper = function () {
			if (CMS.page.grid.getSelectRow().length>1) {
				Dialog.alert("只能选择一个!");
				return;
			}else if(CMS.page.grid.getSelectRow().length<=0){
				Dialog.alert("请选择!");
				return;
			}
			var title = '编辑';
			CMS.page.addWallpaperWin = CMS.page.addWallpaperWin || new Dialog();
			CMS.page.addWallpaperWin.Width = 800;
			CMS.page.addWallpaperWin.Height = 300;
			CMS.page.addWallpaperWin.Title = title;
			CMS.page.addWallpaperWin.URL = contextPath + "/page/jarEdit.html";
			CMS.page.addWallpaperWin.Message = '修改：';
			CMS.page.addWallpaperWin.okButton = "disabled";
			CMS.page.addWallpaperWin.OKEvent = function (button) {
				var inner = CMS.page.addWallpaperWin.innerFrame.contentWindow;
				var postData = inner.CMS.page.getFormData();
				if (postData) {
					CMS.util.HttpAjax(contextPath + '/jar/update.html', JSON.stringify(postData.object),
							function (data) {
								if (data.result == "success") {
									CMS.page.addWallpaperWin.close();
									CMS.page.grid.reload();
									Dialog.alert("修改数据成功!");
								} else {
									Dialog.alert(data.message);
								}
							});
				}
			};
			CMS.page.addWallpaperWin.OnLoad = function() {
				var inner = CMS.page.addWallpaperWin.innerFrame.contentWindow;
				if (CMS.page.grid.getSelectRow()) {
					inner.CMS.page.setFormData(CMS.page.grid.getSelectRow());
				}
			};
			CMS.page.addWallpaperWin.show();
		};
		
		CMS.page.removeWallpaper = function () {
			if (CMS.page.grid.hasSelect()) {
				Dialog.confirm('警告：您确认要删除吗？', function () {
					CMS.util.HttpAjax(contextPath + '/jar/delById.html',
							JSON.stringify({ids: CMS.page.grid.getSelectRowIds("id"),status:'-1'}),
							function (data) {
								if (data.result == 'success') {
									Dialog.alert("提示：删除成功!", function () {
										CMS.page.grid.reload();
									})
								} else {
									Dialog.alert(data.message);
								}
							})
				})
			} else {
				Dialog.alert("请先至少选中一个!");
			}
		};
		
		CMS.page.jokecheck = function (jokeid,jokestatus) {
			CMS.util.HttpAjax(contextPath + '/joke/updateStatus.html',
					JSON.stringify({ids: jokeid,status:jokestatus}),
					function (data) {
						if (data.result == 'success') {
							Dialog.alert("提示：审核成功!", function () {
								CMS.page.grid.reload();
							})
						} else {
							Dialog.alert(data.message);
						}
					});
		};
		
		CMS.page.details = function (element, index) {
			var rowData = CMS.page.grid.getRowData(index).getValue('rowData');
			if (!CMS.page.detailWin)
				CMS.page.detailWin = new Dialog();
			CMS.page.detailWin.Width = 750;
			CMS.page.detailWin.Height = 450;
			CMS.page.detailWin.Title = '查看资源信息';
			CMS.page.detailWin.Message = "<b>[" + rowData.name + "]</b>的详细信息如下：";
			CMS.page.detailWin.URL = contextPath + '/admin.do?r=ad_manage/detail_list';
			CMS.page.detailWin.OnLoad = function () {
				var inner = CMS.page.detailWin.innerFrame.contentWindow,
						innerDoc = inner.document;
				inner.CMS.page.setFormData(rowData, true);
			};
			CMS.page.detailWin.show();
		};
		
		CMS.page.OrderChange = function (element, index) {
			var rowData = CMS.page.grid.getRowData(index).getValue('rowData'),
					old = rowData.showOrder,
					newVal = element.value, id = rowData.id;
			if (old != newVal) {
				if (!/^\d+$/.test(newVal)) {
					element.value = old;
					Dialog.alert('排序必须是数字！');
					return
				}
		
				if (CMS.util.isEmpty(newVal)) {
					element.value = old;
					Dialog.alert('排序不能为空！');
					return
				}
		
				if (newVal == 0) {
					element.value = old;
					Dialog.alert('排序不能为0！');
					return
				}
				CMS.util.HttpAjax(contextPath + '/wallpaper/updateWallpaper.html',
						JSON.stringify({id: id, operate: 3, orderBy: newVal}),
						function (data) {
							if (data.result == 'success') {
								//Dialog.alert("修改排序成功!", function(){
								CMS.page.grid.reload();
								//})
							} else {
								Dialog.alert(data.message);
							}
						}, CMS.util.ajaxProcess());
			}
		};
</script>
</body>
</html>

