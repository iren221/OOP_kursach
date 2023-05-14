package com.example.registration;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.content.Context;
import android.content.Intent;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
//@RunWith(AndroidJUnit4.class)
//public class ExampleInstrumentedTest {
//    @Test
//    public void useAppContext() {
//        // Context of the app under test.
//        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
//        assertEquals("com.example.registration", appContext.getPackageName());
//    }
//}

@RunWith(AndroidJUnit4.class)
public class TestAuth {
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testLoginSuccess() {
        mActivityRule.launchActivity(new Intent());

        // Ожидаем, что активность запустится
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Вводим правильный логин и пароль
        onView(withId(R.id.login)).perform(typeText("Kirill"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(click(), typeText("7890"), closeSoftKeyboard());

        // Нажимаем на кнопку "Войти"
        onView(withId(R.id.btn_login)).perform(click());

    }

    @Test
    public void testLoginIsEmpty() {
        mActivityRule.launchActivity(new Intent());

        // Ожидаем, что активность запустится
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Вводим некорректный логин и пароль
        onView(withId(R.id.login)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(click(), typeText("1234"), closeSoftKeyboard());

        // Нажимаем на кнопку "Войти"
        onView(withId(R.id.btn_login)).perform(click());

    }

    @Test
    public void testLoginUnCorrect() {
        mActivityRule.launchActivity(new Intent());

        // Ожидаем, что активность запустится
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Вводим некорректный логин и пароль
        onView(withId(R.id.login)).perform(replaceText("Ирина"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(click(), typeText("5678"), closeSoftKeyboard());

        // Нажимаем на кнопку "Войти"
        onView(withId(R.id.btn_login)).perform(click());

    }

    @Test
    public void testLoginSuc() {
        mActivityRule.launchActivity(new Intent());

        // Ожидаем, что активность запустится
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Вводим пароль, логин заполнен пользователем Kirill
        //onView(withId(R.id.login)).perform(replaceText("Ирина"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(click(), typeText("7890"), closeSoftKeyboard());

        // Нажимаем на кнопку "Войти"
        onView(withId(R.id.btn_login)).perform(click());

    }

    @Test
    public void testLoginUnSuc() {
        mActivityRule.launchActivity(new Intent());

        // Ожидаем, что активность запустится
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Вводим пароль, логин заполнен пользователем Kirill
        //onView(withId(R.id.login)).perform(replaceText("Ирина"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(click(), typeText("5678"), closeSoftKeyboard());

        // Нажимаем на кнопку "Войти"
        onView(withId(R.id.btn_login)).perform(click());

    }
}