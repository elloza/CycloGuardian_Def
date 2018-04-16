package com.example.sergi.cycloguardian.Activities;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Chronometer;

import com.example.sergi.cycloguardian.Fragments.FragmentGallery;
import com.example.sergi.cycloguardian.Fragments.FragmentGaleryList;
import com.example.sergi.cycloguardian.Fragments.FragmentGraph;
import com.example.sergi.cycloguardian.Fragments.FragmentMap;
import com.example.sergi.cycloguardian.Models.Session;
import com.example.sergi.cycloguardian.R;
import com.example.sergi.cycloguardian.Services.MainService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StartActivity extends AppCompatActivity {

    // A reference to the service used to get location updates.
    private MainService mService = null;
    Chronometer chronometerSession;

    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MainService.LocalBinder binder = (MainService.LocalBinder) service;
            mService = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        //Iniciamos los fragments
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentGraph(), getString(R.string.title_fragment_graph));
        adapter.addFragment(new FragmentMap(), getString(R.string.title_fragment_map));
        adapter.addFragment(new FragmentGallery(), getString(R.string.title_fragment_image));
        adapter.addFragment(new FragmentGaleryList(), getString(R.string.title_fragment_gallery));
        viewPager.setAdapter(adapter);



        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        startService(new Intent(this, MainService.class));
        //Start the service
      /*  bindService(new Intent(this, MainService.class), mServiceConnection,
                Context.BIND_AUTO_CREATE);*/

        //Set the time of session start
        Session.getInstance().setSessionStart(new Date());

        //Start the chronometrer
        chronometerSession = (Chronometer) findViewById(R.id.chronometerSession);
        chronometerSession.setBase(SystemClock.elapsedRealtime());
        chronometerSession.start();

    }


    // Adapter for the viewpager using FragmentPagerAdapter
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
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

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public void stopSession(View view) {
        long elapsedMillis = 0;
        //TODO detener el servicio
        stopService(new Intent(this, MainService.class));

        //TODO detener timestamp y setearlo en la Session
        Session.getInstance().setSessionEnd(new Date());
        elapsedMillis = SystemClock.elapsedRealtime() - chronometerSession.getBase();
        chronometerSession.stop();
        Session.getInstance().setTimeElapsedSession(elapsedMillis);

        //TODO volcar la sessión al disco
        //TODO programar un trabajo subir Session al servidor (jobs)

        //Change activity
        Intent intent = new Intent(this, SummaryActivity.class);
        startActivity(intent);
    }

}
