var express = require('express');
var auth = require('../auth');
var router = express.Router();

router.use(auth.authMiddleware);

router.post('/', function(req, res) {
    res.send('Add new youoweme');
});

router.patch('/:youowemeId', function(req, res) {
    res.send('Update youoweme ' + req.params['youowemeId']);
});

router.delete('/:roommateId', function(req, res) {
    res.send('Delete roommate ' + req.params['roommateId']);
});

router.get(':roommateId/avatar', function(req, res) {
    res.send('Get avatar for roommate ' + req.params['roommateId']);
})

module.exports = router;