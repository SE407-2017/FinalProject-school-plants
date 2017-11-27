package com.example.tony.finalproject_plants;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.hamcrest.Matchers.not;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;

import static com.example.tony.finalproject_plants.R.id.txt_find;
import static com.example.tony.finalproject_plants.R.id.search_text;
import static com.example.tony.finalproject_plants.R.id.search_button;
import static com.example.tony.finalproject_plants.R.id.results;

/**
 * Created by hu on 2017/11/21.
 */

@RunWith(AndroidJUnit4.class)
public class findclassInstrumentationTest {
    private static final List<Plant> plantList = Plant.getAllPlants();

    private Context context = InstrumentationRegistry.getTargetContext();

    @Rule
    public ActivityTestRule mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void searchSuccess() {
        onView(withId(txt_find)).perform(click());
        for(Plant plant:plantList) {
            onView(withId(search_text)).perform(clearText(), typeText(plant.getName()), closeSoftKeyboard());
            onView(withId(search_button)).perform(click());
            onView(withId(results)).check(matches(withText(getDescription(plant.getName()))));
            pressBack();
        }
    }

    @Test
    public void searchFailed() {
        onView(withId(txt_find)).perform(click());
        onView(withId(search_text)).perform(typeText("Not included."), closeSoftKeyboard());
        onView(withId(search_button)).perform(click());
        onView(withText("No Result!")).inRoot(withDecorView(not(mActivityRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private String getDescription(String name){
        FileUtils fu = new FileUtils(context);
        return fu.getPlantDescription(Plant.getPlantByName(name));
    }
}