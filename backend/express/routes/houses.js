var express = require("express");
var auth = require("../auth");
var router = express.Router();
var knex = require("../db");

var debtCalculator = require("../debt_calculator");
var admin = require("firebase-admin");
var houses = require("../modules/houses");
var purchases = require("../modules/purchases");

router.use(auth.authMiddleware);

router.post("/", async function(req, res) {
    console.log("Uid: ", res.locals.user.uid);
    try {
        const id = await houses.addHouse(req.body.name, res.locals.user.uid);
        res.json({id: id});
    } catch (error) {
        console.log(error);
        res.status(error.status || 500).send(error.message);
    }
});

router.delete("/:houseId", async function(req, res) {
    try {
        var rowsDeleted = await houses.deleteHouse(req.params["houseId"],
            res.locals.user.uid);
        res.json({"rows deleted": rowsDeleted});
    } catch (error) {
        console.log(error);
        res.status(error.status || 500).send(error.message);
    }
});

router.get("/:houseId", async function(req, res) {
    try {
        res.json(await houses.getHouse(req.params["houseId"],
            res.locals.user.uid));
    } catch (error) {
        console.log(error);
        res.status(error.status || 500).send(error.message);
    }
});

router.get("/:houseId/purchases", async function(req, res) {
    try {
        res.json(await purchases.getPurchases(res.locals.user.uid,
            req.params["houseId"]));
    } catch (error) {
        console.log(error);
        res.status(error.status || 500).send(error.message);
    }
});

router.post("/:houseId/purchases", async function(req, res) {
    var purchaser = req.body.purchaser;
    var amount = req.body.amount;
    var memo = req.body.memo;
    var divisions = req.body.divisions;

    try {
        var id = await purchases.addPurchase(purchaser, amount, memo, divisions);
        res.json({id: id});
    } catch (error) {
        console.log(error);
        res.status(error.status || 500).send(error.message);
    }
});

router.get("/:houseId/purchases/:purchaseId", async function(req, res) {
    try {
        res.json(await purchases.getPurchase(req.params["purchaseId"]));
    } catch (error) {
        console.log(error);
        res.status(error.status || 500).send(error.message);
    }
});

router.delete("/:houseId/purchases/:purchaseId", async function(req, res) {
    try {
        var rowsDeleted = await purchases.deletePurchase(req.params["purchaseId"]);
        res.json({"rows deleted": rowsDeleted});
    } catch (error) {
        console.log(error);
        res.status(error.status || 500).send(error.message);
    }
});

router.patch("/:houseId/purchases/:purchaseId", async function(req, res) {
    res.send("Patch purchase " + req.params["purchaseId"] + " from house " + req.params["houseId"]);
});

router.post("/:houseId/purchases/:purchaseId/receipt", function(req, res) {
    res.send("Add receipt for purchase " + req.params["purchaseId"] + " from house " + req.params["houseId"]);
});

router.get("/:houseId/statistics/:roommateId", async function(req, res) {
    var houseId = req.params["houseId"];
    var roommateId = req.params["roommateId"];

    var allDebts = await debtCalculator.getAllDebts(knex, houseId);
    var you_owe = 0;
    var you_are_owed = 0;

    for (debt of allDebts) {
        if (debt["payer"] == roommateId) {
            you_owe += debt["amount"];
        } else if (debt["payee"] == roommateId) {
            you_are_owed += debt["amount"];
        }
    }

    res.json({you_owe: you_owe, you_are_owed: you_are_owed});
});

router.get("/:houseId/debts/:roommateId", async function(req, res) {
    var houseId = req.params["houseId"];
    var roommateId = req.params["roommateId"];

    var allDebts = await debtCalculator.getAllDebts(knex, houseId);
    var house = await houses.getHouse(houseId);

    var debts = {};

    for (roommate of house.roommates) {
        if (roommate != roommateId) {
            debts[roommate] = 0;
        }
    }

    for (debt of allDebts) {
        var amount = debt["amount"];

        if (debt["payer"] == roommateId) {
            var roommate = debt["payee"].toString();
            debts[roommate] -= amount;
        } else if (debt["payee"] == roommateId) {
            var roommate = debt["payer"].toString();
            debts[roommate] += amount;
        }
    }

    res.json(debts);
});

router.get("/:houseId/debts/:userRoommateId/:otherRoommateId", async function(req, res) {
    var houseId = req.params["houseId"];

    var userRoommateId = req.params["userRoommateId"];
    var otherRoommateId = req.params["otherRoommateId"];

    var allDebts = await debtCalculator.getAllDebts(knex, houseId);

    var debts = allDebts.filter(d => (d.payee == userRoommateId &&
        d.payer == otherRoommateId) || (d.payee == otherRoommateId &&
        d.payer == userRoommateId));

    var purchases = debts.filter(d => d.type === "purchase")
        .map(d => d.purchase);

    var reimbursements = debts.filter(d => d.type === "payed back")
        .map(d => d.youoweme);

    res.json({purchases: purchases, reimbursements: reimbursements});
});

module.exports = router;
