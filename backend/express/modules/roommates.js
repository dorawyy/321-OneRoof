const BadRequestError = require("./errors/BadRequestError");
const ForbiddenError = require("./errors/ForbiddenError");

var knex = require("../db");
// var houses = require("./houses");

var roommates = roommates || {};

roommates.validateRoommateId = async function (roommateId) {
    var response = await knex.select().table("roommates")
        .where({roommate_id: roommateId});
    return response.length > 0;
}

roommates.getRoommateId = async function (uid) {
    var response = await knex.select("roommate_id")
        .from("roommates")
        .where("roommate_uid", uid);

    if (response.length == 0) {
        throw new BadRequestError("uid " + uid + " not found");
    }

    return response[0].roommate_id;
}

roommates.setHouse = async function (roommateId, uid) {
    if (!(await this.validateRoommateId(roommateId))) {
        throw new BadRequestError("roommate id not found");
    }

    var userRoommate = await roommates.getRoommateFromUid(uid);

    if (userRoommate.permissions !== "owner") {
        throw new ForbiddenError("requester is not the house owner");
    }

    var rowsUpdated = await knex("roommates")
        .update("roommate_house", userRoommate.house)
        .where("roommate_id", roommateId);

    return rowsUpdated;
}

roommates.setHouseOfOwner = async function (uid, houseId) {
    var rowsUpdated = await knex("roommates")
        .update("roommate_house", houseId)
        .where("roommate_uid", uid);

    return rowsUpdated;
}

roommates.getRoommateFromUid = async function (uid) {
    var roommatesList = await knex.select()
        .table("roommates")
        .where("roommate_uid", uid);

    var roommate = roommatesList[0];

    if (!roommate.roommate_house) {
        return {name: roommate.roommate_name};
    }
    
    var housesList = await knex.select("house_admin")
        .from("houses")
        .where("house_id", roommate.roommate_house);
    
    var house = housesList[0];
    
    return {
        name: roommate.roommate_name,
        permissions: house.house_admin === roommate.roommate_id ? 
            "owner" : "member",
        house: roommate.roommate_house
    };
}

roommates.checkIfUserIsInHouse = async function (uid, houseId) {
    var roommate = await this.getRoommateFromUid(uid);
    console.log(roommate);
    console.log(houseId);
    return roommate.house == houseId;
}

module.exports = roommates;
