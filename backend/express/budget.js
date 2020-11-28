var debtCalculator = require("./debt_calculator");
var knex = require("./db");

var budgetCalculator = {};

function doubleFactorial(n){
    var retval;
    if(n < 2){
        retval = 1;
    }
    else{
        retval =  n * doubleFactorial(n - 2);
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
        console.log(sum);
    }
    return sum;
}

function gamma(n){
    var retval;
    if(Math.abs(n - Math.round(n)) < 0.01){
        retval =  factorial(n - 1);
    }
    else{
        var m = Math.round(n * 2);
        retval =  Math.sqrt(Math.PI)*doubleFactorial(m - 2)/(2**((m-1)/2));
    }
    return retval;
}

budgetCalculator.tDistCDF = function tDistCDF(t, v){
    var cp;
    if(t < 0){
        cp = 1 - tDistCDF(-1*t, v);
    }
    else if (v == 1){
        cp = 1/2 + 1/Math.PI*Math.atan(t);
    }
    else if (v == 2){
        cp = 1/2 + t/(2*Math.sqrt(2)*Math.sqrt(1 + t**2/2));
    }
    else if (v == 3){
        cp = 1/2 + 1/Math.PI*(t/(Math.sqrt(3)*(1+t**2/3)) + Math.atan(t/Math.sqrt(3)));
    }
    else {
        var F12 = hypergeom(1/2, 1/2*(v+1), 3/2, -1 * (t**2)/v);
        cp = 1/2 + t * gamma(1/2*(v + 1)) * F12 / (Math.sqrt(v * 3.1415926) * gamma(v/2));

    }
    return cp;    
}


budgetCalculator.budgetPredictionFromList = function budgetPredictionFromList(purchases, limit){
    var probability;
    var purchase;
    var sum = 0;
    var max = 0;
    var validPurchasesCount = 0;

    for(purchase of purchases){
        if (purchase != null && purchase > 0){
            validPurchasesCount += 1;
            sum += purchase;
        }
        if (purchase > max){
            max = purchase;
        }
    }

    if(validPurchasesCount < 2){
        if (purchases.length === 0){
            return {
                "monthly_budget": limit,
                "likelihood": 0,
                "mean_purchase": 0,
                "number_of_purchases": 0,
                "most_expensive_purchase": 0,
                "monthly_spending": 0
              };
        }
        else{
            if(purchases[0] > limit){
                probability = 1;
            }
            else if(purchases[0] === limit){
                probability = 0.5;
            }
            else{
                probability = 0;
            }
            return  {
                "monthly_budget": limit,
                "likelihood": probability,
                "mean_purchase": purchases[0],
                "number_of_purchases": purchases.length,
                "most_expensive_purchase": purchases[0],
                "monthly_spending": purchases[0]
              };
        }
    }

    var mean = sum/validPurchasesCount;
    var sumSq = 0;

    for(purchase of purchases){
        sumSq  += (purchase - mean) ** 2;
    }

    var perPurchase = limit/validPurchasesCount;

    var variance = sumSq / (validPurchasesCount - 1);
    var sigma = Math.sqrt(variance);

    var testStatistic = (mean - perPurchase) * Math.sqrt(validPurchasesCount) / sigma;

    probability = tDistCDF(testStatistic, validPurchasesCount - 1);

    return {
        "budget": limit,
        "likelihood": probability,
        "mean_purchase": mean,
        "number_of_purchases": purchases.length,
        "most_expensive_purchase": max,
        "monthly_spending": sum
      };
}


budgetCalculator.budgetPrediction = async function budgetPrediction(roommateID){
    var purchases = await debtCalculator.getTotalSpent(knex, roommateID);

    budget = await knex.select("roommate_budget")
    .from("roommates")
    .where("roommate_id", roommateID);

    var limit = budget[0];
    limit = limit ? limit["budget_goal"] : 1000;

    console.log(purchases); // eslint-disable-line no-console

    return budgetCalculator.budgetPredictionFromList(purchases, limit);
};
module.exports = budgetCalculator;
