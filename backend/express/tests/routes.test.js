const request = require("supertest")
const app = require("../app")

describe("Houses endpoints", () => {
    it("should create a new house", async () => {
        const res = await request(app)
            .post("/houses/")
            .send({
                name: "Test house",
                uid: 0
            });
        expect(res.body.id).toEqual(1);
        expect(res.body).toHaveProperty("post");
        done();
    });

    it("should get a house", async () => {
        const res = await request(app)
            .get("/houses/")
            .query({ houseId: 1})
            .send();
        expect(res.statusCode).toEqual(201);
        expect(res.body).toHaveProperty("get");
        done();
    });

    it("should fail to get a house", async () => {
        const res = await request(app)
            .get("/houses/")
            .query({ houseId: 321})
            .send();
        expect(res.statusCode).toEqual(400);
        expect(res.body).toHaveProperty("get");
        done();
    });
  });

describe("Index endpoints", () => {
    it("should login", async () => {
        const res = await request(app)
            .post("/login")
            .send({
                fcm: "fcm test",
            });
        expect(res.body.invite_code).toEqual(1);
        expect(res.body).toHaveProperty("post");
        done();
    });

    it("should post a new payment", async () => {
        const res = await request(app)
            .post("/payment")
            .send({
                name: "Test house",
                uid: 0
            });
        expect(res.body.id).toEqual(1);
        expect(res.body).toHaveProperty("post");
        done();
    });
});

describe("Roommates endpoints", () => {
    it("should add roommate", async () => {
        const res = await request(app)
            .post("/roommates/login")
            .send({
                name: "test roommate",
            });
        expect(res.statusCode).toEqual(201);
        expect(res.body).toHaveProperty("post");
        done();
    });
});

describe("Youowemes endpoints", () => {
    it("should send youoweme", async () => {
        const res = await request(app)
            .post("/youowemes")
            .send({
                target: 0
            });
        expect(res.statusCode).toEqual(400);
        expect(res.body).toHaveProperty("post");
        done();
    });

    it("should send youoweme", async () => {
        const res = await request(app)
            .patch("/youowemes")
            .send({
                payed: true
            });
        expect(res.statusCode).toEqual(200);
        expect(res.body).toHaveProperty("patch");
        done();
    });
});