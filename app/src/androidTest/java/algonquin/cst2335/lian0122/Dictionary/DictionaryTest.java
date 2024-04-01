package algonquin.cst2335.lian0122.Dictionary;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
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

import algonquin.cst2335.lian0122.R;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class DictionaryTest {

    @Rule
    public ActivityScenarioRule<DictionaryRoom> mActivityScenarioRule =
            new ActivityScenarioRule<>(DictionaryRoom.class);

    @Test
    public void dictionaryRoomSearchTest() {
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.searchField),
                        childAtPosition(
                                allOf(withId(R.id.main),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("sun"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.search_Button), withText("Search"),
                        childAtPosition(
                                allOf(withId(R.id.main),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                1),
                        isDisplayed()));
        materialButton.perform(click());
    }

    @Test
    public void dictionarySaveTest() {
    ViewInteraction actionMenuItemView = onView(
            allOf(withId(R.id.item_1), withContentDescription("Save"),
                    childAtPosition(
                            childAtPosition(
                                    withId(R.id.dictionaryRoomToolbar),
                                    1),
                            3),
                    isDisplayed()));
        actionMenuItemView.perform(click());

    ViewInteraction button = onView(
            allOf(withId(R.id.item_1), withContentDescription("Save"),
                    withParent(withParent(withId(R.id.dictionaryRoomToolbar))),
                    isDisplayed()));
        button.check(matches(isDisplayed()));
}

    @Test
    public void dictionarySavedTermsTest() {
        ViewInteraction materialButton = onView(
                allOf(withId(R.id.savedTerms_Button), withText("Saved terms"),
                        childAtPosition(
                                allOf(withId(R.id.main),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        materialButton.perform(click());
    }

    @Test
    public void dictionaryAddTwiceTest() {
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.searchField),
                        childAtPosition(
                                allOf(withId(R.id.main),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("sun"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.search_Button), withText("Search"),
                        childAtPosition(
                                allOf(withId(R.id.main),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                1),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.item_1), withContentDescription("Save"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.dictionaryRoomToolbar),
                                        1),
                                3),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction actionMenuItemView2 = onView(
                allOf(withId(R.id.item_1), withContentDescription("Save"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.dictionaryRoomToolbar),
                                        1),
                                3),
                        isDisplayed()));
        actionMenuItemView2.perform(click());

        ViewInteraction button = onView(
                allOf(withId(R.id.item_1), withContentDescription("Save"),
                        withParent(withParent(withId(R.id.dictionaryRoomToolbar))),
                        isDisplayed()));
        button.check(matches(isDisplayed()));
    }

    @Test
    public void dictionaryAuthorTest() {
        ViewInteraction overflowMenuButton = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.dictionaryRoomToolbar),
                                        1),
                                4),
                        isDisplayed()));
        overflowMenuButton.perform(click());

        ViewInteraction materialTextView = onView(
                allOf(withId(androidx.transition.R.id.title), withText("About"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.appcompat.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView.perform(click());
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
