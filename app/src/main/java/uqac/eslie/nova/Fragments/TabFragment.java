package uqac.eslie.nova.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.net.MalformedURLException;

import uqac.eslie.nova.Adapter.ViewPagerAdapter;
import uqac.eslie.nova.R;

/**
 * Created by ESTEL on 19/05/2017.
 */

public class TabFragment extends Fragment {
    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    ViewPagerAdapter adapter;

    public  static TabFragment newInstance() {
        TabFragment fragment = new TabFragment();
        return fragment;
    }

    public TabFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_tab, container, false);
        viewPager =  root.findViewById(R.id.viewpager);
        try{
            setupViewPager(viewPager);
            tabLayout =  root.findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(viewPager);
            tabLayout.setTabTextColors(Color.WHITE, Color.WHITE);
        }
        catch (Exception e){

        }

        return root;
    }

    private  void setupViewPager(ViewPager viewPager) throws MalformedURLException {
        adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new MyCarPooling(), "Mes covoiturages");
        adapter.addFragment(new MyImages(), "Mes photos");
        adapter.addFragment(new ChartFragment(), "Notifications");
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout.setupWithViewPager(viewPager);

    }


}

