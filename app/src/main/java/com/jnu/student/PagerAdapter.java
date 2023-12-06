package com.jnu.student;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
public class PagerAdapter extends FragmentStateAdapter {
    public PagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
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
            default:
                return null;
        }
    }
    @Override
    public int getItemCount() {
        return 3;
    }
}
