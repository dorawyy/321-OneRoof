package ca.oneroof.oneroof.api;

public class BudgetStats {
    public double likelihood;
    public double mean_purchase; // in cents
    public int number_of_purchases;
    public int most_expensive_purchase; // in cents
    public int month_spending; // in cents
    public int budget;
    // TODO: add budget in once endpoint is updated
}
