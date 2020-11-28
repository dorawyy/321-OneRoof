const budgetCalculator = require("../budget");

test('Simple list, far under', () => {
    var purchases = [50, 20, 10, 5];
    var limit = 1500;
    expect(budgetCalculator.budgetPredictionFromList(purchases, limit).likelihood)
    .toBeCloseTo(0, 4);
});

test('Simple list, over', () => {
    var purchases = [50, 20];
    var limit = 10;
    expect(budgetCalculator.budgetPredictionFromList(purchases, limit).likelihood)
    .toBeCloseTo(0.8524, 4);
});

test('Empty', () => {
    var purchases = [];
    var limit = 10;
    expect(budgetCalculator.budgetPredictionFromList(purchases, limit).likelihood)
    .toBeCloseTo(0, 4);
});

// test('Simple list, slightly under', () => {
//     var purchases = [100, 200, 145, 355, 100];
//     var limit = 1000;
//     expect(budgetCalculator.budgetPredictionFromList(purchases, limit).likelihood)
//     .toBeCloseTo(0.3476, 4);
//   });