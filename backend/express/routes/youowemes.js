var express = require('express');
var auth = require('../auth');
var router = express.Router();
var admin = require('firebase-admin');

router.use(auth.authMiddleware);

router.post('/', async function(req, res) {
    var memo = req.body.memo;
    var target = req.body.target;
    var amount = req.body.amount;
    var status = req.body.status;
    var topic = 'youoweme';

    var registrationTokens = await knex.select('token')
        .from('tokens')
        .where('roommate_id', target);

    const message = {
        notification:{
            title:"youoweme",
            body: memo
        },
        tokens: registrationTokens,
        topic : topic
    }

    var id = await knex('youowemes')
        .insert({youoweme_you: target,
            youoweme_me: 0,
            youoweme_amount: amount,
            youoweme_create_date: new Date()
        });

    admin.messaging().sendMulticast(message)
    .then((response) => {
        console.log(response.successCount + ' messages were sent successfully')
    });
    res.json({id: id[0]});
});

router.patch('/:youowemeId', function(req, res) {
    res.send('Update youoweme ' + req.params['youowemeId']);
});

router.delete('/:youowemeId', function(req, res) {
    res.send('Delete youoweme ' + req.params['youowemeId']);
});

module.exports = router;