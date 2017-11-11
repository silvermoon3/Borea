package uqac.eslie.nova.Fragments;

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
        viewPager = (ViewPager) root.findViewById(R.id.viewpager);
        try {
            setupViewPager(viewPager);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        tabLayout = (TabLayout) root.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        return root;
    }

    private  void setupViewPager(ViewPager viewPager) throws MalformedURLException {

        adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(Fragment.instantiate(getContext(), CarFragment.class.getName()), "Mes covoiturages");
        adapter.addFragment(new HomeFragment(), "Mes photos");
        adapter.addFragment(new WeatherFragment(), "Fresh");
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout.setupWithViewPager(viewPager);

    }




}

