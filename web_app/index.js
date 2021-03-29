const path = require('path');
var express = require('express');

let app = express();
var port = process.env.PORT || 8080;

app.use(express.urlencoded());
app.use(express.json());

var messagesPhone = [];
var messagesComputer = [];

app.get('/', function(req,res) {
    console.log("Visitor arrived");
    res.sendFile(path.join(__dirname+'/static/index.html'));
});

app.get('/static/bundle.js', function(req,res) {
    console.log("Visitor wants js file");
    res.sendFile(path.join(__dirname+'/static/bundle.js'));
});

app.get('/register', function(req,res) {
    console.log("Visitor arrived");
    res.sendFile(path.join(__dirname+'/static/register.html'));
});

app.get('/assets/google-brands.svg', function(req,res) {
    console.log("Visitor wants image file");
    res.sendFile(path.join(__dirname+'/assets/google-brands.svg'));
});

app.get('/sendToPhone/:msg', function(req, res) {
    res.send("ok");
    messagesPhone.push(req.params.msg)
    console.log("New message for phone: " + req.params.msg)
});

app.get('/sendToComputer/:msg', function(req, res) {
    res.send("ok");
    messagesComputer.push(req.params.msg)
    console.log("New message for computer: " + messagesComputer)
});

app.get('/retriveMessageForPhone', function(req, res) {
    if (messagesPhone != "") {
        res.send(messagesPhone);
        console.log("Phone retrived messages: " + messagesPhone)
        messagesPhone = []
    } else res.send("");
});

app.get('/retriveMessageForComputer', function(req, res) {
    if (messagesComputer != "") {
        res.send(messagesComputer);
        console.log("Computer retrived messages: " + messagesComputer)
        messagesComputer = []
    } else res.send("");
});

app.listen(port, function () {
    console.log("Running RestHub on port " + port);
});

