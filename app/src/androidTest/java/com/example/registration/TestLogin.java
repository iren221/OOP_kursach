package com.example.registration;

import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Test;

public class TestLogin {
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testLoginSuccess() {
        ActivityScenario<MainActivity> scenario = activityRule.getScenario();

        // Вводим правильный email и пароль

        onView(withId(R.id.login)).perform(typeText("Irina"));
        onView(withId(R.id.password)).perform(typeText("1234"), closeSoftKeyboard());

        // Нажимаем на кнопку "Войти"
        onView(withId(R.id.btn_login)).perform(click());

        // Проверяем, что мы перешли на главный экран
        onView(withId(R.id.pet_layout)).check(matches(isDisplayed()));
    }
}
