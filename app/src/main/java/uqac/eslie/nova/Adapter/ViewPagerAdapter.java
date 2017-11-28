package uqac.eslie.nova.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import uqac.eslie.nova.Fragments.HomeFragment;
import uqac.eslie.nova.Fragments.MyCarPooling;
import uqac.eslie.nova.Fragments.MyImages;


/**
 * Created by ESTEL on 18/05/2017.
 */


public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();


    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return new MyCarPooling();
        if (position == 1)
            return new MyImages();
        if (position == 2)
            return new HomeFragment();


        return null;
        //return mFragmentList.get(position);
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