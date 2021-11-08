package com.example.bottomapp.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.bottomapp.Beat;
import com.example.bottomapp.helpers.Databaser;
import com.example.bottomapp.PageFragment;
import com.example.bottomapp.R;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class


FeedFragment extends Fragment {




    static final String TAG = "myLogs";
    static final int PAGE_COUNT = 10;

    ViewPager2 pager;
    RecyclerView.Adapter adapter;

    Databaser databaser;


    public ArrayList<Beat> beats = new ArrayList<Beat>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_feed, container, false);


        pager = (ViewPager2) root.findViewById(R.id.playerViewPager);
        adapter = new MyFragmentStateAdapter(getChildFragmentManager(), getLifecycle());
        pager.setAdapter(adapter);

        databaser = new Databaser();

        databaser.getAllBeatsArray(beats -> {
//
            //.i("KEK", "Lambda. beats = " + beats + "; this.beats = " + this.beats);
            Collections.shuffle(beats);
            this.beats = beats;
            adapter.notifyDataSetChanged();
//
        });

        //beats.add(new Beat("333", "nightcore mode", "https://firebasestorage.googleapis.com/v0/b/beaton-4a6c6.appspot.com/o/beats%2F0da52329-f1c2-4831-9a73-cda1fa56b618.mp3?alt=media", "indian", "lol", "https://firebasestorage.googleapis.com/v0/b/beaton-4a6c6.appspot.com/o/images%2Fcovers%2F03c4a1ac-41d1-41a4-bb61-b7236c4660ca.png?alt=media"));
        //beats.add(new Beat("111", "entris", "https://beaton.netlify.app/123.mp3", "virus", "wow", "https://sun9-12.userapi.com/impg/A3cKJ1pu39MaU35a3YyVRzvRSDjmaOtLZ9njlw/2tzJK_ZhT_w.jpg?size=2160x2160&quality=96&sign=2e67bf50ac61ced4d721877ba30210d8&type=album"));

        pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected, position = " + position);

                PageFragment.Play(beats.get(position));
            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        return root;

    }


    private class MyFragmentStateAdapter extends FragmentStateAdapter {

        public MyFragmentStateAdapter(FragmentManager fm, Lifecycle lifecycle) {
            super(fm, lifecycle);
        }


        @NonNull
        @Override
        public Fragment createFragment(int position) {

            return PageFragment.newInstance(beats.get(position));
            //return PageFragment.newInstance(new Beat("111", "entris", "https://beaton.netlify.app/123.mp3", "virus", "wow", "https://sun9-12.userapi.com/impg/A3cKJ1pu39MaU35a3YyVRzvRSDjmaOtLZ9njlw/2tzJK_ZhT_w.jpg?size=2160x2160&quality=96&sign=2e67bf50ac61ced4d721877ba30210d8&type=album"));
        }

        @Override
        public int getItemCount() {

            //Log.i("KEK", "GetItemsCount. beats = " + beats + ".");
            return beats.size();

        }
    }
}
