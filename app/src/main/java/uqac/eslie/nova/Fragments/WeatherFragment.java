package uqac.eslie.nova.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
<<<<<<< HEAD
import java.util.Date;
=======
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import uqac.eslie.nova.BDD.DataBaseHelper;
import uqac.eslie.nova.BDD.KP;
>>>>>>> 105a599ef1d7ad1c88287323c484448e72c9c45e

import uqac.eslie.nova.Helper.Chart.MyCustomAxis;
import uqac.eslie.nova.Helper.DataFetching.MyAsyncTaskJsonKP;
import uqac.eslie.nova.Helper.DataFetching.MyAsyncTaskJsonWeather;
import uqac.eslie.nova.Helper.DataFetching.MyAsyncTaskTxt;

import uqac.eslie.nova.R;


public class WeatherFragment extends Fragment implements SeekBar.OnSeekBarChangeListener,
        OnChartValueSelectedListener {

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

    //Chart
    protected BarChart mChart;
    protected BarChart mChart2;
    protected Typeface mTfRegular;
    protected Typeface mTfLight;
    ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    DatabaseReference ref = database.child("KP");

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

    private boolean needToUpdate(){

        Date currentTime = Calendar.getInstance().getTime();
        return DataBaseHelper.getCurrentKP().getDernierMiseAJour() == currentTime;

    }
    @Override
    public void onStart(){
        super.onStart();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                KP kp = dataSnapshot.getValue(KP.class);
                DataBaseHelper.setCurrentKP(kp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    private void getKPValue(ArrayList<String[]> array){
        Date currentTime = Calendar.getInstance().getTime();
        KP kp = new KP(currentTime, Integer.parseInt(kp27Array.get(1)[3]));

        kp.setH_03(Integer.parseInt(kp3Array.get(1)[1]));
        kp.setH_36(Integer.parseInt(kp3Array.get(2)[1]));
        kp.setH_69(Integer.parseInt(kp3Array.get(3)[1]));
        kp.setH_912(Integer.parseInt(kp3Array.get(4)[1]));
        kp.setH_1215(Integer.parseInt(kp3Array.get(5)[1]));
        kp.setH_1518(Integer.parseInt(kp3Array.get(6)[1]));
        kp.setH_1821(Integer.parseInt(kp3Array.get(7)[1]));
        kp.setH_2100(Integer.parseInt(kp3Array.get(8)[1]));
        KP.save(kp);


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_weather, container, false);

        //if(needToUpdate()) {
            myTxtTask = new MyAsyncTaskTxt(this);
            myTxtTask.delegate = this;
            myTxtTask.execute(kp27Url);

            myTxtTask = new MyAsyncTaskTxt(this);
            myTxtTask.delegate = this;
            myTxtTask.execute(kp3Url);
    //}

        myJsonTask = new MyAsyncTaskJsonKP(this);
    //    myJsonTask.delegate = this;
    //    myJsonTask.execute(kp1Url);

        myJsonTask2 = new MyAsyncTaskJsonWeather(this);
      //  myJsonTask2.delegate = this;
     //   myJsonTask2.execute(weather);

        mChart = root.findViewById(R.id.chart);
        mChart.setOnChartValueSelectedListener(this);

        mChart2 = root.findViewById(R.id.chart2);
        mChart2.setOnChartValueSelectedListener(this);

        // mChart.setHighlightEnabled(false);

        mChart.setDrawBarShadow(false);
        mChart2.setDrawBarShadow(false);

        mChart.setDrawValueAboveBar(true);
        mChart2.setDrawValueAboveBar(true);

        mChart.getDescription().setEnabled(false);
        mChart2.getDescription().setEnabled(false);
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount(60);
        mChart2.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);
        mChart2.setPinchZoom(false);

        mChart.setDoubleTapToZoomEnabled(false);
        mChart2.setDoubleTapToZoomEnabled(false);

        // draw shadows for each bar that show the maximum value
        // mChart.setDrawBarShadow(true);

        mChart.setDrawGridBackground(false);
        mChart2.setDrawGridBackground(false);



        mChart.setDrawBarShadow(false);
        mChart.setDrawGridBackground(false);
        mChart.setGridBackgroundColor(Color.WHITE);
        mChart2.setGridBackgroundColor(Color.WHITE);

        IAxisValueFormatter xAxisFormatter = new MyCustomAxis(mChart);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(mTfLight);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(xAxisFormatter);
        mChart.getAxisLeft().setDrawGridLines(false);
        xAxis.setTextColor(Color.WHITE);
        mChart.getAxisLeft().setTextColor(Color.WHITE);
        mChart.getAxisRight().setTextColor(Color.WHITE);


        /* chart 2  */
        mChart2.setDrawBarShadow(false);
        mChart2.setDrawGridBackground(false);

        IAxisValueFormatter xAxisFormatter2 = new MyCustomAxis(mChart);
        XAxis xAxis2 = mChart2.getXAxis();

        xAxis2.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis2.setTypeface(mTfLight);
        xAxis2.setDrawGridLines(false);
        xAxis2.setGranularity(1f); // only intervals of 1 day
        xAxis2.setLabelCount(7);
        xAxis2.setValueFormatter(xAxisFormatter2);
        mChart2.getAxisLeft().setDrawGridLines(false);
        xAxis2.setTextColor(Color.WHITE);
        mChart2.getAxisLeft().setTextColor(Color.WHITE);
        mChart2.getAxisRight().setTextColor(Color.WHITE);


        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
        l.setTextColor(Color.WHITE);

        Legend l2 = mChart2.getLegend();
        l2.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l2.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l2.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l2.setDrawInside(false);
        l2.setForm(Legend.LegendForm.SQUARE);
        l2.setFormSize(9f);
        l2.setTextSize(11f);
        l2.setXEntrySpace(4f);
        l2.setTextColor(Color.WHITE);

       // setDataForKP();
        return root;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    private void setDataForKP(){
        float spaceForBar = 10f;
        float barWidth = 7f;

     //   KP kp = DataBaseHelper.getCurrentKP();
        /*if(kp !=null){
            yVals1.add(new BarEntry(0 * spaceForBar, kp.getH_03()));
            yVals1.add(new BarEntry(1 * spaceForBar, kp.getH_36()));
            yVals1.add(new BarEntry(2 * spaceForBar, kp.getH_69()));
            yVals1.add(new BarEntry(3 * spaceForBar, kp.getH_912()));
            yVals1.add(new BarEntry(4 * spaceForBar, kp.getH_1215()));
            yVals1.add(new BarEntry(5 * spaceForBar, kp.getH_1518()));
            yVals1.add(new BarEntry(6 * spaceForBar, kp.getH_1821()));
            yVals1.add(new BarEntry(7 * spaceForBar, kp.getH_2100()));
        }*/
       /* if(kp3Array.size() != 0){
            yVals1.add(new BarEntry(0 * spaceForBar, Integer.parseInt(kp3Array.get(1)[1])));
            yVals1.add(new BarEntry(1 * spaceForBar, Integer.parseInt(kp3Array.get(2)[1])));
            yVals1.add(new BarEntry(2 * spaceForBar, Integer.parseInt(kp3Array.get(3)[1])));
            yVals1.add(new BarEntry(3 * spaceForBar, Integer.parseInt(kp3Array.get(4)[1])));
            yVals1.add(new BarEntry(4 * spaceForBar, Integer.parseInt(kp3Array.get(5)[1])));
            yVals1.add(new BarEntry(5 * spaceForBar, Integer.parseInt(kp3Array.get(6)[1])));
            yVals1.add(new BarEntry(6 * spaceForBar, Integer.parseInt(kp3Array.get(7)[1])));
            yVals1.add(new BarEntry(7 * spaceForBar, Integer.parseInt(kp3Array.get(8)[1])));
      }
      else{*/
            for (int i = 0; i < 8; i++) {
                // float val = (float) (Math.random() * spaceForBar);
                yVals1.add(new BarEntry(i * spaceForBar, 0));
            }
        //}

        BarDataSet set1;
        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet)mChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "Coefficient KP");
            set1.setColors(Color.rgb(209, 196, 233));
            //set1.setColors(ColorTemplate.);
            set1.setValueTextColor(Color.WHITE);
            set1.setDrawIcons(false);

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setValueTypeface(mTfLight);
            data.setBarWidth(barWidth);
            mChart.setData(data);
            mChart.refreshDrawableState();


        }
    }


    private void setData(int count, float range) {
        float spaceForBar = 10f;
        float barWidth = 7f;

        ArrayList<BarEntry> yVals2 = new ArrayList<BarEntry>();

        int[] cloud = new int[count];
        for (int i = 0; i < count; i++) {
            int val = (int) (Math.random() * 100);
            cloud[i] = val;
        }

        for (int i = 0; i < count; i++) {
           // float val = (float) (Math.random() * range);
            yVals2.add(new BarEntry(i * spaceForBar, cloud[i]));
        }

        BarDataSet set2;



        if (mChart2.getData() != null &&
                mChart2.getData().getDataSetCount() > 0) {
            set2 = (BarDataSet)mChart2.getData().getDataSetByIndex(0);
            set2.setValues(yVals2);
            mChart2.getData().notifyDataChanged();
            mChart2.notifyDataSetChanged();
        } else {
            set2 = new BarDataSet(yVals2, "Couverture nuageuse");
            set2.setColors(Color.rgb(209, 196, 233));
            set2.setValueTextColor(Color.WHITE);
            set2.setDrawIcons(false);
            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set2);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setValueTypeface(mTfLight);
            data.setBarWidth(barWidth);
            mChart2.setData(data);
        }
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {


        mChart.setFitBars(true);
        mChart.invalidate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }
    protected RectF mOnValueSelectedRectF = new RectF();
    @SuppressLint("NewApi")
    @Override
    public void onValueSelected(Entry e, Highlight h) {

        if (e == null)
            return;

        RectF bounds = mOnValueSelectedRectF;
        mChart.getBarBounds((BarEntry) e, bounds);

        MPPointF position = mChart.getPosition(e, mChart.getData().getDataSetByIndex(h.getDataSetIndex())
                .getAxisDependency());

        Log.i("bounds", bounds.toString());
        Log.i("position", position.toString());

        MPPointF.recycleInstance(position);
    }

    @Override
    public void onNothingSelected() {
    };


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
