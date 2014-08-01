var config = require('../utils/config');
var mysql = require('../utils').mysql;
var crypto = require('crypto');
var jokeDap = require('../dao').jokeDao;

/**
 * 
 */

// var jokeList = "select * from city";
exports.joke = function(req, res) {
	var conn = mysql.connection();
	var YResult = {};
	var list_data = new Array();
	var parameter = config.parameter(req);
	console.log('op---' + parameter.op);
	console.log('parameter---' + JSON.stringify(parameter));
//	console.log("req.body---" + req.body);
//	console.log("req.body.page---" + req.body.page);
//	console.log("req.body.pagesize---" + req.body.pagesize);
	
//	var headers = req.headers;
//	console.log(req.headers);
//	console.log(req.body);
//	console.log(req.connection.remoteAddress);
//	parameter.ip = headers['x-real-ip'] || headers['x-forwarded-for'];

	if (!parameter.op) {
		res.send("op error!");
		return;
	}

	if (parameter.op == config.op.activation) {
		var nor = false;
		
		parameter.ip = req.connection.remoteAddress
		parameter.creatTime = config.dateTime();
		console.log(parameter.creatTime);
		console.log(parameter.ip);
		
		if(parameter.imei||parameter.mac||parameter.display){
			YResult.status = -1;
			YResult.message = 'parameter error';
			res.send(JSON.stringify(YResult));
			return;
		}
		
		var hash = crypto.createHash("md5");
		hash.update(parameter.imei+parameter.mac+parameter.display);
		var deviceid = hash.digest('hex');

		var sqlsel = "select * from tb_activation where deviceid = '" + deviceid+"'";
		var sqlins = "insert into tb_activation(deviceid,imsi,imei,mac,os,display,"
			+"ip,network,language,brand,model,channel,vcode,vname,create_time) "
			+"values ('"+ deviceid + "','" + parameter.imsi +"','" + parameter.imei + "','" 
			+ parameter.mac + "','" + parameter.os + "','" + parameter.display 
			+ "','" + parameter.ip + "','" + parameter.network + "','" + parameter.language 
			+ "','" + parameter.brand + "','" + parameter.model + "','" + parameter.channel 
			+ "','" + parameter.vcode + "','" + parameter.vname + "','" + parameter.creatTime + "')";
			
//			var sqlins = "insert into tb_activation(deviceid,imsi,imei,mac,os,display,"
//			+"ip,network,language,brand,model,channel,vcode,vname,create_time,province,city) "
//			+"values ('"+ deviceid + "','" + parameter.imsi +"','" + parameter.imei + "','" 
//			+ parameter.mac + "','" + parameter.os + "','" + parameter.display 
//			+ "','" + parameter.ip + "','" + parameter.network + "','" + parameter.language 
//			+ "','" + parameter.brand + "','" + parameter.model + "','" + parameter.channel 
//			+ "','" + parameter.vcode + "','" + parameter.vname + "','" + parameter.create_time 
//			+ "','" + parameter.province+ "','" + parameter.city+ "')";
				
		console.log(sqlsel + "///" + sqlins);
		conn.query(sqlsel).on("row", function(r) {
			nor = true;
			list_data.push(r);
		}).on("end", function() {
			if (!nor) {
				conn.query(sqlins).on("result", function(result) {
//					conn.query(sqlins);
				}).on("end", function() {
					YResult.status = 1;
					res.send(JSON.stringify(YResult));
					// resp.redirect("/user");
				}).on('error', function(e) {
					YResult.status = -1;
					YResult.message = 'error';
					res.send(JSON.stringify(YResult));
				});
			} else {
				YResult.status = 1;
				res.send(JSON.stringify(YResult));
			}
		}).on('error', function(e) {
			console.log(e);
			YResult.status = -1;
			YResult.message = 'error';
			res.send(JSON.stringify(YResult));
		});

		return;

	} else if (parameter.op == config.op.list) {

		var sql = "select j.id,u.nick_name AS title,j.msg,j.type,j.url from tb_joke j "
				+ " left join tb_user u on j.userid = u.id where j.status = 1 ";

		if (parameter.type) {
			sql = sql + " AND j.type = " + parameter.type
		}

		if (parameter.page && parameter.pagesize) {
			var m = (parameter.page - 1) * parameter.pagesize;
			var n = parameter.pagesize;
			sql = sql + "LIMIT " + m + "," + n;
		}

		console.log(sql);
		conn.query(sql).on("row", function(r) {
			list_data.push(r);
		}).on("end", function() {
			// console.log(list_data);
			// console.log(JSON.stringify(list_data));
			YResult.data = list_data;
			YResult.count = list_data.length;
			YResult.status = 1;
			YResult.page = parameter.page;
			YResult.pagesize = parameter.pagesize;
			res.send(JSON.stringify(YResult));
		}).on('error', function(e) {
			console.log(e);
			YResult.status = -1;
			YResult.message = 'error';
			res.send(JSON.stringify(YResult));
		});
		return;

	} else if (parameter.op == config.op.news) {
		var sql = "select j.id,u.name AS title,j.msg,j.type,j.url from tb_news j "
				+ " left join tb_user u on j.userid = u.id where j.status = 1 ";

		if (parameter.type) {
			sql = sql + " AND j.type = " + parameter.type
		}

		if (parameter.page && parameter.pagesize) {
			var m = (parameter.page - 1) * parameter.pagesize;
			var n = parameter.pagesize;
			sql = sql + "LIMIT " + m + "," + n;
		}

		console.log(sql);
		conn.query(sql).on("row", function(r) {
			list_data.push(r);
		}).on("end", function() {
			// console.log(list_data);
			// console.log(JSON.stringify(list_data));
			YResult.data = list_data;
			YResult.count = list_data.length;
			YResult.status = 1;
			YResult.page = parameter.page;
			YResult.pagesize = parameter.pagesize;
			res.send(JSON.stringify(YResult));
		}).on('error', function(e) {
			console.log(e);
			YResult.status = -1;
			YResult.message = 'error';
			res.send(JSON.stringify(YResult));
		});
		return;
	} else if (parameter.op == config.op.sexy) {
		var sql = "select j.id,u.name AS title,j.msg,j.type,j.url from tb_sexy j "
				+ " left join tb_user u on j.userid = u.id where j.status = 1 ";

		if (parameter.type) {
			sql = sql + " AND j.type = " + parameter.type
		}

		if (parameter.page && parameter.pagesize) {
			var m = (parameter.page - 1) * parameter.pagesize;
			var n = parameter.pagesize;
			sql = sql + "LIMIT " + m + "," + n;
		}

		console.log(sql);
		conn.query(sql).on("row", function(r) {
			list_data.push(r);
		}).on("end", function() {
			// console.log(list_data);
			// console.log(JSON.stringify(list_data));
			YResult.data = list_data;
			YResult.count = list_data.length;
			YResult.status = 1;
			YResult.page = parameter.page;
			YResult.pagesize = parameter.pagesize;
			res.send(JSON.stringify(YResult));
		}).on('error', function(e) {
			console.log(e);
			YResult.status = -1;
			YResult.message = 'error';
			res.send(JSON.stringify(YResult));
		});
		return;
	} else {
		res.send("op error!");
		return;
	}

};

exports.list1 = {
	"dbname" : "desk",
	"user" : "root",
	"passwd" : "jin111111"
}
