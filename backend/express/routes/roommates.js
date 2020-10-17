var express = require('express');
var router = express.Router();

router.post('/', function(req, res) {
    res.send('Add new roommate');
});

router.get('/:roommateId', function(req, res) {
    res.send('Get roommate ' + req.params['roommateId']);
});

router.delete('/:roommateId', function(req, res) {
    res.send('Delete roommate ' + req.params['roommateId']);
});

router.get(':roommateId/avatar', function(req, res) {
    res.send('Get avatar for roommate ' + req.params['roommateId']);
})

module.exports = router;