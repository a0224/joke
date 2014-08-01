var config = require('../utils/config');
var jokeDao = require('../dao').jokeDao;
var newsDao = require('../dao').newsDao;
var uploadDao = require('../dao').uploadDao;
var sexDao = require('../dao').sexDao;
var pushDao = require('../dao').pushDao;
var apkDao = require('../dao').apkDao;


/**
 * 
 */
exports.joke = function(req, res) {
	var parameter = config.parameter(req);
	console.log('op---' + parameter.op);
	console.log('parameter---' + JSON.stringify(parameter));

	if (!parameter.op) {
		res.send("op error!");
		return;
	}
	// joke ////////////////////////////////////////////////////////////////////
	if (parameter.op == "activation") {
		jokeDao.activation(req, res, parameter, function callback(result) {
			res.send(JSON.stringify(result));
		});
		return;
	}
	if (parameter.op == "joke") {
		jokeDao.joke(res, parameter, config.res);
		return;
	}
	if (parameter.op == "jokeinfo") {
		jokeDao.jokeinfo(parameter, function callback(result) {
			res.send(JSON.stringify(result));
		});
		return;
	}
	if (parameter.op == "joketalk") {
		jokeDao.joketalk(parameter, function callback(result) {
			res.send(JSON.stringify(result));
		});
		return;
	}
	if (parameter.op == "jokeevaluate") {
		jokeDao.evaluate(parameter, function callback(result) {
			res.send(JSON.stringify(result));
		});
		return;
	}
	if (parameter.op == "jokeaddtalk") {
		jokeDao.jokeaddtalk(parameter, function callback(result) {
			res.send(JSON.stringify(result));
		});
		return;
	}
	if (parameter.op == "jokeaddjoke") {
		jokeDao.jokeaddjoke(parameter, function callback(result) {
			res.send(JSON.stringify(result));
		});
		return;
	}
	if (parameter.op == "jokeofusers") {
		jokeDao.jokeofusers(parameter, function callback(result) {
			res.send(JSON.stringify(result));
		});
		return;
	}

	// login ////////////////////////////////////////////////////////////////
	if (parameter.op == "login") {
		jokeDao.jokelogin(parameter, function callback(result) {
			res.send(JSON.stringify(result));
		});
		return;
	}

	// upload ////////////////////////////////////////////////////////////////
	if (parameter.op == "upfile") {
		uploadDao.upfile(req, function callback(result) {
			res.send(JSON.stringify(result));
		});
		return;
	}

	// news ////////////////////////////////////////////////////////////////////
	if (parameter.op == "newlist") {
		newsDao.newlist(parameter, function callback(result) {
			res.send(JSON.stringify(result));
		});
		return;
	}
	if (parameter.op == "newinfo") {
		newsDao.newinfo(parameter, function callback(result) {
			res.render('index', result);
		});
		return;
	}
	if (parameter.op == "newinfo1") {
		newsDao.newinfo1(req, function callback(result) {
			res.send(JSON.stringify(result));
		});
		return;
	}
	
	// sex ////////////////////////////////////////////////////////////////////

	if (parameter.op == "sexlist") {
		sexDao.sexlist(parameter, function callback(result) {
			res.send(JSON.stringify(result));
		});
		return;
	}
	
	if (parameter.op == "sexinfo") {
		sexDao.sexinfo(parameter, function callback(result) {
			res.render('index', result);
		});
		return;
	}
	
	// push ////////////////////////////////////////////////////////////////////
	if (parameter.op == "pushlist") {
		pushDao.list(parameter, function callback(result) {
			res.send(JSON.stringify(result));
		});
		return;
	}
	
	if (parameter.op == "pushinfo") {
		pushDao.info(parameter, function callback(result) {
			res.render('index', result);
		});
		return;
	}
	
	// apk ////////////////////////////////////////////////////////////////////
	if (parameter.op == "apklist") {
		apkDao.list(parameter, function callback(result) {
			res.send(JSON.stringify(result));
		});
		return;
	}
	
	if (parameter.op == "apkinfo") {
		apkDao.info(parameter, function callback(result) {
			res.render('index', result);
		});
		return;
	}
	
	// version ////////////////////////////////////////////////////////////////
	if (parameter.op == "upversion") {
		uploadDao.upversion(parameter, function callback(result) {
			res.send(JSON.stringify(result));
		});
		return;
	}
	
	res.send("op error!");
	return;

};
