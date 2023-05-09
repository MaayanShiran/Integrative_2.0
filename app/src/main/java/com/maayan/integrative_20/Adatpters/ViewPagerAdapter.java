package com.maayan.integrative_20.Adatpters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.maayan.integrative_20.Fragments.TabOne;
import com.maayan.integrative_20.Fragments.TabTwo;

public class ViewPagerAdapter extends FragmentStateAdapter {


    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
      switch (position){
          case 0 : return new TabOne();
          case 1: return new TabTwo();
          default: return new TabOne();
      }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
