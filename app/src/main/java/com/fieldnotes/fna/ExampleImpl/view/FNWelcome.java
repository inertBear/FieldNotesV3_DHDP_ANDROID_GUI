package com.fieldnotes.fna.ExampleImpl.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.fieldnotes.fna.ExampleImpl.preferences.PreferenceFragment;
import com.fieldnotes.fna.R;

import java.util.ArrayList;
import java.util.List;

public class FNWelcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_welcome);
        //setup view pager and tab layout
        ViewPager viewPager = findViewById(R.id.viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FNAdd(),
                getResources().getString(R.string.ADD_NOTE_FRAGMENT_TITLE));
        adapter.addFragment(new FNSearch(),
                getResources().getString(R.string.SEARCH_NOTE_FRAGMENT_TITLE));
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_fna, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        View user_settings = findViewById(R.id.user_settings);
        switch (item.getItemId()) {
            case R.id.user_settings:
                user_settings.setVisibility(View.INVISIBLE);
                PreferenceFragment preferences = new PreferenceFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(android.R.id.content, preferences,
                                getResources().getString(R.string.PREFERENCE_FRAGMENT_TITLE))
                        .addToBackStack(null)
                        .commit();
                return true;
            case android.R.id.home:
                user_settings.setVisibility(View.VISIBLE);
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Adapter for the ViewPagerAdapter
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
