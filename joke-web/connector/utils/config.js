var os = require("os");
var dbconfig = null;
var path = null;
if (os.type() === "Windows_NT") {
	console.log("config_win");
	dbconfig = require('./configwin');
	path = require('./configwin').path;
} else {
	console.log("config_linux");
	dbconfig = require('./configlinux');
	path = require('./configlinux').path;
}
exports.path = path;
exports.dbconfig = dbconfig;

exports.mysql = require('./mysqlNative');

exports.YResult = {
// 'root':'',
// 'data':'',
// 'msg':'',
// 'status':'',
// 'count':''
}

exports.server_url = "http://114.215.170.91:3000/joke?";

exports.op = {
	'joke' : 'joke',
	'activation' : 'activation',
	'news' : 'news',
	'sexy' : 'sexy'
//
}

exports.parameter = function(req) {
	var parameter = {};

	if (req.params) {
		for ( var p in req.params) {
			// console.log('req.params[p]---'+req.params[p]);
			// console.log('p---'+p);
			parameter[p] = decodeURIComponent(req.params[p]);
		}
	}
	if (req.body) {
		for ( var p in req.body) {
			// console.log('req.body[p]---'+req.body[p]);
			// console.log('p---'+p);
			parameter[p] = decodeURIComponent(req.body[p]);
		}
	}
	if (req.query) {
		for ( var p in req.query) {
			// console.log('req.query[p]---'+JSON.stringify(req.query[p]));
			// console.log('p---'+JSON.stringify(p));
			parameter[p] = decodeURIComponent(req.query[p]);
		}
	}

	return parameter;
}

exports.md5 = function(req) {

	var crypto = require('crypto');
	var hash = crypto.createHash("md5");
	hash.update(new Buffer("huangdanhua", "binary"));
	var encode = hash.digest('hex');

	return encode;
}

exports.dateTime = function(req) {

	var myDate = new Date();
	var year = myDate.getYear(); // 获取当前年份(2位)
	var fullYear = myDate.getFullYear(); // 获取完整的年份(4位,1970-????)
	var month = myDate.getMonth(); // 获取当前月份(0-11,0代表1月)
	if (month < 10)
		month = '0' + (month + 1);
	var date = myDate.getDate(); // 获取当前日(1-31)
	if (date < 10)
		date = '0' + date;
	var day = myDate.getDay(); // 获取当前星期X(0-6,0代表星期天)
	var time = myDate.getTime(); // 获取当前时间(从1970.1.1开始的毫秒数)
	var hour = myDate.getHours(); // 获取当前小时数(0-23)
	if (hour < 10)
		hour = '0' + hour;
	var minute = myDate.getMinutes(); // 获取当前分钟数(0-59)
	if (minute < 10)
		minute = '0' + minute;
	var second = myDate.getSeconds(); // 获取当前秒数(0-59)
	if (second < 10)
		second = '0' + second;
	var milliSecond = myDate.getMilliseconds(); // 获取当前毫秒数(0-999)
	var localeDate = myDate.toLocaleDateString(); // 获取当前日期
	var localeTime = myDate.toLocaleTimeString(); // 获取当前时间
	return fullYear + "-" + month + "-" + date + " " + localeTime; // 获取日期与时间
}

function getDate(date) {
	var Y = date.getFullYear();
	var M = date.getMonth() + 1;
	if (M < 10)
		M = '0' + M;
	var D = date.getDate();
	if (D < 10)
		D = '0' + D;
	var h = date.getHours();
	if (h < 10)
		h = '0' + h;
	var m = date.getMinutes();
	if (m < 10)
		m = '0' + m;
	var s = date.getSeconds();
	if (s < 10)
		s = '0' + s;
	return (Y + '-' + M + '-' + D + ' ' + h + ':' + m + ':' + s);
}

exports.dtpath = function() {
	var myDate = new Date();
	var year = myDate.getYear(); // 获取当前年份(2位)
	var fullYear = myDate.getFullYear(); // 获取完整的年份(4位,1970-????)
	var month = myDate.getMonth(); // 获取当前月份(0-11,0代表1月)
	if (month < 10)
		month = '0' + (month + 1);
	var date = myDate.getDate(); // 获取当前日(1-31)
	if (date < 10)
		date = '0' + date;
	var day = myDate.getDay(); // 获取当前星期X(0-6,0代表星期天)
	var time = myDate.getTime(); // 获取当前时间(从1970.1.1开始的毫秒数)
	var hour = myDate.getHours(); // 获取当前小时数(0-23)
	if (hour < 10)
		hour = '0' + hour;
	var minute = myDate.getMinutes(); // 获取当前分钟数(0-59)
	if (minute < 10)
		minute = '0' + minute;
	var second = myDate.getSeconds(); // 获取当前秒数(0-59)
	if (second < 10)
		second = '0' + second;
	var milliSecond = myDate.getMilliseconds(); // 获取当前毫秒数(0-999)
	var localeDate = myDate.toLocaleDateString(); // 获取当前日期
	var localeTime = myDate.toLocaleTimeString(); // 获取当前时间
	return fullYear + "/" + month + "/" + date + "/"; // 获取日期与时间
}

exports.finame = function() {
	var myDate = new Date();
	var year = myDate.getYear(); // 获取当前年份(2位)
	var fullYear = myDate.getFullYear(); // 获取完整的年份(4位,1970-????)
	var month = myDate.getMonth(); // 获取当前月份(0-11,0代表1月)
	if (month < 10)
		month = '0' + (month + 1);
	var date = myDate.getDate(); // 获取当前日(1-31)
	if (date < 10)
		date = '0' + date;
	var day = myDate.getDay(); // 获取当前星期X(0-6,0代表星期天)
	var time = myDate.getTime(); // 获取当前时间(从1970.1.1开始的毫秒数)
	var hour = myDate.getHours(); // 获取当前小时数(0-23)
	if (hour < 10)
		hour = '0' + hour;
	var minute = myDate.getMinutes(); // 获取当前分钟数(0-59)
	if (minute < 10)
		minute = '0' + minute;
	var second = myDate.getSeconds(); // 获取当前秒数(0-59)
	if (second < 10)
		second = '0' + second;
	var milliSecond = myDate.getMilliseconds(); // 获取当前毫秒数(0-999)
	var localeDate = myDate.toLocaleDateString(); // 获取当前日期
	var localeTime = myDate.toLocaleTimeString(); // 获取当前时间
	return fullYear + "" + month + "" + date + "" + hour + "" + minute + ""
			+ second + "" + milliSecond; // 获取日期与时间
}

exports.res = function(res, result) {
	res.send(JSON.stringify(result));
}

exports.isAddResUrl = function(r) {
	if (r && r != "" && r != " " && r.indexOf('http:') < 0) {
		return path.server_res_url + r
	} else {
		return r;
	}

}

exports.isChgImg = function(img) {
	if (img && img != "" && img != " ") {
		return img.substring(0, img.lastIndexOf('.')) + "_mic"
				+ img.substring(img.lastIndexOf('.'), img.length);
	} else {
		return img;
	}

}
