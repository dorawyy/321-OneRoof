exports.seed = (knex) => {
    return knex("roommates").del()
     .then(() => {
        return knex("roommates").insert([
          {"roommate_name": "Roommate 1", "roommate_budget": 50000, "roommate_uid": 1},
          {"roommate_name": "Roommate 2", "roommate_house": 1, "roommate_budget": 50000, "roommate_uid": 2},
          {"roommate_name": "Roommate 3", "roommate_house": 1, "roommate_budget": 70000, "roommate_uid": "foo"},
          {"roommate_name": "Fake roommate", "roommate_budget": 70000, "roommate_uid": "Fake roommate"},
        ]);
      });
  };