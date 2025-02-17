package algonquin.cst2335.lian0122.Sun;

import static androidx.test.espresso.Espresso.onView;
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
public class Sun_TestPreferencesLoad {

    @Rule
    public ActivityScenarioRule<Sun_MainActivity> activityRule = new ActivityScenarioRule<>(Sun_MainActivity.class);

    @Test
    public void testPreferencesLoadOnStartup() {
        onView(ViewMatchers.withId(R.id.editTextLatitude)).check(matches(withText(not(""))));
        onView(withId(R.id.editTextLongitude)).check(matches(withText(not(""))));
    }
}
