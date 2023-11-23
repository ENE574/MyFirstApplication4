package com.jnu.student;
import static com.jnu.student.Mark.marks;
import static com.jnu.student.Task.taskList0;
import static com.jnu.student.Task.taskList1;
import static com.jnu.student.Task.taskList2;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.List;
public class MainActivity extends AppCompatActivity {
//    public static ViewPager viewPager;
    static ViewPager2 viewPager;
    private TabLayout tabLayout;
    private BottomNavigationView bottomNavigationView;
    Gson gson = new Gson();
    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences = getSharedPreferences("my_app_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("tasklist0", gson.toJson(taskList0));
        editor.putString("tasklist1", gson.toJson(taskList1));
        editor.putString("tasklist2", gson.toJson(taskList2));
        editor.putInt("marks", marks);
        editor.apply();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences("my_app_preferences", Context.MODE_PRIVATE);
        String tasklist0 = sharedPreferences.getString("tasklist0",null);
        String tasklist1 = sharedPreferences.getString("tasklist1",null);
        String tasklist2 = sharedPreferences.getString("tasklist2",null);
        if (tasklist0 != null) {
            taskList0 = gson.fromJson(tasklist0, new TypeToken<List<Task>>() {}.getType());
        }
        if (tasklist1 != null) {
            taskList1 = gson.fromJson(tasklist1, new TypeToken<List<Task>>() {}.getType());
        }
        if (tasklist2 != null) {
            taskList2 = gson.fromJson(tasklist2, new TypeToken<List<Task>>() {}.getType());
        }
        int marks = sharedPreferences.getInt("marks", 0);
        Mark.marks = marks;
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                if(item.getItemId()==R.id.navigation_home)
                    viewPager.setCurrentItem(0);
                else if(item.getItemId()==R.id.navigation_dashboard)
                    viewPager.setCurrentItem(3);
                else if(item.getItemId()==R.id.navigation_notifications)
                    viewPager.setCurrentItem(4);
                return true;
            }
        });
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        viewPager.setAdapter(new PageViewFragmentAdapter(getSupportFragmentManager(), getLifecycle()));

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText("每日任务");
                            break;
                        case 1:
                            tab.setText("每周任务");
                            break;
                        case 2:
                            tab.setText("普通任务");
                            break;
                        case 3:
                            tab.setText("奖励");
                            break;
                        case 4:
                            tab.setText("我的");
                            break;
                    }
                }).attach();
    }

    private class PageViewFragmentAdapter extends FragmentStateAdapter {
        public PageViewFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return DayTaskFragment.newInstance();
                case 1:
                    return WeekTaskFragment.newInstance();
                case 2:
                    return NormalTaskFragment.newInstance();
                case 3:
                    return RewardFragment.newInstance();
                case 4:
                    return MineFragment.newInstance();
            }
            return TaskFragment.newInstance();
        }

        @Override
        public int getItemCount() {
            return 5;
        }
    }
}
