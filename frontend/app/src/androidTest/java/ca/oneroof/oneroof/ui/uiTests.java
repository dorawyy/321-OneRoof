package ca.oneroof.oneroof.ui;

import android.content.Context;
import android.content.Intent;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.android21buttons.fragmenttestrule.FragmentTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ca.oneroof.oneroof.R;

import static android.app.PendingIntent.getActivity;
import static androidx.test.espresso.Espresso.onView;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class uiTests {
    // start mainActivity
    @Rule
    public ActivityTestRule<MainActivity> activityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void clickLoginBtn() {
        // go to login fragment
        activityActivityTestRule.getActivity().getSupportFragmentManager().beginTransaction();

        // click login button and go to... nowhere, since firebase isn't instantiated here
        onView(withId(R.id.login_button))
                .perform(click())
                .check((matches(isDisplayed())));
    }
}
