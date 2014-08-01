var config = require('../utils/config');
var mysql = require('../utils').mysql;

/**
 * list
 */
exports.list = function(parameter, callback) {
	var conn = mysql.connection();
	var list_data = new Array();
	var YResult = {};

	var sql = "select j.id,j.name,j.descrip,j.img,j.url,j.channel,j.price "
			+ " from tb_apk j " + "where j.status = 1 ";

	var sqlcount = "select count(j.id) AS count from tb_apk j "
			+ "where j.status = 1 ";

	var sqltaklcount = "select count(j.id)  from tb_apk j "
			+ "where j.status = 1 ";

	if (parameter.type) {
		sql = sql + " AND j.type = " + parameter.type
	}

	sql = sql + " order by j.price desc ";
	if (parameter.page && parameter.pagesize) {
		var m = (parameter.page - 1) * parameter.pagesize;
		var n = parameter.pagesize;
		sql = sql + " LIMIT " + m + "," + n;
	} else {
		sql = sql + " LIMIT " + 0 + "," + 10;
	}
	
	console.log(sql + "//" + sqlcount + "//" + sqltaklcount);

	conn.query(sqlcount).on("row", function(r) {
		// list_data.push(r);
	}).on("end", function() {
		YResult.count = this.result.rows[0]["count"];
		conn.query(sql).on("row", function(r) {
			r.img = config.isAddResUrl(r.img);
			r.url = config.isAddResUrl(r.url);
			list_data.push(r);
		}).on("end", function() {
			YResult.data = list_data;
			YResult.status = 1;
			YResult.page = parameter.page;
			YResult.pagesize = parameter.pagesize;
			callback(YResult);
		}).on('error', function(e) {
			console.log(e);
			YResult.status = -1;
			YResult.message = 'error';
			callback(YResult);
		});

	}).on('error', function(e) {
		console.log(e);
		YResult.status = -1;
		YResult.message = 'error';
		callback(YResult);
	});

};

/**
 * info
 */
exports.info = function(parameter, callback) {
	var conn = mysql.connection();
	var list_data = new Array();
	var YResult = {};

	if (!parameter.id) {
		YResult.status = -1;
		YResult.message = 'id error';
		callback(YResult);
		return;
	}

	var sql = "select j.memo " + "from tb_apk j "
			+ "where j.status = 1 and j.id = " + parameter.id;

	conn.query(sql).on("row", function(r) {
		list_data.push(r);
	}).on("end", function() {
		YResult.data = list_data;
		YResult.status = 1;
		callback(YResult);
	}).on('error', function(e) {
		console.log(e);
		YResult.status = -1;
		YResult.message = 'error';
		callback(YResult);
	});

};
