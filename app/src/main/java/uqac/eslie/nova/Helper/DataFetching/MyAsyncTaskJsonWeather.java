package uqac.eslie.nova.Helper.DataFetching;

import android.os.AsyncTask;

import java.net.URL;

import uqac.eslie.nova.Fragments.WeatherFragment;

/**
 * Created by eliea on 02/11/2017.
 */

public class MyAsyncTaskJsonWeather extends AsyncTask<URL, Integer, String> {
    public WeatherFragment delegate = null;

    public MyAsyncTaskJsonWeather(WeatherFragment delegate) {
        this.delegate = delegate;
    }

    @Override
    protected String doInBackground(URL... urls) {
        //ArrayList<String[]> result = new ArrayList<String[]>();
        //result = TXTParser.doIt(urls[0]);
        //return result;
        JSONParser http = new JSONParser();
        URL url = urls[0];
        String jsonStr = http.makeServiceCall(url.toString());
        return jsonStr;
    }

    @Override
    protected void onPostExecute(String res){
        delegate.processFinish(res, "weather");
    }
}
