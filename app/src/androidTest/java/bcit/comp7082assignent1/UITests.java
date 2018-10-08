package bcit.comp7082assignent1;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.StringContains.containsString;

@RunWith(AndroidJUnit4.class)
public class UITests {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void beforeTest(){
        
    }

    @Test
    public void TestCaptionFilter() {
        onView(withId(R.id.caption)).perform(replaceText("test1"), closeSoftKeyboard());
        onView(withId(R.id.btnRight)).perform(click());
        onView(withId(R.id.btnLeft)).perform(click());
        onView(withId(R.id.caption)).check(matches(withText(containsString("test1"))));
        onView(withId(R.id.btnFilter)).perform(click());
        onView(withId(R.id.search_caption)).perform(typeText("test1"), closeSoftKeyboard());
        onView(withId(R.id.search_search)).perform(click());
        onView(withId(R.id.caption)).check(matches(withText(containsString("test1"))));
        for (int i = 0; i <= 5; i++) {
            onView(withId(R.id.btnRight)).perform(click());
            onView(withId(R.id.caption)).check(matches(withText(containsString("test1"))));
            onView(withId(R.id.btnLeft)).perform(click());
            onView(withId(R.id.caption)).check(matches(withText(containsString("test1"))));
        }
    }

    @Test
    public void TestDateFilter() {
        onView(withId(R.id.caption)).perform(replaceText("test1"), closeSoftKeyboard());
        onView(withId(R.id.btnRight)).perform(click());
        onView(withId(R.id.btnLeft)).perform(click());
        onView(withId(R.id.caption)).check(matches(withText(containsString("test1"))));
        onView(withId(R.id.btnFilter)).perform(click());
        onView(withId(R.id.search_caption)).perform(typeText("test1"), closeSoftKeyboard());
        onView(withId(R.id.search_search)).perform(click());
        onView(withId(R.id.caption)).check(matches(withText(containsString("test1"))));
        for (int i = 0; i <= 5; i++) {
            onView(withId(R.id.btnRight)).perform(click());
            onView(withId(R.id.caption)).check(matches(withText(containsString("test1"))));
            onView(withId(R.id.btnLeft)).perform(click());
            onView(withId(R.id.caption)).check(matches(withText(containsString("test1"))));
        }
    }

    @Test
    public void TestLocationFilter() {
        onView(withId(R.id.caption)).perform(replaceText("test1"), closeSoftKeyboard());
        onView(withId(R.id.btnRight)).perform(click());
        onView(withId(R.id.btnLeft)).perform(click());
        onView(withId(R.id.caption)).check(matches(withText(containsString("test1"))));
        onView(withId(R.id.btnFilter)).perform(click());
        onView(withId(R.id.search_caption)).perform(typeText("test1"), closeSoftKeyboard());
        onView(withId(R.id.search_search)).perform(click());
        onView(withId(R.id.caption)).check(matches(withText(containsString("test1"))));
        for (int i = 0; i <= 5; i++) {
            onView(withId(R.id.btnRight)).perform(click());
            onView(withId(R.id.caption)).check(matches(withText(containsString("test1"))));
            onView(withId(R.id.btnLeft)).perform(click());
            onView(withId(R.id.caption)).check(matches(withText(containsString("test1"))));
        }
    }

    @After
    public void afterTest(){

    }
}
