package com.jnu.student;
import static com.jnu.student.Mark.marks;
import static com.jnu.student.Reward.rewardList;
import static com.jnu.student.Task.taskList0;
import static com.jnu.student.Task.taskList1;
import static com.jnu.student.Task.taskList2;
import android.content.Context;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.List;
public class MainActivity extends AppCompatActivity {
    static ViewPager2 bottomViewPager;
    private BottomNavigationView bottomMenu;
    Gson gson = new Gson();

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences = getSharedPreferences("my_app_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("tasklist0", gson.toJson(taskList0));
        editor.putString("tasklist1", gson.toJson(taskList1));
        editor.putString("tasklist2", gson.toJson(taskList2));
        editor.putString("rewardlist", gson.toJson(rewardList));
        editor.putInt("marks", marks);
        editor.apply();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomViewPager = findViewById(R.id.viewPager);
        bottomMenu = findViewById(R.id.navigation);
       NavigationAdapter bottomAdapter =new NavigationAdapter(getSupportFragmentManager(), getLifecycle());
        bottomViewPager.setAdapter(bottomAdapter);
        bottomViewPager.setOffscreenPageLimit(bottomAdapter.getItemCount());
        bottomViewPager.setUserInputEnabled(false);
        bottomMenu.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.navigation_home) {
                    bottomViewPager.setCurrentItem(0);
                    return true;
                }
                if (item.getItemId() == R.id.navigation_dashboard) {
                    bottomViewPager.setCurrentItem(1);
                    return true;
                }
                if (item.getItemId() == R.id.navigation_notifications) {
                    bottomViewPager.setCurrentItem(2);
                    return true;
                }
                return false;
            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences("my_app_preferences", Context.MODE_PRIVATE);
        String tasklist0 = sharedPreferences.getString("tasklist0",null);
        String tasklist1 = sharedPreferences.getString("tasklist1",null);
        String tasklist2 = sharedPreferences.getString("tasklist2",null);
        String rewardlist = sharedPreferences.getString("rewardlist",null);
        if (tasklist0 != null) {
            taskList0 = gson.fromJson(tasklist0, new TypeToken<List<Task>>() {}.getType());
        }
        if (tasklist1 != null) {
            taskList1 = gson.fromJson(tasklist1, new TypeToken<List<Task>>() {}.getType());
        }
        if (tasklist2 != null) {
            taskList2 = gson.fromJson(tasklist2, new TypeToken<List<Task>>() {}.getType());
        }
        if (rewardlist != null) {
            rewardList = gson.fromJson(rewardlist, new TypeToken<List<Reward>>() {}.getType());
        }
        marks = sharedPreferences.getInt("marks", 0);
        Calendar dailyCalendar = Calendar.getInstance();
        dailyCalendar.setTimeInMillis(System.currentTimeMillis());
        dailyCalendar.set(Calendar.HOUR_OF_DAY, 2);
        dailyCalendar.setTimeInMillis(System.currentTimeMillis());
        dailyCalendar.set(Calendar.DAY_OF_WEEK, 2);
        dailyCalendar.set(Calendar.HOUR_OF_DAY, 2);
    }
}
//package com.jnu.student;
//import static com.jnu.student.Mark.marks;
//import static com.jnu.student.Task.taskList0;
//import static com.jnu.student.Task.taskList1;
//import static com.jnu.student.Task.taskList2;
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.view.MenuItem;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager;
//import androidx.lifecycle.Lifecycle;
//import androidx.viewpager.widget.ViewPager;
//import androidx.viewpager2.adapter.FragmentStateAdapter;
//import androidx.viewpager2.widget.ViewPager2;
//
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//import com.google.android.material.tabs.TabLayout;
//import com.google.android.material.tabs.TabLayoutMediator;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import java.util.List;
//public class MainActivity extends AppCompatActivity {
//    static ViewPager2 viewPager;
//    private BottomNavigationView bottomNavigationView;
//    Gson gson = new Gson();
//    @Override
//    public void onPause() {
//        super.onPause();
//        SharedPreferences sharedPreferences = getSharedPreferences("my_app_preferences", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("tasklist0", gson.toJson(taskList0));
//        editor.putString("tasklist1", gson.toJson(taskList1));
//        editor.putString("tasklist2", gson.toJson(taskList2));
//        editor.putInt("marks", marks);
//        editor.apply();
//    }
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        viewPager=findViewById(R.id.viewPager);
//        bottomNavigationView = findViewById(R.id.navigation);
//        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                Fragment selectedFragment = null;
//                if(item.getItemId()==R.id.navigation_home)
//                    viewPager.setCurrentItem(0);
//                else if(item.getItemId()==R.id.navigation_dashboard)
//                    viewPager.setCurrentItem(3);
//                else if(item.getItemId()==R.id.navigation_notifications)
//                    viewPager.setCurrentItem(4);
//                return true;
//            }
//        });
//        viewPager = findViewById(R.id.viewPager);
//
//        viewPager.setAdapter(new PageViewFragmentAdapter(getSupportFragmentManager(), getLifecycle()));
//        SharedPreferences sharedPreferences = getSharedPreferences("my_app_preferences", Context.MODE_PRIVATE);
//        String tasklist0 = sharedPreferences.getString("tasklist0",null);
//        String tasklist1 = sharedPreferences.getString("tasklist1",null);
//        String tasklist2 = sharedPreferences.getString("tasklist2",null);
//        if (tasklist0 != null) {
//            taskList0 = gson.fromJson(tasklist0, new TypeToken<List<Task>>() {}.getType());
//        }
//        if (tasklist1 != null) {
//            taskList1 = gson.fromJson(tasklist1, new TypeToken<List<Task>>() {}.getType());
//        }
//        if (tasklist2 != null) {
//            taskList2 = gson.fromJson(tasklist2, new TypeToken<List<Task>>() {}.getType());
//        }
//        int marks = sharedPreferences.getInt("marks", 0);
//        Mark.marks = marks;
//    }
//
////    private class PageViewFragmentAdapter extends FragmentStateAdapter {
////        public PageViewFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
////            super(fragmentManager, lifecycle);
////        }
////
////        @NonNull
////        @Override
////        public Fragment createFragment(int position) {
////            switch (position) {
////                case 0:
////                    return DayTaskFragment.newInstance();
////                case 1:
////                    return WeekTaskFragment.newInstance();
////                case 2:
////                    return NormalTaskFragment.newInstance();
////                case 3:
////                    return RewardFragment.newInstance();
////                case 4:
////                    return MineFragment.newInstance();
////            }
////            return TaskFragment.newInstance();
////        }
////
////        @Override
////        public int getItemCount() {
////            return 5;
////        }
//    }
//}
