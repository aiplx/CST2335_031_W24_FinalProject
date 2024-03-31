package algonquin.cst2335.lian0122;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class Sun_TestToolbarWidget {

    @Rule
    public ActivityScenarioRule<Menu_MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(Menu_MainActivity.class);

    @Test
    public void testToolbarWidget() {
        ViewInteraction materialButton = onView(
                allOf(withId(R.id.btnOpenSunriseSunsetLookup), withText("Open Sunrise and Sunset Lookup"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                0),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction button = onView(
                allOf(withId(R.id.action_song), withContentDescription("SunSeeker"),
                        withParent(withParent(allOf(withId(R.id.mainToolbar), withContentDescription("SunSearch Menu Bar")))),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

        ViewInteraction button2 = onView(
                allOf(withId(R.id.action_recepie), withContentDescription("SunSeeker"),
                        withParent(withParent(allOf(withId(R.id.mainToolbar), withContentDescription("SunSearch Menu Bar")))),
                        isDisplayed()));
        button2.check(matches(isDisplayed()));

        ViewInteraction button3 = onView(
                allOf(withId(R.id.action_dictionary), withContentDescription("SunSeeker"),
                        withParent(withParent(allOf(withId(R.id.mainToolbar), withContentDescription("SunSearch Menu Bar")))),
                        isDisplayed()));
        button3.check(matches(isDisplayed()));

        ViewInteraction button4 = onView(
                allOf(withId(R.id.action_sunSeeker), withContentDescription("SunSeeker"),
                        withParent(withParent(allOf(withId(R.id.mainToolbar), withContentDescription("SunSearch Menu Bar")))),
                        isDisplayed()));
        button4.check(matches(isDisplayed()));

        ViewInteraction button5 = onView(
                allOf(withId(R.id.action_favorites), withContentDescription("Favorites"),
                        withParent(withParent(allOf(withId(R.id.mainToolbar), withContentDescription("SunSearch Menu Bar")))),
                        isDisplayed()));
        button5.check(matches(isDisplayed()));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}