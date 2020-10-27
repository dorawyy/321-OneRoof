exports.up = function(knex) {
    return knex.schema.createTable('budgets', (table) => {
        table.integer('budget_goal').unsigned().notNullable();
        table.integer('budget_roommate').unsigned().notNullable();
        table.foreign('budget_roommate').references('roommates.roommate_id');
    });
};

exports.down = function(knex) {
    return knex.schema.dropTable('budgets');
};