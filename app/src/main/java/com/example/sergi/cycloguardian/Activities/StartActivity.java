package com.example.sergi.cycloguardian;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.os.IBinder;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.example.sergi.cycloguardian.Fragments.FragmentGaleria;
import com.example.sergi.cycloguardian.Fragments.FragmentGrafica;
import com.example.sergi.cycloguardian.Fragments.FragmentMap;
import com.example.sergi.cycloguardian.Services.MainService;
import com.example.sergi.cycloguardian.Utils.Util;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

public class StartActivity extends AppCompatActivity {

    // A reference to the service used to get location updates.
    private MainService mService = null;

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
        adapter.addFragment(new FragmentGrafica(), "GRÁFICO");
        adapter.addFragment(new FragmentMap(), "MAPA");
        adapter.addFragment(new FragmentGaleria(), "GALERIA");
        viewPager.setAdapter(adapter);

        //Creación del EventBus
        EventBus myEventBus = EventBus.getDefault();

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

        //startService(new Intent(this, MainService.class));
        //Start the service
        bindService(new Intent(this, MainService.class), mServiceConnection,
                Context.BIND_AUTO_CREATE);

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

}
