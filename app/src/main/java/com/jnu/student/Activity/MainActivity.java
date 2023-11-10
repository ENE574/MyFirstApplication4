package com.jnu.student.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jnu.student.Fragment.MineFragment;
import com.jnu.student.Fragment.RewardFragment;
import com.jnu.student.Fragment.TaskFragment;
import com.jnu.student.R;

public class MainActivity extends AppCompatActivity   implements BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {
    ViewPager viewPager;
    BottomNavigationView mNavigationView;
    TaskFragment taskFragments = new TaskFragment();
    RewardFragment  rewardFragments = new RewardFragment();
    MineFragment mineFragments =new MineFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //页面初始化导航栏
        init();
    }

    private void init() {

        //获取页面标签对象
        viewPager = findViewById(R.id.viewPager);
        viewPager.addOnPageChangeListener(this);
        mNavigationView = findViewById(R.id.navigation);
        mNavigationView.setOnNavigationItemSelectedListener(this);


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

    //实现接口的相关方法  implements上面两个方法后 alt+enter就会弹出这些接口，直接回车实现他们
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mNavigationView.getMenu().getItem(position).setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        viewPager.setCurrentItem(menuItem.getOrder());
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_index_msg, menu);
        return super.onCreateOptionsMenu(menu);

    }
}


//package com.jnu.student;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager;
//import androidx.lifecycle.Lifecycle;
//import androidx.viewpager2.adapter.FragmentStateAdapter;
//import androidx.viewpager2.widget.ViewPager2;
//import android.os.Bundle;
//import com.google.android.material.tabs.TabLayout;
//import com.google.android.material.tabs.TabLayoutMediator;
//import com.jnu.student.Fragment.BrowserFragment;
//import com.jnu.student.Fragment.ShopItemFragment;
//import com.jnu.student.Fragment.TecentMapFragment;
//
//public class MainActivity extends AppCompatActivity {
//    public class PageViewFragmentAdapter extends FragmentStateAdapter {
//        public PageViewFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
//            super(fragmentManager, lifecycle);
//        }
//        @NonNull
//        @Override
//        public Fragment createFragment(int position) {
//            switch(position)
//            {
//                case 0:
//                    return ShopItemFragment.newInstance();
//                case 1:
//                    return  TecentMapFragment.newInstance();
//                case 2:
//                    return BrowserFragment.newInstance();
//            }
//            return ShopItemFragment.newInstance();
//        }
//        @Override
//        public int getItemCount() {
//            return 3;
//        }
//    }
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.activity_main);
//        ViewPager2 viewPager2Main= findViewById(R.id.viewpager2_main);
//        viewPager2Main.setAdapter(new PageViewFragmentAdapter(getSupportFragmentManager(),getLifecycle()));
//
//        TabLayout tabLayout=findViewById(R.id.tablayout_header);
//        TabLayoutMediator tabLayoutMediator=new TabLayoutMediator(tabLayout, viewPager2Main, new TabLayoutMediator.TabConfigurationStrategy() {
//            @Override
//            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
//                switch(position)
//                {
//                    case 0:
//                        tab.setText(R.string.tab_caption_1_item);
//                        break;
//                    case 1:
//                        tab.setText(R.string.tab_caption_2_map);
//                        break;
//                    case 2:
//                        tab.setText(R.string.tab_caption_3_browser);
//                        break;
//                }
//            }
//        });
//        tabLayoutMediator.attach();
//    }
//}