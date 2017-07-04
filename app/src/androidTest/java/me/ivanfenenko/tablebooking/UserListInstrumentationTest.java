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
import me.ivanfenenko.tablebooking.utils.RecyclerViewMatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class UserListInstrumentationTest {

    private MockWebServer mockWebServer = new MockWebServer();

    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }

    @Rule
    public ActivityTestRule<UserListActivity> activityTestRule =
            new ActivityTestRule<>(UserListActivity.class, false, true);

    @Before
    public void setup() throws IOException {
        mockWebServer.start(3333);
    }

    @After
    public void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    public void show_list_error() throws IOException {
        mockWebServer.enqueue(new MockResponse().setResponseCode(500));

        activityTestRule.launchActivity(new Intent());

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.recyclerView))
                .check(matches(hasDescendant(withId(R.id.error_text))));

    }
}
