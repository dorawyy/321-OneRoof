package ca.oneroof.oneroof.api;

public class DebtSummary {
    public int you_owe;
    public int you_are_owed;

    public String youOweString() {
        return String.format("$%d.%d", you_owe / 100, you_owe % 100);
    }

    public String youAreOwedString() {
        return String.format("$%d.%d", you_are_owed / 100, you_are_owed % 100);
    }

}
