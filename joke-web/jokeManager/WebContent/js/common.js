var T = window,
	cms = $, A = $;

var config = {
	// 系统初始化左边子菜单个数大小，需要适当大小
	initSubNum: 20,
	// 系统加载主面板的timeout时间限制
	timeoutOfIframeLoad: 5000,
	// 系统右边子菜单收缩展开延迟时间
	timeoutOfContract: 300,
	// 浏览器改变大小延迟改变主面板的事件
	timeoutOfwindowResize: 100,
	// 左边菜单收缩后的宽度（不可改动）
	subCloseWidth: 37,
	// 左边菜单展开后的宽度（不可改动）
	subOpenWidth: 145,
	// 左边菜单是否可收缩
	canContract: true,
	// 加载主页面(iframe)时是否显示Mask
	showLoadMask: true,
	// 是否可以支持菜单弹出Dialog模式（对于左边菜单）
	canWinForNav: true,
	// 是否可以支持菜单弹出全屏Dialog（对于左边菜单）
	fullScreenWin: false,
	// grid表格的分页bar的布局格式（lr：左右， rl：右左，left：全部显示在左边，right：全部显示在右边，center：全部显示在中间）
	dataGridPageLayout: "lr",
	// 是否启用grid表格双分页条模式
	twoDataGridPage: true,
	// grid表格隔行换色的css
	dataGridEvenStyle: "grideven",
	// grid表格row鼠标移动上去css
	dataGridOverStyle: "gridover",
	// grid表格的分页条前面分页信息模版定义
	dataPageMsgModel: "{pageSize}条/页，共{recordCount}条，共{totalPages}页，当前第{currentPage}页",
	// grid表格是否提供修改分页大小的功能
	dataGridChangePage: true,
	// grid表格中是否提供右键菜单操作
	dataRightFileEvents: true,
	// grid表格中是否支持半选提示功能
	dataGridBanSelect: false
};

A.extend({
	namespace: function () {
		var obj, space;
		A.each(arguments,
			function (index, str) {
				space = str.split(".");
				obj = T[space[0]] = T[space[0]] || {};
				A.each(space.slice(1),
					function (i, o) {
						obj = obj[o] = obj[o] || {}
					})
			});
		return obj
	}
});

A.namespace("CMS.util", "CMS.setting", "CMS.args", "CMS.fun", "CMS.page", "CMS.main", "CMS.core");
A.extend(CMS.args, {
	EMPTY: "",
	NULL: null,
	FUN: new Function(),
	Object: {},
	ERROR: "服务端出现错误,请联系管理员!",
	can: false,
	error: this.EMPTY,
	canSubmit: false,
	jumpPage: true,
	canCloseAlert: true,
	hasAlert: false
});

CMS.util.topWindow = function () {
	var parentWin = window;
	while (parentWin != parentWin.parent) {
		if (parentWin.parent.document.getElementsByTagName("FRAMESET").length > 0) break;
		parentWin = parentWin.parent;
	}
	return parentWin;
};

CMS.util.sendOperateLog = function(data){
	if(!data.moduleName){
		data.moduleName = A.trim(A(window.parent.document.body).find('li.onselect').text());
	}
// CMS.util.HttpAjax(contextPath+'/log_detail/addLog.html',
// JSON.stringify(data));
}

CMS.main.mainMask = A(CMS.util.topWindow().document.getElementById("mainmask"));
CMS.main.maincontent = A(CMS.util.topWindow().document.getElementById("maincontent"));

A.cookie = function (name, value, options) {
	if (typeof value != 'undefined') {
		options = options || {};
		if (value === null) {
			value = '';
			options.expires = -1;
		}
		var expires = '';
		if (options.expires && (typeof options.expires == 'number' || options.expires.toUTCString)) {
			var date;
			if (typeof options.expires == 'number') {
				date = new Date();
				date.setTime(date.getTime() + (options.expires * 24 * 60 * 60 * 1000));
			} else {
				date = options.expires;
			}
			expires = '; expires=' + date.toUTCString();
		}
		var path = options.path ? '; path=' + (options.path) : '';
		var domain = options.domain ? '; domain=' + (options.domain) : '';
		var secure = options.secure ? '; secure' : '';
		document.cookie = [name, '=', encodeURIComponent(value), expires, path, domain, secure].join('');
	} else {
		var cookieValue = null;
		if (document.cookie && document.cookie != '') {
			var cookies = document.cookie.split(';');
			for (var i = 0; i < cookies.length; i++) {
				var cookie = A.trim(cookies[i]);
				if (cookie.substring(0, name.length + 1) == (name + '=')) {
					cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
					break;
				}
			}
		}
		return cookieValue;
	}
};

A.each(config, function (name, value) {
	var val = A.cookie(name);
	if (val == "true" || val == "false") val = eval(val);
	if ((val != "" && val != null) || typeof val == "boolean") {
		config[name] = val;
	}
});

A.extend(CMS.setting, config);


String.prototype.replaceall = function (replace, str) {
	if (typeof replace == 'object') {
		var self = this;
		A.each(replace, function (name, rep) {
			self = self.replace(new RegExp(name, "gm"), rep);
		});
		return self
	}
	return this.replace(new RegExp(replace, 'gm'), str)
};

String.prototype.trim = function () {
	return this.replace(/(^\s*)|(\s*$)/g, "");
};

String.prototype.html = function () {
	return this.replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;").replace(/\"/g, "&quot;").replace(/\\n/g, "<br/>")
};

String.prototype.unHTML = function () {
	return this.replace(/&amp;/g, "&").replace(/&lt;/g, "<").replace(/&gt;/g, ">").replace(/&quot;/g, "\"").replace(/<br>/g, "\\n")
};

// 给数组添加insert方法,用来在下标后面新添加一个元素
Array.prototype.insert = function (index, data) {
	if (isNaN(index) || index < 0 || index > this.length) {
		this.push(data);
	} else {
		var temp = this.slice(index);
		this[index] = data;
		for (var i = 0, len = temp.length; i < len; i++) {
			this[index + 1 + i] = temp[i];
		}
	}
	return this;
};

// 给数组添加remove方法,如果dust为ture，则返回被删除的元素
Array.prototype.remove = function (e, dust) {
	if (dust) {
		var dustArr = [];
		for (var i = 0, self; (self = this[i]) != null; i++) {
			e == self && dustArr.push(this.splice(i, 1)[0]);
		}
		return dustArr;
	}
	for (var i = 0, self; (self = this[i]) != null; i++) {
		e == self && this.splice(i, 1);
	}
	return this;
};

Array.prototype.indexOf = function (func) {
	for (var i = 0, self; (self = this[i]) != null; i++) {
		if (self == arguments[0]) return i;
	}
	return -1;
};
if (typeof Array.prototype.map != "function") {
	Array.prototype.map = function (fn, context) {
		var arr = [];
		if (typeof fn === "function") {
			for (var k = 0, length = this.length; k < length; k++) {
				arr.push(fn.call(context, this[k], k, this));
			}
		}
		return arr;
	};
}
if (typeof Array.prototype.filter != "function") {
	Array.prototype.filter = function (fn, context) {
		var arr = [];
		if (typeof fn === "function") {
			for (var k = 0, length = this.length; k < length; k++) {
				fn.call(context, this[k], k, this) && arr.push(this[k]);
			}
		}
		return arr;
	};
}

// window.console
window.console = window.console ? window.console : {
	assert: function () {
	},
	clear: function () {
	},
	constructor: function () {
	},
	count: function () {
	},
	debug: function () {
	},
	dir: function () {
	},
	dirxml: function () {
	},
	error: function () {
	},
	group: function () {
	},
	groupCollapsed: function () {
	},
	groupEnd: function () {
	},
	info: function () {
	},
	log: function () {
	},
	markTimeline: function () {
	},
	profile: function () {
	},
	profileEnd: function () {
	},
	time: function () {
	},
	timeEnd: function () {
	},
	timeStamp: function () {
	},
	trace: function () {
	},
	warn: function () {
	}
};

// 判断IE常用浏览器版本
CMS.core.IE = (function(){
	if(document.documentMode){
		return document.documentMode;
	}
	for( var v = 3,
			 el = document.createElement('b'),
			 all = el.all || [];
		 el.innerHTML = '<!--[if gt IE ' + (++v) + ']><i><![endif]-->',
			 all[0];
		);
	return v > 4 ? v : false;
}() );
CMS.core.isIE = !!CMS.core.IE;
CMS.core.isIE6 = CMS.core.IE === 6;
CMS.core.isIE8 = CMS.core.IE === 8;
CMS.core.isIE7 = CMS.core.IE === 7;
if (CMS.core.isIE6) try {
	document.execCommand('BackgroundImageCache', false, true);
} catch (e) {
}
CMS.core.isSafari = navigator.userAgent.indexOf('Safari')>0 && navigator.userAgent.indexOf('Chrome')<0;

CMS.core.Body = A('body').css('overflow-x', 'hidden');
CMS.core.Window = A(window);
CMS.core.document = A(document);

CMS.core.console = function (message) {
	eval('(CMS.core.isIE ? alert : console.log)(message)');
};

// 创建一个element的简单方法
CMS.core.createElement = function (tagName, cls, child, appendto, zhtml) {
	var element = (tagName.substring(0, 1) == "<" ? A(tagName) : A("<" + tagName + ">")).addClass(cls);
	child && (appendto && element.appendTo(child) || element.append(child));
	zhtml && typeof appendto == 'boolean' && appendto && element.append(zhtml);
	return element
};

CMS.core.updateClass = function (elem, addCla, reCla) {
	return elem.addClass(addCla).removeClass(reCla)
};

// 普通的js对象转换成json类型
// 注意：该对象中每个字段值中不能包括"{"和"}"，否则会忽略该字段
CMS.core.obj2Json = function (obj) {
	var ret = [];
	A.each(obj, function (name, val) {
		if (val != null) {
			val = typeof val == "string" && val.indexOf("{") == 0 ? val : '"' + val + '"';
			ret.push('"' + name + '":' + val)
		}
	});
	return "{" + ret.join(",") + "}"
};

// 合并两个数组，并且去重
CMS.core.concat = function (arr, arr2) {
	arr = arr.concat(arr2);
	var newArray = [], provisionalTable = {};
	for (var i = 0, item; (item = arr[i]) != null; i++) {
		if (!provisionalTable[item]) {
			newArray.push(item);
			provisionalTable[item] = true
		}
	}
	;
	return newArray
};

// 初始化每个App中的load元素
CMS.core.showMask = function () {
	// window.parent.document.getElementById('mainmask').style.display = 'none';
	A('#mainmask').hide();
// CMS.core.maskEle = (CMS.core.maskEle || CMS.core.createElement('div',
// 'opacity2 pageoverLay', CMS.core.Body, true))
// .height(CMS.core.Window.height() - 10).css({top:
// CMS.util.getPageScrollTop()}).show()
	CMS.core.maskEle = (CMS.core.maskEle || CMS.core.createElement('div', 'opacity2 pageoverLay', CMS.core.Body, true))
		.height(CMS.core.Window.height() - 10).css({top: 0, position: 'fixed'}).show();
};

CMS.core.hideMask = function () {
	CMS.core.maskEle && CMS.core.maskEle.hide()
};

CMS.util.isInt = function (str) {
	return /^\-?\d+$/.test('' + str);
};

CMS.util.isNumber = function (str) {
	var t = CMS.args.EMPTY + str;
	for (var i = 0, len = str.length; i < len; i++) {
		var chr = str.charAt(i);
		if (chr != '.' && chr != 'E' && isNaN(parseInt(chr))) {
			return false;
		}
	}
	return true;
};

CMS.util.isEmail = function (str) {
	return str && str.match(/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/) != null
};

CMS.util.mask = function (flag) {
	eval('CMS.main.mainMask.find("img").show().end().' + (flag ? 'show' : 'hide') + '()');
	return true;
};

CMS.util.loc = String(window.document.location.href);

CMS.util.getQueryStr = function (url, str) {
	
	if(arguments.length == 1){
		str = url;
		url = CMS.util.loc;
	}
	var rs = new RegExp("(^|)" + str + "=([^\&]*)(\&|$)", "gi").exec(decodeURI(url)),
		tmp;
	if (tmp = rs) {
		var query = tmp[2], len = query.length;
		return (query.substring(len - 1) == "#") && query.substring(0, len - 1)
			|| (query.substring(len - 5) == "#this") && query.substring(0, len - 5)
			|| query
	}
	return ""
};

CMS.util.getPageScrollTop = function () {
	return window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop || 0
};

CMS.util.isEmpty = function (str) {
	return A.trim(str) == '' || str == null || str == undefined;
};

CMS.core.now = new Date();
var nowDayOfWeek = CMS.core.now.getDay(),
	nowDay = CMS.core.now.getDate(),
	nowMonth = CMS.core.now.getMonth(),
	nowYear = CMS.core.now.getYear();
nowYear += (nowYear < 2000) ? 1900 : 0;

// 上月日期
var lastMonthDate = new Date();
lastMonthDate.setDate(1);
lastMonthDate.setMonth(lastMonthDate.getMonth() - 1);
var lastYear = lastMonthDate.getYear(), lastMonth = lastMonthDate.getMonth();

// 0-全，1-年月日，2-月日 3-时分 4-年月日时分
CMS.util.formatStrDate = function (value, type, r) {
	value = value.replace("T","").trim();
	if (value == "" || value == null || value == undefined)
		return CMS.args.EMPTY;
	var year = value.substr(0, 4), month = value.substr(5, 3),
		day = value.substr(8, 2), hour = value.substr(10, 3),
		minute = value.substr(13, 3), second = value.substr(16, 2);
	switch (type) {
		case 0:
			if (r && r == "EN")
				return year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
			else
				return year + "年" + month + "月" + day + "日 " + hour + ":" + minute + ":" + second;
		case 1:
			if (r && r == "EN")
				return year + "-" + month + "-" + day;
			else
				return year + "年" + month + "月" + day + "日";
		case 2:
			if (r && r == "EN")
				return month + "-" + day + "-";
			else
				return month + "月" + day + "日";
		case 3:
			return hour + ":" + minute;
		case 4:
			if (r && r == "EN")
				return year + "/" + month + "/" + day + " " + hour + ":" + minute;
			else
				return year + "年" + month + "月" + day + "日" + " " + hour + ":" + minute;
		case 5:
			if (r && r == "EN")
				return year + "/" + month + "/" + day;
			else
				return year + "年" + month + "月" + day + "日";
		case 6:
			if (r && r == "EN")
				return year + "" + month + "" + day;
			else
				return year + "" + month + "" + day + " " + hour + "" + minute + "" + second;
	}
};

CMS.util.formatDate = function (date, type) {
	var myyear = date.getFullYear(), mymonth = date.getMonth() + 1,
		myweekday = date.getDate(), myhour = date.getHours(),
		mymin = date.getMinutes(), mysec = date.getSeconds();

	if (mymonth < 10) mymonth = "0" + mymonth;
	if (myweekday < 10) myweekday = "0" + myweekday;
	if (myhour < 10) myhour = "0" + myhour;
	if (mymin < 10) mymin = "0" + mymin;
	if (mysec < 10) mysec = "0" + mysec;

	switch (type) {
		case 0:
			return (myyear + "-" + mymonth + "-" + myweekday);
		case 1:
			return (myyear + "/" + mymonth + "/" + myweekday);
		case 2:
			return (myyear + "年" + mymonth + "月" + myweekday + "日");
		case 3:
			return (mymonth + "月" + myweekday + "日");
		case 4:
			return (mymonth + "/" + myweekday);
		case 5:
			return (myyear + "" + mymonth + "" + myweekday);
		case 6:
			return (myyear + "" + mymonth + "" + myweekday + "" + myhour + "" + mymin + "" + mysec);
		case 7:
			return (myyear + "-" + mymonth + "-" + myweekday + " " + myhour + ":" + mymin + ":" + mysec);
		case 8:
			return (mymonth + "-" + myweekday);
	}
};

// 获得某月的天数
CMS.util.getMonthDays = function (myMonth) {
	return (new Date(nowYear, myMonth + 1, 1) - new Date(nowYear, myMonth, 1)) / (1000 * 60 * 60 * 24);
}

// 获得本月的开始日期
CMS.util.getMonthStartDate = function () {
	return new Date(nowYear, nowMonth, 1);
};

// 获得本月的结束日期
CMS.util.getMonthEndDate = function () {
	return new Date(nowYear, nowMonth, CMS.util.getMonthDays(nowMonth));
};

CMS.util.addDays = function (date, days) {
	return new Date(new Date(date).valueOf() + days * 24 * 60 * 60 * 1000)
};

CMS.util.getMonthStartDate = function () {
	return new Date(nowYear, nowMonth, 1)
};

CMS.util.getMonthEndDate = function () {
	return new Date(nowYear, nowMonth, CMS.util.getMonthDays(nowMonth))
};

// 获得上月开始时间
CMS.util.getLastMonthStartDate = function () {
	if (nowMonth == 0) {
		return new Date(nowYear - 1, lastMonth, 1);
	}
	return new Date(nowYear, lastMonth, 1);
};

// 获得上月结束时间
CMS.util.getLastMonthEndDate = function () {
	if (nowMonth == 0) {
		return new Date(nowYear - 1, lastMonth, CMS.util.getMonthDays(lastMonth));
	}
	return new Date(nowYear, lastMonth, CMS.util.getMonthDays(lastMonth));
};

CMS.util.formatFloat = function (num) {
	if (!num) return "0.00";
	num = num + CMS.args.EMPTY;
	var index = num.indexOf(".");
	if (index > 0) {
		var len = num.substring(index).length;
		num = parseFloat(num).toFixed(2) || 0;
		if (len > 2) {
			return parseFloat(num.substring(0, index + 3)) || 0;
		}
	}
	return parseFloat(num).toFixed(2) || 0;
};

CMS.util.Map = function () {
	this.isMap = true;
	var struct = function (key, value, other) {
		this.key = key;
		this.value = value;
		this.other = other
	};
	this.arr = new Array();
	this.getValue = function (key) {
		for (var i = 0, self; (self = this.arr[i]) != null; i++) {
			if (self.key == key) return self.value
		}
		return null
	};
	this.getOtherVal = function (key) {
		for (var i = 0, self; (self = this.arr[i]) != null; i++) {
			if (self.key == key) return self.other
		}
		return null
	};
	this.put = function (key, value, other) {
		for (var i = 0, self; (self = this.arr[i]) != null; i++) {
			if (self.key == key) {
				if (typeof other == Boolean || other == true) {
					self.value += "," + value
				} else {
					self.value = value;
					self.other = other
				}
				return
			}
		}
		this.arr[this.arr.length] = new struct(key, value, other);
		return this
	};
	this.remove = function (key) {
		var v;
		for (var i = 0, len = this.arr.length; i < len; i++) {
			v = this.arr.pop();
			if (v.key == key) continue;
			this.arr.unshift(v)
		}
	};
	this.size = function () {
		return this.arr.length
	};
	this.removeAll = function () {
		for (var a = 0, self; (self = this.arr[a]) != null; a++) {
			var _key = self.key, v;
			for (var i = 0, len = this.arr.length; i < len; i++) {
				v = this.arr.pop();
				if (v.key == _key) {
					continue
				}
				this.arr.unshift(v)
			}
		}
	};
	this.isEmpty = function () {
		return this.arr.length <= 0
	};
	this.keySet = function () {
		var keyArr = [];
		for (var i = 0, self; (self = this.arr[i]) != null; i++) {
			var _key = self.key;
			keyArr[i] = _key
		}
		return keyArr
	};
	this.valSet = function () {
		var valArr = [];
		for (var i = 0, self; (self = this.arr[i]) != null; i++) {
			var _val = self.value;
			valArr[i] = _val
		}
		return valArr
	};
	this.alertKeyAndVal = function () {
		var store = "";
		for (var i = 0, self; (self = this.arr[i]) != null; i++) {
			var _val = self.key;
			var _key = self.value;
			store += "key:" + _key + ",val:" + _val
		}
		return store
	};
};

// ajax请求全局设置
A.ajaxSetup({type:'post',dataType:'JSON',contentType:'application/json',timeout:30000});

CMS.util.menuList = function (element, list, width, onclick) {
	this.element = CMS.util.toJquery(element);
	this.onclick = onclick;
	this.width = width;
	this.contextMenuItems = list;

	this.menusItems = [];
	this.menuHeight = 0;
	this.rightList = null;
	this.init();
};


CMS.util.menuList.prototype.init = function () {
	var menu = this;
	this.element.bind("contextmenu",function (ev) {
		ev = ev || window.parent.event;
		menu.createJmeumDiv();
		if (menu.contextMenuItems.length > 0) {
			menu.dispaly(ev);
			if (menu.onclick) menu.onclick.call(menu, ev)
		}
		return false;
	}).click(function () {
			if (menu.rightList) menu.hide();
		});
	A(window.parent.document).unbind('click').bind('click', function (e) {
		if (menu.rightList) menu.hide();
	});

	A(document.getElementById('mainIframe').contentWindow.document.body)
		.unbind('click').bind('click', function (e) {
			if (menu.rightList) menu.hide();
		})
};

CMS.util.menuList.prototype.createJmeumDiv = function () {
	var menu = this;
	if (!menu.rightList) {
		menu.rightList = window.parent.CMS.core.createElement('div', 'contextmenu radius2', window.parent.CMS.core.Body, true).css({
			width: menu.width + "px"
		}).hide().css({
				position: 'absolute',
				zIndex: '500'
			}).bind('click', CMS.args.FUN);
		menu.createMenuItems();
	}
};

CMS.util.menuList.prototype.createMenuItems = function () {
	var menu = this;
	menu.JDivforList = window.parent.CMS.core.createElement('div', 'menuLists radius2', menu.rightList, true).css({
		width: (CMS.core.isIE6 ? (menu.width - 8) : (menu.width - 6)) + "px"
	});
	var index = 0, len = menu.contextMenuItems.length, AMenuItem;
	for (var i = 0; i < len; i++) {
		var id = menu.contextMenuItems[i].id,
			title = menu.contextMenuItems[i].title,
			ico = menu.contextMenuItems[i].ico,
			hr = menu.contextMenuItems[i].hr,
			hasCode = menu.contextMenuItems[i].hasCode,
			childs = [], innerList = "";
		childs = hasCode ? menu.contextMenuItems[i].child : [];

		if (hr) {
			menu.menuHeight = menu.menuHeight + 5;
			innerList += "<a class='hr' href='javascript:'></a>";
		} else {
			menu.menuHeight = menu.menuHeight + 24;
			innerList += "<a class='item' href='javascript:'>";
			!hasCode ? innerList += "<p id='" + id + "' class='" + ico + "'>" + title + "</p></a>" : innerList += "<p class='haschild' iscreate='false' id='" + id + "'  class='" + ico + "'>" + title + "</p></a>";
		}

		AMenuItem = menu.JDivforList.append(A(innerList));

		if (hasCode) {
			var innerchildList = "";
			index = i;
			AMenuItem.find("p").mouseover(function () {
				var off = !CMS.core.isSafari ? A(this).offset() : A(this).offsetFix();
				if (A(this).attr("iscreate") == "true") {
					A("#right" + A(this).attr('id'), window.parent.CMS.core.Body).css({
						'left': parseInt(off.left + menu.width - 10) + 'px',
						'top': off.top + 'px'
					}).show();
				} else {
					for (var u = 0, child; (child = menu.contextMenuItems[index].child[u]) != null; u++) {
						innerchildList += "<a class='item' href='javascript:'><p id='" + child.id + "' style='background:url(" + child.ico + ") no-repeat 3px 5px;'>" + child.title + "</p></a>";
					}
					;
					A("<div />").attr('id', 'right' + A(this).attr('id')).css({
						'border': '#718BB7 1px solid',
						'zIndex': '501',
						'position': 'absolute',
						'left': parseInt(off.left + menu.width - 10) + 'px',
						'top': off.top + 'px',
						'width': menu.width + 'px'
					}).append(CMS.core.createElement('div', 'child menuLists').css({
							'width': (menu.width - 5) + "px"
						}).html(innerchildList)).appendTo(window.parent.CMS.core.Body);
					A(this).attr("iscreate", "true");
				}
			}).mouseout(function () {
				})
		}
	}

	if (CMS.core.isIE) {
		var height = menu.menuHeight + (CMS.core.isIE6 ? ((len * 3) + 1) : 2);
		A("<div class='rightShadow' style='height:" + height + "px'></div>" +
			"<div class='bottomShadow' style='width:" + (menu.width - 6) + "px'></div>").appendTo(menu.rightList);
	}

	menu.hasStroke ? menu.Jstroke = window.parent.CMS.core.createElement('div', 'stroke', menu.rightList, true).css({
		height: (len * 25) + 4
	}) : "";
	menu.clickFunInit();
};

CMS.util.menuList.prototype.clickFunInit = function () {
	var menu = this;
	A("div.menuLists a", menu.rightList).each(function (index) {
		A(this).click(function () {
			menu.hide();
			menu.contextMenuItems[index].onClickFun();
		})
	})
};

CMS.util.menuList.prototype.hide = function () {
	this.rightList.hide()
};

CMS.util.menuList.prototype.dispaly = function (e) {
	var left = e.clientX + (window.parent.CMS.main.openFlag ? 145 : 40),
		top = e.clientY + 100;
	left = (left + this.width) > A(window.parent.document).width() ? left - this.width : left;
	top = (top + this.menuHeight + 15) > A(window.parent.document).height() ?
		(top - this.menuHeight - (CMS.core.isIE6 ? 15 : 3)) : top;
	this.rightList.css({left: left, top: top}).fadeIn(100)
};

CMS.util.toJquery = function (element) {
	return typeof element == 'string' || typeof element == 'object' ? A(element) : element
};

CMS.util.ajaxProcess = function () {
	return {
		before: function () {
			CMS.core.showMask()
		},
		complete: function () {
			CMS.core.hideMask()
		}
	}
};

/**
 * ajax请求
 * 
 * @param url
 *            string,请求地址
 * @param data
 *            [object]，请求参数，可选
 * @param callback
 *            Function，请求成功回调函数
 * @param funs
 *            [{before:Function, complete:Function}]，请求前后回调函数，可选
 * @param type
 *            [boolean]，是否同步请求，可选，默认否
 * @constructor
 */
CMS.util.HttpAjax = function (url, data, callback, funs, type) {
	if (typeof data == 'function') {
		type = funs;
		funs = callback;
		callback = data;
		data = CMS.args.EMPTY
	}
	if (typeof data != 'string') {
		data = A.param(data)
	}
	type = !type;
	funs = funs || {};
	A.ajax({
		url: url,
		async: type,
		cache: false,
		data: '{"data":' + data + ',"jsessionid":"' + (jsessionid || 0)+'"}',
		beforeSend: function(){funs.before && funs.before.call(this) || CMS.args.FUN}, 
		complete: function(XMLHttpRequest,textStatus){  
			funs.complete && funs.complete.call(this) || CMS.args.FUN
		},
		success: function (JSON) {
			if (JSON) {
				if (JSON.timeout) {
					Dialog.alert('您登陆后长时间没有使用，请重新登录吧！', function () {
						window.parent.location.replace(contextPath + "/");
					});
					return
				}
				if (JSON.roleover) {
					Dialog.alert('很抱歉,你没有访问这个功能的权限!', function () {
						window.parent.location.replace(contextPath + "/");
					});
					return
				}
				if (JSON.error) {
					window.parent.CMS.core.hideMask();
					Dialog.alert('系统错误,请稍候重试,或联系管理员解决!', function () {
						window.parent.location.replace(contextPath + "/");
					});
					return
				}
				if (JSON.result=='500') {
					window.parent.CMS.core.hideMask();
					Dialog.alert('系统错误,请稍候重试,或联系管理员解决!', function () {
						window.parent.location.replace(contextPath + "/");
					});
					return
				}
				
				callback && callback.call(this, JSON)
			}
		},
		error: function (XMLHttpRequest, textStatus, errorThrown) {
			CMS.core.hideMask();
//			Dialog.alert('系统错误,请联系管理员!', function () {
//				window.parent.location.replace(contextPath + "/");
//			});
//			return
			
//			 Dialog.alert('请求发生错误');
		},
		timeout: function () {
			CMS.core.hideMask();
			Dialog.alert('您的网络有问题，请求已超时，请重试！');
		}
	});
};


CMS.util.DataGrid = function (obj) {
	var grid = this;
	this.elem = obj.elem;
	this.tableObj = CMS.util.toJquery(obj.elem);
	this.currentPage = obj.currentPage ? obj.currentPage : 1;
	this.pageSize = obj.pageSize || 10;
	this.noPager = obj.noPager;
	this.url = obj.url;
	this.page = null;
	this.pagebar = null;
	this.postData = obj.postData;
	this.colModelItem = obj.colModelItem;
	this.operateFunArray = obj.operateFunItem || [];
	this.noIntervalColor = obj.noIntervalColor;

	this.noDate = (obj.noDate == null || typeof obj.noDate == undefined || obj.noDate == "") ? "没有检测到任何数据" : obj.noDate;
	this.complete = obj.complete;
	this.rowClick = obj.rowClick;
	this.afterEvent = obj.afterEvent;
	this.beforeEvent = obj.beforeEvent;
	this.checkboxChangeEvent = obj.checkboxChangeEvent;
	this.afterLoadRow = obj.afterLoadRow;
	this.error = obj.error;
	this.checkAllBox = obj.checkAllBox;
	this.retData = null;
	this.dataName = obj.dataName;
	this.totalPropertyName = obj.totalPropertyName || 'totalProperty';
	this.contextMenu = obj.contextMenu;
	// 是否单选
	this.singleSelection = obj.singleSelection;
	// 是否禁用点击选择行
	this.disableRowClick = obj.disableRowClick;

	this.actioncol = this.operateFunArray && grid.tableObj.find("tr:first th:last");
	this.operateObj = {
		colNameItem: new Array(),
		dataRowItem: new Array(),
		data: new Array(),
		colModel: new CMS.util.Map(),
		colFunItem: new CMS.util.Map(),
		colmsg: new CMS.util.Map(),
		colalign: new CMS.util.Map()
	};
	this.recordCount = 0;
	this.fristInit = true;
	this.paramObj = {temp: 0};

	this.insterNum = null;
	this.newData = null;
	this.oldData = null;

	this.sorts = obj.sorts || [];
	this.initSort = obj.initSort || '';
	this.sortMsg = {};
	this.sortName = null;
	this.sortModel = "asc";
	this.nosortModel = "desc";

	// this.gmask = A('<div class="dataGridLoad"><img
	// src="images/gridload.gif"></div>').appendTo("body");
	this.showGmask();
	this.initColModel();
	if (!this.noPager) {
		this.initRows();
		this.initPageBar();
		if (this.initSort) {
			var fristSort = this.initSort.split(',');
			this.sortCol(fristSort[0], fristSort[1] || 'asc', true);
		}
		this.load();
		this.setCurrentpage();
		this.bindEvent();
	} else {
		if (this.initSort) {
			var fristSort = this.initSort.split(',');
			this.sortCol(fristSort[0], fristSort[1] || 'asc', true);
		}
		this.noPageLoad()
	}
	if(this.singleSelection){
		this.checkboxChangeEvent = function(index, checked, dataMap){
			grid.operateObj.checkboxItem.prop('checked',false);
			grid.operateObj.checkboxItem.eq(index).prop('checked',checked);
		}
	}
	if (CMS.setting.dataRightFileEvents) this.initContextMenu()
};
CMS.util.DataGrid.prototype = {
	initColModel: function () {
		var grid = this, width, align;
		if(grid.singleSelection){
			grid.tableObj.find("thead tr .gridcheckbox").find("input").hide();
		}
		grid.operateObj.checkbox = grid.tableObj.find("thead tr .gridcheckbox").find("input");
		grid.hasCheckbox = grid.operateObj.checkbox.length != 0;
		grid.colModel = grid.tableObj.find("thead tr .gridcolumn").each(function (i, col) {
			var column = A(this), name = grid.operateObj.colNameItem[i] = column.attr("name");
			width = column.attr("width");
			align = column.attr("align") || "left",
				sort = grid.sortUtil(name, column),
				isDisplay = !column.hasClass('hidden') && column.attr('display') != 'none';

			column.css("text-align", align);

			if (width && width.substring(width.length - 1) != "%") {
				column.html(CMS.core.createElement('div', 'columnnowrap', A(this).html() + sort).css({width: width, "text-align": align}));
				grid.operateObj.colmsg.put(i, true, width)
			} else {
				sort && column.append(sort)
			}
			grid.operateObj.colalign.put(i, align, isDisplay);
		}).hover(function () {
				var elem = A(this), b = elem.find("b");
				elem.hasClass("cansort") && !elem.hasClass("onsort") && !CMS.util.isEmpty(A.trim(b.attr('class'))) && b.addClass("asc");
			},function () {
				var elem = A(this);
				elem.hasClass("cansort") && !elem.hasClass("onsort") && elem.find("b").removeClass();
			}).bind("click", function () {
				grid.sortCol(A(this).attr('name'), 'asc')
			});
		// console.log(grid.colModel)
		if (grid.colModelItem) {
			for (var i = 0, obj; (obj = grid.colModelItem[i]) != null; i++) {
				grid.operateObj.colModel.put(obj.name, obj.xtype);
				grid.operateObj.colFunItem.put(obj.name, obj.renderer)
			}
		}
		grid.operateFunArray.length > 0 && grid.actioncol.css("text-align", "center").attr("align", "center");
	},

	clearSort: function () {
		this.sortName = this.sortModel = CMS.args.EMPTY;
		this.colModel.filter('.cansort').removeClass('onsort').find('b').removeClass();
		this.colModel.find('.columnnowrap label').removeClass('onsort').next().removeClass();
	},

	sortCol: function (name, model, notLoad) {
		var element, grid = this, model = model || 'asc';
		if (element = this.sortMsg[name]) {
			if (!element.hasClass('onsort')) {
				grid.clearSort();
				element.addClass('onsort').find('b').removeClass().addClass(model);
				grid.sortName = name;
				grid.sortModel = model;
			} else {
				var b = element.find('b'), newModel = b.hasClass('asc') ? "desc" : "asc";
				b.removeClass().addClass(newModel);
				grid.sortName = name;
				grid.sortModel = newModel;
			}
			// console.log(grid.sortName +"....."+ grid.sortModel);
			!notLoad && grid.reload();
		}
	},

	sortUtil: function (name, elem) {
		var grid = this;
		if (this.indexOf(name, this.sorts) >= 0) {
			grid.sortMsg[name] = elem;
			elem.addClass("pointer cansort color378BCB");
			return "<b></b>"
		}
		grid.sortMsg[name] = false;
		return "";
	},

	indexOf: function (name, arr) {
		for (var i = 0, len = arr.length; i < len; i++) {
			if (name == arr[i]) return i;
		}
		return -1;
	},

	pageLayout: function () {
		switch (CMS.setting.dataGridPageLayout) {
			case 'lr':
				return '<label class="pagerMsg fl"></label><div class="pagerBut fr"></div>';
			case 'rl':
				return '<div class="pagerBut fl"></div><label class="pagerMsg fr"></label>';
			case 'left':
				return '<label class="pagerMsg fl"></label><div class="pagerBut fl"></div>';
			case 'right':
				return '<div class="pagerBut fr"></div><label class="pagerMsg fr"></label>';
			case 'center':
				return '<label class="pagerMsg"></label><div class="pagerBut"></div>';
		}
		return null;
	},

	initContextMenu: function () {
		var grid = this;
		if (this.contextMenu && this.contextMenu.length > 0) {
			new window.parent.CMS.util.menuList(this.tableObj, this.contextMenu || [], 150, function (e) {
				var checkbox = A(e.target).parents("tr.grid_row").find(".col_checkbox");
				if (!checkbox.prop("checked")) {
					grid.hasCheckbox && grid.operateObj.checkboxItem.prop("checked", false) && grid.operateObj.checkbox.prop("checked", false);
					checkbox.prop("checked", true);
				}
			})
		}
	},

	initPageBar: function () {
		var grid = this, time = new Date(), layout = this.pageLayout(),
			pagerwarpper = '<div class="pagerwarpper" style="text-align:center;"></div>';
		this.tableObj.after(pagerwarpper);
		CMS.setting.twoDataGridPage && this.tableObj.before(pagerwarpper);
		this.pagebar = A(".pagerwarpper", this.tableObj.parent());
		this.page = A(layout);
		this.pagebar.append(this.page);
		this.pagebar.find(".pagerBut").append('<input class="button radius4 first" xtype="first" type="button" value="首页"/>' +
			'<input class="button radius4 prev" xtype="prev" type="button" value="上页"/>' +
			'<input class="button radius4 next" xtype="next" type="button" value="下页"/>' +
			'<input class="button radius4 last" xtype="last" type="button" value="末页"/>' +

			'<select class="select changePageSize" style="width:78px;display:' + (!CMS.setting.dataGridChangePage ? "none" : "inline") + ';">' +
			'	<option value="10" ' + (grid.pageSize == 10 ? 'selected' : '') + '>10条/页</option>' +
			'	<option value="15" ' + (grid.pageSize == 15 ? 'selected' : '') + '>15条/页</option>' +
			'	<option value="20" ' + (grid.pageSize == 20 ? 'selected' : '') + '>20条/页</option>' +
			'	<option value="25" ' + (grid.pageSize == 25 ? 'selected' : '') + '>25条/页</option>' +
			'	<option value="30" ' + (grid.pageSize == 30 ? 'selected' : '') + '>30条/页</option>' +
			'</select>' +

			'<label> 跳转到</label>' +
			'<input class="input gotoinput" style="width:30px;height:14px;line-height:14px;" size="3" value="1" type="text"/>' +
			'<label>页</label>' +
			'<input class="button radius4 gotopageindex" type="button" value="GO"/>');
		this.pageButs = A('.prev, .first, .next, .last', this.pagebar).css("cursor", "not-allowed");
		this.prev = A('.prev', this.pagebar);
		this.first = A('.first', this.pagebar);
		this.next = A('.next', this.pagebar);
		this.last = A('.last', this.pagebar);

		pagerwarpper = null;
	},
	initRows: function () {
		var rows = [], grid = this, wi = '',
			trs = grid.tableObj.find(".grid_row"), trlen = trs.size();
		grid.insterNum = null;

		if (!trlen) {
			grid.createRow(rows, 0);
			var row = A(rows.join(""));
			for (var i = 0; i < this.pageSize; i++) {
				row.clone().attr("index", i).find(".col_checkbox").attr("indexTag", i)
					.end().appendTo(grid.tableObj)
			}
			row = null;
			rows = [];
			grid.dataRow = grid.oldData = this.tableObj.find("tbody tr");
			// grid.dataRow = grid.oldData = this.tableObj.find("tr:gt(0)");
			if (!grid.noPager) {
				grid.dataRow.hide()
			} else {
				grid.dataRow.show()
			}
			;
			if (grid.hasCheckbox) grid.operateObj.checkboxItem = grid.dataRow.find("td:eq(0) input");
			grid.newData = null;
			return;
		}

		if (trlen > 0 && this.pageSize > trlen) {
			var index = 0, frist = trs.eq(0)
			grid.oldData = grid.dataRow;
			for (var i = trlen; i < this.pageSize; i++) {
				var clone = frist.clone().removeClass(CMS.setting.dataGridEvenStyle).appendTo(grid.tableObj);
				if (!grid.noPager) {
					clone.hide()
				} else {
					clone.show();
				}
				clone.attr("index", i).find(".col_checkbox").attr("indextag", i);
				index++;
			}
			grid.dataRow = grid.tableObj.find("tbody tr");
			grid.newData = grid.dataRow.filter(function (index) {
				return trlen <= index
			});
			if (grid.hasCheckbox)
				grid.operateObj.checkboxItem = grid.dataRow.find("td:eq(0) input").prop("checked", false);
			return;
		}
	},

	insterRow: function (data, inster) {
		var grid = this, frist, index = inster + 1, newRow = [],
			map = new CMS.util.Map(), hasNewData = false;

		if (this.dataRow.length > 0) {
			var hidden = this.dataRow.filter(':hidden');
			if (hidden.size() > 0) {
				hasNewData = true;
				frist = hidden.eq(0).show()
			} else {
				frist = grid.dataRow.eq(0).clone().removeClass(CMS.setting.dataGridEvenStyle)
			}
		} else {
			grid.createRow(newRow, 0);
			frist = A(newRow.join(""));
			newRow = null;
		}

		grid.insterNum = index;
		grid.oldData = grid.dataRow;
		grid.dataRow.eq(inster).after(frist);
		grid.dataRow = this.tableObj.find("tbody tr");

		grid.newData = grid.dataRow.eq(index);
		grid.dataRow.each(function (i, t) {
			if (grid.insterNum > i) return;
			A(this).attr("index", i).find(".col_checkbox").attr("indexTag", i);
		});

		var thisRow = grid.dataRow.eq(index);
		map = grid.setRowData(index, data, thisRow);
		if (hasNewData) {
			grid.rowColor()
		} else {
			grid.bindEvent()
		}
		grid.operateObj.data.splice(index, 0, map);
		grid.operateObj.dataRowItem.splice(index, 0, thisRow);

		if (grid.hasCheckbox)
			grid.operateObj.checkboxItem = grid.dataRow.find("td:eq(0) input")
				.prop("checked", false);
		return thisRow
	},

	deleteRow: function (index) {
		this.dataRow.eq(index).hide().appendTo(this.tableObj);
		this.dataRow = this.tableObj.find("tbody tr");
		this.operateObj.data.splice(index, 1);
		this.operateObj.dataRowItem.splice(index, 1);

		this.dataRow.each(function (i, t) {
			A(this).attr("index", i).find(".col_checkbox").attr("indexTag", i);
		});

		this.rowColor();
		if (this.hasCheckbox)
			this.operateObj.checkboxItem = this.dataRow.find("td:eq(0) input")
				.prop("checked", false);
	},


	createRow: function (rows, i) {
		var grid = this;
		rows.push('<tr class="grid_row" index="', i, '">');
		grid.hasCheckbox && rows.push('<td><input type="'+(grid.singleSelection?"radio":"checkbox")+'" class="col_checkbox" indexTag="' + i + '"/></td>');
		for (var j = 0, len = grid.operateObj.colNameItem.length; j < len; j++) {
			var wi, val = grid.operateObj.colmsg.getValue(j), other = grid.operateObj.colmsg.getOtherVal(j),
				align = grid.operateObj.colalign.getValue(j),
				display = grid.operateObj.colalign.getOtherVal(j) ? '' : 'hidden';
			wi = 'style=' + (val ? 'width:' + other + ';' : " ") + "text-align:" + align + ";'";
			rows.push('<td class="grid_column ', display, '"><div class="inner" ' + wi + '>&nbsp;</div></td>')
		}
		grid.operateFunArray.length > 0 && rows.push('<td class="grid_action" align="center"><div style="text-align:center;width:' + (grid.operateFunArray.length > 0 && grid.actioncol.attr("width") || 60) + ';"><ul>' + this.initColBut() + '</ul></div></td>');
		rows.push('</tr>')
	},

	// event
	checkboxClick: function (e) {
		var self = A(this), grid = e.data.grid, index = self.attr("indexTag");
		if (grid.checkboxChangeEvent) grid.checkboxChangeEvent.call(grid, index, self.prop("checked"), grid.operateObj.data[index])
	},

	initColBut: function () {
		var grid = this, actions = [];
		A.each(grid.operateFunArray, function (i, action) {
			actions.push('<li>' + (action.type == "link" ?
				'<a href=' + (typeof action.linkurl == 'function' ? action.linkurl.call(grid, grid) : action.linkurl || "javascript:void(0);") + '>' + action.text + '</a>' : (action.type == 'text' ? '<span class="color0679AC pointer">' + action.text + '</span>' :
				'<span><img title=' + action.tip + ' src="' + action.iconurl + '"></span>')) + '</li>')
		});
		return actions.join("")
	},

	fillRow: function (index, rowData) {
		var grid = this, map = new CMS.util.Map(),
			row = grid.dataRow.eq(index);

		map = grid.setRowData(index, rowData, row);
		grid.operateObj.data[index] = map;
		grid.operateObj.dataRowItem[index] = row;
		if (grid.afterLoadRow) grid.afterLoadRow.call(grid, row, map)
	},

	setRowData: function (index, rowData, row) {
		var grid = this, map = new CMS.util.Map(),
			k = grid.hasCheckbox ? 1 : 0;
		map.put('rowData', rowData);
		A.each(grid.operateObj.colNameItem, function (i, n) {
			var html, colVal = rowData[n],
				colModel = grid.operateObj.colModel.getValue(n);

			grid.hasCheckbox && n == "id" && grid.operateObj.checkboxItem.eq(index).attr("idValue", colVal);
			html = grid.getColValue(colModel, colVal, rowData, row, index, n);
			row.find('td:eq(' + (i + k) + ')').find('div.inner').html((html === undefined || html === null || html === '') && '&nbsp;' || html);
			map.put(n, colVal)
		});
		return map
	},

	getColValue: function (colModel, colVal, rowData, row, index, n) {
		if (!colModel && colVal != 0 && colVal != "0") return (colVal || CMS.args.EMPTY).toString().html();

		if (colModel == "user-defined") {
			var funs = this.operateObj.colFunItem, fun = funs.getValue(n);
			if (A.isFunction(fun)) {
				return fun.call(this, colVal, rowData, row, index)
			}
		} else if (colModel == "link") {
			return '<a href="#">' + (colVal || CMS.args.EMPTY).html() + '</a>'
		} else if (colVal === 0 || colVal === "0") {
			return 0;
		}
		return CMS.args.EMPTY;
	},

	buildData: function () {
		var grid = this;
		grid.paramObj.hasDataCount = 0;
		A.each(grid.getDataRootByPath('root', 'root'), function (index, centext) {
			if (grid.paramObj.hasDataCount >= grid.pageSize) return false;
			grid.fillRow(index, centext);
			grid.paramObj.temp++;
			grid.paramObj.hasDataCount++;

		})
	},
	RowEventStyle: function () {
		var grid = this, hasClass = false;
		grid.rowColor(grid.newData || grid.dataRow);
		(grid.newData || grid.dataRow).find("td.grid_column").bind("click", {grid: grid}, grid.columnClick)
	},

	rowColor: function (rows) {
		if(!this.noIntervalColor){
			if (!rows) rows = this.dataRow;
			var next, hasClass = false, vlast = rows.eq(0).prev();

			rows.each(function (i) {
				var self = A(this).removeClass(CMS.setting.dataGridEvenStyle);
				if (i == 0 && vlast && vlast.hasClass(CMS.setting.dataGridEvenStyle)) {
					hasClass = true;
				}
				hasClass && self.addClass(CMS.setting.dataGridEvenStyle);
				hasClass = !hasClass;
			});
			next = rows.filter(":last").next();
			next.length > 0 && this.rowColor(this.dataRow.slice(next.attr("index")))
		}
	},

	// event
	columnClick: function (e) {
		var grid = e.data.grid;
		grid.hasCheckbox && grid.operateObj.checkboxItem.prop("checked", false) && A(this).parent().find(".col_checkbox").prop("checked", true)
		&& grid.operateObj.checkbox.prop("checked", false);
	},

	bindEvent: function () {
		var grid = this;
		if(!grid.disableRowClick){
			this.RowEventStyle();
		}

		(grid.newData || grid.dataRow).each(function (i, n) {
			var self = A(this), operate = self.find("td.grid_action li");
			A.each(operate, function (index, c) {
				A(this).bind("click", {grid: grid, index: index}, grid.actionClick)
			});
		}).find("td:eq(0) input").bind("click", {grid: grid}, grid.checkboxClick);// :visible

		if (grid.hasCheckbox && !grid.newData) {
			grid.operateObj.checkbox.click(function () {
				var checked = A(this).prop("checked");
				grid.operateObj.checkboxItem.filter(':visible').prop("checked", checked);
				if (grid.checkAllBox) grid.checkAllBox.call(grid, checked, grid.operateObj.data, grid.retData)
			});
			grid.operateObj.checkboxItem.click(function(){
				if(grid.operateObj.checkboxItem.filter(':checked:visible').length === grid.operateObj.checkboxItem.filter(':visible').length){
					grid.operateObj.checkbox.prop('checked', true);
				}else{
					grid.operateObj.checkbox.prop('checked', false);
				}
			});
		}
	},

	// event
	actionClick: function (e) {
		var grid = e.data.grid, index = e.data.index, i = A(this).parents("tr").attr("index"),
			action = grid.operateFunArray[index];
		!action.linkurl && grid.operateFunArray[index].fun(grid.operateObj.data[i])
	},

	removepagerClass: function () {
		this.setPageCursor(this.pageButs.removeClass('current'), true)
	},

	setPager: function () {
		this.removepagerClass();

		if (this.totalPages == 1 || this.totalPages == 0) {
			this.setPageCursor(this.pageButs.addClass('current'), true);
			return
		}
		if (this.currentPage == 1) {
			this.setPageCursor(A('.first,.prev', this.pagebar).addClass('current'), true);
			this.setPageCursor(A('.next,.last', this.pagebar), false);
			return
		}
		if (this.currentPage == this.totalPages) {
			this.setPageCursor(A('.next,.last', this.pagebar).addClass('current'), true);
			this.currentPage != 1 && this.setPageCursor(A('.first,.prev', this.pagebar), false);
			return
		}
		if (this.currentPage > 1 && this.currentPage < this.totalPages) {
			this.removepagerClass();
			this.setPageCursor(this.pageButs, false);
			return
		}
		this.totalPages == 0 && this.setPageCursor(this.pageButs.addClass("current"), true);
	},

	setPageCursor: function (elem, flag) {
		elem.prop('disabled', flag).css('cursor', flag ? 'not-allowed' : 'pointer')
	},

	setCurrentpage: function () {
		var grid = this;
		this.first.on('click', function () {
			if (!A(this).hasClass('current')) {
				grid.currentPage = 1;
				grid.setTableData()
			}
		});
		this.prev.on('click',function () {
			if (!A(this).hasClass('current')) {
				grid.currentPage -= 1;
				if (grid.currentPage < 1) {
					grid.currentPage = 1
				}
				grid.setTableData()
			}
		});
		this.next.on('click',function () {
			if (!A(this).hasClass('current')) {
				grid.currentPage = parseInt(grid.currentPage) + 1;
				if (grid.currentPage > grid.totalPages) {
					grid.currentPage = grid.totalPages
				}
				grid.setTableData()
			}
		});
		this.last.on('click',function () {
			if (!A(this).hasClass('current')) {
				grid.currentPage = grid.totalPages;
				grid.setTableData()
			}
		});

		this.goInput = A('.gotoinput', this.pagebar).on('blur',function () {
			A('.gotoinput', this.pagebar).val($(this).val());
		});

		this.goBut = A('.gotopageindex', this.pagebar).on('click',function () {
			var pageIndex = A.trim(grid.goInput.val());
			if (pageIndex == grid.currentPage) return;
			if (pageIndex == '' || pageIndex == 0) {
				Dialog.alert("页码不能为0或空，请输入正确的页码后在跳转!");
				grid.goInput.val(grid.currentPage);
				return
			}
			if (!/^[0-9]*[1-9][0-9]*$/.test(pageIndex)) {
				Dialog.alert("页码必须为正整数，请确认!");
				grid.goInput.val(grid.currentPage);
				return
			}
			if (pageIndex <= 0 || pageIndex > grid.totalPages) {
				Dialog.alert("填入的页码有误，页码大小必须在1到最大页码之间!");
				grid.goInput.val(grid.currentPage);
				return
			}
			grid.currentPage = pageIndex;
			grid.reload();
			grid.goInput.val(pageIndex)
		});

		this.changePageSize = A(".changePageSize", this.pagebar).change(function () {
			grid.pageSize = A(this).find("option:selected").attr("value");
			grid.changePageSize.find("option[value=" + grid.pageSize + "]").prop("selected", true);
			grid.showGmask();
			grid.currentPage = 1;
			grid.timeout = setTimeout(function () {
				grid.initRows();
				grid.bindEvent();
				grid.reload();
				clearTimeout(grid.timeout)
			}, CMS.core.isIE6 ? 1 : 0);
		})
	},

	isFristInit: function () {
		var grid = this;
		grid.setPager();
		grid.fristInit = false
	},

	showGmask: function () {
		// (!this.fristInit || this.noPager) && CMS.core.showMask()
		CMS.core.showMask()
	},

	setTableData: function () {
		this.showGmask();
		this.load();
		this.setPager();
		this.isFristInit()
	},

	setPagerMsg: function () {
		return CMS.setting.dataPageMsgModel.replaceall({
			'{pageSize}': this.pageSize,
			'{recordCount}': this.recordCount,
			'{totalPages}': this.totalPages,
			'{currentPage}': this.currentPage
		})
	},

	noPageLoad: function () {
		var grid = this, paramt = {};

		if (grid.postData && grid.postData.pageNo) {
			delete grid.postData.pageNo;
		}

		if (grid.postData && grid.postData.pageSize) {
			delete grid.postData.pageSize;
		}

		if (grid.postData && grid.postData.sortName) {
			delete grid.postData.sortName;
		}

		if (grid.postData && grid.postData.sortModel) {
			delete grid.postData.sortModel;
		}

		grid.postData = A.extend({
			'sortName': grid.sortName,
			'sortModel': grid.sortModel,
			'pageNo': grid.noPager ? 0 : grid.currentPage,
			'pageSize': grid.noPager ? 0 : grid.pageSize
		}, grid.postData);

		A.each(grid.postData, function (name, value) {
			if (value === null || A.trim(value).length == 0) {
				return;
			} else {
				paramt[name] = value;
			}
		});
		var datas = JSON.stringify(paramt);

		CMS.util.HttpAjax(grid.url, datas, function (data) {
			if (data.result == 'success') {
				grid.retData = data;
				var root = grid.getDataRootByPath('root', 'root'),
					len = root.length || 0,
					can = !grid.dataRow || grid.dataRow.length < len;
				grid.pageSize = len;

				can && grid.initRows();
				grid.dataRow.slice(0, len).show();
				grid.dataRow.slice(len).hide();
				grid.buildData();
				grid.hasCheckbox && grid.dataRow.find('td:eq(0) input').prop('checked', false);
				grid.operateObj.checkbox.prop('checked', false);
				can && grid.bindEvent();
				if(!grid.countTip){
					grid.countTip = $('<div class="countTip" style="text-align:left; height:25px; line-height:25px; padding: 0 5px;"></div>');
					grid.tableObj.after(grid.countTip)
				}
				grid.countTip.html('共' + len + '条数据');
				if (grid.afterEvent) grid.afterEvent.call(grid, data, grid.operateObj.data, grid.dataRow)
			} else {
				if (data.message) {
					Dialog.alert(data.message);
				}
			}
		}, {
			before: function () {
				if (grid.beforeEvent) grid.beforeEvent.call(grid, grid)
			},
			complete: function () {
				grid.IEtimeout = setTimeout(function () {
					CMS.core.hideMask();
					clearTimeout(grid.IEtimeout)
				}, CMS.core.isIE6 ? 300 : 0)
			}
		})
	},

	load: function () {
		var grid = this, pageBar = grid.pagebar, paramt = {};

		if (grid.postData && grid.postData.pageNo) {
			delete grid.postData.pageNo;
		}

		if (grid.postData && grid.postData.pageSize) {
			delete grid.postData.pageSize;
		}

		if (grid.postData && grid.postData.sortName) {
			delete grid.postData.sortName;
		}

		if (grid.postData && grid.postData.sortModel) {
			delete grid.postData.sortModel;
		}

		grid.postData = A.extend({
			'sortName': grid.sortName,
			'sortModel': grid.sortModel,
			'pageNo': grid.noPager ? 0 : grid.currentPage,
			'pageSize': grid.noPager ? 0 : grid.pageSize
		}, grid.postData);

		A.each(grid.postData, function (name, value) {
			if (value === null || A.trim(value).length == 0) {
				return;
			} else {
				paramt[name] = value;
			}
		});
		var datas = JSON.stringify(paramt);

		CMS.util.HttpAjax(grid.url, datas, function (data) {
			if (data.result == 'success') {
				grid.retData = data;
				var root = grid.getDataRootByPath('root', 'root'), temp = 1,
					len = grid.pageSize >= root.length ? root.length : grid.pageSize;

				if (grid.pagebar.size() > 1) {
					pageBar = grid.pagebar.eq(1)
				}
				A(".noDataTip", grid.tableObj.parent()).remove();

				grid.dataRow.slice(0, len).show();
				grid.dataRow.slice(len).hide();

				grid.hasCheckbox && grid.operateObj.checkboxItem.attr('idValue', '').prop('checked', false)
				&& grid.operateObj.checkbox.prop('checked', false);
				A.each(data, function (i, c) {
					grid.recordCount = parseInt(grid.getDataRootByPath('totalProperty', 'totalProperty')) || 0;
					grid.totalPages = Math.ceil((grid.recordCount / grid.pageSize)) || 0;
					if (temp == 2) grid.buildData();
					temp++
				});
				if (grid.afterEvent) grid.afterEvent.call(grid, data, grid.operateObj.data, grid.operateObj.dataRowItem);

			} else {
				grid.retData = {};
				Dialog.alert(data.message);
				return
			}
		}, {
			before: function () {
				if (grid.beforeEvent) grid.beforeEvent.call(grid, grid)
			},
			complete: function () {
				grid.totalPages = grid.totalPages || 0;
				if (grid.currentPage > grid.totalPages) grid.currentPage = grid.totalPages;
				grid.currentPage = grid.currentPage || 1;

				// if (grid.paramObj.temp == 0 && grid.currentPage != 1) {
				if (grid.paramObj.temp == 0 && grid.totalPages != 0) {
					// grid.currentPage -= 1;

					grid.reload();
					grid.setPager();
				} else {
					grid.paramObj.temp = 0;
					grid.isFristInit();
					A(".pagerMsg", grid.pagebar).html(grid.setPagerMsg());
				}
				grid.dataRow.removeClass("last_row");
				grid.tableObj.find("tbody tr:visible:last").addClass("last_row");

				if (grid.complete) grid.complete.call(grid, grid);
				grid.IEtimeout = setTimeout(function () {
					CMS.core.hideMask();
					clearTimeout(grid.IEtimeout)
				}, CMS.core.isIE6 ? 300 : 0);

				if (grid.recordCount == 0) {
					var pageBar = grid.pagebar;
					if (grid.pagebar.size() > 1) {
						pageBar = grid.pagebar.eq(1)
					}
					pageBar.before('<div class="center c noDataTip" style="overflow:hidden;padding-top:50px;height:80px;border-bottom:1px solid #CCCCCC">抱歉，没有查询到任何数据</div>');
				}
			}
		})
	},
	getPostData: function (obj) {
		var grid = this, paramt = {};
		obj = A.extend({
			'sortName': grid.sortName,
			'sortModel': grid.sortModel,
			'pageNo': grid.noPager ? 0 : grid.currentPage,
			'pageSize': grid.noPager ? 0 : grid.pageSize
		}, obj);

		A.each(obj, function (name, value) {
			if (value == null || A.trim(value).length == 0) {
				return;
			} else {
				paramt[name] = value;
			}
		});
		return JSON.stringify(paramt)
	},
	setUrl: function (newUrl) {
		this.url = newUrl;
		return newUrl
	},
	reload: function (url, data) {
		var grid = this;
		this.showGmask();
		if (url) {
			this.setUrl(url);
			this.fristInit = true;
			this.currentPage = 1;
			if (data) this.postData = data;
			else this.postData = {};
		}
		grid.IEtimeout = setTimeout(function () {
			clearTimeout(grid.IEtimeout)
			if (!grid.noPager) {
				grid.load();
				return
			}
			grid.noPageLoad()
		}, CMS.core.isIE6 ? 1 : 0)
	},

	getDataRootByPath: function (name, type) {
		var grid = this, rootName = name || 'root',
			path = type == 'root' ? grid.dataName : grid.totalPropertyName;
		
		if (path) {
			if (path.indexOf('.') > 0) {
				var retData = grid.retData;
				A.each(path.split('.'), function (i, name) {
					retData = retData[name]
				});
				return retData
			} else {
				return grid.retData[path]
			}
		}
		return grid.retData[rootName]
	},

	getPageSize: function () {
		return this.pageSize
	},

	getPageNo: function () {
		return this.currentPage
	},

	getRecordCount: function () {
		return this.recordCount
	},

	getSelectRow: function () {
		var grid = this, arr = [];
		if (!grid.hasCheckbox) return arr;
		grid.operateObj.checkboxItem.each(function (i, c) {
			var self = A(this);
			if (self.prop('checked') && grid.paramObj.hasDataCount > i) {
				arr.push({o: grid.operateObj.data[i], index: self.attr('indextag')})
			}
		});
		return arr
	},
	
	getSelectRowIds: function(id){
		var arr = [];
		A.each(this.getSelectRow(), function(i, row){
			arr.push(row.o.getValue(id));
		});
		return arr.join(',')
	},

	hasSelect: function () {
		return this.getSelectRow().length > 0
	},

	getRows: function () {
		return this.dataRow
	},

	getTable: function () {
		return this.tableObj
	},

	getTableParent: function () {
		return this.tableObj.parent()
	},

	selectFlag: function (flag) {
		this.hasCheckbox && this.operateObj.checkboxItem.prop('checked', flag)
		&& this.operateObj.checkbox.prop('checked', flag)
	},

	getRowData: function (index) {
		return this.operateObj.data[index]
	},

	getRow: function (index) {
		return this.dataRow.eq(index)
	}
};

// 用于tableGrid中列的双排序功能
// gird为使用于哪个grid， columnElem指排序的那一列表头， names表示排序字段集合（需要按照顺序）
CMS.util.doubleSort = function (grid, columnElem, names) {
	columnElem = CMS.util.toJquery(columnElem).addClass('cansort pointer');
	var titles = (A.trim(columnElem.text())).split('/');
	columnElem.html('<div class="columnnowrap doubleSort"><label name="' + names[0] + '" class="pointer">' + titles[0] + '</label><b>' +
		'</b><span style="color:#666;cursor:default;">/</span><label name="' + names[1] + '" class="pointer">' + titles[1] + '</label><b></b></div>');

	columnElem.find('label').bind('click', function () {
		var elem = A(this);
		if (!elem.hasClass('onsort')) {
			grid.clearSort();
			elem.addClass('onsort').siblings().removeClass('onsort').end()
				.next().removeClass().addClass('asc');
			grid.sortName = elem.attr('name');
			grid.sortModel = 'asc';
		} else {
			var b = elem.next(), newModel = b.hasClass('asc') ? "desc" : "asc";
			b.removeClass().addClass(newModel);
			grid.sortName = elem.attr('name');
			grid.sortModel = newModel;
		}
		columnElem.addClass('onsort');
		grid.reload();
	})
};

// 用于tableGrid中列的双排序功能
// gird为使用于哪个grid， columnElem指排序的那一列表头， names表示排序字段集合（需要按照顺序）
CMS.util.singleSort = function(grid, columnElem, names){
	columnElem = CMS.util.toJquery(columnElem).addClass('cansort pointer');
	var titles = A.trim(columnElem.text());
	columnElem.html('<div class="columnnowrap doubleSort"><label name="'+ names+'" class="pointer">'+ titles +
			'</label><b></b></div>');

	columnElem.find('label').bind('click', function(){
		var elem = A(this); 
		if(!elem.hasClass('onsort')){
			grid.clearSort();
			elem.addClass('onsort').siblings().removeClass('onsort').end()
				.next().removeClass().addClass('asc');
			grid.sortName = elem.attr('name');
			grid.sortModel = 'asc';
		} else {
			var b = elem.next(), newModel = b.hasClass('asc') ? "desc" : "asc";
			b.removeClass().addClass(newModel);
			grid.sortName = elem.attr('name');
			grid.sortModel = newModel; 
		}
		columnElem.addClass('onsort'); 
		grid.reload();
	})	 
};



CMS.util.Tab = function (options) {
	this.parent = options.parent;
	this.tabHead = options.tabHead.find('a');
	this.tabContext = options.tabContext;
	this.onclick = options.onclick;
	this.onLoad = options.onLoad;
	this.uninitFrist = options.uninitFrist;
	this.index = 0;
	this.init();
};

CMS.util.Tab.prototype.init = function () {
	var tab = this;
	if (this.onLoad) this.onLoad.call(this);
	this.urlMap = new CMS.util.Map();
	this.tabChilds = this.tabContext.children('div').hide();
	this.tabHead.each(function (index) {
		A(this).attr('tabIndex', index);
	});
	this.tabChilds.eq(0).show();
	this.check();
	this.initEvent();
};

CMS.util.Tab.prototype.initEvent = function () {
	var tab = this;
	this.tabHead.bind('click', function () {
		var but = A(this), tabIndex = but.attr('tabIndex');
		CMS.core.updateClass(CMS.core.updateClass(but, 'tabbutton', 'tabbuttonnone').siblings(),
			'tabbuttonnone', 'tabbutton');
		tab.tabChilds.eq(tabIndex).fadeIn("fast").siblings().hide();
		tab.check(tabIndex);
		tab.index = tabIndex;
	})
};

CMS.util.Tab.prototype.check = function (index) {
	if (this.uninitFrist && index == undefined) {
		return;
	}
	var tab = this, index = index || 0,
		child = tab.tabChilds.eq(index),
		url = child.attr('url'),
		height = child.attr('height') || 'auto',
		iframeId = child.attr('iframeId') || new Date(),
		newUrl = '', same = true;

	if (tab.onclick) newUrl = tab.onclick.call(this, child, url, index);
	if (url && newUrl && newUrl != url) {
		url = newUrl;
		same = false
	}
	if (url) {
		if (!child.find('iframe').size()) {
			this.createIframe(iframeId, height).appendTo(child).attr('src', url);
		} else {
			setTimeout(function () {
				newUrl && !same && child.find('iframe').attr('src', newUrl);
			}, 1)
		}
		child.attr('url', newUrl);
	}
};

CMS.util.Tab.prototype.createIframe = function (id, height) {
	return A('<iframe class="tabiframe" align="center" id="' + id + '" width="100%" ' +
		'height="' + height + '" src="" frameborder="0" scrolling="auto"></iframe>');
};

CMS.util.Tab.prototype.getIndex = function () {
	return this.index
};

// tooltip提示框实现
CMS.util.Tip = function (element, message, appendto) {
	this.element = typeof element == "string" ? A(element) : element;
	this.message = message;
	this.autoClose = false;
	this.clock = 9;
	this.appendto = appendto || CMS.core.Body
	this.init();
};

CMS.core.TipIdCounter = 0;

CMS.util.Tip.AutoCloseTips = [];

CMS.util.Tip.prototype.init = function () {
	var arr = [];
	arr.push('<table border="0" cellspacing="0" cellpadding="0" class="tooltiptable">');
	arr.push('	<tr><td class="corner topleft"> </td><td class="topcenter"> </td>');
	arr.push(' 		<td class="corner topright"> </td></tr><tr><td class="bodyleft"> </td>');
	arr.push('		<td class="tooltipcontent">' + this.message + '</td>');
	arr.push('		<td class="bodyright"> </td></tr>');
	arr.push('	<tr><td class="corner footerleft"> </td><td class="footercenter"> </td>');
	arr.push('		<td class="corner footerright"> </td></tr></table>');
	arr.push('<div class="tooltipfang"></div>');
	this.html = arr.join('');
};

CMS.util.Tip.prototype.show = function () {
	this.tip = CMS.core.createElement('div', 'tooltip callout' + this.clock, this.appendto, true, this.html).css({
		'position': 'absolute', top: 0, left: 0
	}).attr('id', 'CMS_tip_' + (CMS.core.TipIdCounter++));
	this.setPosition();
	this.autoClose && CMS.util.Tip.AutoCloseTips.push(this)
};

CMS.util.Tip.prototype.setPosition = function () {
	var tip = this, pos = !CMS.core.isSafari ? this.element.offset() : this.element.offsetFix(), dim = {
		width: tip.element.innerWidth(),
		height: tip.element.innerHeight()
	} , seat = this.clock, x, y;
	// 这个还是有点不全，待有时间的时候补充，但是面前clock为9已经够本项目使用
	if (seat == 2 || seat == 3 || seat == 4) x = pos.left + dim.width;
	if (seat == 8 || seat == 9 || seat == 10) x = pos.left + dim.width;

	if (seat == 11 || seat == 12 || seat == 1) y = pos.top + dim.height + CMS.core.Body.scrollTop();
	if (seat == 5 || seat == 6 || seat == 7) y = pos.top - dim.height + CMS.core.Body.scrollTop();
	if (seat == 9 || seat == 3) y = pos.top + (dim.height / 2) - (this.tip.height() / 2);

	if (this.element.attr('vtype') == 'upload') {
		x += 55;
	}
	this.tip.css({left: x, top: y, zIndex: 800}).show()
};

CMS.util.Tip.prototype.close = function () {
	this.tip && this.tip.remove();
	this.tip = null
};

CMS.util.Tip.show = function (element, message, autoClose, clock, appendto) {
	var tip = new CMS.util.Tip(element, message);
	tip.autoClose = autoClose;
	tip.clock = clock || 9;
	tip.show();
	if (!tip.autoClose) {
		if (!element.tips) element.tips = [];
		element.tips.push(tip);
	}
	return tip;
};

CMS.util.Tip.close = function (element) {
	element = typeof element == 'string' ? A(element) : element;
	if (element.tips) {
		for (var i = 0; i < element.tips.length; i++) {
			if (element.tips[i]) element.tips[i].close();
		}
		element.tips = [];
	}
};

CMS.core.Body.bind('click', function () {
	var arr = CMS.util.Tip.AutoCloseTips;
	for (var i = arr.length; i > 0; i--) {
		arr[i - 1].close();
		arr.splice(i - 1, 1)
	}
});


// 创建Form表单中的编辑组件工具对象
CMS.util.Edit = {};
CMS.util.Edit.names = ['id', 'type', 'name'];

CMS.util.Edit.getMessage = function (element, arr) {
	element = CMS.util.toJquery(element).eq(0);
	if (!element || element.size() == 0) return {};
	var mes = {}, data = element.data('message'), oldArr = element.data('names');
	if (data && arr == oldArr) return data;
	A.each(arr || ['type', 'name'], function (index, name) {
		try {
			mes[name] = (name == 'id' || name == 'type')
				&& (element.get())[name] || element.attr(name) || element.prop(name);
		} catch (e) {
			// 防止当获取id时，但是id实际上不存在，那么会抛出异常，这里捕捉下
			CMS.core.console('该元素不存在' + name + '这个属性！' + e);
			mes[name] = null;
		}
	});
	element.data('message', mes);
	element.data('names', arr);
	return mes
};

CMS.util.Edit.getData = function (element) {
	element = CMS.util.toJquery(element);
	var mes = CMS.util.Edit.getMessage(element, CMS.util.Edit.names), value;
	if (!mes || !mes.type) return null;

	if (['text', 'password'].indexOf(mes.type) >= 0) {
		value = A.trim(element.val())
	}
	if (mes.type == 'textarea') {
		value = A.trim(element.val())
	}
	if (mes.type == 'select-one') {
		value = element.find('option:selected').attr('value');
	}
	if (['checkbox', 'radio'].indexOf(mes.type) >= 0) {
		var arr = [];
		element.each(function (index) {
			var _elem = A(this);
			_elem.is(":checked") && arr.push(_elem.attr('value'));
		});
		value = arr.join(',');
		delete arr;
	}
	if (value == undefined) return null;
	return {name: mes.name, value: value, type: mes.type}
};

CMS.util.Edit.setData = function (element, obj, name) {
	name = name || 'name';
	var arr = ['id', 'type', 'name', 'setDataFun'];
	arr.indexOf(name) < 0 && arr.push(name);
	var mes = CMS.util.Edit.getMessage(element, arr), fun,
		val = typeof obj == 'object' ? (obj[mes[name]]) : obj;

	// 这里根据element的类型，然后来给element赋值
	switch (mes.type) {
		case 'text':
		case 'textarea':
		case 'password':
		case 'hidden':
			element.val(val);
			break;
		case 'select-one':
			element.find('option[value=' + val + ']').prop('selected', true);
			break;
		case 'checkbox':
		case 'radio':
			if (CMS.util.isEmpty(val)) break;
			val = CMS.args.EMPTY + val;
			var vals = [];
			if (val.indexOf(',') >= 0) {
				vals = val.split(',')
			} else if (val.indexOf('^') >= 0) {
				vals = val.split('^')
			} else {
				vals = [val]
			}
			element.each(function () {
				var cr = A(this);
				cr.prop('checked', vals.indexOf(cr.attr('value')) >= 0)
			});
			break;
	}

	if (mes.setDataFun && (fun = eval(mes.setDataFun))) {
		fun.call(element, val, obj)
	}
};

// 给element获得焦点，并且验证
CMS.util.Edit.focusVerify = function (element) {
	element = CMS.util.toJquery(element).eq(0);
	CMS.util.Edit.verify(element.focus());
};


CMS.util.Edit.verifyRegex = {
	intege: /^-?[1-9]\d*$/i,
	intege1: /^[1-9]\d*$/i,
	intege2: /^-[1-9]\d*$/i,
	num: /^([+-]?)\d*\.?\d+$/i,
	num1: /^([1-9]\d{0,9}|0)$/i,
	num2: /^(-[1-9]\d{0,9}|0)$/i,
	// 正整数 + 小数（2位小数）
	num3: /^((0)|([1-9]\d{0,9})|([1-9]\d{0,9}\.\d{1,2})|(0\.\d{1,2}))$/i,
	// 关键字
	keywords: /^([\u4e00-\u9fa50-9A-Za-z]+\s)*[\u4e00-\u9fa50-9A-Za-z]+$/i,
	email: /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/i,
	decmal: /^([+-]?)\d*\.\d+$/i,
	decmal1: /^([1-9]\d*.\d*)|(0.\d*[1-9]\d*)$/i,
	decmal2: /^(-([1-9]\d*.\d*|0).\d*[1-9]\d*)$/i,
	decmal3: /^(-?([1-9]\d*.\d*|0).\d*[1-9]\d*|0?.0+|0)$/i,
	decmal4: /^[1-9]\d*.\d*|0.\d*[1-9]\d*|0?.0+|0$/i,
	decmal5: /^(-([1-9]\d*.\d*|0.\d*[1-9]\d*))|0?.0+|0$/i,
	password: /^[a-zA-Z0-9]{5,16}$/i,
	percent: /^\d*(\.(\d{1,2}))?%$/i,
	price: /\d*(\.(\d{1,2}))?$/i,
	bankAccount: /^[0-9]{5,30}$/i,
	color: /^[a-fA-F0-9]{6}$/i,
	url: /^http[s]?:\/\/([\w-]+\.)+[a-zA-Z]{2,7}(:\d{1,5})?(\/[-+\w./?%*&=#]*)?$/i,
	chinese: /^[\u4E00-\u9FA5\uF900-\uFA2D]+$/i,
	ascii: /^[\x00-\xFF]+$/i,
	zipcode: /^\d{6}$/i,
	mobile: /^(13|14|15|18)[0-9]{9}$/i,
	ip: /^(25[0-5]|2[0-4]\d|[0-1]\d{2}|[1-9]?\d)\.(25[0-5]|2[0-4]\d|[0-1]\d{2}|[1-9]?\d)\.(25[0-5]|2[0-4]\d|[0-1]\d{2}|[1-9]?\d)\.(25[0-5]|2[0-4]\d|[0-1]\d{2}|[1-9]?\d)$/i,
	picture: /(.*)\.(jpg|bmp|gif|ico|pcx|jpeg|tif|png|raw|tga)$/i,
	rar: /(.*)\.(rar|zip|7zip|tgz)$/i,
	date: /^\d{4}(\-|\/|\.)\d{1,2}\1\d{1,2}$/i,
	qq: /^[1-9]*[1-9][0-9]*$/i,
	tel: /^(\d{3,4})-(\d{7})$/i,
	username: /^\w{4,16}$/i,
	letter: /^[A-Za-z]+$/i,
	letter_u: /^[A-Z]+$/i,
	letter_l: /^[a-z]+$/i,
	idcard: /^[1-9]([0-9a-z]{14}|[0-9a-z]{17})$/i
};

// 根据element中的verify属性设置来验证element输入的合法性， 最后返回该element
CMS.util.Edit.verify = function (element, showtip, outTipFun) {

	if (element.is(':hidden') ||
		(!element.is('input') && !element.is('textarea') && !element.is('select')))
		return true;

	var verify = CMS.util.Edit.getMessage(element, ['verify', 'type', 'verifyFun']),
		rule = (verify.verify || '').split('\|'), error = [], power = [] , title = rule[0],
		data = CMS.util.Edit.getData(element), value = data.value, fun;

	if (verify.verifyFun && (fun = eval(verify.verifyFun))) {
		value = fun.call(element, data)
	}

	A.each(rule.remove(title), function (i, ver) {
		var fName = null, fValue = null, op = '=';

		// 不包含'='号的判断
		if (ver.indexOf('=') < 0) {
			if (ver.indexOf('>') > 0) {
				op = '>'
			} else if (ver.indexOf('<') > 0) {
				op = '<'
			}
			// 包括'='号的判断
		} else {
			if (ver.charAt(ver.indexOf('=') - 1) == '>') {
				op = '>='
			} else if (ver.charAt(ver.indexOf('=') - 1) == '<') {
				op = '<='
			}
		}

		if (ver.indexOf(op) > 0) {
			fName = ver.substring(0, ver.indexOf(op));
			fValue = ver.substring(ver.indexOf(op) + op.length);
		} else {
			fName = ver;
		}

		// 根据类型来判断当前文本框中的信息是否符合判断
		switch (fName) {
			case 'NotNull':
				if (CMS.util.isEmpty(value)) {
					if (data.type == 'select-one' && value.lenght == 0) {
						error.push('必须选择' + title)
					} else {
						error.push(title + '不能为空')
					}
				}
				break;
			case 'Regex':
				if (CMS.util.isEmpty(value) || !fValue) {
					break;
				}
				if (fValue.indexOf('^') != 0) {
					fValue = '^' + fValue;
				}
				if (fValue[fValue.length - 1] != '$') {
					fValue += '$';
				}
				!new RegExp(fValue).test(value) && error.push(title + '不符合规则');
				break;
			case 'Number':
				if (CMS.util.isEmpty(value)) {
					break;
				}
				!CMS.util.isNumber(value) && error.push(title + '必须是数字');
				break;
			case 'Int':
				if (CMS.util.isEmpty(value)) {
					break;
				}
				!CMS.util.isInt(value) && error.push(title + '必须是整数');
				break;
			case 'Email':
				if (CMS.util.isEmpty(value)) {
					break;
				}
				!CMS.util.isEmail(value) && error.push(title + '不是正确的电子邮箱地址');
				break;
			case 'XLength':
				if (CMS.util.isEmpty(value)) {
					break;
				}
				// 汉字，全角字符等双字节字符算两个字符
				var vlen = value.replace(/[^\x00-\xff]/g, "aa").length;
			case 'Length':
				if (CMS.util.isEmpty(value)) {
					break;
				}
				if (isNaN(fValue)) {
					error.push('校验规则错误，Length后面必须是数字');
				} else {
					try {
						if (vlen === undefined) {
							vlen = value.length;
						}
						var len = parseInt(fValue), err = (op == "=" && vlen != len) && (title + '长度必须是' + len) ||
							(op == ">" && vlen <= len) && (title + '长度必须大于' + len) ||
							(op == "<" && vlen >= len) && (title + '长度必须小于' + len) ||
							(op == ">=" && vlen < len) && (title + '长度必须大于或等于' + len) ||
							(op == "<=" && vlen > len) && (title + '长度必须小于或等于' + len);
						err.length > 0 && error.push(err)
					} catch (ex) {
						error.push('校验规则错误，Length后面必须是整数' + ex.message);
					}
				}
				break;
			case 'Function':
				if (!fValue) {
					break;
				}
				var tit = eval(fValue).call(element, value, title);
				tit && error.push(tit);
				break;
			case 'regexEnum':
				if (!fValue || CMS.util.isEmpty(value)) {
					break;
				}
				if (!CMS.util.Edit.verifyRegex[fValue].test("" + value)) {
					error.push(title + '的格式不正确');
				}
				break;
		}
	});

	if (outTipFun) {
		return outTipFun.call(element, error);
	}

	if (!showtip) {
		if (!error.length) {
			element.removeClass('borderred').data('tip') && element.data('tip').close();
			return true;
		}
		element.removeClass('borderred').data('tip') && element.data('tip').close();
		element.data('tip', CMS.util.Tip.show(element, error.join('<br/>'), false));
		return false
	} else {
		if (!error.length) {
			element.removeClass('borderred').data('tip') && element.data('tip').close();
			return true;
		}
		element.addClass('borderred');
		return false
	}
};

// 创建Form工具对象
CMS.util.Form = {};
CMS.util.Form.itemClassName = 'formItem';
CMS.util.Form.cookie = {}

// 获取指定Form表单下的所有字段的值
CMS.util.Form.getData = function (form, className, mapping, model) {
	className = className || CMS.util.Form.itemClassName;
	form = (CMS.util.toJquery(form) || CMS.core.Body);
	this.collections = new CMS.util.Map();
	var temp = {}, self = this;

	A('input, select, textarea', form).each(function () {
		var edit = A(this), obj, value;
		if (!edit.hasClass(className) || edit.is(':hidden')) return;
		obj = CMS.util.Edit.getData(edit);
		if (!obj) return;
		value = obj.value;
		if (obj.type != 'checkbox' && obj.type != 'radio') {
			self.collections.put(obj.name, value);
		} else {
			if (!temp[obj.name]) temp[obj.name] = [];
			value != null && value != undefined && value != '' && temp[obj.name].push(value);
		}
	});

	A.each(temp, function (name, arr) {
		self.collections.put(name, arr.join(','));
	});
	temp = null;

	this.mapping = mapping || self.collections.keySet();
	this.model = model || {};
	this.createObject();
	this.hash = this.each();
	this.toObject();

	var form = this;
	A.each(this.collections.keySet(), function (index, key) {
		form.insert(form.hash && form.hash.getValue(key) || '', key, form.collections.getValue(key), true)
	})
};

CMS.util.Form.getData.prototype.toObject = function () {
	// console.log(this.obj)
	return this.obj || {}
};

CMS.util.Form.getData.prototype.toJson = function () {
	// console.log(JSON.stringify(this.obj || {}))
	return JSON.stringify(this.obj || {})
}

CMS.util.Form.getData.prototype.createObject = function (model) {
	this.obj = A.extend({}, typeof this.model == 'object' ? this.model : eval('' + this.model + ''));
};

CMS.util.Form.getData.prototype.each = function () {
	var mapping = new CMS.util.Map();
	A.each(this.mapping, function (i, path) {
		var arr = path.indexOf('.') >= 0 ? path.split('.') : [path],
			len = arr.length;
		mapping.put(arr[len - 1], len > 1 ? arr.slice(0, len - 1).join('.') : '')
	});
	return mapping
};

CMS.util.Form.getData.prototype.insert = function (path, name, value, flag) {
	if (arguments.length == 2) {
		flag = value;
		value = name;
		name = path;
		path = ''
	}
	if (!value && !flag) return this.obj;
	if (value.call) value = value();

	// 判断value的最终类型是什么，如果是object的话，那么需要把里面所有的变量值都encode一下
	if (typeof value == 'object') {
		A.each(value, function (name, val) {
			val = (val.toString() || CMS.args.EMPTY).replaceall('%', '％');
			value[name] = val;
		})
	} else {
		value = (value.toString() || CMS.args.EMPTY).replaceall('%', '％');
		value = value;
	}

	// encodeURIComponent(value);%25
	value = value;
	if(0==value){
		return ;
	}
	var arr = path.indexOf(".") >= 0 ? path.split(".") : [], len = arr.length, temp, self = this;

	if (len && (value || CMS.util.isEmpty(value))) {
		temp = self.obj[arr[0]] = self.obj[arr[0]] || {};
		A.each(arr.slice(1), function (i, o) {
			temp = temp[o] = temp[o] || {};
			if (len = i + 1) {
				temp[name] = value
			}
		})
	} else {
		if (path == '') {
			this.obj[name] = value;
			return this.obj
		}
		if (!value && value != ""&&0!=value&&value!=0) {
			this.obj[path] = name;
			return this.obj
		}
		temp = this.obj[path] = this.obj[path] || {};
		temp[name] = value
	}

	return this.obj
};

CMS.util.Form.getData.prototype.remove = function (path, name) {
	var arr = path.indexOf(".") >= 0 ? path.split(".") : [], len = arr.length, temp, self = this;
	if (!this.obj[arr[0] || path]) return this.obj;
	temp = !name ? this.obj : this.obj[arr[0] || path];
	if (len && name) {
		A.each(arr.slice(1), function (i, o) {
			if (!temp[o]) return this.o;
			temp = temp[o]
		})
	}
	temp[name || path] = undefined;
	this.obj = temp;
	return this.obj
};

// 给指定Form表单下的所有字段赋值
CMS.util.Form.setData = function (element, data, name) {
	CMS.util.toJquery(element).each(function () {
		var input = A(this);
		CMS.util.Edit.setData(input, data, name);
	})
};

// 一次性验证所有element
CMS.util.Form.verifyAll = function (element, outTipFun) {
	var hasError = false;
	CMS.util.toJquery(element).each(function () {
		if (!CMS.util.Edit.verify(A(this), true, outTipFun)) {
			hasError = true
		}
	});
	return hasError
};

// 给element添加验证
CMS.util.Form.verify = function (element, outTipFun, emptyFun) {
	element = CMS.util.toJquery(element);
	element.bind('focus',function () {
		CMS.util.Edit.verify(A(this), false, outTipFun);
	}).bind('keyup',function () {
			CMS.util.Edit.verify(A(this), false, outTipFun);
		}).bind('change',function () {
			CMS.util.Edit.verify(A(this), false, outTipFun);
		}).bind('blur', function () {
			if (outTipFun && emptyFun) emptyFun.call(element)
			var tip = A(this).data('tip');
			if (tip) tip.close();
		})
};


CMS.util.Edit.Combox = function (element, lists, type, callback,paramData) {
	this.disabled = false;
	this.type = type;
	this.callback = callback;
	if (arguments.length == 2) {
		this.type = 'click';
		this.callback = CMS.args.FUN;
	}

	if (arguments.length == 3) {
		if (type.call) {
			this.type = 'click';
			this.callback = type;
		} else {
			this.type = type;
			this.callback = CMS.args.FUN;
		}
	}
	this.element = CMS.util.toJquery(element);

	this.element.addClass('pointer combox').css({
		'background-position': this.element.width() - 10 + 'px 10px'
	});

	if (lists.join) {
		// 当lists的类型是数组类型的话
		this.list = lists;
	} else if (typeof lists == 'object') {
		// 当lists的类型是对象的话
		this.url = lists.url;
		this.data = lists.data;
	} else {
		// 当lists的类型为字符串的话
		this.url = lists;
		this.data = paramData||"{}";
	}
	this.display = false;
	this.init()
};

CMS.util.Edit.Combox.prototype.reset = function (lists, callback) {
	this.display = false;
	this.list = lists;
	this.callback = callback || this.callback || CMS.args.FUN;
	this.url = null;
	this.element.empty();
	this.init();
};

CMS.util.Edit.Combox.prototype.init = function () {
	var combox = this;
	if (combox.url)
		CMS.util.HttpAjax(combox.url, combox.data, function (data) {
			// if(data.result == 'success'){
			// combox.list = data.root || [];
			// }
			combox.list = data.root || [];
			combox.createList();
		}, {
			before: function () {
				CMS.core.showMask()
			},
			complete: function () {
				CMS.core.hideMask()
			}
		})
	else
		combox.createList();
};

CMS.util.Edit.Combox.prototype.getValue = function () {
	return this.selectValue == 0 ? 0 : this.selectValue || null;
};

CMS.util.Edit.Combox.prototype.getElement = function () {
	return this.element
};

CMS.util.Edit.Combox.prototype.getText = function () {
	return this.element.text()
};


CMS.util.Edit.Combox.prototype.createList = function () {
	var combox = this, html = ['<ul id="', this.element.attr('id') , '_combox_list">'],
		key = combox.element.attr('keyName'),
		value = combox.element.attr('valueName'),
		isObject = key && value && (combox.list[0] == undefined || typeof combox.list[0] == 'object');

	A('#' + this.element.attr('id') + "_combox_list").remove()

	var temp = {}, eleText = A.trim(combox.element.text());
	combox.dataMap = new CMS.util.Map();
	if (eleText != '') {
		if (isObject) {
			temp[key] = 0;
			temp[value] = eleText
		} else {
			temp = eleText
		}
		combox.list.unshift(temp);
	}

	var setValStr = this.element.attr('setValue');

	A.each(combox.list, function (i, context) {
		if (context == null || context == undefined)
			return
		var vkey = vvalue = context;
		if (isObject) {
			vkey = context[key];
			vvalue = context[value];
		}
		combox.dataMap.put(vkey, vvalue, i);
		if (!i) {
			combox.selectValue = vkey;
			combox.selectText = vvalue;
			// vkey && combox.callback.call(combox, vkey, vvalue)
		}
		if (setValStr)
			vvalue = eval(setValStr + ".call(this, context)");
		html.push('	 <li class="pointer c" val="', vkey, '">', vvalue, '</li>');
	});
	html.push('</ul>');
	combox.listElement = A(html.join('')).appendTo(CMS.core.Body).addClass('hidden absolute comboxlist');

	if (combox.list.length > 8) {
		combox.listElement.height(185).width(combox.listElement.width() + 20).css({
			'overflow': 'auto'
		});
	}
	combox.listElementOne = combox.listElement.find('li');
	combox.setValue(combox.selectValue);
	// combox.callback.call(combox, combox.selectValue, combox.selectText);
	combox.initEvent()
};


CMS.util.Edit.Combox.prototype.setValue = function (value) {
	var onselect = this.listElementOne.filter('.onselect'), selected;
	onselect.size() > 0 && onselect.removeClass('onselect');
	selected = this.listElementOne.eq(this.dataMap.getOtherVal(value));

	if (selected) {
		selected.addClass('onselect');
		this.element.html(selected.find('div:first').text() || selected.text()).attr('value', selected.attr('val') || '');
		this.selectValue = value;
	} else {
		this.selectValue = 0;
	}
	// this.callback && this.callback.call(this, this.selectValue,
	// selected.text());
};

CMS.util.Edit.Combox.prototype.initEvent = function () {
	var combox = this;
	switch (this.type) {
		case 'click':
			combox.element.bind('click', function () {
				if(!combox.disabled){
					CMS.core.comboxFouce = combox.element;
					combox.positions();
				}
			});
			CMS.core.document.bind('mouseup', function (e) {
				if(!combox.disabled){
					if (A(e.target).parent().get(0) == combox.listElement[0] || combox.listElement.index(e.target) >= 0) {
						CMS.core.comboxFouce = combox.element;
						combox.positions();
					} else if (A(e.target) != combox.listElement) {
						CMS.core.comboxFouce = null;
						combox.hidden();
					}
				}
			});
			break;
	}

	combox.listElementOne.bind('click',function () {
		var li = A(this), text = li.text(), value = li.attr('val');
		combox.element.html(text).attr('value', value || '');
		combox.hidden();
		combox.setValue(value);
		combox.callback.call(combox, value, text);
		CMS.core.comboxFouce = combox.element;
	}).hover(function () {
			A(this).addClass('hover');
		}, function () {
			A(this).removeClass('hover');
		})
};

/**
 * 禁用combox
 * 
 * @param disabled
 */
CMS.util.Edit.Combox.prototype.setDisabled = function(disabled){
	var combox = this;
	if(arguments.length == 0 || disabled) {
		this.disabled = true;
		combox.element.addClass('disabled');
	}else {
		this.disabled = false;
		combox.element.removeClass('disabled');
	}
};

CMS.util.Edit.Combox.prototype.hidden = function () {
	this.listElement.hide();
	this.display = false;
};

CMS.util.Edit.Combox.prototype.positions = function () {
	var combox = this,
		offset = !CMS.core.isSafari ? this.element.offset() : this.element.offsetFix(),
		tempNum = !CMS.core.isSafari ? 0 : 1,
		documentHeight = document.documentElement.clientHeight,
		listWidth = this.element.attr('listWidth') && parseInt(this.element.attr('listWidth')) || 0,
		top;
	if(documentHeight+(document.body.scrollTop||0) - (offset.top + combox.element.outerHeight(true) - tempNum) 
			< this.listElement.outerHeight(true) && offset.top > this.listElement.outerHeight(true)){
		top = offset.top - this.listElement.outerHeight(true);
	}else {
		top = offset.top + combox.element.outerHeight(true) - tempNum;
	}
	this.listElement.css({
		left: offset.left - tempNum,
		top: top,
		width: combox.element.width() + listWidth + 6
	}).fadeIn('fast');
	combox.display = true;
};

// multiCombox
CMS.util.Edit.multiCombox = function (element, lists, type, callback) {
	this.disabled = false;
	this.type = type;
	this.callback = callback;
	if (arguments.length == 2) {
		this.type = 'click';
		this.callback = CMS.args.FUN;
	}

	if (arguments.length == 3) {
		if (type.call) {
			this.type = 'click';
			this.callback = type;
		} else {
			this.type = type;
			this.callback = CMS.args.FUN;
		}
	}
	this.element = CMS.util.toJquery(element);

	this.element.addClass('pointer combox').css({
		'background-position': this.element.width() - 10 + 'px 10px'
	});

	if (lists.join) {
		// 当lists的类型是数组类型的话
		this.list = lists;
	} else if (typeof lists == 'object') {
		// 当lists的类型是对象的话
		this.url = lists.url;
		this.data = lists.data;
	} else {
		// 当lists的类型为字符串的话
		this.url = lists;
		this.data = {}
	}
	this.display = false;
	this.init()
};

CMS.util.Edit.multiCombox.prototype.reset = function (lists, callback) {
	this.display = false;
	this.list = lists;
	this.callback = callback || this.callback || CMS.args.FUN;
	this.url = null;
	this.element.empty();
	this.init();
};

CMS.util.Edit.multiCombox.prototype.init = function () {
	var combox = this;
	if (combox.url)
		CMS.util.HttpAjax(combox.url, combox.data, function (data) {
			// if(data.result == 'success'){
			// combox.list = data.root || [];
			// }
			combox.list = data.root || [];
			combox.createList();
		}, {
			before: function () {
				CMS.core.showMask()
			},
			complete: function () {
				CMS.core.hideMask()
			}
		})
	else
		combox.createList();
};

CMS.util.Edit.multiCombox.prototype.getValue = function () {
	return this.selectValue == 0 ? 0 : this.selectValue || null;
};

CMS.util.Edit.multiCombox.prototype.getElement = function () {
	return this.element
};

CMS.util.Edit.multiCombox.prototype.getText = function () {
	return this.element.text()
};


CMS.util.Edit.multiCombox.prototype.createList = function () {
	var combox = this, html = ['<ul id="', this.element.attr('id') , '_combox_list" class="multiComboxList">'],
		key = combox.element.attr('keyName'),
		value = combox.element.attr('valueName'),
		isObject = key && value && (combox.list[0] == undefined || typeof combox.list[0] == 'object');

	A('#' + this.element.attr('id') + "_combox_list").remove()

	var temp = {}, eleText = A.trim(combox.element.text());
	combox.dataMap = new CMS.util.Map();
	if (eleText != '') {
		if (isObject) {
			temp[key] = 0;
			temp[value] = eleText
		} else {
			temp = eleText
		}
		combox.list.unshift(temp);
	}

	var setValStr = this.element.attr('setValue');

	A.each(combox.list, function (i, context) {
		if (context == null || context == undefined)
			return
		var vkey = vvalue = context;
		if (isObject) {
			vkey = context[key];
			vvalue = context[value];
		}
		combox.dataMap.put(vkey, vvalue, i);
		if (!i) {
			combox.selectValue = vkey;
			combox.selectText = vvalue;
		}
		if (setValStr){
			vvalue = eval(setValStr + ".call(this, context)");
		}
		html.push('<li class="pointer c onselect" val="'+vkey+'">');
		html.push('    <div class="item '+((context.subItems&&context.subItems.length>0)?'hasChild':'')+'">'+vvalue+'</div>');
		if(context.subItems&&context.subItems.length){
			html.push('<div class="subItems" style="visibility: hidden;">');
			html.push('<ul>');
			for(var i=0;i<context.subItems.length;i++){
				var subItem = context.subItems[i], subItemValue = subItem[value];
				if(subItem[key]!=0){
					if (setValStr){
						subItemValue = eval(setValStr + ".call(this, subItem)");
					}
					html.push('<li pid="'+vkey+'" val="'+subItem[key]+'" title="'+subItem[value]+'">'+subItemValue+'</li>');
				}
			}
			html.push('</ul>');
			html.push('</div>');
		}
		html.push('</li>');
	});
	html.push('</ul>');
	combox.listElement = A(html.join('')).appendTo(CMS.core.Body).addClass('hidden absolute comboxlist');

	if (combox.list.length > 8) {
		combox.listElement.height(185).width(combox.listElement.width() + 20).css({
			'overflow': 'auto'
		});
	}
	combox.listElementOne = combox.listElement.find('li');
	combox.setValue(combox.selectValue);
	combox.initEvent()
};


CMS.util.Edit.multiCombox.prototype.setValue = function (value) {
	var onselect = this.listElementOne.filter('.onselect'), selected;
	onselect.size() > 0 && onselect.removeClass('onselect');
	selected = (this.listElementOne.find("li[val=" + value + "]").length>0?
		this.listElementOne.find("li[val=" + value + "]"):
		this.listElementOne.filter("li[val=" + value + "]"));
	selected = selected.length>0?selected:this.listElementOne.eq(0);
	if (selected) {
		selected.addClass('onselect');
		this.element.html(selected.find('div.item').text() || selected.text()).attr('value', selected.attr('val') || '');
		this.selectValue = value;
	} else {
		this.selectValue = 0;
	}
};

CMS.util.Edit.multiCombox.prototype.initEvent = function () {
	var combox = this;
	switch (this.type) {
		case 'click':
			combox.element.bind('click', function () {
				if(!combox.disabled){
					CMS.core.comboxFouce = combox.element;
					combox.positions();
				}
			});
			CMS.core.document.bind('mouseup', function (e) {
				if(!combox.disabled){
					if (A(e.target).parent().get(0) == combox.listElement[0] || combox.listElement.index(e.target) >= 0) {
						CMS.core.comboxFouce = combox.element;
						combox.positions();
					} else if (A(e.target) != combox.listElement) {
						CMS.core.comboxFouce = null;
						combox.hidden();
					}
				}
			});
			break;
	}

	combox.listElement.on('mouseenter', 'li', function () {
		if (A(this).find('div.subItems').length > 0) {
			A(this).find('div.subItems').css("visibility", "visible");
			A(this).find('.item').addClass('hover');
		}
	});
	combox.listElement.on('mouseleave', 'li', function () {
		A(this).find('div.subItems').css("visibility", "hidden");
		A(this).find('.item').removeClass('hover');
	});

	combox.listElementOne.bind('click',function (event) {
		var li = A(this), text = li.find('div:first').text() || li.text(), value = li.attr('val'), parentValue, parentText;
		combox.element.html(text).attr('value', value || '');
		combox.hidden();
		combox.setValue(value);
		if(li.attr('pid')){
			parentValue = li.attr('pid');
			parentText = li.parents('.subItems').prev().text();
		}else{
			parentValue = value;
			parentText = text;
			value = text = undefined;
		}
		CMS.core.comboxFouce = combox.element;
		combox.listElementOne.removeClass('onselect');
		li.addClass('onselect');
		if ('cancelBubble' in event) {
			event.cancelBubble = true;
		} else {
			event.stopPropagation();
		}
		combox.callback.call(combox, parentValue, parentText, value, text);
	}).hover(function () {
			A(this).addClass('hover');
		}, function () {
			A(this).removeClass('hover');
		});
};

CMS.util.Edit.multiCombox.prototype.setDisabled = function(disabled){
	var combox = this;
	if(arguments.length == 0 || disabled) {
		this.disabled = true;
		combox.element.addClass('disabled');
	}else {
		this.disabled = false;
		combox.element.removeClass('disabled');
	}
};

CMS.util.Edit.multiCombox.prototype.hidden = function () {
	this.listElement.hide();
	this.display = false;
};

CMS.util.Edit.multiCombox.prototype.positions = function () {
	var combox = this,
		offset = !CMS.core.isSafari ? this.element.offset() : this.element.offsetFix(),
		tempNum = !CMS.core.isSafari ? 0 : 1,
		documentHeight = document.documentElement.clientHeight,
		listWidth = this.element.attr('listWidth') && parseInt(this.element.attr('listWidth')) || 0,
		top;
	if(documentHeight+(document.body.scrollTop||0) - (offset.top + combox.element.outerHeight(true) - tempNum) < this.listElement.outerHeight(true) && offset.top > this.listElement.outerHeight(true)){
		top = offset.top - this.listElement.outerHeight(true);
	}else {
		top = offset.top + combox.element.outerHeight(true) - tempNum;
	}
	this.listElement.css({
		left: offset.left - tempNum,
		top: top,
		width: combox.element.width() + listWidth + 6
	}).fadeIn('fast');
	this.listElement.find('.hasChild').css('backgroundPositionX', (this.listElement.width()-20)+'px');
	this.listElement.find('.subItems').css('left', (this.listElement.width()-1)+'px');
	combox.display = true;
};

CMS.util.showImage = function (imageUrl) {
	if (!imageUrl) return;

	CMS.page.imgReady(imageUrl, function(){
		var iwidth = this.width,
			iheight = this.height,
			img = {}, image = this;
		if(iwidth == 0 || iheight == 0){
			return;
		}
		CMS.core.showover = false;
		CMS.core.binded = CMS.core.binded || false;
		CMS.core.loadWapper = (CMS.core.loadWapper ||
			CMS.core.createElement('div', 'loadimgwapper c radius4 shadow6CCC', CMS.core.Body, true)).addClass('loadbackimg').css({
				// overflow: "hidden"
			}).show();
		CMS.core.wrapperCloseBtn = (
			CMS.core.createElement('div', 'wrapperCloseBtn', CMS.core.loadWapper, true)).css({
				display: "none"
			});
		CMS.core.ShadeMask = (CMS.core.ShadeMask ||
			CMS.core.createElement('div', 'imageLoad', CMS.core.Body, true)).fadeIn('normal');

		var client = {
			width: document.documentElement.clientWidth,
			height: document.documentElement.clientHeight
		};

		if (!CMS.core.binded) {
			CMS.core.loadWapper.on('click','.wrapperCloseBtn', function () {
				if (CMS.core.showover) {
					CMS.core.ShadeMask.fadeOut(100);
					CMS.core.loadWapper.css({
						width: 70,
						height: 50,
						top: (client.height - 50) / 2,
						left: (client.width - 70) / 2
					}).empty().hide();
				}
			})
		}

		CMS.core.loadWapper.css({
			top: (client.height - 50) / 2,
			left: (client.width - 70) / 2
		});

		if(client.width > client.height){
			img.height=(iheight && iheight > client.height ? client.height - 50 : iheight) || 90;
			img.width = img.height*(iwidth/iheight);
		}else{
			img.width=(iwidth && iwidth > client.width ? client.width - 50 : iwidth) || 120;
			img.height = img.width*(iheight/iwidth);
		}
		CMS.core.loadWapper.animate({
			top: (client.height - img.height) / 2,
			left: (client.width - img.width) / 2,
			width: img.width,
			height: img.height
		}, 700, function () {
			CMS.core.showover = true;
			if (!iwidth && !iheight) {
				CMS.core.loadWapper.removeClass('loadbackimg').empty()
			} else {
				$(image).css({
					width: img.width,
					height: img.height
				}).appendTo(CMS.core.loadWapper.empty()).show();
				CMS.core.loadWapper.css('overflow', 'visible');
				CMS.core.wrapperCloseBtn.appendTo(CMS.core.loadWapper).show();
			}
		})
	});

// setTimeout(function () {
// var iwidth = parseInt(CMS.core.loadImgWapper.width()),
// iheight = parseInt(CMS.core.loadImgWapper.height()),
// img = {
// width: (iwidth && iwidth > client.width ? client.width - 50 : iwidth) || 120,
// height: (iheight && iheight > client.height ? client.height - 50 : iheight)
// || 90
// };
// CMS.core.loadWapper.animate({
// top: (client.height - img.height) / 2,
// left: (client.width - img.width) / 2,
// width: img.width,
// height: img.height
// }, 700, function () {
// CMS.core.showover = true;
// if (!iwidth && !iheight) {
// CMS.core.loadWapper.removeClass('loadbackimg').empty()
// } else {
// CMS.core.loadImgWapper.css({
// width: img.width,
// height: img.height
// }).appendTo(CMS.core.loadWapper.empty()).show();
// CMS.core.loadWapper.css('overflow', 'visible');
// CMS.core.wrapperCloseBtn.appendTo(CMS.core.loadWapper).show();
// }
// })
// }, 1000)
};

CMS.page.uploading = function () {
	CMS.page.uploadingWin = new Dialog();
	CMS.page.uploadingWin.Width = 380;
	CMS.page.uploadingWin.Height = 150;
	CMS.page.uploadingWin.ShowCloseButton = false;
	CMS.page.uploadingWin.Title = "正在上传中...";
	CMS.page.uploadingWin.InvokeElementId = "uploadingDiv";
	CMS.page.uploadingWin.show();
};

CMS.args.picTypes = ["png", "jpg", "gif", "bmp", "jpeg", "PNG", "JPG", "GIF", "BMP", "JPEG"];

CMS.util.Edit.AjaxUpload = function (element, url, lastFloder, input, callback, error, fileType) {

	element = CMS.util.toJquery(element);
	input = CMS.util.toJquery(input);
	fileType = fileType || CMS.args.picTypes;

	var tipTitle = fileType.indexOf('apk') >= 0 ? '文件' : '文件';

	if (!CMS.page.UploadArray) {
		CMS.page.UploadArray = [];
	}
	var arrLen = CMS.page.UploadArray.length,
		upload = CMS.page.UploadArray[arrLen] = new AjaxUpload(element, {
			action: url,
			name: "file",
			responseType: "json",
			data: {lastFloder: lastFloder},
			onSubmit: function (file, ext) {
				upload._settings.data.lastFloder = lastFloder;
				window.parent.CMS.page.uploading();
			},
			onComplete: function (file, data) {
				window.parent.CMS.page.uploadingWin && window.parent.CMS.page.uploadingWin.close();
				if (parseInt(data.status) == 1) {
					lastFloder = data.lastFloder;
					CMS.util.getFileType(data.url, fileType || [], function (isok) {
						if (!isok) {
							data.url = CMS.args.EMPTY;
							return;
						}
						input.val(data.url);
						callback && callback.call(this, data, element, input);
					});
					return;
				}
				if (parseInt(data.status) == -1) {
					Dialog.alert("上传" + tipTitle + "格式错误!");
				} else if (parseInt(data.status) == 2) {
					Dialog.alert("上传" + tipTitle + "的尺寸不正确!");
				} else if (parseInt(data.status) == 0) {
					Dialog.alert(data.message || "文件错误！");
				} else if (parseInt(data.status) == 4) {
					var fileSize = input.attr('fileSize');
					if (fileSize) {
						Dialog.alert("上传的文件不能大于" + fileSize + "!");
					} else {
						Dialog.alert(data.message || "上传的文件的大小不正确!");
					}
				} else if (parseInt(data.status) == 5) {
					Dialog.alert(data.message || '上传图片宽度不符合要求！');
				} else if (parseInt(data.status) == 6) {
					Dialog.alert(data.message || '上传图片高度不符合要求！');
				} else {
					Dialog.alert("上传" + tipTitle + "失败!");
				}
				error && error.call(input, data, element, input);
			}
		});
	return upload
};

CMS.util.getFileType = function (url, types, callback) {
	url = url || ".";
	if (!types.length) {
		types = ["png", "jpg", "gif", "bmp", "jpeg"]
	}
	var type = url.substring(url.indexOf(".", url.length - 5) + 1),
		isOk = false;
	A.each(types, function (i, fileType) {
		if (fileType == (type || CMS.args.EMPTY).toLowerCase()) {
			isOk = true
		}
	});
	callback.call(this, isOk);
	if (!isOk) Dialog.alert("上传的文件必须是" + types.join("、") + "格式")
};


// 防止用户的backspace, F5, Alt ←, Alt →, Alt+R键
CMS.page._keyPress = function (event) {
	var target = event.target,
		tag = event.target.tagName.toUpperCase();

	if ((event.altKey) && ((event.keyCode == 37) || (event.keyCode == 39))) {    // 屏蔽
																					// Alt+
																					// 方向键
																					// ← 和
																					// Alt+
																					// 方向键
																					// →
		event.returnValue = false;
		return false;
	}
	if (event.keyCode == 8) {				// 屏蔽退格删除键
		if ((tag == 'INPUT' && !A(target).prop("readonly")) || (tag == 'TEXTAREA' && !A(target).prop("readonly"))) {
			if ((target.type.toUpperCase() == "RADIO") || (target.type.toUpperCase() == "CHECKBOX")) {
				return false
			} else {
				return true
			}
		} else {
			return false
		}
	}
	if (event.keyCode == 116) {
		// 兼容IE
		event = window.event || event;
		event.keyCode = 0;
		event.returnValue = false;
		event.cancelBubble = true;
		return false;   					// 屏蔽F5刷新键
	}
	if ((event.ctrlKey) && (event.keyCode == 82)) {
		return false; 						// 屏蔽Alt+R
	}
};

// 屏蔽鼠标右键
CMS.core.document.bind('keydown', CMS.page._keyPress).bind('contextmenu', function () {
	return true
});

if (window.parent.CMS.core.goPageWin) window.parent.CMS.core.goPageWin.close();

// 截取特定长度字符串
CMS.util.stringXZ = function (length, col) {
	col = (col || CMS.args.EMPTY).toString();
	if (col.length > length) {
		return '<span title="' + (col).html() + '">' + (col.substring(0, length).html() + '…') + '</span>';
	}
	return col.html()
};

CMS.util.getSimpleApkName = function (name) {
	var str = name.split(',');
	if (str.length == 1) {
		return str[0].split('|')[1] || str[0];
	} else {
		return (str[0].indexOf('1|') == 0) ? (str[0].split('|')[1]) : (str[1].split('|')[1]);
	}
};

CMS.util.imgError = function (element) {
	element.src = contextPath + "/images/uu_icon.png";
};

/**
 * 图片头数据加载就绪事件 - 更快获取图片尺寸
 * 
 * @version 2011.05.27
 * @author TangBin
 * @see http://www.planeart.cn/?p=1121
 * @param {String}
 *            图片路径
 * @param {Function}
 *            尺寸就绪
 * @param {Function}
 *            加载完毕 (可选)
 * @param {Function}
 *            加载错误 (可选)
 * @example imgReady('http://www.google.com.hk/intl/zh-CN/images/logo_cn.png',
 *          function () { alert('size ready: width=' + this.width + '; height=' +
 *          this.height); });
 */
CMS.page.imgReady = (function () {
	var list = [], intervalId = null,

	// 用来执行队列
		tick = function () {
			var i = 0;
			for (; i < list.length; i++) {
				list[i].end ? list.splice(i--, 1) : list[i]();
			};
			!list.length && stop();
		},

	// 停止所有定时器队列
		stop = function () {
			clearInterval(intervalId);
			intervalId = null;
		};

	return function (url, ready, load, error) {
		var onready, width, height, newWidth, newHeight,
			img = new Image();

		img.src = url;

		// 如果图片被缓存，则直接返回缓存数据
		if (img.complete) {
			ready.call(img);
			load && load.call(img);
			return;
		};

		width = img.width;
		height = img.height;

		// 加载错误后的事件
		img.onerror = function () {
			error && error.call(img);
			onready.end = true;
			img = img.onload = img.onerror = null;
		};

		// 图片尺寸就绪
		onready = function () {
			newWidth = img.width;
			newHeight = img.height;
			if (newWidth !== width || newHeight !== height ||
				// 如果图片已经在其他地方加载可使用面积检测
				newWidth * newHeight > 1024
				) {
				ready.call(img);
				onready.end = true;
			};
		};
		onready();

		// 完全加载完毕的事件
		img.onload = function () {
			// onload在定时器时间差范围内可能比onready快
			// 这里进行检查并保证onready优先执行
			!onready.end && onready();

			load && load.call(img);

			// IE gif动画会循环执行onload，置空onload即可
			img = img.onload = img.onerror = null;
		};

		// 加入队列中定期执行
		if (!onready.end) {
			list.push(onready);
			// 无论何时只允许出现一个定时器，减少浏览器性能损耗
			if (intervalId === null) intervalId = setInterval(tick, 40);
		};
	};
})();

jQuery.fn.offsetFix=function(options,returnObject){var x=0,y=0,elem=this[0],parent=this[0],sl=0,st=0,options=jQuery.extend({margin:true,border:true,padding:false,scroll:true},options||{});do{x+=parent.offsetLeft||0;y+=parent.offsetTop||0;if(jQuery.browser.mozilla||jQuery.browser.msie){var bt=parseInt(jQuery.css(parent,'borderTopWidth'))||0;var bl=parseInt(jQuery.css(parent,'borderLeftWidth'))||0;x+=bl;y+=bt;if(jQuery.browser.mozilla&&parent!=elem&&jQuery.css(parent,'overflow')!='visible'){x+=bl;y+=bt}}var op=parent.offsetParent;if(op&&(op.tagName=='BODY'||op.tagName=='HTML')){if(jQuery.browser.safari&&jQuery.css(parent,'position')!='absolute'){x+=parseInt(jQuery.css(op,'marginLeft'))||0;y+=parseInt(jQuery.css(op,'marginTop'))||0}break}if(options.scroll){do{sl+=parent.scrollLeft||0;st+=parent.scrollTop||0;parent=parent.parentNode;if(jQuery.browser.mozilla&&parent!=elem&&parent!=op&&parent.style&&jQuery.css(parent,'overflow')!='visible'){y+=parseInt(jQuery.css(parent,'borderTopWidth'))||0;x+=parseInt(jQuery.css(parent,'borderLeftWidth'))||0}}while(parent!=op)}else{parent=parent.offsetParent}}while(parent);if(!options.margin){x-=parseInt(jQuery.css(elem,'marginLeft'))||0;y-=parseInt(jQuery.css(elem,'marginTop'))||0}if(options.border&&(jQuery.browser.safari||jQuery.browser.opera)){x+=parseInt(jQuery.css(elem,'borderLeftWidth'))||0;y+=parseInt(jQuery.css(elem,'borderTopWidth'))||0}else if(!options.border&&!(jQuery.browser.safari||jQuery.browser.opera)){x-=parseInt(jQuery.css(elem,'borderLeftWidth'))||0;y-=parseInt(jQuery.css(elem,'borderTopWidth'))||0}if(options.padding){x+=parseInt(jQuery.css(elem,'paddingLeft'))||0;y+=parseInt(jQuery.css(elem,'paddingTop'))||0}if(options.scroll&&jQuery.browser.opera&&jQuery.css(elem,'display')=='inline'){sl-=elem.scrollLeft||0;st-=elem.scrollTop||0}var returnValue=options.scroll?{top:y-st,left:x-sl,scrollTop:st,scrollLeft:sl}:{top:y,left:x};if(returnObject){jQuery.extend(returnObject,returnValue);return this}else{return returnValue}};

