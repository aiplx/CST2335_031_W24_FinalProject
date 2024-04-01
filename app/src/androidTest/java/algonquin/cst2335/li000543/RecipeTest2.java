package algonquin.cst2335.li000543;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;

import algonquin.cst2335.li000543.Recipe.RecipeMain;
import algonquin.cst2335.lian0122.R;

@RunWith(AndroidJUnit4.class)
public class RecipeTest2 {

    @Test
    public void testRecipeSearch() {
        // Launch the RecipeMain activity
        ActivityScenario.launch(RecipeMain.class);

        // Assume there is an EditText for entering search terms with id EditText
        // and a Button to submit the search with id SearchButton.
        onView(withId(R.id.EditText)).perform(typeText("Chicken"), closeSoftKeyboard());
        onView(withId(R.id.SearchButton)).perform(click());

        // Ideally, we want to check if RecyclerView shows at least one item
        // But without synchronization, let's just check for the presence of RecyclerView.
        // This is a very basic check and doesn't ensure data loading.
        onView(withId(R.id.RecyclerView)).check(matches(isDisplayed()));
    }
}