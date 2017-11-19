package uqac.eslie.nova.Helper.DataFetching;

import android.os.AsyncTask;

import java.net.URL;
import java.util.ArrayList;

import uqac.eslie.nova.Fragments.WeatherFragment;

/**
 * Created by eliea on 02/11/2017.
 */

public class MyAsyncTaskTxt extends AsyncTask<URL, Integer, ArrayList<String[]>> {
    public WeatherFragment delegate = null;

    public MyAsyncTaskTxt(WeatherFragment delegate) {
        this.delegate = delegate;
    }

    @Override
    protected ArrayList<String[]> doInBackground(URL... urls) {
        ArrayList<String[]> result = new ArrayList<String[]>();
        result = TXTParser.doIt(urls[0]);
        return result;
    }

    @Override
    protected void onPostExecute(ArrayList<String[]> result){
        delegate.processFinish(result);
    }
}
