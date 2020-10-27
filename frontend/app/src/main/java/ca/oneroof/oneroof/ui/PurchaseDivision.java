package ca.oneroof.oneroof.ui;

import java.util.ArrayList;

public class PurchaseDivision {

    int amount;
    ArrayList<Integer> roommates;
    String memo;


    public PurchaseDivision(int amount, ArrayList<Integer> roommates, String memo) {
        this.amount = amount;

        for(int i = 0; i < roommates.size(); i++) {
            this.roommates.add(roommates.get(i));
        }

        this.memo = memo;
    }
}
