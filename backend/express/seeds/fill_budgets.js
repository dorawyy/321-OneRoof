exports.seed = (knex) => {
    return knex("budgets").del()
      .then(() => {
        return knex("budgets").insert([
          {budget_roommate: 1, budget_goal: 1000},
          {budget_roommate: 2, budget_goal: 1500},

        ]);
      });
    };