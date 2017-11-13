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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
    ArrayList<String[]> cloudArrayTodayAndTomorrow = new ArrayList<String[]>();
    ArrayList<String[]> kpArrayTodayAndTomorrow = new ArrayList<String[]>();
    URL kp27Url;
    URL kp3Url;
    URL kp1Url;
    URL weather;

    public WeatherFragment() throws MalformedURLException {
        kp27Url = new URL("http://services.swpc.noaa.gov/text/27-day-outlook.txt");
        kp3Url = new URL("http://services.swpc.noaa.gov/text/3-day-forecast.txt");
        kp1Url = new URL("http://services.swpc.noaa.gov/products/noaa-estimated-planetary-k-index-1-minute.json");
        weather = new URL("https://api.apixu.com/v1/forecast.json?key=848095d955c54bfab29213656172810&q=48.421291,-71.068205&days=7");
        // TO DO : inject real GPS coordinates in the link
    }
    public void processFinish(ArrayList<String[]> result) {
        if(result.get(0)[0] == "27") {
            kp27Array = result;
            //TextView vt = this.getView().findViewById(R.id.actuellement_kp_valeur);
            //vt.setText(kp27Array.get(1)[3]);
        }
        else if(result.get(0)[0] == "3"){
            ArrayList<String[]> kp3Array = new ArrayList<String[]>();
            kp3Array = result;

            //aujourd'hui
            kpArrayTodayAndTomorrow.add(new String[]{"19 - 22", kp3Array.get(1)[1]});
            kpArrayTodayAndTomorrow.add(new String[]{"22 - 01", kp3Array.get(2)[1]});
            kpArrayTodayAndTomorrow.add(new String[]{"01 - 04", kp3Array.get(3)[1]});
            kpArrayTodayAndTomorrow.add(new String[]{"04 - 07", kp3Array.get(4)[1]});
            kpArrayTodayAndTomorrow.add(new String[]{"07 - 10", kp3Array.get(5)[1]});
            kpArrayTodayAndTomorrow.add(new String[]{"10 - 13", kp3Array.get(6)[1]});
            kpArrayTodayAndTomorrow.add(new String[]{"13 - 16", kp3Array.get(7)[1]});
            kpArrayTodayAndTomorrow.add(new String[]{"16 - 19", kp3Array.get(8)[1]});

            //demain
            kpArrayTodayAndTomorrow.add(new String[]{"19 - 22", kp3Array.get(1)[2]});
            kpArrayTodayAndTomorrow.add(new String[]{"22 - 01", kp3Array.get(2)[2]});
            kpArrayTodayAndTomorrow.add(new String[]{"01 - 04", kp3Array.get(3)[2]});
            kpArrayTodayAndTomorrow.add(new String[]{"04 - 07", kp3Array.get(4)[2]});
            kpArrayTodayAndTomorrow.add(new String[]{"07 - 10", kp3Array.get(5)[2]});
            kpArrayTodayAndTomorrow.add(new String[]{"10 - 13", kp3Array.get(6)[2]});
            kpArrayTodayAndTomorrow.add(new String[]{"13 - 16", kp3Array.get(7)[2]});
            kpArrayTodayAndTomorrow.add(new String[]{"16 - 19", kp3Array.get(8)[2]});
        }
    }

    public String UTC(String currentTime){
        String UTCTime = "";
        int minutes = Integer.parseInt(currentTime.substring(3,5));
        int hours = Integer.parseInt(currentTime.substring(0,2));

        if(minutes - 5 < 0) {
            hours--;
            minutes += 60 - 5;
        }
        else{
            minutes -= 5;
        }

        if(hours + 5 >= 24){
            //day--;
            hours -= 24 - 5;
        }
        else{
            hours += 5;
        }
        String hour = "";
        if(hours < 10){
            hour = "0" + String.valueOf(hours);
        }
        else{
            hour = String.valueOf(hours);
        }
        String minute = "";
        if(minutes <10){
            minute = "0" + String.valueOf(minutes);
        }
        else{
            minute = String.valueOf(minutes);
        }

        UTCTime = hour + ":" + minute;
        return UTCTime;
    }
    public void processFinish(String result, String diff) {
    try {
            if (diff == "kp") {

                //actuellement
                JSONArray jsonArray= new JSONArray(result);
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                String currentTime = sdf.format(new Date());
                String currentUTC = UTC(currentTime);
                int y = 0;
                while (y < jsonArray.length()){
                    JSONArray jsonArray2 = jsonArray.getJSONArray(y);
                    String time_tag = jsonArray2.getString(0);
                    if (time_tag.contains(currentUTC)){
                        String kp = jsonArray2.getString(1);
                        TextView vt = this.getView().findViewById(R.id.actuellement_kp_valeur);
                        vt.setText(kp);
                        break;
                    }
                    y++;
                }
            }

            else if (diff == "weather") {
                JSONObject jsonObject= new JSONObject(result);

                // actuellement
                JSONObject current = jsonObject.getJSONObject("current");
                String cloudNow = current.getString("cloud");
                TextView vt = this.getView().findViewById(R.id.actuellement_couverture_valeur);
                vt.setText(cloudNow + "%");

                // aujourd'hui
                JSONObject forecast = jsonObject.getJSONObject("forecast");
                JSONArray forecastday = forecast.getJSONArray("forecastday");
                JSONObject firstDay = forecastday.getJSONObject(0); //day 0
                JSONArray hour = firstDay.getJSONArray("hour");
                int y = 0;
                while (y < hour.length()){
                    JSONObject thatHour = hour.getJSONObject(y);
                    String time = thatHour.getString("time");
                    if (time.contains("00:00") || time.contains("03:00") || time.contains("06:00") || time.contains("09:00") || time.contains("12:00") || time.contains("15:00") || time.contains("18:00") || time.contains("21:00")){
                        String cloud = thatHour.getString("cloud");
                        cloudArrayTodayAndTomorrow.add(new String[]{time.substring(time.length()-5,time.length()), cloud});
                    }
                    y++;
                }

                // demain
                JSONObject secondDay = forecastday.getJSONObject(1); //day 1
                hour = firstDay.getJSONArray("hour");
                y = 0;
                while (y < hour.length()){
                    JSONObject thatHour = hour.getJSONObject(y);
                    String time = thatHour.getString("time");
                    if (time.contains("00:00") || time.contains("03:00") || time.contains("06:00") || time.contains("09:00") || time.contains("12:00") || time.contains("15:00") || time.contains("18:00") || time.contains("21:00")){
                        String cloud = thatHour.getString("cloud");
                        cloudArrayTodayAndTomorrow.add(new String[]{time.substring(time.length()-5,time.length()), cloud});
                    }
                    y++;
                }
                //cloudArrayTodayAndTomorrow.add(new String[]{"the end", "LUL"}); // debug
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
