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

router.delete('/:youowemeId', function(req, res) {
    res.send('Delete youoweme ' + req.params['youowemeId']);
});

module.exports = router;