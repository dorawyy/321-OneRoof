package ca.oneroof.oneroof.api;

import java.util.ArrayList;

public class Purchase {
    public String memo;
    public int purchaser;
    public String purchaser_name;
    public ArrayList<Division> divisions;
    int amount;

    public String dollarString() {
        return String.format("$%d.%d", amount / 100, amount % 100);
    }
}
