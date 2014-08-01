var mysqlNative = require('../utils').MysqlNative;
var config = require('../utils/config');
var conn = mysqlNative.connection();
var list_data = new Array();

/**
 * 
 */
var jokeList = "select * from joke";
// var jokeList = "select * from city";

exports.list = function(req, res) {
	
	conn.query(jokeList).on("row", function(r) {
		// var dateFormat = new Date();
		// dateFormat.setTime(r.s_time*1000);
		//	
		// var yyyy=dateFormat.getFullYear();
		// var mm=('0'+(dateFormat.getMonth()+1)).slice(-2);
		// var dd=('0'+(dateFormat.getDay()+1)).slice(-2);
		//	
		// var hh=('0'+dateFormat.getHours()).slice(-2);
		// var ii=('0'+dateFormat.getMinutes()).slice(-2);
		// var ss=('0'+dateFormat.getSeconds()).slice(-2);

		// yyyy+"-"+mm+"-"+dd+" "+hh+"-"+ii+"-"+ss
		list_data.push(r);
		// list_data[r.id] = r;
		// console.log(list_data);
	}).on("end", function() {
		console.log(list_data);
		config.YResult.root = list_data;
		res.send(config.YResult);
		// mysqlNative.close();
		// console.log('end mysql Client');
		// resp.render('user', {"list_data":list_data} );
	});

	exports.list1 = {
		"dbname" : "desk",
		"user" : "root",
		"passwd" : "jin111111"
	}

};
