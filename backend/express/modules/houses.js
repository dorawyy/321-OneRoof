const BadRequestError = require("./errors/BadRequestError");
const ForbiddenError = require("./errors/ForbiddenError");

var knex = require("../db");
var roommates = require("./roommates");

var houses = houses || {};

/* Returns id */
houses.addHouse = async function (name, uid) {
    if (!(typeof name === "string")) {
        throw new BadRequestError("name is not a string");
    }

    var roommateId = await roommates.getRoommateId(uid);
    var id;

    try {
        var response = await knex("houses")
            .insert({house_name: name, house_admin: roommateId}, ["id"]);
        id = response[0];
    } catch (error) {
        throw new Error("Failed to insert house \n" + error);
    }

    await roommates.setHouseOfOwner(uid, id);
    
    return id;
}

houses.deleteHouse = async function (houseId, uid) {
    if (!(this.validateHouseId(houseId))) {
        throw new BadRequestError("house id is found");
    }

    var roommate;

    try {
        roommate = await roommates.getRoommateFromUid(uid);
    } catch (error) {
        throw new BadRequestError("requester not found");
    }

    if (roommate.house != houseId || roommate.permissions !== "owner") {
        throw new ForbiddenError("requester is not the house owner");
    }

    var rowsDeleted = await knex("houses")
        .where("house_id", houseId)
        .del();
        
    return rowsDeleted;
}

houses.getHouse = async function (houseId) {
    let house = {};
    house["id"] = houseId;

    var houseAttributes = await knex.select("house_name", "house_admin")
        .from("houses")
        .where("house_id", houseId);

    if (houseAttributes.length === 0) {
        throw new BadRequestError("house id " + houseId + " not found");
    }

    house["name"] = houseAttributes[0]["house_name"];
    house["admin"] = houseAttributes[0]["house_admin"];
    
    var roommates = await knex.select("roommate_id", "roommate_name")
        .from("roommates")
        .where("roommate_house", houseId);

    house["roommates"] = roommates.map(r => r.roommate_id);
    house["roommate_names"] = roommates.map(r => r.roommate_name);
    
    return house;
}

houses.validateHouseId = async function (houseId) {
    var response = await knex.select().table("houses").where({house_id: houseId});
    return response.length > 0;
}

module.exports = houses;
