exports.seed = (knex) => {
    return knex("purchases").del()
     .then(() => {
        return knex("purchases").insert([
          {purchase_roommate: 1, purchase_amount: 3000, purchase_memo: "Test purchase"},
          {purchase_roommate: 2, purchase_amount: 1000},
        ]);
      });
  };