var jokeServ = require('../service').jokeServ;
var mobileServ = require('../service').mobileServ;
/*
 * GET joke listing.
 */
module.exports = function(app) {
	app.get('/joke', jokeServ.joke);
	app.get('/user', jokeServ.joke);
	app.get('/mobile', mobileServ.service);
	
	app.post('/joke', jokeServ.joke);
	app.post('/user', jokeServ.joke);
	app.post('/mobile', mobileServ.service);

//	app.get('/joke/aa', function(req, res) {
//		res.send(req.query.aa);
//	});
//
//	app.get('/joke/bb', function(req, res) {
//		res.setHeader('Content-Type', 'application/json;charset=utf-8'); 
//		res.send(req.body.aa);
//	});
};