package ca.oneroof.oneroof.api;

public class Debt {
    public int roommate;
    public String roommate_name;
    public int amount;

    public String descriptor() {
        if (amount > 0) {
            return "owes you";
        } else {
            return "is owed";
        }
    }
}
