package ca.oneroof.oneroof.ui;

import java.util.ArrayList;

public class DivisionEdit {
    public int amount;
    public ArrayList<String> roommateNames;
    public ArrayList<Boolean> roommateEnables;

    public DivisionEdit(ArrayList<String> roommateNames) {
        this.roommateNames = roommateNames;
        this.roommateEnables = new ArrayList<>();
        for (int i = 0; i < this.roommateNames.size(); i++) {
            this.roommateEnables.add(false);
        }
        this.amount = 0;
    }
}
