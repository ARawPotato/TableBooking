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
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class UserListInstrumentationTest {

    private MockWebServer mockWebServer = new MockWebServer();

    @Rule
    public ActivityTestRule<UserListActivity> activityTestRule =
            new ActivityTestRule<>(UserListActivity.class, false, true);

    @Before
    public void setup() throws IOException {
        mockWebServer.start();
    }

    @After
    public void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    public void show_list() throws IOException {
        UserListActivity activity = activityTestRule.getActivity();

        mockWebServer.enqueue(new MockResponse());

        activityTestRule.launchActivity(new Intent());

            
    }
}
