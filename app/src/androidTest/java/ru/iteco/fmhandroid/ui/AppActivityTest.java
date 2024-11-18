package ru.iteco.fmhandroid.ui;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import static ru.iteco.fmhandroid.ui.CustomMatchers.isToast;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingPolicies;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import ru.iteco.fmhandroid.R;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AppActivityTest {

    private SimpleIdlingResource idlingResource;

    @Before
    public void setUp() {
        // Создаем и регистрируем IdlingResource
        idlingResource = new SimpleIdlingResource();
        Espresso.registerIdlingResources(idlingResource);

        // Устанавливаем время ожидания для IdlingResource
        IdlingPolicies.setIdlingResourceTimeout(15, TimeUnit.SECONDS);
    }

    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    @Test
    public void appActivityTest() throws InterruptedException {

        // Проверяем, что экран загрузился и элемент отображается
        onView(allOf(withId(R.id.login_text_input_layout), isDisplayed()))
                .check(matches(isDisplayed()));


        onView(withId(R.id.login_text_input_layout)).perform(click());
        // Устанавливаем состояние "занят"

        onView(withId(R.id.login))
                .perform(typeText("login2"));
        onView(allOf(withId(R.id.password_text_input_layout), isDisplayed()))
                .check(matches(isDisplayed()));

        onView(withId(R.id.password_text_input_layout)).perform(click());
        onView(withId(R.id.password))
                .perform(typeText("password2"), closeSoftKeyboard());
        onView(withId(R.id.enter_button))
                .perform(click());

        Thread.sleep(10000);
        onView(allOf(withText("Новости"), isDisplayed()))
                .check(matches(withText("Новости")));
        onView(withId(R.id.authorization_image_button)).perform(click());
        ViewInteraction materialTextView = onView(
                allOf(withId(android.R.id.title), withText("Выйти"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView.perform(click());
        onView(allOf(withId(R.id.login_text_input_layout), isDisplayed()))
                .check(matches(isDisplayed()));
        // Проверяем, что отображается текст "Новости"

    }
    @Test
    public void WrongLogTest() throws InterruptedException {

        // Проверяем, что экран загрузился и элемент отображается
        onView(allOf(withId(R.id.login_text_input_layout), isDisplayed()))
                .check(matches(isDisplayed()));


        onView(withId(R.id.login_text_input_layout)).perform(click());
        // Устанавливаем состояние "занят"

        onView(withId(R.id.login))
                .perform(typeText("log"));
        onView(allOf(withId(R.id.password_text_input_layout), isDisplayed()))
                .check(matches(isDisplayed()));

        onView(withId(R.id.password_text_input_layout)).perform(click());
        onView(withId(R.id.password))
                .perform(typeText("password2"), closeSoftKeyboard());

        onView(withId(R.id.enter_button))
                .perform(click());

       onView(withText(R.string.error))
              .inRoot(isToast())
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
