const budgetCalculator = require("../budget");

test('Simple list, far under', () => {
    var purchases = [5000, 2000, 1000, 500];
    var limit = 150000;
    expect(budgetCalculator.budgetPredictionFromList(purchases, limit).likelihood)
    .toBeCloseTo(0, 4);
});

test('Simple list, over', () => {
    var purchases = [5000, 2000];
    var limit = 1000;
    expect(budgetCalculator.budgetPredictionFromList(purchases, limit).likelihood)
    .toBeCloseTo(0.8524, 4);
});

test('Empty', () => {
    var purchases = [];
    var limit = 1000;
    expect(budgetCalculator.budgetPredictionFromList(purchases, limit).likelihood)
    .toBeCloseTo(0, 4);
});

test('Far overrun v = 4', () => {
    var purchases = [2000, 5000, 1000, 500, 1500];
    var limit = 1000;
    expect(budgetCalculator.budgetPredictionFromList(purchases, limit).likelihood)
    .toBeCloseTo(0.9575, 4);
});
