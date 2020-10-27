var express = require('express');
var router = express.Router();
var knex = require('../db');
var lodash = require('lodash');
var debtCalculator = require('../debt_calculator');

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

router.get('/:houseId/purchases', async function(req, res) {
    var houseId = req.params['houseId'];

    var purchases = await knex.select('purchase_roommate', 
        'purchase_amount', 'purchase_memo')
        .from('purchases')
        .join('roommates', 'roommate_id', '=', 'purchase_roommate')
        .join('houses', 'house_id', '=', 'roommate_house')
        .where('house_id', houseId);

    var returnPurchases = purchases.map((purchase) => { 
        return {memo: purchase['purchase_memo'], 
            purchaser: purchase['purchase_roommate'],
            amount: purchase['purchase_amount']}});

    res.json(returnPurchases);
});

router.post('/:houseId/purchases', async function(req, res) {
    var roommate = req.body.roommate;
    var amount = req.body.amount;
    var memo = req.body.memo;
    var divisions = req.body.divisions;

    var purchaseId = await knex('purchases')
        .insert({purchase_roommate: roommate, purchase_amount: amount, 
        purchase_memo: memo});

    for (division of divisions) {
        var divisionAmount = division['amount'];
        var divisionMemo = division['memo'];
        var roommates = division['roommates'];

        var divisionId = await knex('divisions')
            .insert({division_purchase: purchaseId, 
                division_amount: divisionAmount,
                division_memo: divisionMemo});

        for (roommateId of roommates) {
            await knex('division_roommate_join')
                .insert({division_roommate_join_division: divisionId[0],
                division_roommate_join_roommate: roommateId});
        }
    }

    res.json({id: purchaseId[0]});
});

router.get('/:houseId/purchases/:purchaseId', async function(req, res) {
    var purchaseId = req.params['purchaseId'];

    var purchase = await knex.select('purchase_roommate', 
        'purchase_amount', 'purchase_memo')
        .from('purchases')
        .where('purchase_id', purchaseId);

    var divisions = await knex.select('division_id', 'division_amount', 
        'division_memo', 'division_roommate_join_roommate')
        .from('divisions')
        .join('division_roommate_join', 'division_roommate_join_division', '=', 'division_id')
        .where('division_purchase', purchaseId);

    var groupedDivisions = lodash.groupBy(divisions, 'division_id');
    console.log(groupedDivisions);
    var divisionsList = new Array();
    for (const [_, group] of Object.entries(groupedDivisions)) {
        var roommates = new Array();
        group.forEach(roommate => {
            roommates.push(roommate['division_roommate_join_roommate']);
        });
        divisionsList.push({amount: group[0]['division_amount'], memo: group[0]['division_memo'], roommates: roommates});
    }

    res.json({roommate: purchase[0]['purchase_roommate'], 
        amount: purchase[0]['purchase_amount'], 
        divisions: divisionsList, 
        memo: purchase[0]['purchase_memo']});
});

router.delete('/:houseId/purchases/:purchaseId', async function(req, res) {
    const purchaseId = req.params['purchaseId'];

    var rowsDeleted = await knex('purchases')
        .where('purchase_id', purchaseId)
        .del();
        
    res.json({'rows deleted': rowsDeleted});
});

router.patch('/:houseId/purchases/:purchaseId', async function(req, res) {
    res.send('Patch purchase ' + req.params['purchaseId'] + ' from house ' + req.params['houseId']);
});

router.post('/:houseId/purchases/:purchaseId/receipt', function(req, res) {
    res.send('Add receipt for purchase ' + req.params['purchaseId'] + ' from house ' + req.params['houseId']);
});

router.get('/:houseId/statistics/:roommateId', async function(req, res) {
    var houseId = req.params['houseId'];
    var roommateId = req.params['roommateId'];

    var allDebts = await debtCalculator.getAllDebts(knex, houseId);
    var you_owe = 0;
    var you_are_owed = 0;

    for (debt of allDebts) {
        if (debt['payer'] == roommateId) {
            you_owe += debt['amount'];
        } else if (debt['payee'] == roommateId) {
            you_are_owed += debt['amount'];
        }
    }

    res.json({you_owe: you_owe, you_are_owed: you_are_owed});
});

router.get('/:houseId/debts/:roommateId', async function(req, res) {
    res.send('Get all debts for roommate ' + req.params['roommateId'] + 'from house ' + req.params['houseId']);
});

router.get('/:houseId/debts/:userRoommateId/:otherRoommateId', async function(req, res) {
    res.send('Get all debts for roommates ' + req.params['userRoommateId'] + ' and ' + req.params['otherRoommateId'] + ' from house ' + req.params['houseId']);
});

module.exports = router;