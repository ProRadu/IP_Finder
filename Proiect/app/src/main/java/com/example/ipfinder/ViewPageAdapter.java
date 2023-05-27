package com.example.ipfinder;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPageAdapter extends FragmentStateAdapter {
    public ViewPageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new ProfileFragment();
            case 1: return new LocationFinderFragment();
            case 2: return new TutorialFragment();
            default: return new ProfileFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
