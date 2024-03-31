package algonquin.cst2335.lian0122;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestSaveToFavorites {

    @Rule
    public ActivityScenarioRule<Sun_MainActivity> activityRule = new ActivityScenarioRule<>(Sun_MainActivity.class);

    @Test
    public void testSaveToFavorites() {
        onView(withId(R.id.editTextLatitude)).perform(typeText("45.4215"), closeSoftKeyboard());
        onView(withId(R.id.editTextLongitude)).perform(typeText("-75.6972"), closeSoftKeyboard());
        onView(withId(R.id.btnSaveToFavorites)).perform(click());

        onView(withText(R.string.location_saved)).check(matches(isDisplayed()));
    }
}
