const debtCalculator = require('../debt_calculator');

jest.mock('../debt_calculator');

test('purcahse info', async () => {
    const divisions = [{amount: 500, roommates: [1, 2]}, {amount: 1000, roommates: [1]}];
    const purchase = {roommate: 1, amount: 1500, divisions: divisions, memo: 'test purchase'};
    debtCalculator.getPurchaseInfo.mockResolvedValue(purchase);
  
    const testPurchase = await debtCalculator.getPurchaseInfo(null, null);
    return expect(testPurchase).toEqual(purchase);
  });