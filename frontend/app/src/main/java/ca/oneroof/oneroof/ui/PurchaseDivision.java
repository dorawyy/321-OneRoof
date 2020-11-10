package ca.oneroof.oneroof.ui;

import java.util.ArrayList;

public class PurchaseDivision {

    private int amount;
    private ArrayList<Integer> roommates;
    private String memo;


    public PurchaseDivision(int amount, ArrayList<Integer> roommates, String memo) {
        this.amount = amount;
        this.roommates.addAll(roommates);
        this.memo = memo;
    }
}
