package ru.iteco.fmhandroid.ui;

import android.view.View;
import android.widget.Toast;

import androidx.test.espresso.Root;
import androidx.test.espresso.matcher.BoundedMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class CustomMatchers {

    // Matcher для Toast
    public static Matcher<Root> isToast() {
        return new BoundedMatcher<Root, Root>(Root.class) { // Исправлено: указание 'Root.class'
            @Override
            public void describeTo(Description description) {
                description.appendText("это toast");
            }

            @Override
            protected boolean matchesSafely(Root root) {
                // Проверяем, что корневой элемент отображается
                return root.getDecorView() != null && root.getDecorView().isShown();
            }
        };
    }
}