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

    public String caption;
    public String expectedCaption1;
    public String expectedCaption2;
    public String expectedCaption3;
    public String maxDate;
    public String minDate;
    public String expectedDate1;
    public String expectedDate2;
    public String expectedDate3;
    public Integer latitude1;
    public Integer longitude1;
    public Integer latitude2;
    public Integer longitude2;

    @Before
    public void beforeTest() {
        //onView(withId(R.id.btnCamera)).perform(click());
        caption = "caption1";
        expectedCaption1 = "caption1";
        expectedCaption2 = "caption2";
        expectedCaption3 = "caption3";
        minDate = "20181010190926";
        maxDate = "20181010190928";
        expectedDate1 = "20181010_190905";
        expectedDate2 = "20181010_190927";
        expectedDate3 = "20181010_190917";
        latitude1 = 49;
        longitude1 = -123;
        latitude2 = 1;
        longitude2 = 1;
    }

    @Test
    public void TestCaptionFilter() {
        onView(withId(R.id.btnFilter)).perform(click());
        onView(withId(R.id.search_caption)).perform(typeText(caption), closeSoftKeyboard());
        onView(withId(R.id.search_search)).perform(click());
        onView(withId(R.id.caption)).check(matches(withText(containsString(expectedCaption1))));
        for (int i = 0; i <= 3; i++) {
            onView(withId(R.id.btnRight)).perform(click());
            onView(withId(R.id.caption)).check(matches(withText(containsString(expectedCaption1))));
            onView(withId(R.id.btnLeft)).perform(click());
            onView(withId(R.id.caption)).check(matches(withText(containsString(expectedCaption1))));
        }
    }

    @Test
    public void TestDateFilter() {
        onView(withId(R.id.btnFilter)).perform(click());
        onView(withId(R.id.search_fromDate)).perform(typeText(minDate), closeSoftKeyboard());
        onView(withId(R.id.search_toDate)).perform(typeText(maxDate), closeSoftKeyboard());
        onView(withId(R.id.search_search)).perform(click());
        onView(withId(R.id.timestamp)).check(matches(withText(containsString(expectedDate2))));
        for (int i = 0; i <= 3; i++) {
            onView(withId(R.id.btnRight)).perform(click());
            onView(withId(R.id.timestamp)).check(matches(withText(containsString(expectedDate2))));
            onView(withId(R.id.btnLeft)).perform(click());
            onView(withId(R.id.timestamp)).check(matches(withText(containsString(expectedDate2))));
        }
    }

    @Test
    public void TestLocationFilter() {
        onView(withId(R.id.btnFilter)).perform(click());
        onView(withId(R.id.search_latitude)).perform(typeText(latitude1.toString()), closeSoftKeyboard());
        onView(withId(R.id.search_longitude)).perform(typeText(longitude1.toString()), closeSoftKeyboard());
        onView(withId(R.id.search_search)).perform(click());
        onView(withId(R.id.caption)).check(matches(withText(containsString("caption2"))));
        onView(withId(R.id.btnRight)).perform(click());
        onView(withId(R.id.caption)).check(matches(withText(containsString("caption3"))));
        onView(withId(R.id.btnRight)).perform(click());
        onView(withId(R.id.caption)).check(matches(withText(containsString("caption1"))));
        onView(withId(R.id.btnFilter)).perform(click());
        onView(withId(R.id.search_latitude)).perform(typeText(latitude2.toString()), closeSoftKeyboard());
        onView(withId(R.id.search_longitude)).perform(typeText(longitude2.toString()), closeSoftKeyboard());
        onView(withId(R.id.search_search)).perform(click());
        onView(withId(R.id.timestamp)).check(matches(withText(containsString("Timestamp"))));
    }

    @After
    public void afterTest() {

    }
}
