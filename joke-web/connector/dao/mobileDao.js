var config = require('../utils/config');
var mysql = require('../utils').mysql;

/**
 * new
 */
exports.search = function(parameter, callback) {
	var conn = mysql.connection();
	var list_data = new Array();
	var YResult = {};

	var regPartton = /1[3-8]+\d{9}/;
	if (!parameter.mobile || !regPartton.test(parameter.mobile)) {
		YResult.status = -1;
		YResult.message = 'mobile error';
		callback(YResult);
		return;
	}

	var mobile = parameter.mobile.substr(0, 7);
	// console.log('mobile---' + mobile);
	var sql = "select j.MobileArea AS area,j.MobileType AS type "
			+ "from Dm_Mobile j " + "where j.MobileNumber = " + mobile;

	var countsql = "select count(ID) " + "from Dm_Mobile j "
			+ "where j.MobileNumber = " + mobile;

	conn.query(sql).on("row", function(r) {
		YResult.data = r;
	}).on("end", function() {
		YResult.status = 1;
		callback(YResult);
	}).on('error', function(e) {
		console.log(e);
		YResult.status = -1;
		YResult.message = 'error';
		callback(YResult);
	});

};
