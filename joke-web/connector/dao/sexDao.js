var config = require('../utils/config');
var mysql = require('../utils').mysql;

/**
 * sexlist
 */
exports.sexlist = function(parameter, callback) {
	var conn = mysql.connection();
	var list_data = new Array();
	var YResult = {};

	var sql = "select j.id,j.img,j.title,j.descrip AS 'describe',j.talk,j.ischarge,j.price,"
			+ "j.url,j.type,j.source from tb_sexy j  left join tb_user u on j.userid = u.id "
			+ "where j.status = 1 ";

	var sqlcount = "select count(j.id) AS count from tb_sexy j "
			+ "where j.status = 1 ";

	var sqltaklcount = "select count(j.id)  from tb_sexy j "
			+ "where j.status = 1 ";

	if (parameter.type) {
		sql = sql + " AND j.type = " + parameter.type
	}

	sql = sql + " order by j.motime desc";
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
			if(r.url && r.url!=''){
			}else{
				r.url = config.server_url + "op=sexinfo&id=" + r.id;
			}
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
 * sexinfo
 */
exports.sexinfo = function(parameter, callback) {
	var conn = mysql.connection();
	var list_data = new Array();
	var YResult = {};

	if (!parameter.id) {
		YResult.status = -1;
		YResult.message = 'id error';
		callback(YResult);
		return;
	}

	var sql = "select j.memo "
			+ "from tb_sexy j "
			+ "where j.status = 1 and j.id = " + parameter.id;

	conn.query(sql).on("row", function(r) {
		// list_data.push(r);
		// r.memo = r.memo.replace('/&lt;/ig', "<");
		// r.memo = r.memo.replace('/&gt;/ig', ">");
		// r.memo = r.memo.replace('/&quot;/ig', "\"");
		// r.memo = r.memo.replace('/&apos;/ig', "\'");
		// r.memo = r.memo.replace('/&amp;/ig', "&");
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
