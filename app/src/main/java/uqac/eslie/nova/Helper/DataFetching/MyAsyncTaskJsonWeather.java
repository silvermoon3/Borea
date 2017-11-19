package uqac.eslie.nova.Helper.DataFetching;

import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

import uqac.eslie.nova.Fragments.WeatherFragment;
import uqac.eslie.nova.R;

/**
 * Created by eliea on 02/11/2017.
 */

public class MyAsyncTaskJsonWeather extends AsyncTask<URL, Integer, ArrayList<String[]>> {
    public WeatherFragment delegate = null;

    public MyAsyncTaskJsonWeather(WeatherFragment delegate) {
        this.delegate = delegate;
    }

    @Override
    protected ArrayList<String[]> doInBackground(URL... urls) {
        ArrayList<String[]> result = new ArrayList<String[]>();
        //result = TXTParser.doIt(urls[0]);
        //return result;
        JSONParser http = new JSONParser();
        URL url = urls[0];
        String jsonStr = http.makeServiceCall(url.toString());
        try {
            result = extractData(jsonStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    private ArrayList<String[]> extractData(String jsonStr) throws JSONException {
        ArrayList<String[]> res = new ArrayList<String[]>();

        JSONObject jsonObject= new JSONObject(jsonStr);
        // actuellement
        JSONObject current = jsonObject.getJSONObject("current");
        String cloudNow = current.getString("cloud");
        res.add(new String[]{"now", cloudNow});

        // aujourd'hui
        JSONObject forecast = jsonObject.getJSONObject("forecast");
        JSONArray forecastday = forecast.getJSONArray("forecastday");
        JSONObject firstDay = forecastday.getJSONObject(0); //day 0
        JSONArray hour = firstDay.getJSONArray("hour");
        int y = 0;
        while (y < hour.length()){
            JSONObject thatHour = hour.getJSONObject(y);
            String time = thatHour.getString("time");
            if (time.contains("01:00") || time.contains("04:00") || time.contains("07:00") || time.contains("10:00") || time.contains("13:00") || time.contains("16:00") || time.contains("19:00") || time.contains("22:00")){
                String cloud = thatHour.getString("cloud");
                res.add(new String[]{time.substring(time.length()-5,time.length()), cloud});
            }
            y++;
        }

        // demain
        JSONObject secondDay = forecastday.getJSONObject(1); //day 1
        hour = secondDay.getJSONArray("hour");
        y = 0;
        while (y < hour.length()){
            JSONObject thatHour = hour.getJSONObject(y);
            String time = thatHour.getString("time");
            if (time.contains("01:00") || time.contains("04:00") || time.contains("07:00") || time.contains("10:00") || time.contains("13:00") || time.contains("16:00") || time.contains("19:00") || time.contains("22:00")){
                String cloud = thatHour.getString("cloud");
                res.add(new String[]{time.substring(time.length()-5,time.length()), cloud});
            }
            y++;
        }

        // semaine
        int i = 0;
        float cloud = 0;
        while(i < forecastday.length()){
            JSONObject theDay = forecastday.getJSONObject(i);
            hour = theDay.getJSONArray("hour");
            cloud = 0;
            y = 0;
            while(y < hour.length()){
                JSONObject thatHour = hour.getJSONObject(y);
                String time = thatHour.getString("time");
                if (time.contains("18:00") || time.contains("19:00") || time.contains("20:00") || time.contains("21:00") || time.contains("22:00") || time.contains("23:00") || time.contains("00:00") || time.contains("01:00") || time.contains("02:00") || time.contains("03:00") || time.contains("04:00") || time.contains("05:00")) {
                    String theCloud = thatHour.getString("cloud");
                    cloud += Float.parseFloat(theCloud);
                }
                y++;
            }
            cloud /= 12;
            res.add(new String[]{Float.toString((int)cloud)});
            i++;
        }

        return res;
    }

    @Override
    protected void onPostExecute(ArrayList<String[]> res){
        delegate.processFinish(res, "weather");
    }
}
