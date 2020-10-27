package ca.oneroof.oneroof.ui;

import java.util.ArrayList;

public class DivisionEdit {
    public int amount;
    public ArrayList<String> roommateNames;
    public ArrayList<Boolean> roommateEnables;
    public ArrayList<Integer> roommates;

    public DivisionEdit(ArrayList<String> roommateNames, ArrayList<Integer> roommates) {
        this.roommateNames = roommateNames;
        this.roommateEnables = new ArrayList<>();
        this.roommates = roommates;
        for (int i = 0; i < this.roommateNames.size(); i++) {
            this.roommateEnables.add(false);
        }
        this.amount = 0;
    }
}
