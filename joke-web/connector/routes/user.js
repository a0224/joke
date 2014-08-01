/*
 * GET users listing.
 */
module.exports = function(app){
    app.get('/users', function(req, res){
        res.send("respond with a resource");
    });
    
    app.get(/^\/users?(?:\/(\d+)(?:\.\.(\d+))?)?/, function(req, res){
        res.send(req.params);
    });
};