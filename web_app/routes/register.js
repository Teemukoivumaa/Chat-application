var express = require('express');
var router = express.Router();

//Middle ware that is specific to this router
router.use(function timeLog(req, res, next) {
  console.log('Time: ', Date.now());
  next();
});

router.get('/', function(req, res) {
    console.log("Visitor arrived");
    var path = __dirname
    path = path.replace('routes', 'static/register.html')

    res.sendFile(path);
});

router.get('/sendData', function(req, res) {
    var newUser = require('./firebase')
    newUser.registerUser('Testi', 'testi@gmail.com', 'testi');
    res.send()
});

module.exports = router;
