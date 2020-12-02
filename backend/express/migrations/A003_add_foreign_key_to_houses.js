exports.up = function(knex) {
    return knex.schema.table("houses", function (table) {
        table.integer("house_admin").unsigned().notNullable();
        table.foreign("house_admin").references("roommates.roommate_id");
    });
};

exports.down = function(knex) {
    
};