var lodash = require("lodash");

var debtCalculator = debtCalculator || {};

debtCalculator.getPurchaseInfo = async function (knex, purchase) {
    var divisions = await knex.select("division_id", "division_amount", 
        "division_memo", "division_roommate_join_roommate")
        .from("divisions")
        .join("division_roommate_join", "division_roommate_join_division", "=", "division_id")
        .where("division_purchase", purchase["purchase_id"]);

    var groupedDivisions = lodash.groupBy(divisions, "division_id");
    var divisionsList = new Array();

    for (const [_, group] of Object.entries(groupedDivisions)) {
        var roommates = new Array();
        group.forEach(roommate => {
            roommates.push(roommate["division_roommate_join_roommate"]);
        });
        divisionsList.push({amount: group[0]["division_amount"], memo: group[0]["division_memo"], roommates: roommates});
    }

    return {roommate: purchase["purchase_roommate"], 
        amount: purchase["purchase_amount"], 
        divisions: divisionsList, 
        memo: purchase["purchase_memo"]};
}

debtCalculator.getAllRoommatePairs = async function (knex, houseId) {
    var roommates = await knex.select()
        .table("roommates")
        .where("roommate_house", houseId);

    var pairs = new Array();

    for (let i = 0; i < roommates.length; i++) {
        for (let j = i + 1; j < roommates.length; j++) {
            pairs.push({roommate1: roommates[i]["roommate_id"], 
                roommate2: roommates[j]["roommate_id"]});
        }
    }

    return pairs;
}

debtCalculator.getAllDebts = async function (knex, houseId) {
    var allPurchases = await knex.select("purchase_id", "purchase_roommate", "purchase_amount")
        .table("purchases")
        .join("roommates", "roommate_id", "=", "purchase_roommate")
        .join("houses", "roommate_house", "=", "house_id")
        .where("house_id", houseId);

    var purchasesInfo = new Array();

    for (purchase of allPurchases) {
        purchasesInfo.push(await this.getPurchaseInfo(knex, purchase));
    }

    var debts = new Array();

    for (purchaseInfo of purchasesInfo) {
        var purchaser = purchaseInfo["roommate"];

        for (division of purchaseInfo["divisions"]) {
            var numRoommates = division["roommates"].length;
            var amountPerRoommate = division["amount"] / numRoommates;
            for (roommate of division["roommates"]) {
                if (roommate != purchaser) {
                    debts.push({payee: purchaser, payer: roommate, amount: amountPerRoommate});
                }
            }
        }
    }

    return debts;
}

debtCalculator.getTotalSpent = async function(knex, roommateId) {
    var allPurchases = await knex.select("purchase_id", "purchase_roommate", "purchase_amount")
        .table("purchases")
        .join("roommates", "roommate_id", "=", "purchase_roommate")
        .join("houses", "roommate_house", "=", "house_id");

    var purchasesInfo = new Array();

    for (purchase of allPurchases) {
        purchasesInfo.push(await this.getPurchaseInfo(knex, purchase));
    }

    var purchase_amounts = [];

    for (purchaseInfo of purchasesInfo) {
        for (division of purchaseInfo["divisions"]) {
            var numRoommates = division["roommates"].length;
            var amountPerRoommate = division["amount"] / numRoommates;
            for (roommate of division["roommates"]) {
                if (roommate == roommateId) {
                    purchase_amounts.push(amountPerRoommate);
                }
            }
        }
    }

    return purchase_amounts;
}

module.exports = debtCalculator;



