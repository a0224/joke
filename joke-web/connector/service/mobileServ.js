var config = require('../utils/config');
var mobileDao = require('../dao').mobileDao;

/**
 * 
 */
exports.service = function(req, res) {
	var parameter = config.parameter(req);
	console.log('op---' + parameter.op);
	console.log('parameter---' + JSON.stringify(parameter));

	if (!parameter.op) {
		res.send("op error!");
		return;
	}

	if (parameter.op == "search") {
		mobileDao.search(parameter, function callback(result) {
			res.send(JSON.stringify(result));
		});

	}else {
		res.send("op error!");
	}

};
