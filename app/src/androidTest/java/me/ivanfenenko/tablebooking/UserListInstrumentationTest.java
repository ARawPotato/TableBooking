package me.ivanfenenko.tablebooking;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import me.ivanfenenko.tablebooking.customers.UserListActivity;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class UserListInstrumentationTest {

    @Rule
    public ActivityTestRule<UserListActivity> activityTestRule =
            new ActivityTestRule<>(UserListActivity.class, false, true);

    @Before
    public void setup() throws IOException {
    }

    @After
    public void tearDown() throws IOException {
    }

    @Test
    public void show_list() throws IOException {
        UserListActivity activity = activityTestRule.getActivity();

        activityTestRule.launchActivity(new Intent());

//        onView(witrect(R.id.recycler_view)
//                .atPositionOnView(1, R.id.ofElementYouWantToCheck))
//                .check(matches(withText("Test text")));
    }
}
