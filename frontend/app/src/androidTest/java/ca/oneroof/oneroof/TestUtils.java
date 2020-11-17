package ca.oneroof.oneroof;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;

import org.hamcrest.Matcher;

import ca.oneroof.oneroof.ui.MainActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class TestUtils {
    public static String getText(ViewInteraction matcher) {
        final String[] result = new String[1];
        matcher.perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(TextView.class);
            }

            @Override
            public String getDescription() {
                return "Get text";
            }

            @Override
            public void perform(UiController uiController, View view) {
                result[0] = ((TextView) view).getText().toString();
            }
        });
        return result[0];
    }

    /**
     * Creates a house user user1, then invites user2 to the house with the invite code.
     */
    public static void createHouseInviteOther(String houseName, String user1, String user2) {
        ActivityScenario<MainActivity> scenario;
        scenario = loginAs(user1);

        // Make sure we don't have a house.
        onView(withId(R.id.no_house_header))
                .check(matches(isDisplayed()));

        // Create a new house.
        onView(withId(R.id.create_house_button))
                .perform(click());
        onView(withId(R.id.house_name))
                .perform(replaceText(houseName));
        onView(withText("Create"))
                .perform(click());

        // We should be in the house right now.  Check that we see a list of purchases.
        onView(withId(R.id.house_purchases))
                .check(matches(isDisplayed()));
        // No debts yet
        onView(withId(R.id.text_owe))
                .check(matches(withText("$0.00")));
        onView(withId(R.id.text_are_owed))
                .check(matches(withText("$0.00")));

        scenario.close();

        loginAs(user2);

        // Record our invite code.
        String inviteCode;
        onView(withId(R.id.invite_code))
                .check(matches(isDisplayed()));
        inviteCode = getText(onView(withId(R.id.invite_code)));

        scenario.close();

        // Go back to the other user, to invite user2
        loginAs(user1);

        onView(withId(R.id.action_profile))
                .perform(click());

        onView(withId(R.id.house_settings_btn))
                .perform(click());

        // Type in the invite code, and add them
        onView(withId(R.id.invite_code))
                .perform(replaceText(inviteCode));

        onView(withId(R.id.add_roommate_btn))
                .perform(click());

        // Check that we're back in the profile page
        onView(withId(R.id.house_name))
                .check(matches(withText(houseName)));

        scenario.close();
    }

    public static ActivityScenario<MainActivity> loginAs(String user) {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), MainActivity.class);
        intent.putExtra("authDisabled", true);
        intent.putExtra("authTestUser", user);
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(intent);

        // Check that a login button exists.
        onView(withId(R.id.login_button))
                .check(matches(isDisplayed()));

        // Click login button with the mock login setup.
        onView(withId(R.id.login_button))
                .perform(click());

        return scenario;
    }
}
