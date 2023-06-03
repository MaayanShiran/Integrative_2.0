package com.maayan.integrative_20.Adatpters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.maayan.integrative_20.Fragments.TabOne;
import com.maayan.integrative_20.Fragments.TabOne1;
import com.maayan.integrative_20.Fragments.TabTwo;

public class ViewPagerAdapter extends FragmentStateAdapter {


    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
      switch (position){
          case 0 : return new TabOne1();//
          case 1: return new TabTwo();
          default: return new TabOne1();//
      }
    }

    public Fragment getFragmentOne(){
        return new TabOne1();
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public void updateFragment(int position, Fragment fragment) {
        switch (position) {
            case 0:
                // Update TabOne fragment
                TabOne1 tabOne = (TabOne1) fragment;//
                // Perform any necessary updates on the TabOne fragment
                // ...
                break;
            case 1:
                // Update TabTwo fragment
                TabTwo tabTwo = (TabTwo) fragment;
                // Perform any necessary updates on the TabTwo fragment
                // ...
                break;
            default:
                break;
        }
        notifyDataSetChanged();
    }
}
