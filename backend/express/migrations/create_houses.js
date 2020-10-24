exports.up = function(knex) {
    return knex.schema.createTable('houses', (table) => {
        table.increments('house_id').notNullable();
        table.string('house_name').notNullable();
        table.integer('house_admin').unsigned().notNullable();
        table.foreign('house_admin').references('roommates.roommate_id');
    });
};

exports.down = function(knex) {
    return knex.schema.dropTable('houses');
};

