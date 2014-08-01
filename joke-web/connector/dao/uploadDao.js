var config = require('../utils/config');
var mysql = require('../utils').mysql;
var fs = require('fs');
var path = require('path');
var gm = require('gm'), fs = require('fs'), imageMagick = gm.subClass({
	imageMagick : true
});
var im = require("imagemagick");

/**
 * upfile
 */
exports.upfile = function(req, callback) {
	var conn = mysql.connection();
	var list_data = new Array();
	var YResult = {};

	// if (req.files) {
	// for ( var p in req.files) {
	// console.log('req.files[p]---' + JSON.stringify(req.files[p]));
	// console.log('p---' + JSON.stringify(p));
	// }
	// }

	if (!req.files) {
		YResult.status = -1;
		YResult.message = 'files error';
		callback(YResult);
		return;
	}

	var finame = config.finame();
	var extname = path.extname(req.files.thumbnail.name);
	var dtpath = config.dtpath();
	// 获得文件的临时路径
	var tmp_path = req.files.thumbnail.path;
	// 指定文件上传后的目录 - 示例为"images"目录。
	var target_path = config.path.imuppath + dtpath;
	var target_file = target_path + finame + extname;
	var target_file_mic = target_path + finame + "_mic" + extname;
	var reurl = "/images/" + config.dtpath() + finame + extname;

	console.log(tmp_path + "//" + target_path + "//" + target_file);

	// 创建文件夹
	mkdirs(target_path, "777", function(err) {
		if (!err) {
			console.log(err);
			YResult.status = -1;
			YResult.message = 'error';
			callback(YResult);
			return;
		}

		// 移动文件
		fs.rename(tmp_path, target_file, function(err) {
			if (err) {
				console.log(err);
				YResult.status = -1;
				YResult.message = 'error';
				callback(YResult);
				return;
			}

			// 删除临时文件夹文件,
			fs.unlink(tmp_path, function() {

				if (err) {
					console.log(err);
					YResult.status = -1;
					YResult.message = 'error';
					callback(YResult);
					return;
				} else {
					
					var param = {};
					param.srcPath = path.normalize(target_file);
					// var bf = new Buffer(req.body.imgData, "base64");
					// param.srcData = bf;
					param.dstPath = path.normalize(target_file_mic);
					param.width = 100;
					param.height = 100;
					im.resize(param, function(err, stdout, stderr) {
						if (err) {
							console.log(err);
							console.log(stdout);
							console.log(stderr);
							YResult.status = -1;
							YResult.message = 'error';
							callback(YResult);
							return;
						} else {
							YResult.status = 1;
							YResult.url = reurl;
							YResult.message = 'success';
							callback(YResult);
							return;
						}
					});

					imageMagick(target_file).resize(150, 150, '!') // 加('!')强行把图片缩放成对应尺寸150*150！
					.autoOrient().write(target_file_mic, function(err) {
						if (err) {
							console.log(err);
							YResult.status = -1;
							YResult.message = 'error';
							callback(YResult);
							return;
						} else {
							YResult.status = 1;
							YResult.url = reurl;
							YResult.message = 'success';
							callback(YResult);
							return;
						}
					});
				}

			});
		});
	});

};

// 创建多层文件夹 异步
function mkdirs(dirpath, mode, callback) {
	dirpath = path.normalize(dirpath);
	callback = callback || function() {
	};

	fs.exists(dirpath, function(exitsmain) {
		if (!exitsmain) {
			// 目录不存在
			var pathtmp;
			var pathlist = dirpath.split(path.sep);
			var pathlistlength = pathlist.length;
			var pathlistlengthseed = 0;

			mkdir_auto_next(mode, pathlist, pathlist.length, function(
					callresult) {
				if (callresult) {
					callback(true);
				} else {
					callback(false);
				}
			});

		} else {
			callback(true);
		}

	});
}

// 异步文件夹创建 递归方法
function mkdir_auto_next(mode, pathlist, pathlistlength, callback,
		pathlistlengthseed, pathtmp) {
	callback = callback || function() {
	};
	// console.log("mode ---" + mode);
	// console.log("pathlist ---" + pathlist);
	// console.log("pathlistlength ---" + pathlistlength);
	// console.log("pathlistlengthseed ---" + pathlistlengthseed);
	// console.log("pathtmp ---" + pathtmp);

	if (pathlistlength > 0) {

		if (!pathlistlengthseed) {
			pathlistlengthseed = 0;
		}

		if (pathlistlengthseed >= pathlistlength) {
			callback(true);
		} else {

			if (pathtmp) {
				pathtmp = path.join(pathtmp, pathlist[pathlistlengthseed]);
			} else {
				pathtmp = pathlist[pathlistlengthseed];
			}

			fs.exists(pathtmp, function(exists) {
				if (!exists) {
					fs.mkdir(pathtmp, mode, function(isok) {
						if (!isok) {
							mkdir_auto_next(mode, pathlist, pathlistlength,
									function(callresult) {
										callback(callresult);
									}, pathlistlengthseed + 1, pathtmp);
						} else {
							callback(false);
						}
					});
				} else {
					mkdir_auto_next(mode, pathlist, pathlistlength, function(
							callresult) {
						callback(callresult);
					}, pathlistlengthseed + 1, pathtmp);
				}
			});

		}

	} else {
		callback(true);
	}

}

/**
 * upversion
 */
exports.upversion = function(parameter, callback) {
	var conn = mysql.connection();
	var list_data = new Array();
	var YResult = {};

	var sql = "select j.id,j.vname,j.vcode,j.url,j.descrip from tb_app_version j where j.status = 1 ";

	var sqljokecount = "select count(j.id) AS count  " + "from tb_app_version j "
			+ "where j.status = 1 ";

	if (parameter.type) {
		sql = sql + " AND j.type = " + parameter.type
	}

	sql = sql + " order by j.vcode desc";
	sql = sql + " LIMIT " + 0 + "," + 1;

	console.log(sql + "//" + sqljokecount + "//");

	conn.query(sqljokecount).on("row", function(r) {
		// list_data.push(r);
	}).on("end", function() {
		if(this.result.rows[0]["count"]>=1){
			conn.query(sql).on("row", function(r) {
				YResult.id = r.id;
				YResult.url = config.isAddResUrl(r.url);
				YResult.vname = r.vname;
				YResult.vcode = r.vcode;
				YResult.descrip = r.descrip;
			}).on("end", function() {
				YResult.status = 1;
				callback(YResult);
			}).on('error', function(e) {
				console.log(e);
				YResult.status = -1;
				YResult.message = 'error';
				callback(YResult);
			});
		}else{
			console.log(e);
			YResult.status = -1;
			YResult.message = 'error';
			callback(YResult);
		}
		
	}).on('error', function(e) {
		console.log(e);
		YResult.status = -1;
		YResult.message = 'error';
		callback(YResult);
	});

};
