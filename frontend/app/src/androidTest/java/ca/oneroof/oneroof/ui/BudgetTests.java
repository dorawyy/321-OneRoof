package ca.oneroof.oneroof.ui;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.PerformException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.util.HumanReadables;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;
import java.util.concurrent.TimeoutException;

import ca.oneroof.oneroof.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static ca.oneroof.oneroof.TestUtils.checkStats;
import static ca.oneroof.oneroof.TestUtils.createHouseInviteOther;
import static ca.oneroof.oneroof.TestUtils.createSharedPurchase;
import static ca.oneroof.oneroof.TestUtils.loginAs;
import static ca.oneroof.oneroof.TestUtils.setBudget;

@RunWith(AndroidJUnit4.class)
public class BudgetTests {
    private ActivityScenario<MainActivity> scenario;

    // To run this test, run the backend with AUTH_DISABLED=1.
    @Test
    public void budgetTest() {
        // create random users for the test
        Random random = new Random();
        String user1 = "User1" + random.nextInt();
        String user2 = "User2" + random.nextInt();

        // create new house for the user
        createHouseInviteOther("Test house", user1, user2);

        // log in and navigate to budget page
        scenario = loginAs(user1);

        onView(withId(R.id.action_profile))
                .perform(click());
        onView(withId(R.id.budget_btn))
                .perform(click());

        // since this is a new user, check that no monthly budget is set yet (and all stats are 0)
        // starting budget is automatically set to $10.00
        checkStats(scenario, "10.00", "$0", "$0",
                "0", "$0", "0");

        // then, set a new monthly budget and check that the display is updated
        // we have no purchases, so the stats should still all be 0
        setBudget(scenario, "125.00");
        checkStats(scenario, "125.00", "$0", "$0",
                "0", "$0", "0");
        scenario.close();

        // now, add a new purchase shared between user1 and user2 and check the stats for user1
        createSharedPurchase(user1, user2, 2000, "test purchase 1");
        scenario = loginAs(user1);

        onView(withId(R.id.action_profile))
                .perform(click());
        onView(withId(R.id.budget_btn))
                .perform(click());

        checkStats(scenario, "125.00", "$10", "$10",
                "1", "$10", "0");
        scenario.close();

        // have user2 add a purchase and check user1's stats
        createSharedPurchase(user2, user1, 2000, "test purchase 2");
        scenario = loginAs(user1);

        onView(withId(R.id.action_profile))
                .perform(click());
        onView(withId(R.id.budget_btn))
                .perform(click());

        checkStats(scenario, "125.00", "$30", "$15",
                "2", "$20", "0");
        scenario.close();


        // try to change the budget to a negative value: budget shouldn't change
        setBudget(scenario, "-1");
        checkStats(scenario, "125.00", "$30", "$15",
                "2", "$20", "0");

    }
}
