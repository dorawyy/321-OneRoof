package ca.oneroof.oneroof;

import android.view.View;
import android.widget.TextView;

import androidx.test.espresso.PerformException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.util.HumanReadables;

import org.hamcrest.Matcher;

import java.util.concurrent.TimeoutException;

import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;

public class TimeoutCheckViewAction implements ViewAction {
    @Override
    public Matcher<View> getConstraints() {
        return isAssignableFrom(TextView.class);
    }

    @Override
    public String getDescription() {
        return "Wait for text to appear.";
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
}
