var debtCalculator = require('./debt_calculator');

var budgetCalculator = budgetCalculator || {};

function t_dist_cdf(t, v){
    if(t < 0){
        return 1 - t_dist_cdf(-1*t, v);
    }
    var F12 = hypergeom(1/2, 1/2*(v+1), 3/2, -1 * (t**2)/v);
    return 1/2 + t * gamma(1/2*(v + 1)) * F12 / (Math.sqrt(v * 3.1415926) * gamma(v/2));
}

function gamma(n){
    var retval;
    if(Math.abs(n - Math.round(n)) < 0.01){
        retval =  factorial(n - 1);
    }
    else{
        var m = Math.round(n * 2);
        retval =  1.7724538*double_factorial(m - 2)/(2**((m-1)/2));
    }
    return retval;
}

function double_factorial(n){
    var retval;
    if(n < 2){
        retval = 1;
    }
    else{
        retval =  n * double_factorial(n - 2);
    }
    return retval;
}

function factorial(n){
    if(n < 2){
        return 1;
    }
    else{
        return n * factorial(n - 1);
    }
}

function pochhammer(q, n){
    var m;
    var retval = 1;
    for(m = 0; m < n; m++){
        retval = retval * (q + m);
    }
    return retval;
}

function hypergeom(a, b, c, z){
    var n;
    var sum = 0;
    for(n = 0; n < 60; n++){
        sum += (pochhammer(a, n) * pochhammer(b, n) / (pochhammer(c, n) * factorial(n)) )* (z ** n) ;
    }
    return sum;
}

function budget_prediction_from_list(purchases, limit){
    if(purchases.length < 2){
        if (purchases.length == 0){
            return {
                'monthly_budget': limit,
                'likelihood': 0,
                'mean_purchase': 0,
                'number_of_purchases': 0,
                'most_expensive_purchase': 0,
                'monthly_spending': 0
              };
        }
        else{
            var probability;
            if(purchases[0] > limit){
                probability = 1;
            }
            else if(purchases[0] == limit){
                probability = 0.5;
            }
            else{
                probability = 0;
            }
            return  {
                'monthly_budget': limit,
                'likelihood': probability,
                'mean_purchase': purchases[0],
                'number_of_purchases': purchases.length,
                'most_expensive_purchase': purchases[0],
                'monthly_spending': purchases[0]
              };
        }
    }
    var purchase;
    var sum = 0;
    var max = 0;

    for(purchase of purchases){
        sum += purchase;
        if (purchase > max){
            max = purchase;
        }
    }

    var mean = sum/purchases.length;
    var sum_sq = 0;

    for(purchase of purchases){
        sum_sq  += (purchase - mean) ** 2;
    }

    var per_purchase = limit/purchases.length;

    var variance = sum_sq / (purchases.length - 1);
    var std_dev = Math.sqrt(variance);

    var test_statistic = (mean - per_purchase) * Math.sqrt(purchases.length) / std_dev;

    var probability = t_dist_cdf(test_statistic, purchases.length - 1);

    return {
        'monthly_budget': limit,
        'likelihood': probability,
        'mean_purchase': mean,
        'number_of_purchases': purchases.length,
        'most_expensive_purchase': max,
        'monthly_spending': sum
      };
}

/*
console.log(t_dist_cdf(-2.74, 10));
console.log(budget_prediction_from_list([], 10));
console.log(budget_prediction_from_list([4000, 2000, 3000, 1000], 10000));
console.log(budget_prediction_from_list([4000, 2000, 3000, 3000], 10000));
*/


budgetCalculator.budget_prediction = function budget_prediction(roommate_id){
    var purchases = debtCalculator.getTotalSpent(roommate_id);

    budget = knex.select('budget_goal')
    .from('budgets')
    .where('budget_roommate', roommate_id);

    var limit = budget[0]['budget_goal'];

    return budget_prediction_from_list(purchases, limit);

}

module.exports = budgetCalculator;
