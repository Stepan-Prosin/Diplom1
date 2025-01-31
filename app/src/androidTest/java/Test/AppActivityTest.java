package Test;
import Pages.Pagesdata;
import Utils.UTILsbase;
import Data.DataBase;
import Pages.CustomMatchers;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import static java.lang.Thread.sleep;

import static Pages.Pagesdata.childAtPosition;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.core.app.ActivityScenario;
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

import java.util.concurrent.TimeoutException;

import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.AppActivity;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AppActivityTest {
    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    private View decorView;

    @Before
    public void setUp() {
        mActivityScenarioRule.getScenario().onActivity(activity -> {
            decorView = activity.getWindow().getDecorView();
        });
    }

    @Test
    public void appActivityTest() throws InterruptedException {
        // Проверяем, что экран загрузился и элемент отображается
        onView(isRoot()).perform(Pagesdata.waitDisplayed(R.id.login_text_input_layout, 30000))
                .check(matches(isDisplayed()));

        // Выполняем вход с валидными данными через утилитный метод
        UTILsbase.performLogin(DataBase.VALID_LOGIN, DataBase.VALID_PASSWORD);

        // Проверка успешного входа
        onView(isRoot()).perform(Pagesdata.waitDisplayed(R.id.container_list_news_include_on_fragment_main, 20000))
                .check(matches(isDisplayed()));

        // Выполняем выход из аккаунта
        onView(withId(R.id.authorization_image_button)).perform(click());
        ViewInteraction materialTextView = onView(
                allOf(withId(android.R.id.title), withText("Log out"),
                        Pagesdata.childAtPosition(
                                Pagesdata.childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView.perform(click());

        // Проверяем, что снова отображается экран входа
        onView(isRoot()).perform(Pagesdata.waitDisplayed(R.id.login_text_input_layout, 20000))
                .check(matches(isDisplayed()));
    }

    @Test
    public void wrongLogTest() throws InterruptedException {
        // Проверяем, что экран загрузился и элемент отображается
        onView(isRoot()).perform(Pagesdata.waitDisplayed(R.id.login_text_input_layout, 10000))
                .check(matches(isDisplayed()));

        // Выполняем вход с некорректными данными через утилитный метод
        UTILsbase.performLogin(DataBase.INVALID_LOGIN, DataBase.INVALID_PASSWORD);

        // Проверяем, что отображается сообщение об ошибке
        
        Pagesdata.checkToastMessage(DataBase.ERROR_MESSAGE,decorView);
    }

    @After
    public void tearDown() {
        // Здесь можно добавить код для очистки после тестов, если это необходимо
    }
}