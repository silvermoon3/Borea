package uqac.eslie.nova.Helper.DataFetching;

import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import uqac.eslie.nova.Fragments.WeatherFragment;

import static uqac.eslie.nova.Helper.otherUtils.UTC;


/**
 * Created by eliea on 02/11/2017.
 */

public class MyAsyncTaskJsonKP extends AsyncTask<URL, Integer, ArrayList<String[]>> {
    public WeatherFragment delegate = null;

    public MyAsyncTaskJsonKP(WeatherFragment delegate) {
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

        //actuellement
        JSONArray jsonArray= new JSONArray(jsonStr);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String currentTime = sdf.format(new Date());
        String currentUTC = UTC(currentTime);
        int y = 0;
        while (y < jsonArray.length()){
            JSONArray jsonArray2 = jsonArray.getJSONArray(y);
            String time_tag = jsonArray2.getString(0);
            if (time_tag.contains(currentUTC)){
                String kp = jsonArray2.getString(1);
                //TextView vt = this.getView().findViewById(R.id.actuellement_kp_valeur);
                //vt.setText(kp);
                res.add(new String[]{kp});
                break;
            }
            y++;
        }
        return res;
    }

    @Override
    protected void onPostExecute(ArrayList<String[]> result){
        delegate.processFinish(result, "kp");
    }
}
