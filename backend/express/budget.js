function t_dist_cdf(t, v){
    var F12 = hypergeom(1/2, 1/2(v+1), 3/2, -1 * (t**2)/v);
    return 1/2 + t * gamma(1/2(v + 1)) * F12 / (Math.sqrt(v * 3.1415926) * gamma(v/2));
}

function gamma(n){
    if(Math.abs(n - Math.round(n)) < 0.01){
        return factorial(n - 1);
    }
    else{
        var m = Math.round(n * 2);
        return 1.7724538*factorial(factorial(m - 2))/(2**((m-1)/2));
    }
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
    for(n = 0; n < 10; n++){
        sum += pochhammer(a, n) * pochhammer(b, n) * (z ** n) / (pochhammer(c, n) * factorial(n));
    }
    return sum;
}

function budget_prediction_from_list(purchases, limit){
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

    var variance = sum_sq / (purchases.length - 1);
    var std_dev = Math.sqrt(variance);

    var test_statistic = (mean - limit) * Math.sqrt(purchases.length) / std_dev;

    var probability = 1 - t_dist_cdf(test_statistic, purchases.length - 1);

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
function budget_prediction(roommate_id){
    divisions = knex.select('division_roommate_join_division')
    .from('division_roommate_join')
    .where('division_roommate_join_roommate', roommate_id);

    var i;
    for(i = 0; i < divisions.length; i++){

    }

}
*/