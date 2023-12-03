package com.jnu.student;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
public class NavigationAdapter extends FragmentStateAdapter {
    public NavigationAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new TaskSelectionFragment();
            case 1:
                return new RewardFragment();
            case 2:
                return new MineFragment();
            default:
                return null;
        }
    }
    @Override
    public int getItemCount() {
        return 3;
    }
}
