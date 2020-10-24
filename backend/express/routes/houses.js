var express = require('express');
var router = express.Router();

router.post('/', function(req, res) {
  res.send('Add house');
});

router.delete('/', function(req, res) {
    res.send('Delete house');
});

router.get('/:houseId', function(req, res) {
    res.send('Get house ' + req.params['houseId']);
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