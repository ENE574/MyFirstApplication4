package com.jnu.student;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
public class TaskSelectionFragment extends Fragment {
    static ViewPager2 viewPager;
    public TaskSelectionFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_task_selection, container, false);
        viewPager = rootView.findViewById(R.id.viewPager);
        TabLayout tabLayout = rootView.findViewById(R.id.tabLayout);
        PagerAdapter pagerAdapter = new PagerAdapter(requireActivity().getSupportFragmentManager(), getLifecycle());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(pagerAdapter.getItemCount());
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
                    }
                }).attach();

        return rootView;
    }
}
