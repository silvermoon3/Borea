package uqac.eslie.nova.Helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import uqac.eslie.nova.Fragments.AccountFragment;

/**
 * Created by ESTEL on 04/11/2017.
 */

public  class BitmapHelper extends AsyncTask<String, Integer, Bitmap> {
    private AccountFragment delegate = null;
    private String src;


    public BitmapHelper(AccountFragment delegate, String src){
        this.delegate = delegate;
        this.src = src;

    }




    public Bitmap doInBackground(String... src) {
        try {
            URL url = new URL(src[0]);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Bitmap result){
        delegate.processFinish(result);
    }

}
