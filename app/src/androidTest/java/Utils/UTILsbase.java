package Utils;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import ru.iteco.fmhandroid.R;

public class UTILsbase {

    public static void performLogin(String login, String password) {
        onView(withId(R.id.login_text_input_layout)).perform(click());
        onView(withId(R.id.login)).perform(typeText(login));
        onView(withId(R.id.password_text_input_layout)).perform(click());
        onView(withId(R.id.password)).perform(typeText(password), closeSoftKeyboard());
        onView(withId(R.id.enter_button)).perform(click());
    }

    // Другие утилитные методы
}