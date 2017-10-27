package uqac.eslie.nova;


import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import uqac.eslie.nova.Fragments.AccountFragment;
import uqac.eslie.nova.Fragments.AddCarpoolingFragment;
import uqac.eslie.nova.Fragments.CarFragment;
import uqac.eslie.nova.Fragments.HomeFragment;
import uqac.eslie.nova.Fragments.MapFragment;
import uqac.eslie.nova.Fragments.WeatherFragment;
import uqac.eslie.nova.Helper.Helper_NavigationBottomBar;

public class MainActivity extends AppCompatActivity
    implements    HomeFragment.clickAddCarpooling
{

    Fragment fragment = null;
    Fragment home;
    Fragment weather;
    Fragment car;
    Fragment map;
    Fragment account;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    transaction.replace(R.id.content, new HomeFragment()).commit();
                    transaction.addToBackStack("home");
                    return true;
                case R.id.navigation_weather:
                    transaction.replace(R.id.content, weather).commit();
                    transaction.addToBackStack("weather");
                   // transaction.replace(R.id.content, new WeatherFragment()).commit();
                    return true;
                case R.id.navigation_find_a_car:
                    transaction.replace(R.id.content, car).commit();
                    transaction.addToBackStack("car");
                    return true;
                case R.id.navigation_place:
                    transaction.replace(R.id.content, map).commit();
                    transaction.addToBackStack("map");
                    return true;
                case R.id.navigation_account:
                    transaction.replace(R.id.content, account).commit();
                    transaction.addToBackStack("account");
                    return true;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, "TAG").commit();
            return true;
        }

    };

    public void switchFragment(Fragment f){

        Bundle args = new Bundle();
        //args.putInt(ArticleFragment.ARG_POSITION, position);
        f.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.content, f).commit();
        transaction.addToBackStack("test");


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // startActivity(new Intent(this, LoginActivity.class));
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        home = new HomeFragment();
        weather= new WeatherFragment();
        car= new CarFragment();
        map = new MapFragment();
        account = new AccountFragment();
        transaction.replace(R.id.content, new HomeFragment()).commit();
        transaction.disallowAddToBackStack();
        Helper_NavigationBottomBar helper = new Helper_NavigationBottomBar();

        helper.disableShiftMode(navigation);
        calculateHashKey("uqac.eslie.nova");

    }


    @Override
    protected void onPause(){
        super.onPause();
        //Sauvegarder les données

    }
    @Override
    public void onCarPoolingClick() {
        startActivity(new Intent(MainActivity.this, addCarPooling.class));
      //  AddCarpoolingFragment fragment = new AddCarpoolingFragment();
       // switchFragment(fragment);

    }


    private void calculateHashKey(String yourPackageName) {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    yourPackageName,
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:",
                        Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }






}
