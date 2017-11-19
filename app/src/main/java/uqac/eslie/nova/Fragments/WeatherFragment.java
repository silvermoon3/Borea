package uqac.eslie.nova.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import java.util.Date;

import java.util.Calendar;
import java.util.List;


import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;
import uqac.eslie.nova.BDD.DataBaseHelper;
import uqac.eslie.nova.BDD.KP;


import uqac.eslie.nova.Helper.DataFetching.MyAsyncTaskJsonKP;
import uqac.eslie.nova.Helper.DataFetching.MyAsyncTaskJsonWeather;
import uqac.eslie.nova.Helper.DataFetching.MyAsyncTaskTxt;

import uqac.eslie.nova.R;


public class WeatherFragment extends Fragment {


    MyAsyncTaskTxt myTxtTask;
    MyAsyncTaskJsonKP myJsonTask;
    MyAsyncTaskJsonWeather myJsonTask2;
    ArrayList<String[]> kpArrayWeek = new ArrayList<String[]>();
    ArrayList<String[]> cloudArrayTodayAndTomorrowAndWeek = new ArrayList<String[]>();
    ArrayList<String[]> kpArrayTodayAndTomorrow = new ArrayList<String[]>();
    URL kp27Url;
    URL kp3Url;
    URL kp1Url;
    URL weather;

    //Chart
    private static final int DEFAULT_DATA = 0;
    private static final int SUBCOLUMNS_DATA = 1;
    private static final int STACKED_DATA = 2;
    private static final int NEGATIVE_SUBCOLUMNS_DATA = 3;
    private static final int NEGATIVE_STACKED_DATA = 4;

    private ColumnChartView chart;
    private ColumnChartView chart2;
    private ColumnChartData data;
    private ColumnChartData data2;
    private boolean hasAxes = true;
    private boolean hasAxesNames = true;
    private boolean hasLabels = false;
    private boolean hasLabelForSelected = false;
    private int dataType = DEFAULT_DATA;

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
            kpArrayWeek = result;
            //TextView vt = this.getView().findViewById(R.id.actuellement_kp_valeur);
            //vt.setText(kpArrayWeek.get(1)[3]);

        }
        else if(result.get(0)[0] == "3"){

            kpArrayTodayAndTomorrow = result;
            /*
            // -------- KP TODAY --------
            TextView vt = this.getView().findViewById(R.id.today_KP_1922);
            vt.setText(kpArrayTodayAndTomorrow.get(1)[1]);

            vt = this.getView().findViewById(R.id.today_KP_221);
            vt.setText(kpArrayTodayAndTomorrow.get(2)[1]);

            vt = this.getView().findViewById(R.id.today_KP_14);
            vt.setText(kpArrayTodayAndTomorrow.get(3)[1]);

            vt = this.getView().findViewById(R.id.today_KP_47);
            vt.setText(kpArrayTodayAndTomorrow.get(4)[1]);

            vt = this.getView().findViewById(R.id.today_KP_710);
            vt.setText(kpArrayTodayAndTomorrow.get(5)[1]);

            vt = this.getView().findViewById(R.id.today_KP_1013);
            vt.setText(kpArrayTodayAndTomorrow.get(6)[1]);

            vt = this.getView().findViewById(R.id.today_KP_1316);
            vt.setText(kpArrayTodayAndTomorrow.get(7)[1]);

            vt = this.getView().findViewById(R.id.today_KP_1619);
            vt.setText(kpArrayTodayAndTomorrow.get(8)[1]);

            // -------- KP TOMORROW --------
            vt = this.getView().findViewById(R.id.tomorrow_KP_1922);
            vt.setText(kpArrayTodayAndTomorrow.get(1)[2]);

            vt = this.getView().findViewById(R.id.tomorrow_KP_221);
            vt.setText(kpArrayTodayAndTomorrow.get(2)[2]);

            vt = this.getView().findViewById(R.id.tomorrow_KP_14);
            vt.setText(kpArrayTodayAndTomorrow.get(3)[2]);

            vt = this.getView().findViewById(R.id.tomorrow_KP_47);
            vt.setText(kpArrayTodayAndTomorrow.get(4)[2]);

            vt = this.getView().findViewById(R.id.tomorrow_KP_710);
            vt.setText(kpArrayTodayAndTomorrow.get(5)[2]);

            vt = this.getView().findViewById(R.id.tomorrow_KP_1013);
            vt.setText(kpArrayTodayAndTomorrow.get(6)[2]);

            vt = this.getView().findViewById(R.id.tomorrow_KP_1316);
            vt.setText(kpArrayTodayAndTomorrow.get(7)[2]);

            vt = this.getView().findViewById(R.id.tomorrow_KP_1619);
            vt.setText(kpArrayTodayAndTomorrow.get(8)[2]);
            */
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

/*
    private void getKPValue(ArrayList<String[]> array){
        Date currentTime = Calendar.getInstance().getTime();
        KP kp = new KP(currentTime, Integer.parseInt(kpArrayWeek.get(1)[3]));

        kp.setH_03(Integer.parseInt(kpArrayTodayAndTomorrow.get(1)[1]));
        kp.setH_36(Integer.parseInt(kpArrayTodayAndTomorrow.get(2)[1]));
        kp.setH_69(Integer.parseInt(kpArrayTodayAndTomorrow.get(3)[1]));
        kp.setH_912(Integer.parseInt(kpArrayTodayAndTomorrow.get(4)[1]));
        kp.setH_1215(Integer.parseInt(kpArrayTodayAndTomorrow.get(5)[1]));
        kp.setH_1518(Integer.parseInt(kpArrayTodayAndTomorrow.get(6)[1]));
        kp.setH_1821(Integer.parseInt(kpArrayTodayAndTomorrow.get(7)[1]));
        kp.setH_2100(Integer.parseInt(kpArrayTodayAndTomorrow.get(8)[1]));
        KP.save(kp);


    }
*/
    public void processFinish(ArrayList<String[]> result, String diff) {
        if (diff == "kp") {
            //actuellement
            TextView vt = this.getView().findViewById(R.id.actuellement_kp_valeur);
            vt.setText(result.get(0)[0]);
        }

        else if (diff == "weather") {

            cloudArrayTodayAndTomorrowAndWeek = result;
            TextView vt = this.getView().findViewById(R.id.actuellement_couverture_valeur);
            vt.setText(cloudArrayTodayAndTomorrowAndWeek.get(0)[1]);
            /*
            // -------- weather --------
            TextView vt = this.getView().findViewById(R.id.today_weather_1922);
            vt.setText(cloudArrayTodayAndTomorrowAndWeek.get(6)[1]);

            vt = this.getView().findViewById(R.id.today_weather_221);
            vt.setText(cloudArrayTodayAndTomorrowAndWeek.get(7)[1]);

            vt = this.getView().findViewById(R.id.today_weather_14);
            vt.setText(cloudArrayTodayAndTomorrowAndWeek.get(0)[1]);

            vt = this.getView().findViewById(R.id.today_weather_47);
            vt.setText(cloudArrayTodayAndTomorrowAndWeek.get(1)[1]);

            vt = this.getView().findViewById(R.id.today_weather_710);
            vt.setText(cloudArrayTodayAndTomorrowAndWeek.get(2)[1]);

            vt = this.getView().findViewById(R.id.today_weather_1013);
            vt.setText(cloudArrayTodayAndTomorrowAndWeek.get(3)[1]);

            vt = this.getView().findViewById(R.id.today_weather_1316);
            vt.setText(cloudArrayTodayAndTomorrowAndWeek.get(4)[1]);

            vt = this.getView().findViewById(R.id.today_weather_1619);
            vt.setText(cloudArrayTodayAndTomorrowAndWeek.get(5)[1]);

            // -------- weather TOMORROW --------
            vt = this.getView().findViewById(R.id.tomorrow_weather_1922);
            vt.setText(cloudArrayTodayAndTomorrowAndWeek.get(14)[1]);

            vt = this.getView().findViewById(R.id.tomorrow_weather_221);
            vt.setText(cloudArrayTodayAndTomorrowAndWeek.get(15)[1]);

            vt = this.getView().findViewById(R.id.tomorrow_weather_14);
            vt.setText(cloudArrayTodayAndTomorrowAndWeek.get(8)[1]);

            vt = this.getView().findViewById(R.id.tomorrow_weather_47);
            vt.setText(cloudArrayTodayAndTomorrowAndWeek.get(9)[1]);

            vt = this.getView().findViewById(R.id.tomorrow_weather_710);
            vt.setText(cloudArrayTodayAndTomorrowAndWeek.get(10)[1]);

            vt = this.getView().findViewById(R.id.tomorrow_weather_1013);
            vt.setText(cloudArrayTodayAndTomorrowAndWeek.get(11)[1]);

            vt = this.getView().findViewById(R.id.tomorrow_weather_1316);
            vt.setText(cloudArrayTodayAndTomorrowAndWeek.get(12)[1]);

            vt = this.getView().findViewById(R.id.tomorrow_weather_1619);
            vt.setText(cloudArrayTodayAndTomorrowAndWeek.get(13)[1]);
            */
            generateData();
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
        myJsonTask.delegate = this;
        myJsonTask.execute(kp1Url);

        myJsonTask2 = new MyAsyncTaskJsonWeather(this);
        myJsonTask2.delegate = this;
        myJsonTask2.execute(weather);


        chart =  root.findViewById(R.id.chart);
        chart2 =  root.findViewById(R.id.chart2);



       // setDataForKP();
        return root;
    }


    private void generateData() {
        generateKPData();
        generateCloudData();


    }


    private void generateKPData() {
        int numSubcolumns = 1;
        int numColumns = 11;

        // Column can have many subcolumns, here by default I use 1 subcolumn in each of 8 columns.
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;
        for (int i = 3; i < numColumns; ++i) {

            values = new ArrayList<SubcolumnValue>();
            for (int j = 0; j < numSubcolumns; ++j) {
                if(i%numColumns == 9)
                    values.add(new SubcolumnValue(Integer.parseInt(kpArrayTodayAndTomorrow.get(1)[1]), chooseColorKP(Integer.parseInt(kpArrayTodayAndTomorrow.get(1)[1])) ));
                else if(i%numColumns == 10)
                    values.add(new SubcolumnValue(Integer.parseInt(kpArrayTodayAndTomorrow.get(2)[1]), chooseColorKP(Integer.parseInt(kpArrayTodayAndTomorrow.get(2)[1]))));
                else
                    values.add(new SubcolumnValue(Integer.parseInt(kpArrayTodayAndTomorrow.get((i)%numColumns)[1]), chooseColorKP(Integer.parseInt(kpArrayTodayAndTomorrow.get((i)%numColumns)[1]))));



            }


            Column column = new Column(values);
            column.setHasLabels(hasLabels);
            column.setHasLabelsOnlyForSelected(hasLabelForSelected);
            columns.add(column);
        }

        data = new ColumnChartData(columns);

        if (hasAxes) {
            List<Float> valuesX= new ArrayList<>();
            List<String> labelsX= new ArrayList<>();
            for (int i = 0; i< 8; i++){
                valuesX.add((float)i);
            }
            labelsX.add("1-4h");
            labelsX.add("4-7h");
            labelsX.add("7-10h");
            labelsX.add("10-13h");
            labelsX.add("13-16h");
            labelsX.add("16-19h");
            labelsX.add("19-22h");
            labelsX.add("22-1h");

            Axis axisX = Axis.generateAxisFromCollection(valuesX,labelsX);
            Axis axisY = new Axis().setHasLines(true);
            if (hasAxesNames) {
                axisX.setName("Heures");
                axisY.setName("Coefficient KP");
            }
            data.setAxisXBottom(axisX);
            data.setAxisYLeft(axisY);
        } else {
            data.setAxisXBottom(null);
            data.setAxisYLeft(null);
        }

        chart.setColumnChartData(data);

    }


    private void generateCloudData() {
        int numSubcolumns = 1;
        int numColumns =8;
        // Column can have many subcolumns, here by default I use 1 subcolumn in each of 8 columns.
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;
        for (int i = 0; i < numColumns; ++i) {

            values = new ArrayList<SubcolumnValue>();
            for (int j = 0; j < numSubcolumns; ++j) {

                values.add(new SubcolumnValue(Integer.parseInt(cloudArrayTodayAndTomorrowAndWeek.get(i)[1]), ChartUtils.pickColor()));


            }


            Column column = new Column(values);
            column.setHasLabels(hasLabels);
            column.setHasLabelsOnlyForSelected(hasLabelForSelected);

            columns.add(column);
        }

        data2 = new ColumnChartData(columns);

        if (hasAxes) {
            List<Float> valuesX= new ArrayList<>();
            List<String> labelsX= new ArrayList<>();
            for (int i = 0; i< 8; i++){
                valuesX.add((float)i);
            }
            labelsX.add("1-4h");
            labelsX.add("4-7h");
            labelsX.add("7-10h");
            labelsX.add("10-13h");
            labelsX.add("13-16h");
            labelsX.add("16-19h");
            labelsX.add("19-22h");
            labelsX.add("22-1h");

            Axis axisX = Axis.generateAxisFromCollection(valuesX,labelsX);
            Axis axisY = new Axis().setHasLines(true);

            if (hasAxesNames) {
                axisX.setName("Heures");
                axisY.setName("Couverture nuageuse");
            }
            data2.setAxisXBottom(axisX);
            data2.setAxisYLeft(axisY);
        } else {
            data2.setAxisXBottom(null);
            data2.setAxisYLeft(null);
        }

        chart2.setColumnChartData(data2);

    }



    private int chooseColorKP(int val){
        switch (val)
        {
            case 0: return Color.WHITE;

            case 1: return Color.BLACK;

            case 2: return Color.GREEN;

            case 3: return Color.YELLOW;

            case 4: return Color.RED;

            case 5: return Color.BLACK;

            default: return Color.MAGENTA;
        }

    }


    private int getSign() {
        int[] sign = new int[]{-1, 1};
        return sign[Math.round((float) Math.random())];
    }








}
