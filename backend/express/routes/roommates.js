var express = require('express');
var auth = require('../auth');
const knex = require('../db');
var router = express.Router();

router.use(auth.authMiddleware);

router.post('/', async function(req, res) {
    var name = req.body.name;
    var house = req.body.house;

    var id = await knex('roommates')
        .insert({roommate_name: name, roommate_house: house});
    res.json({id: id[0]});
});

router.get('/:roommateId', async function(req, res) {
    var roommateId = req.params['roommateId'];

    var roommate = await knex.select('roommate_name', 'roommate_house')
        .from('roommates')
        .where('roommate_id', roommateId);

    var house = await knex.select('house_admin')
        .from('houses')
        .where('house_id', roommate[0]['roommate_house']);

    var permissions = house[0]['house_admin'] == roommateId ? 'owner' : 'member';
    res.json({name: roommate[0]['roommate_name'], permissions: permissions})
});

router.delete('/:roommateId', async function(req, res) {
    const roommateId = req.params['roommateId'];

    var rowsDeleted = await knex('roommates')
        .where('roommate_id', roommateId)
        .del();
        
    res.json({'rows deleted': rowsDeleted});
});

router.get(':roommateId/avatar', async function(req, res) {
    res.send('Get avatar for roommate ' + req.params['roommateId']);
})

router.get('/:roommateId/budget', async function(req, res) {
    var roommateId = req.params['roommateId'];

    // get last 30 days from purchases db

    // send back budget json onbject
});

module.exports = router;
