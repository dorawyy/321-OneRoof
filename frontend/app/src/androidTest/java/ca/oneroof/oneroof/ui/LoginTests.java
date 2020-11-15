package ca.oneroof.oneroof.ui;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

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

import java.util.concurrent.TimeoutException;

import ca.oneroof.oneroof.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class LoginTests {
    private static Intent intent;
    static {
        intent = new Intent(ApplicationProvider.getApplicationContext(), MainActivity.class);
        intent.putExtra("authDisabled", true);
    }

    // start mainActivity
    @Rule
    public ActivityScenarioRule<MainActivity> activityActivityTestRule =
            new ActivityScenarioRule<MainActivity>(intent);

    // To run this test, run the backend with AUTH_DISABLED=1.
    @Test
    public void clickLoginBtn() {
        // Check that a login button exists.
        onView(withId(R.id.login_button))
                .check(matches(isDisplayed()));

        // Click login button with the mock login setup.
        onView(withId(R.id.login_button))
                .perform(click());

        // Check that a house is displayed, with 1s.
        onView(withId(R.id.house_name_header))
                .perform(new ViewAction() {
                    @Override
                    public Matcher<View> getConstraints() {
                        return isAssignableFrom(TextView.class);
                    }

                    @Override
                    public String getDescription() {
                        return "Wait for house name to appear.";
                    }

                    // Idea from https://stackoverflow.com/questions/49796132/android-espresso-wait-for-text-to-appear
                    // Good page for reference.
                    @Override
                    public void perform(UiController uiController, View view) {
                        long endTime = System.currentTimeMillis() + 1000;
                        do {
                            if (((TextView) view).getText().equals("House 1")) {
                                return;
                            }

                            uiController.loopMainThreadForAtLeast(50);
                        } while (System.currentTimeMillis() < endTime);

                        throw new PerformException.Builder()
                                .withActionDescription(getDescription())
                                .withCause(new TimeoutException("Waited over 1s for the house name to appear."))
                                .withViewDescription(HumanReadables.describe(view))
                                .build();
                    }
                });
    }
}
