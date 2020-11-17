package ca.oneroof.oneroof.ui;

import android.app.Activity;

import androidx.annotation.ContentView;
import androidx.test.core.app.ActivityScenario;

import static androidx.test.espresso.Espresso.onView;

import org.junit.Test;

import java.util.Random;

import ca.oneroof.oneroof.R;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withTagKey;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static ca.oneroof.oneroof.TestUtils.createHouseInviteOther;
import static ca.oneroof.oneroof.TestUtils.createSharedPurchase;
import static ca.oneroof.oneroof.TestUtils.loginAs;

public class DebtTest {
    private ActivityScenario<MainActivity> scenario;

    // To run this test, run the backend with AUTH_DISABLED=1.
    @Test
    public void debtTest() {
        // Make a random user for this test.  User1 will invite User2.
        Random random = new Random();
        String user1 = "User1" + random.nextInt();
        String user2 = "User2" + random.nextInt();

        createHouseInviteOther("Debt house", user1, user2);

        // Make sure there's no debt to begin with
        ActivityScenario<MainActivity> scenario = loginAs(user1);
        onView(withId(R.id.text_are_owed))
                .check(matches(withText("$0.00")));
        onView(withId(R.id.text_owe))
                .check(matches(withText("$0.00")));
        scenario.close();

        createSharedPurchase(user1, user2, 10000, "Test purchase for debts");

        // Make sure user1 is owed 100.00, and user2 owes 100.00
        scenario = loginAs(user1);
        onView(withId(R.id.text_are_owed))
                .check(matches(withText("$100.00")));
        onView(withId(R.id.text_owe))
                .check(matches(withText("$0.00")));
        scenario.close();

        // Make sure user1 is owed 100.00, and user2 owes 100.00
        scenario = loginAs(user2);
        onView(withId(R.id.text_are_owed))
                .check(matches(withText("$0.00")));
        onView(withId(R.id.text_owe))
                .check(matches(withText("$100.00")));
        scenario.close();

//        createSharedPurchase(user2, user1, 75000, "Test purchase for debts");

    }
}
