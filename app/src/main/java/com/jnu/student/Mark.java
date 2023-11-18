package com.jnu.student;
import android.app.Application;
public class Mark extends Application {
    public static int marks = 0;
    public int getMarks() {
        return marks;
    }
}
