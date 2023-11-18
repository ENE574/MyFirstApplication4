package com.jnu.student;
import android.os.Bundle;
import android.view.Menu;
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
public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private TabLayout tabLayout;

    TaskFragment taskFragments = new TaskFragment();
    RewardFragment  rewardFragments = new RewardFragment();
    MineFragment mineFragments =new MineFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), getLifecycle()));
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
                            tab.setText("副本任务");
                            break;
                    }
                }).attach();
    }
    private void init() {

        //获取页面标签对象
        viewPager = findViewById(R.id.viewPager);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                // 页面切换时的处理逻辑
            }
        });
        class PageViewFragmentAdapter extends FragmentStateAdapter {
            public PageViewFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
                super(fragmentManager, lifecycle);
            }
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                switch(position)
                {
                    case 0:
                        return TaskFragment.newInstance();
                    case 1:
                        return  RewardFragment.newInstance();
                    case 2:
                        return MineFragment.newInstance();
                }
                return TaskFragment.newInstance();
            }
            @Override
            public int getItemCount() {
                return 3;
            }
        }
    }
}