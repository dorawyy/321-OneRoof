var knex = require("../db");
var roommates = require("./roommates");

var houses = houses || {};

/* Returns id */
houses.addHouse = async function (name, uid) {
    if (!(typeof name === "string")) {
        throw new Error("name is not a string");
    }

    var roommateId;

    try {
        roommateId = await roommates.getRoommateId(uid);
    } catch (error) {
        throw new Error("requester not found");
    }

    var response = await knex("houses")
        .insert({house_name: name, house_admin: roommateId}, ["id"]);

    console.log(houses);
    var id = response[0];
    await roommates.setHouse(roommateId, id);
    
    return id;
}

houses.getHouse = async function (houseId) {

}

houses.validateHouseId = async function (houseId) {
    var response = await knex.select().table("houses").where({house_id: houseId});
    return response.length > 0;
}

module.exports = houses;
