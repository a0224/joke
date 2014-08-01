var users = require("./user");
var joke = require("./joke");

module.exports = function(app) {
	app.get('/', function(req, res) {
		res.send("index");
	});
	users(app);
	joke(app);
}