var config = require('../utils/config');
var mysql = require('../utils').mysql;
var crypto = require('crypto');

/**
 * joke
 */
exports.joke = function(res, parameter, callback) {
	var conn = mysql.connection();
	var list_data = new Array();
	var YResult = {};

	var sql = "select j.id,j.uname AS title,j.uimg AS titleimg,j.img AS img,j.good,j.bad,"
			+ "j.msg,j.type,j.source,j.talk "
			+ " from tb_joke j "
			+ "where j.status = 1 ";

	var sqljokecount = "select count(j.id) AS count  " + "from tb_joke j "
			+ "where j.status = 1 ";

	var sqltaklcount = "select count(j.id)  " + "from tb_talk j "
			+ "where j.status = 1 ";

	if (parameter.type) {
		sql = sql + " AND j.type = " + parameter.type
	}

	sql = sql + " order by j.motime desc";
	if (parameter.page && parameter.pagesize && parameter.page < 20 && parameter.pagesize < 20) {
		var m = (parameter.page - 1) * parameter.pagesize;
		var n = parameter.pagesize;
		sql = sql + " LIMIT " + m + "," + n;
	} else {
		sql = sql + " LIMIT " + 0 + "," + 10;
	}

	console.log(sql + "//" + sqljokecount + "//" + sqltaklcount);

	conn.query(sqljokecount).on("row", function(r) {
		// list_data.push(r);
	}).on("end", function() {
		YResult.count = this.result.rows[0]["count"];

		conn.query(sql).on("row", function(r) {
			r.img = config.isAddResUrl(r.img);
			r.imgmic = config.isChgImg(r.img);
			r.titleimg = config.isAddResUrl(r.titleimg);
			list_data.push(r);
		}).on("end", function() {
			YResult.data = list_data;
			YResult.status = 1;
			YResult.page = parameter.page;
			YResult.pagesize = parameter.pagesize;
			callback(res, YResult);
		}).on('error', function(e) {
			console.log(e);
			YResult.status = -1;
			YResult.message = 'error';
			callback(res, YResult);
		});

	}).on('error', function(e) {
		console.log(e);
		YResult.status = -1;
		YResult.message = 'error';
		callback(res, YResult);
	});

};

/**
 * jokeinfo
 */
exports.jokeinfo = function(parameter, callback) {
	var conn = mysql.connection();
	var list_data = new Array();
	var YResult = {};

	if (!parameter.id) {
		YResult.status = -1;
		YResult.message = 'id error';
		callback(res, YResult);
		return;
	}

	var sql = "select j.id,j.uname AS title,j.uimg AS titleimg,j.img AS img,j.good,j.bad,"
			+ "j.msg,j.type,j.source,j.talk AS talknum "
			+ "from tb_joke j "
			+ "where j.status = 1 and j.id = " + parameter.id;

	conn.query(sql).on("row", function(r) {
		// list_data.push(r);
		r.img = config.isAddResUrl(r.img);
		r.titleimg = config.isAddResUrl(r.titleimg);
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

/**
 * joketalk
 */
exports.joketalk = function(parameter, callback) {
	var conn = mysql.connection();
	var list_data = new Array();
	var YResult = {};

	if (!parameter.id) {
		YResult.status = -1;
		YResult.message = 'id error';
		callback(YResult);
		return;
	}

	var sql = "select j.id,j.jokeid,j.msg,j.model,u.nick_name AS username,"
			+ "u.img AS usericon,j.motime AS createdate "
			+ "from tb_talk j left join tb_user u on j.userid = u.id "
			+ "where j.status = 1 and j.jokeid = " + parameter.id;

	var sqlcount = "select count(j.id) AS count  " + "from tb_talk j "
			+ "where j.status = 1 and j.jokeid = " + parameter.id;

	sql = sql + " order by j.motime desc";
	if (parameter.page && parameter.pagesize && parameter.page < 20 && parameter.pagesize < 20) {
		var m = (parameter.page - 1) * parameter.pagesize;
		var n = parameter.pagesize;
		sql = sql + " LIMIT " + m + "," + n;
	} else {
		sql = sql + " LIMIT " + 0 + "," + 10;
	}

	console.log(sql + "//" + sqlcount);

	conn.query(sqlcount).on("row", function(r) {
		// list_data.push(r);
	}).on("end", function() {
		console.log("resdata---" + this.result.rows[0]["count"]);
		YResult.count = this.result.rows[0]["count"];

		conn.query(sql).on("row", function(r) {
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
 * evaluate
 */
exports.evaluate = function(parameter, callback) {
	var conn = mysql.connection();
	var list_data = new Array();
	parameter.creatTime = config.dateTime();
	var YResult = {};
	console.log("evaluate parameter---" + JSON.stringify(parameter));

	if (!parameter.userid || !parameter.jokeid || !parameter.type) {
		YResult.status = -1;
		YResult.message = 'parameter error';
		callback(YResult);
		return;
	}

	// var sql = "insert into tb_evaluate(userid,jokeid,type,status,crtime)
	// values ( "
	// + parameter.userid
	// + ","
	// + parameter.jokeid
	// + ","
	// + parameter.type
	// + "," + 1 + ",'" + parameter.creatTime + "' )"
	if (parameter.type === '1' || parameter.type == 1) {
		var sql = "update tb_joke set good = good + 1 where id = "
				+ parameter.jokeid
	} else {
		var sql = "update tb_joke set bad = bad + 1 where id = "
				+ parameter.jokeid
	}

	console.log(sql);
	conn.query(sql).on("result", function(r) {
		// list_data.push(r);
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

/**
 * jokeaddtalk
 */
exports.jokeaddtalk = function(parameter, callback) {
	parameter.creatTime = config.dateTime();
	var conn = mysql.connection();
	var list_data = new Array();
	var YResult = {};

	if (!parameter.userid || !parameter.jokeid || !parameter.msg) {
		YResult.status = -1;
		YResult.message = 'parameter error';
		callback(YResult);
		return;
	}
	var sql = "insert into tb_talk(userid,jokeid,msg,model,status,crtime,motime) values ( "
			+ parameter.userid
			+ ","
			+ parameter.jokeid
			+ ",'"
			+ parameter.msg
			+ "','"
			+ parameter.model
			+ "',"
			+ 1
			+ ",'"
			+ parameter.creatTime
			+ "','" + parameter.creatTime + "' )"

	var sqlup = "update tb_joke set talk = talk + 1 where id = "
			+ parameter.jokeid

	console.log(sql);
	conn.query(sql).on("end", function() {
		YResult.status = 1;
		callback(YResult);
		conn.query(sqlup).on("result", function(r) {
			// list_data.push(r);
		}).on("end", function() {
			YResult.status = 1;
		}).on('error', function(e) {
			console.log(e);
		});
	}).on('error', function(e) {
		console.log(e);
		YResult.status = -1;
		YResult.message = 'error';
		callback(YResult);
	});

};

/**
 * jokeaddjoke
 */
exports.jokeaddjoke = function(parameter, callback) {
	parameter.creatTime = config.dateTime();
	var conn = mysql.connection();
	var list_data = new Array();
	var YResult = {};

	if (!parameter.userid || !parameter.msg) {
		YResult.status = -1;
		YResult.message = 'parameter error';
		callback(YResult);
		return;
	}
	var sql = "insert into tb_joke(userid,msg,type,img,good,bad,talk,status,crtime,motime) values ( "
			+ (parameter.userid ? parameter.userid : 0)
			+ ",'"
			+ parameter.msg
			+ "',"
			+ 3
			+ ",'"
			+ (parameter.imgurl ? parameter.imgurl : '')
			+ "',"
			+ 0
			+ ","
			+ 0
			+ ","
			+ 0
			+ ","
			+ 2
			+ ",'"
			+ parameter.creatTime + "','" + parameter.creatTime + "' )"

	console.log(sql);
	conn.query(sql).on("end", function() {
		YResult.status = 1;
		callback(YResult);
	}).on('error', function(e) {
		console.log(e);
		YResult.status = -1;
		YResult.message = 'error';
		callback(YResult);
	});

};

/**
 * jokeaddjoke
 */
exports.jokeofusers = function(parameter, callback) {
	parameter.creatTime = config.dateTime();
	var conn = mysql.connection();
	var list_data = new Array();
	var YResult = {};

	if (!parameter.userid) {
		YResult.status = -1;
		YResult.message = 'parameter error';
		callback(YResult);
		return;
	}

	var sql = "select j.id,u.nick_name AS title,u.img AS titleimg,j.good,j.bad,"
			+ "j.msg,j.type,j.talk,j.status"
			+ " from tb_joke j  left join tb_user u on j.userid = u.id "
			+ "where j.status<>-1 and j.userid = " + parameter.userid;

	var sqljokecount = "select count(j.id) AS count  " + "from tb_joke j "
			+ "where j.status<>-1 and j.userid = " + parameter.userid;

	if (parameter.type) {
		sql = sql + " AND j.type = " + parameter.type
	}

	sql = sql + " order by j.motime desc";
	if (parameter.page && parameter.pagesize && parameter.page < 20 && parameter.pagesize < 20) {
		var m = (parameter.page - 1) * parameter.pagesize;
		var n = parameter.pagesize;
		sql = sql + " LIMIT " + m + "," + n;
	} else {
		sql = sql + " LIMIT " + 0 + "," + 10;
	}

	console.log(sql + "//" + sqljokecount);

	conn.query(sqljokecount).on("row", function(r) {
		// list_data.push(r);
	}).on("end", function() {
		console.log("resdata---" + this.result.rows[0]["count"]);
		YResult.count = this.result.rows[0]["count"];

		conn.query(sql).on("row", function(r) {
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
 * jokeaddtalk
 */
exports.jokelogin = function(parameter, callback) {
	parameter.creatTime = config.dateTime();
	var conn = mysql.connection();
	var list_data = new Array();
	var YResult = {};

	if (!parameter.username || !parameter.usericon || !parameter.deviceid) {
		YResult.status = -1;
		YResult.message = 'parameter error';
		callback(YResult);
		return;
	}
	var sql = "insert into tb_user(nick_name,img,deviceid,openid,token,role,status,create_time,modify_time) values ( '"
			+ parameter.username
			+ "','"
			+ parameter.usericon
			+ "','"
			+ parameter.deviceid
			+ "','"
			+ parameter.openid
			+ "','"
			+ parameter.token
			+ "',"
			+ 7
			+ ","
			+ 1
			+ ",'"
			+ parameter.creatTime
			+ "','" + parameter.creatTime + "' )"

	var sqlUp = "update tb_user set nick_name='" + parameter.username
			+ "',img='" + parameter.usericon + "',modify_time='"
			+ parameter.creatTime + "' where openid = '" + parameter.openid
			+ "'"

	var sqlSel = "select id from tb_user where openid = '" + parameter.openid
			+ "'";

	console.log(sql + "////" + sqlSel + "///" + sqlUp);

	var sw = false;
	conn.query(sqlSel).on("row", function(r) {
		sw = true;
		list_data.push(r);
	}).on("end", function() {
		if (!sw) {
			conn.query(sql).on("end", function() {
				conn.query(sqlSel).on("row", function(r) {
					list_data.push(r);
				}).on("end", function() {
					YResult.status = 1;
					YResult.id = list_data[0].id;
					// resp.redirect("/user");
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
		} else {
			conn.query(sqlUp).on("end", function() {
				conn.query(sqlSel).on("row", function(r) {
					list_data.push(r);
				}).on("end", function() {
					YResult.status = 1;
					YResult.id = list_data[0].id;
					// resp.redirect("/user");
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
		}

	}).on('error', function(e) {
		console.log(e);
		YResult.status = -1;
		YResult.message = 'error';
		callback(YResult);
	});

};

exports.activation = function(req, res, parameter, callback) {
	var conn = mysql.connection();
	var list_data = new Array();
	var YResult = {};
	var nor = false;

	parameter.ip = req.connection.remoteAddress;
	parameter.creatTime = config.dateTime();
	console.log(parameter.creatTime);
	console.log(parameter.ip);

	if (!parameter.imei || !parameter.mac || !parameter.display) {
		YResult.status = -1;
		YResult.message = 'parameter error';
		res.send(JSON.stringify(YResult));
		return;
	}

	var hash = crypto.createHash("md5");
	hash.update(parameter.imei + parameter.mac + parameter.display);
	var deviceid = hash.digest('hex');

	var sqlsel = "select * from tb_activation where deviceid ='" + deviceid
			+ "'";

	var sqlins = "insert into tb_activation(deviceid,imsi,imei,mac,os,display,"
			+ "ip,network,language,brand,model,channel,vcode,vname,create_time) "
			+ "values ('"
			+ deviceid
			+ "','"
			+ (parameter.imsi || '')
			+ "','"
			+ (parameter.imei || '')
			+ "','"
			+ (parameter.mac || '')
			+ "','"
			+ (parameter.os || '')
			+ "','"
			+ (parameter.display || '')
			+ "','"
			+ (parameter.ip || '')
			+ "','"
			+ (parameter.network || '')
			+ "','"
			+ (parameter.language || '')
			+ "','"
			+ (parameter.brand || '')
			+ "','"
			+ (parameter.model || '')
			+ "','"
			+ (parameter.channel || '')
			+ "','"
			+ (parameter.vcode || '')
			+ "','"
			+ (parameter.vname || '')
			+ "','"
			+ parameter.creatTime
			+ "')";

	console.log(sqlsel + "///" + sqlins);
	conn.query(sqlsel).on("row", function(r) {
		nor = true;
		list_data.push(r);
	}).on("end", function() {
		if (!nor) {
			conn.query(sqlins).on("result", function(result) {
				// conn.query(sqlins);
			}).on("end", function() {
				YResult.status = 1;
				// resp.redirect("/user");
				callback(YResult);
			}).on('error', function(e) {
				YResult.status = -1;
				YResult.message = 'error';
				callback(YResult);
			});
		} else {
			YResult.status = 1;
			callback(YResult);
		}
	}).on('error', function(e) {
		console.log(e);
		YResult.status = -1;
		YResult.message = 'error';
		callback(YResult);
	});

	return YResult;
};
