package com.iwin.fragment01;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sxq on 2016/5/29.
 */
public class TabActivity extends AppCompatActivity {

    ViewPager vpContent;
    List<Fragment> fragments;
    List<String> tabTitles;
    public TabLayout tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(0,ActionBar.DISPLAY_HOME_AS_UP);

        vpContent = (ViewPager) findViewById(R.id.content_tab);
        tab = (TabLayout) findViewById(R.id.tab);

        tabTitles = new ArrayList<>();
        tabTitles.add("TAB");
        tabTitles.add("PHONE");
        tabTitles.add("CAMERA");

        fragments = new ArrayList<>();
        fragments.add(new FragmentTab());
        fragments.add(new FragmentPhone());
        fragments.add(new FragmentCamera());


        final TabAdapter a = new TabAdapter(getSupportFragmentManager(),fragments,tabTitles);

        vpContent.setAdapter(a);

        tab.setupWithViewPager(vpContent);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabTitles.add("123");
                tabTitles.add("123");
                tabTitles.add("123");
                fragments.add(new FragmentPhone());
                fragments.add(new FragmentPhone());
                fragments.add(new FragmentPhone());
                a.notifyDataSetChanged();

            }
        });

    }

    public static class TabAdapter extends FragmentPagerAdapter{

        List<Fragment> fragments;
        List<String> titles;
        FragmentManager fm;

        public TabAdapter(FragmentManager fm,List<Fragment> fragments,List<String> titles) {
            super(fm);
            this.fragments = fragments;
            this.titles = titles;
            this.fm = fm;
        }

        @Override
        public Fragment getItem(int position) {
            Log.d("!!!!", String.valueOf(fm.getBackStackEntryCount()));
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}
