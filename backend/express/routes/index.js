var express = require('express');
var router = express.Router();

router.get('/', function(req, res, next) {
  res.send('Welcome to the One Roof API!');
});

router.post('/receipt-ocr', function(req, res) {
  res.send('Upload receipt and return total');
})

module.exports = router;
