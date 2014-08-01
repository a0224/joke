var mysql = require("mysql-native");

var dbinfo = {"dbname":"desk","user":"root","passwd":"jin111111"};

// create database conenction

var db = mysql.createTCPClient();
// select a database
db.auth(dbinfo.dbname,dbinfo.user,dbinfo.passwd);
db.set('charset' , 'utf8_general_ci');  
db.query("set names utf8");
db.query("set character_set_client=utf8,character_set_connection=utf8");
//db.query("SET character_set_connection=utf8, character_set_results=utf8, character_set_client=binary");
