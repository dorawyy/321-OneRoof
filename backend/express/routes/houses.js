var express = require("express");
var auth = require("../auth");
var router = express.Router();
var knex = require("../db");
var lodash = require("lodash");
var debtCalculator = require("../debt_calculator");
var admin = require("firebase-admin");
var houses = require("../modules/houses");

router.use(auth.authMiddleware);

router.post("/", async function(req, res) {
    try {
        const id = await houses.addHouse(req.body.name, res.locals.user.uid);
        res.json({id: id});
    } catch (error) {
        console.log(error);
        res.status(400).send(error.message);
    }
});

router.delete("/:houseId", async function(req, res) {
    const houseId = req.params["houseId"];

    var rowsDeleted = await knex("houses")
        .where("house_id", houseId)
        .del();
        
    res.json({"rows deleted": rowsDeleted});
});

router.get("/:houseId", async function(req, res) {
    const houseId = req.params["houseId"];
    let house = {};
    house["id"] = houseId;

    var houseAttributes = await knex.select("house_name", "house_admin")
        .from("houses")
        .where("house_id", houseId);

    house["name"] = houseAttributes[0]["house_name"];
    house["admin"] = houseAttributes[0]["house_admin"];
    
    var roommates = await knex.select("roommate_id", "roommate_name")
        .from("roommates")
        .where("roommate_house", houseId);

    house["roommates"] = roommates.map(r => r.roommate_id);
    house["roommate_names"] = roommates.map(r => r.roommate_name);
    console.log(house);
    res.json(house);
});

router.get("/:houseId/purchases", async function(req, res) {
    var houseId = req.params["houseId"];

    var purchases = await knex("purchases")
        .join("roommates", "roommate_id", "=", "purchase_roommate")
        .join("houses", "house_id", "=", "roommate_house")
        .where("house_id", houseId)
        .select("purchase_id", "purchase_roommate", 
                "purchase_amount", "purchase_memo",
                "roommate_name");

    purchases = purchases.map(p => {
        return {
            id: p.purchase_id,
            purchaser: p.purchase_roommate,
            purchaser_name: p.roommate_name,
            amount: p.purchase_amount,
            memo: p.purchase_memo,
        };
    });
    purchases.reverse();

    res.json(purchases);
});

router.post("/:houseId/purchases", async function(req, res) {
    var purchaser = req.body.purchaser;
    var amount = req.body.amount;
    var memo = req.body.memo;
    var divisions = req.body.divisions;

    console.log(req.body)

    var purchaseId = await knex("purchases")
        .insert({purchase_amount: amount, 
        purchase_memo: memo, purchase_roommate: purchaser});

    var allRoommates = new Set();
    for (division of divisions) {
        var divisionAmount = division["amount"];
        var divisionMemo = division["memo"];
        var roommates = division["roommates"];

        console.log(roommates);
        roommates.forEach(r => allRoommates.add(r));

        var divisionId = await knex("divisions")
            .insert({division_purchase: purchaseId, 
                division_amount: divisionAmount,
                division_memo: divisionMemo});

        for (roommateId of roommates) {
            await knex("division_roommate_join")
                .insert({division_roommate_join_division: divisionId[0],
                division_roommate_join_roommate: roommateId});
        }
    }

    var purchaserName = await knex("roommates")
        .where("roommate_id", purchaser)
        .first();

    var tokens = [];
    for (mentioned of allRoommates) {
        var fcmToken = await knex.select("token")
            .from("tokens")
            .where("roommate_id", mentioned);
            console.log(fcmToken);
        for (token of fcmToken) {
            tokens.push(token.token);
        }
    }

    const message = {
        notification: {
            title: `Purchase by ${purchaserName.roommate_name} ($${Math.floor(amount / 100)}.${amount % 100})`,
            body: `You share the cost of this purchase: ${memo}`
        },
        tokens: tokens,
      };
    
    if (tokens.length > 0) {
        let result = await admin.messaging().sendMulticast(message);
        console.log(result);
    }
    
    res.json({id: purchaseId[0]});
});

router.get("/:houseId/purchases/:purchaseId", async function(req, res) {
    var purchaseId = req.params["purchaseId"];

    var purchase = await knex.select("purchase_roommate", 
        "purchase_amount", "purchase_memo",
        "roommate_name")
        .from("purchases")
        .join("roommates", "purchase_roommate", "=", "roommate_id")
        .where("purchase_id", purchaseId);

    var divisions = await knex.select("division_id", "division_amount", 
        "division_memo", "division_roommate_join_roommate",
        "roommate_name")
        .from("divisions")
        .join("division_roommate_join", "division_roommate_join_division", "=", "division_id")
        .join("roommates", "division_roommate_join_roommate", "=", "roommate_id")
        .where("division_purchase", purchaseId);

    var groupedDivisions = lodash.groupBy(divisions, "division_id");
    console.log(groupedDivisions);
    var divisionsList = new Array();
    for (const [_, group] of Object.entries(groupedDivisions)) {
        var roommates = new Array();
        group.forEach(roommate => {
            roommates.push(roommate["division_roommate_join_roommate"]);
        });
        var roommate_names = group.map(d => d.roommate_name);
        divisionsList.push({amount: group[0]["division_amount"], memo: group[0]["division_memo"], roommates: roommates, roommate_names});
    }

    res.json({roommate: purchase[0]["purchase_roommate"], 
        roommate_name: purchase[0]["roommate_name"],
        amount: purchase[0]["purchase_amount"], 
        divisions: divisionsList, 
        memo: purchase[0]["purchase_memo"]});
});

router.delete("/:houseId/purchases/:purchaseId", async function(req, res) {
    const purchaseId = req.params["purchaseId"];

    var rowsDeleted = await knex("purchases")
        .where("purchase_id", purchaseId)
        .del();
        
    res.json({"rows deleted": rowsDeleted});
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
    var roommateId = req.params["roommateId"];
    if (roommateId === 1) {
        res.json({2: -1250});
    } else if (roommateId === 2) {
        res.json({1: 1250});
    } else {
        res.json({});
    }
});

router.get("/:houseId/debts/:userRoommateId/:otherRoommateId", async function(req, res) {
    res.send("Get all debts for roommates " + req.params["userRoommateId"] + " and " + req.params["otherRoommateId"] + " from house " + req.params["houseId"]);
});

module.exports = router;
