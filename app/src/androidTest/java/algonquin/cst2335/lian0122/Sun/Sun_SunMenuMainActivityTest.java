package algonquin.cst2335.lian0122.Sun;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static org.hamcrest.Matchers.not;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import algonquin.cst2335.lian0122.R;
import algonquin.cst2335.lian0122.Sun.Sun_MainActivity;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class Sun_SunMenuMainActivityTest {

    @Rule
    public ActivityScenarioRule<Sun_MainActivity> activityRule = new ActivityScenarioRule<>(Sun_MainActivity.class);

    @Test
    public void testLookupButtonUpdatesUI() {
        onView(ViewMatchers.withId(R.id.editTextLatitude)).perform(typeText("40.7128"), closeSoftKeyboard());
        onView(withId(R.id.editTextLongitude)).perform(typeText("-74.0060"), closeSoftKeyboard());

        onView(withId(R.id.buttonLookup)).perform(click());

        onView(withId(R.id.textViewSunrise)).check(matches(not(withText(""))));
        onView(withId(R.id.textViewSunset)).check(matches(not(withText(""))));
        onView(withId(R.id.textViewSolarNoon)).check(matches(not(withText(""))));
    }
}
