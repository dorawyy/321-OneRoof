var knex = require("../db");

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
    return response[0].roommate_id;
}

module.exports = roommates;
