package ru.iteco.fmhandroid.ui;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import static java.lang.Thread.sleep;
import static ru.iteco.fmhandroid.ui.CustomMatchers.isToast;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingPolicies;
import androidx.test.espresso.PerformException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.util.HumanReadables;
import androidx.test.espresso.util.TreeIterables;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import ru.iteco.fmhandroid.R;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AppActivityTest {
    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);
    private SimpleIdlingResource idlingResource;
    private View decorView;
    @Before
    public void setUp() {
        mActivityScenarioRule.getScenario().onActivity(new ActivityScenario.ActivityAction<AppActivity>() {
            @Override
            public void perform(AppActivity activity) {
                decorView = activity.getWindow().getDecorView();
            }
        });
    }


    /**
     * Perform action of waiting for a specific view id to be displayed.
     * @param viewId The id of the view to wait for.
     * @param millis The timeout of until when to wait for.
     */


    public static ViewAction waitDisplayed(final int viewId, final long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "wait for a specific view with id <" + viewId + "> has been displayed during " + millis + " millis.";
            }

            @Override
            public void perform(final UiController uiController, final View view) {
                uiController.loopMainThreadUntilIdle();
                final long startTime = System.currentTimeMillis();
                final long endTime = startTime + millis;
                final Matcher<View> matchId = withId(viewId);
                final Matcher<View> matchDisplayed = isDisplayed();

                do {
                    for (View child : TreeIterables.breadthFirstViewTraversal(view)) {
                        if (matchId.matches(child) && matchDisplayed.matches(child)) {
                            return;
                        }
                    }

                    uiController.loopMainThreadForAtLeast(50);
                }
                while (System.currentTimeMillis() < endTime);

                // timeout happens
                throw new PerformException.Builder()
                        .withActionDescription(this.getDescription())
                        .withViewDescription(HumanReadables.describe(view))
                        .withCause(new TimeoutException())
                        .build();
            }
        };
    }
    public static ViewAction waitDisplayedText(final String text, final long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "wait for a specific view with text <" + text + "> to be displayed during " + millis + " millis.";
            }

            @Override
            public void perform(final UiController uiController, final View view) {
                uiController.loopMainThreadUntilIdle();
                final long startTime = System.currentTimeMillis();
                final long endTime = startTime + millis;
                final Matcher<View> matchText = withText(text);
                final Matcher<View> matchDisplayed = isDisplayed();

                do {
                    for (View child : TreeIterables.breadthFirstViewTraversal(view)) {
                        if (matchText.matches(child) && matchDisplayed.matches(child)) {
                            return;
                        }
                    }

                    uiController.loopMainThreadForAtLeast(50);
                }
                while (System.currentTimeMillis() < endTime);

                // timeout happens
                throw new PerformException.Builder()
                        .withActionDescription(this.getDescription())
                        .withViewDescription(HumanReadables.describe(view))
                        .withCause(new TimeoutException())
                        .build();
            }
        };
    }
    @Test
    public void appActivityTest() throws InterruptedException {

        // Проверяем, что экран загрузился и элемент отображается
        onView(isRoot()).perform(waitDisplayed(R.id.login_text_input_layout, 10000))
                .check(matches(isDisplayed()));


        onView(withId(R.id.login_text_input_layout)).perform(click());
        // Устанавливаем состояние "занят"

        onView(withId(R.id.login))
                .perform(typeText("login2"));
        onView(isRoot()).perform(waitDisplayed(R.id.password_text_input_layout, 10000))
                .check(matches(isDisplayed()));

        onView(isRoot()).perform(waitDisplayed(R.id.password_text_input_layout, 10000)).perform(click());
        onView(withId(R.id.password))
                .perform(typeText("password2"), closeSoftKeyboard());
        onView(withId(R.id.enter_button))
                .perform(click());


        onView(isRoot()).perform(waitDisplayed(R.id.container_list_news_include_on_fragment_main, 10000))
                .check(matches(isDisplayed()));
        onView(withId(R.id.authorization_image_button)).perform(click());
        ViewInteraction materialTextView = onView(
                allOf(withId(android.R.id.title), withText("Log out"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView.perform(click());
        onView(isRoot()).perform(waitDisplayed(R.id.login_text_input_layout, 10000))
                .check(matches(isDisplayed()));
    }
    @Test
    public void WrongLogTest() throws InterruptedException {

        // Проверяем, что экран загрузился и элемент отображается
        onView(isRoot()).perform(waitDisplayed(R.id.login_text_input_layout, 10000))
                .check(matches(isDisplayed()));


        onView(withId(R.id.login_text_input_layout)).perform(click());
        // Устанавливаем состояние "занят"

        onView(withId(R.id.login))
                .perform(typeText("log"));
        onView(isRoot()).perform(waitDisplayed(R.id.password_text_input_layout, 10000))
                .check(matches(isDisplayed()));

        onView(withId(R.id.password_text_input_layout)).perform(click());
        onView(withId(R.id.password))
                .perform(typeText("password2"), closeSoftKeyboard());

        onView(withId(R.id.enter_button))
                .perform(click());
        onView(isRoot()).perform(waitDisplayedText("Something went wrong. Try again later.", 20000))
                .inRoot(withDecorView(Matchers.not(decorView)))
                .check(matches(isDisplayed()));

        // Проверяем, что отображается текст "Новости"

    }

    @After
    public void tearDown() {


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
