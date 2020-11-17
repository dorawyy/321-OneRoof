const BadRequestError = require("../modules/errors/BadRequestError");
const NotFoundError = require("../modules/errors/NotFoundError");
const ForbiddenError = require("../modules/errors/ForbiddenError");

var knex = require("../db");
jest.mock("../db", () => {
    const mKnex = { 
        select: jest.fn(() => mKnex),
        from: jest.fn(() => mKnex),
        where: jest.fn(() => mKnex),
        insert: jest.fn(() => mKnex),
        into: jest.fn(() => mKnex),
        del: jest.fn(() => mKnex)
    };
    return jest.fn(() => mKnex);
});

var Roommates = require("../modules/roommates");
var roommates = new Roommates(knex);

test("addRoommate", async () => {
    knex().insert.mockResolvedValue([1]);
    const actual = await roommates.addRoommate("Maddie");
    expect(actual).toEqual(1);
});

test("addRoommate with undefined name", async () => {
    knex().insert.mockResolvedValue([1]);

    await expect(async () => await roommates.addRoommate(undefined))
        .rejects.toEqual(new BadRequestError("name is undefined"));
});

test("getRoommateFromId", async () => {
    knex().select
        .mockResolvedValueOnce([{
            roommate_id: 1,
            roommate_name: "Maddie",
            roommate_house: 1
        }])
        .mockResolvedValueOnce([{
            house_admin: 1
        }]);

    const actual = await roommates.getRoommateFromId(1);
    expect(actual).toEqual({
        name: "Maddie",
        permissions: "owner",
        house: 1
    });
});

test("getRoommateFromId with invalid id", async () => {
    knex().select
        .mockResolvedValueOnce([]);

    await expect(async () => await roommates.getRoommateFromId(1))
        .rejects.toEqual(new NotFoundError("roommate id not found"));
});

test("deleteRoommate", async () => {
    knex().del.mockResolvedValue(1);
    const actual = await roommates.deleteRoommate(1);
    expect(actual).toEqual(1);
});

test("deleteRoommate with invalid id", async () => {
    knex().del.mockResolvedValue(0);

    await expect(async () => await roommates.deleteRoommate(1))
        .rejects.toEqual(new NotFoundError("roommate id not found"));
});

test("deleteRoommate without permissions", async () => {
    knex().select
        .mockResolvedValueOnce([{
            roommate_id: 1,
            roommate_name: "Maddie",
            roommate_house: 1
        }])
        .mockResolvedValueOnce([{
            house_admin: 2
        }]);
    knex().del.mockResolvedValue(1);

    await expect(async () => await roommates.deleteRoommate(1))
        .rejects.toEqual(new ForbiddenError("requester is not the house owner"));
});