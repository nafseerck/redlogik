package com.redlogic;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.redlogic", appContext.getPackageName());
    }

    @Test
    public void dateCheckisCorrect(){
        assertEquals(isDateMatches("2020-09-10 11:37:41"),true);
    }

    private boolean isDateMatches(String updatedOn) {
        Date orderDate = convertStringToDate(updatedOn);
        if (orderDate == null){
            return false;
        }else {
            Date currentDate = Calendar.getInstance().getTime();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            if (dateFormat.format(orderDate).matches(dateFormat.format(currentDate))){
                return true;
            }else {
                return false;
            }
        }
    }

    private Date convertStringToDate(String updatedOn) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = format.parse(updatedOn);
            return date;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
