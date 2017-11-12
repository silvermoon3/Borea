package uqac.eslie.nova.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import uqac.eslie.nova.Helper.DataFetching.MyAsyncTaskJsonKP;
import uqac.eslie.nova.Helper.DataFetching.MyAsyncTaskJsonWeather;
import uqac.eslie.nova.Helper.DataFetching.MyAsyncTaskTxt;

import uqac.eslie.nova.R;


public class WeatherFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    MyAsyncTaskTxt myTxtTask;
    MyAsyncTaskJsonKP myJsonTask;
    MyAsyncTaskJsonWeather myJsonTask2;
    ArrayList<String[]> kp27Array = new ArrayList<String[]>();
    ArrayList<String[]> kp3Array = new ArrayList<String[]>();
    URL kp27Url;
    URL kp3Url;
    URL kp1Url;
    URL weather;

    public WeatherFragment() throws MalformedURLException {
        kp27Url = new URL("http://services.swpc.noaa.gov/text/27-day-outlook.txt");
        kp3Url = new URL("http://services.swpc.noaa.gov/text/3-day-forecast.txt");
        kp1Url = new URL("http://services.swpc.noaa.gov/products/noaa-estimated-planetary-k-index-1-minute.json");
        weather = new URL("https://api.apixu.com/v1/forecast.json?key=848095d955c54bfab29213656172810&q=48.421291,-71.068205&days=10");
        // TO DO : inject real GPS coordinates in the link
    }
    public void processFinish(ArrayList<String[]> result) {
        if(result.get(0)[0] == "27") {
            kp27Array = result;
            TextView vt = this.getView().findViewById(R.id.actuellement_kp_valeur);
            vt.setText(kp27Array.get(1)[3]);
        }
        else if(result.get(0)[0] == "3"){
            kp3Array = result;
            TextView vt = this.getView().findViewById(R.id.thisWeek_kp_valeur);
            vt.setText(kp3Array.get(1)[1]);
        }
    }
    public void processFinish(String result, String diff) {
    try {
            if (diff == "kp") {
                JSONArray jsonArray= new JSONArray(result);
            }

            else if (diff == "weather") {
                JSONObject jsonObject= new JSONObject(result);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        myTxtTask = new MyAsyncTaskTxt(this);
        myTxtTask.delegate = this;
        myTxtTask.execute(kp27Url);

        myTxtTask = new MyAsyncTaskTxt(this);
        myTxtTask.delegate = this;
        myTxtTask.execute(kp3Url);

        myJsonTask = new MyAsyncTaskJsonKP(this);
        myJsonTask.delegate = this;
        myJsonTask.execute(kp1Url);

        myJsonTask2 = new MyAsyncTaskJsonWeather(this);
        myJsonTask2.delegate = this;
        myJsonTask2.execute(weather);
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
