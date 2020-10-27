var express = require('express');
var router = express.Router();
const knex = require('../db');
var auth = require('../auth');

router.get('/version', function (req, res) {
    res.send({version: "0.3"});
});

router.use('/login', auth.authMiddleware);
router.post('/login', async function(req, res) {
  console.log('main login');
  var fcm = req.body.fcm;
  console.log('fcm');
  console.log(fcm);
  var uid = res.locals.user.uid;

  var roommate = await knex.select('roommate_id')
        .from('roommates')
        .where('roommate_uid', uid);

  var roommate_id;
  if (roommate.length == 0){
    roommate_id = await knex('roommates')
        .insert({roommate_name: res.locals.user.name, roommate_uid: uid, roommate_house: 1});
  }
  else{
    roommate_id = roommate[0]['roommate_id']
  }
  await knex('tokens')
        .insert({token: fcm, roommate_id: roommate_id});

  res.json({id: roommate_id});
});

router.get('/', function(req, res, next) {
  res.send('Welcome to the One Roof API!');
});

router.post('/receipt-ocr', function(req, res) {
  res.send('Upload receipt and return total');
})

module.exports = router;
