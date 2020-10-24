var express = require('express');
var router = express.Router();
var knex = require('../db');

router.post('/', async function(req, res) {
    const name = req.body.name;
    const admin = req.body.admin;

    var id = await knex('houses')
        .insert({house_name: name, house_admin: admin}, ['id']);
    res.json({id: id[0]});
});

router.delete('/:houseId', async function(req, res) {
    const houseId = req.params['houseId'];

    var rowsDeleted = await knex('houses')
        .where('house_id', houseId)
        .del();
        
    res.json({'rows deleted': rowsDeleted});
});

router.get('/:houseId', async function(req, res) {
    const houseId = req.params['houseId'];
    let house = {};
    house['id'] = houseId;

    var houseAttributes = await knex.select('house_name', 'house_admin')
        .from('houses')
        .where('house_id', houseId);

    house['name'] = houseAttributes[0]['house_name'];
    house['admin'] = houseAttributes[0]['house_admin'];
    
    var roommates = await knex.select('roommate_id')
        .from('roommates')
        .where('roommate_house', houseId);
    
    house['roommates'] = roommates;
    res.json(house);
});

router.get('/:houseId/purchases', function(req, res) {
    res.send('Get puchases for house ' + req.params['houseId']);
});

router.post('/:houseId/purchases', function(req, res) {
    res.send('Add purchases to house ' + req.params['houseId']);
});

router.get('/:houseId/purchases/:purchaseId', function(req, res) {
    res.send('Get purchase ' + req.params['purchaseId'] + ' from house ' + req.params['houseId']);
});

router.delete('/:houseId/purchases/:purchaseId', function(req, res) {
    res.send('Delete purchase ' + req.params['purchaseId'] + ' from house ' + req.params['houseId']);
});

router.patch('/:houseId/purchases/:purchaseId', function(req, res) {
    res.send('Patch purchase ' + req.params['purchaseId'] + ' from house ' + req.params['houseId']);
});

router.post('/:houseId/purchases/:purchaseId/receipt', function(req, res) {
    res.send('Add receipt for purchase ' + req.params['purchaseId'] + ' from house ' + req.params['houseId']);
});

router.get('/:houseId/statistics/:roommateId', function(req, res) {
    res.send('Get statistics for roommate ' + req.params['roommateId'] + ' in house ' + req.params['houseId']);
});

router.get('/:houseId/debts/:roommateId', function(req, res) {
    res.send('Get all debts for roommate ' + req.params['roommateId'] + 'from house ' + req.params['houseId']);
});

router.get('/:houseId/debts/:userRoommateId/:otherRoommateId', function(req, res) {
    res.send('Get all debts for roommates ' + req.params['userRoommateId'] + ' and ' + req.params['otherRoommateId'] + ' from house ' + req.params['houseId']);
});

module.exports = router;