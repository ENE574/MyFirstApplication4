package com.jnu.student;
import static com.jnu.student.Task.taskList0;
import static com.jnu.student.Task.taskList1;
import static com.jnu.student.Task.taskList2;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
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
        viewPager.setCurrentItem(1);
        viewPager.setCurrentItem(0);
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
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int taskType = TaskSelectionFragment.viewPager.getCurrentItem();
        int fragmentType = MainActivity.bottomViewPager.getCurrentItem();
        if (fragmentType == 0) {
            if (taskType == 0) {
                taskList0.remove(item.getOrder());
                DayTaskFragment.adapter.notifyItemRemoved(item.getOrder());
            } else if (taskType == 1) {
                taskList1.remove(item.getOrder());
                WeekTaskFragment.adapter.notifyItemRemoved(item.getOrder());
            } else if (taskType == 2) {
                taskList2.remove(item.getOrder());
                NormalTaskFragment.adapter.notifyItemRemoved(item.getOrder());
            }
        }
        return super.onContextItemSelected(item);
    }
}
