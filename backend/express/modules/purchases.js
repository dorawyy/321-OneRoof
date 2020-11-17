const BadRequestError = require("./errors/BadRequestError");
const ForbiddenError = require("./errors/ForbiddenError");

var knex = require("../db");
var houses = require("./houses");
var roommates = require("./roommates");

var purchases = purchases || {};

purchases.getPurchases = async function (uid, houseId) {
    if (!(await houses.validateHouseId(houseId))) {
        throw new BadRequestError("house id " + houseId + " not found");
    }

    if (!(await roommates.checkIfUserIsInHouse(uid, houseId))) {
        throw new ForbiddenError("requester is not in house " + houseId);
    }

    var purchases = await knex("purchases")
        .join("roommates", "roommate_id", "=", "purchase_roommate")
        .join("houses", "house_id", "=", "roommate_house")
        .where("house_id", houseId)
        .select("purchase_id", "purchase_roommate", 
                "purchase_amount", "purchase_memo",
                "roommate_name");

    return purchases.map(p => {
        return {
            id: p.purchase_id,
            purchaser: p.purchase_roommate,
            purchaser_name: p.roommate_name,
            amount: p.purchase_amount,
            memo: p.purchase_memo,
        };
    });

}

function getPurchase(purchaseId) {

}

module.exports = purchases;