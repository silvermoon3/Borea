package uqac.eslie.nova.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import java.util.Collections;
import java.util.Date;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;


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

import uqac.eslie.nova.Helper.GPSTracker;
import uqac.eslie.nova.MainActivity;
import uqac.eslie.nova.R;

import static android.content.Context.LOCATION_SERVICE;


public class WeatherFragment extends Fragment implements LocationListener {


    MyAsyncTaskTxt myTxtTask;
    MyAsyncTaskJsonKP myJsonTask;
    MyAsyncTaskJsonWeather myJsonTask2;
    ArrayList<String[]> kpArrayWeek = new ArrayList<String[]>();
    ArrayList<String[]> cloudArrayTodayAndTomorrowAndWeek = new ArrayList<String[]>();
    ArrayList<String[]> kpArrayTodayAndTomorrow = new ArrayList<String[]>();
    URL kp27Url;
    URL kp3Url;
    URL kp1Url;
    //URL weather;

    //Chart
    private static final int DEFAULT_DATA = 0;

    private ColumnChartView chartKp_today;
    private ColumnChartView chartCloud_today;
    private ColumnChartView chartKp_tomorrow;
    private ColumnChartView chartCloud_tomorrow;
    private ColumnChartView chartKp_week;
    private ColumnChartView chartCloud_week;
    private ColumnChartData dataKP_today;
    private ColumnChartData dataKP_tomorrow;
    private ColumnChartData dataKP_week;
    private ColumnChartData dataCloud_today;
    private ColumnChartData dataCloud_tomorrow;
    private ColumnChartData dataCloud_week;
    private boolean hasAxes = true;
    private boolean hasAxesNames = true;
    private boolean hasLabels = false;
    private boolean hasLabelForSelected = false;
    private int dataType = DEFAULT_DATA;
    private TextView vt;

    LocationManager locationManager;
    Location location;

    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    DatabaseReference ref = database.child("KP");

    public WeatherFragment() throws MalformedURLException {
        kp27Url = new URL("http://services.swpc.noaa.gov/text/27-day-outlook.txt");
        kp3Url = new URL("http://services.swpc.noaa.gov/text/3-day-forecast.txt");
        kp1Url = new URL("http://services.swpc.noaa.gov/products/noaa-estimated-planetary-k-index-1-minute.json");

        // TO DO : inject real GPS coordinates in the link
    }

    private URL getWeatherURL(){
        GPSTracker gps = new GPSTracker(getActivity().getApplicationContext());

        if(gps.canGetLocation()) {
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            Toast.makeText(
                    getActivity().getApplicationContext(),
                    "Your Location is -\nLat: " + latitude + "\nLong: "
                            + longitude, Toast.LENGTH_LONG).show();

            try {
                return new URL("https://api.apixu.com/v1/forecast.json?key=848095d955c54bfab29213656172810&q=" + latitude + "," + longitude + "&days=7");
            }
            catch (Exception e){

            }

        }

        else {
            gps.showSettingsAlert();
        }

        return null;
    }



    public void processFinish(ArrayList<String[]> result) {
        if(result.get(0)[0] == "27") {
            kpArrayWeek = result;
            //TextView vt = this.getView().findViewById(R.id.actuellement_kp_valeur);
            //vt.setText(kpArrayWeek.get(1)[3]);

        }
        else if(result.get(0)[0] == "3"){

            kpArrayTodayAndTomorrow = result;

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
            vt = this.getView().findViewById(R.id.actuellement_kp_valeur);
            vt.setText(result.get(0)[0]);


        }

        else if (diff == "weather") {

            cloudArrayTodayAndTomorrowAndWeek = result;
            vt = this.getView().findViewById(R.id.actuellement_couverture_valeur);
            vt.setText(cloudArrayTodayAndTomorrowAndWeek.get(0)[1]);

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
        myJsonTask2.execute(getWeatherURL());


        chartKp_today =  root.findViewById(R.id.chartKP_today);
        chartKp_tomorrow = root.findViewById(R.id.chartKP_tomorrow);
        chartKp_week = root.findViewById(R.id.chartKP_week);

        chartCloud_today =  root.findViewById(R.id.chartCloud_today);
        chartCloud_tomorrow =  root.findViewById(R.id.chartCloud_tomorrow);
        chartCloud_week = root.findViewById(R.id.chartCloud_week);
        if(DataBaseHelper.getCurrentWeatherGraph() == 0 || DataBaseHelper.getCurrentWeatherGraph() ==-1){

            chartKp_week.setVisibility(View.INVISIBLE);
            chartCloud_week.setVisibility(View.INVISIBLE);
            if( DataBaseHelper.getCurrentWeatherGraph() ==-1){
                chartKp_tomorrow.setVisibility(View.INVISIBLE);
                chartCloud_tomorrow.setVisibility(View.INVISIBLE);
            }
        }
       // setDataForKP();
        return root;
    }

    private void generateData() {
        generateKPDataToday();
        generateCloudDataToday();
        generateKPDataTomorrow();
        generateCloudDataTomorrow();
        generateKPDataWeek();
        generateCloudDataWeek();
    }


    private void generateKPDataToday() {
        int numSubcolumns = 1;
        int numColumns = 11;

        // Column can have many subcolumns, here by default I use 1 subcolumn in each of 8 columns.
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;
        for (int i = 3; i < numColumns; ++i) {

            values = new ArrayList<SubcolumnValue>();
            for (int j = 0; j < numSubcolumns; ++j) {
                if(i%numColumns == 9)
                {

                        values.add(new SubcolumnValue(Integer.parseInt(kpArrayTodayAndTomorrow.get(1)[1]), chooseColorKP(Integer.parseInt(kpArrayTodayAndTomorrow.get(1)[1]))));

                }
                else if(i%numColumns == 10)
                {

                        values.add(new SubcolumnValue(Integer.parseInt(kpArrayTodayAndTomorrow.get(2)[1]), chooseColorKP(Integer.parseInt(kpArrayTodayAndTomorrow.get(2)[1]))));

                }
                else
                {
                   
                        values.add(new SubcolumnValue(Integer.parseInt(kpArrayTodayAndTomorrow.get((i)%numColumns)[1]), chooseColorKP(Integer.parseInt(kpArrayTodayAndTomorrow.get((i)%numColumns)[1]))));
                }



            }


            Column column = new Column(values);
            column.setHasLabels(hasLabels);
            column.setHasLabelsOnlyForSelected(hasLabelForSelected);
            columns.add(column);
        }

        dataKP_today = new ColumnChartData(columns);

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
            dataKP_today.setAxisXBottom(axisX);
            dataKP_today.setAxisYLeft(axisY);
        } else {
            dataKP_today.setAxisXBottom(null);
            dataKP_today.setAxisYLeft(null);
        }

        chartKp_today.setColumnChartData(dataKP_today);

    }

    private void generateKPDataTomorrow() {
        int numSubcolumns = 1;
        int numColumns = 11;

        // Column can have many subcolumns, here by default I use 1 subcolumn in each of 8 columns.
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;
        for (int i = 3; i < numColumns; ++i) {

            values = new ArrayList<SubcolumnValue>();
            for (int j = 0; j < numSubcolumns; ++j) {
                if(i%numColumns == 9)
                {

                        values.add(new SubcolumnValue(Integer.parseInt(kpArrayTodayAndTomorrow.get(1)[2]), chooseColorKP(Integer.parseInt(kpArrayTodayAndTomorrow.get(1)[2]))));

                }
                else if(i%numColumns == 10)
                {

                        values.add(new SubcolumnValue(Integer.parseInt(kpArrayTodayAndTomorrow.get(2)[2]), chooseColorKP(Integer.parseInt(kpArrayTodayAndTomorrow.get(2)[2]))));
                }
                else
                {


                        values.add(new SubcolumnValue(Integer.parseInt(kpArrayTodayAndTomorrow.get((i) % numColumns)[2]), chooseColorKP(Integer.parseInt(kpArrayTodayAndTomorrow.get((i) % numColumns)[2]))));
                }

            }


            Column column = new Column(values);
            column.setHasLabels(hasLabels);
            column.setHasLabelsOnlyForSelected(hasLabelForSelected);
            columns.add(column);
        }

        dataKP_tomorrow = new ColumnChartData(columns);

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
            dataKP_tomorrow.setAxisXBottom(axisX);
            dataKP_tomorrow.setAxisYLeft(axisY);
        } else {
            dataKP_tomorrow.setAxisXBottom(null);
            dataKP_tomorrow.setAxisYLeft(null);
        }

        chartKp_tomorrow.setColumnChartData(dataKP_tomorrow);

    }
    private void generateKPDataWeek() {
        int numSubcolumns = 1;
        int numColumns = 8;

        // Column can have many subcolumns, here by default I use 1 subcolumn in each of 8 columns.
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;
        for (int i = 1; i < numColumns; ++i) {

            values = new ArrayList<SubcolumnValue>();
            for (int j = 0; j < numSubcolumns; ++j) {

                    values.add(new SubcolumnValue(Integer.parseInt(kpArrayWeek.get(i)[3]), chooseColorKP(Integer.parseInt(kpArrayWeek.get(i)[3]))));



            }


            Column column = new Column(values);
            column.setHasLabels(hasLabels);
            column.setHasLabelsOnlyForSelected(hasLabelForSelected);
            columns.add(column);
        }

        dataKP_week = new ColumnChartData(columns);

        if (hasAxes) {
            List<Float> valuesX= new ArrayList<>();
            List<String> labelsX= new ArrayList<>();
            for (int i = 0; i< 7; i++){
                valuesX.add((float)i);
            }
            for(int i=1; i<8; i++){
                labelsX.add(kpArrayWeek.get(i)[0].substring(5));
            }

          /*  labelsX.add("1-4h");
            labelsX.add("4-7h");
            labelsX.add("7-10h");
            labelsX.add("10-13h");
            labelsX.add("13-16h");
            labelsX.add("16-19h");
            labelsX.add("19-22h");
            labelsX.add("22-1h");*/

            Axis axisX = Axis.generateAxisFromCollection(valuesX,labelsX);

            Axis axisY = new Axis().setHasLines(true);
            if (hasAxesNames) {
                axisX.setName("Jour");
                axisY.setName("Coefficient KP");
            }
            dataKP_week.setAxisXBottom(axisX);
            dataKP_week.setAxisYLeft(axisY);
        } else {
            dataKP_week.setAxisXBottom(null);
            dataKP_week.setAxisYLeft(null);
        }

        chartKp_week.setColumnChartData(dataKP_week);

    }




    private void generateCloudDataToday() {
        int numSubcolumns = 1;
        int numColumns =8;
        // Column can have many subcolumns, here by default I use 1 subcolumn in each of 8 columns.
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;
        for (int i = 0; i < numColumns; ++i) {

            values = new ArrayList<SubcolumnValue>();
            for (int j = 0; j < numSubcolumns; ++j) {
                values.add(new SubcolumnValue(Integer.parseInt(cloudArrayTodayAndTomorrowAndWeek.get(i)[1]), chooseColorCloud(Integer.parseInt(cloudArrayTodayAndTomorrowAndWeek.get(i)[1]))));
            }


            Column column = new Column(values);
            column.setHasLabels(hasLabels);
            column.setHasLabelsOnlyForSelected(hasLabelForSelected);

            columns.add(column);
        }

        dataCloud_today = new ColumnChartData(columns);

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
            dataCloud_today.setAxisXBottom(axisX);
            dataCloud_today.setAxisYLeft(axisY);
        } else {
            dataCloud_today.setAxisXBottom(null);
            dataCloud_today.setAxisYLeft(null);
        }

        chartCloud_today.setColumnChartData(dataCloud_today);

    }

    private void generateCloudDataTomorrow() {
        int numSubcolumns = 1;
        int numColumns =16;
        // Column can have many subcolumns, here by default I use 1 subcolumn in each of 8 columns.
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;
        for (int i = 8; i < numColumns; ++i) {

            values = new ArrayList<SubcolumnValue>();
            for (int j = 0; j < numSubcolumns; ++j) {

                values.add(new SubcolumnValue(Integer.parseInt(cloudArrayTodayAndTomorrowAndWeek.get(i)[1]), chooseColorCloud(Integer.parseInt(cloudArrayTodayAndTomorrowAndWeek.get(i)[1]))));
            }

            Column column = new Column(values);
            column.setHasLabels(hasLabels);
            column.setHasLabelsOnlyForSelected(hasLabelForSelected);

            columns.add(column);
        }

        dataCloud_tomorrow = new ColumnChartData(columns);

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
            dataCloud_tomorrow.setAxisXBottom(axisX);
            dataCloud_tomorrow.setAxisYLeft(axisY);
        } else {
            dataCloud_tomorrow.setAxisXBottom(null);
            dataCloud_tomorrow.setAxisYLeft(null);
        }

        chartCloud_tomorrow.setColumnChartData(dataCloud_tomorrow);

    }


    private void generateCloudDataWeek() {
        int numSubcolumns = 1;
        int numColumns =24;
        // Column can have many subcolumns, here by default I use 1 subcolumn in each of 8 columns.
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;
        for (int i = 17; i < numColumns; ++i) {

            values = new ArrayList<SubcolumnValue>();
            for (int j = 0; j < numSubcolumns; ++j) {

                values.add(new SubcolumnValue((float)Double.parseDouble(cloudArrayTodayAndTomorrowAndWeek.get(i)[0]), chooseColorCloud((int)Double.parseDouble(cloudArrayTodayAndTomorrowAndWeek.get(i)[0]))));
            }

            Column column = new Column(values);
            column.setHasLabels(hasLabels);
            column.setHasLabelsOnlyForSelected(hasLabelForSelected);

            columns.add(column);
        }

        dataCloud_week = new ColumnChartData(columns);

        if (hasAxes) {
            List<Float> valuesX= new ArrayList<>();
            List<String> labelsX= new ArrayList<>();
            for (int i = 0; i< 7; i++){
                valuesX.add((float)i);
            }
            for(int i=1; i<8; i++){
                labelsX.add(kpArrayWeek.get(i)[0].substring(5));
            }


            Axis axisX = Axis.generateAxisFromCollection(valuesX,labelsX);
            Axis axisY = new Axis().setHasLines(true);

            if (hasAxesNames) {
                axisX.setName("Jour");
                axisY.setName("Couverture nuageuse");
            }
            dataCloud_week.setAxisXBottom(axisX);
            dataCloud_week.setAxisYLeft(axisY);
        } else {
            dataCloud_week.setAxisXBottom(null);
            dataCloud_week.setAxisYLeft(null);
        }

        chartCloud_week.setColumnChartData(dataCloud_week);

    }

    private int chooseColorKP(int val){
        switch (val)
        {
            case 0: return Color.WHITE;

            case 1: case 2: return Color.rgb(0, 255, 0);

            case 3: return Color.rgb(255, 255, 0);

            case 4: case 5: return Color.rgb(255, 128, 0);

            case 6: case 7:case 8:case 9:case 10: return Color.rgb(255, 0, 0);

            default: return Color.rgb(255, 255, 255);
        }

    }

    private int chooseColorCloud(int val){
       if(val <=10)
           return Color.rgb(221, 227, 239);
       if(val > 10 && val <=20)
           return Color.rgb(196, 209, 238);
       if(val > 20 && val <= 30)
           return Color.rgb(175, 194, 233);
        if(val > 30 && val <= 40)
            return Color.rgb(155, 177, 227);
        if(val > 40 && val <= 50)
            return Color.rgb(135, 162, 223);
        if(val > 50 && val <= 60)
            return Color.rgb(115, 147, 217);
        if(val > 60 && val <= 70)
            return Color.rgb(90, 121, 190);
         if(val > 70 && val <= 80)
             return Color.rgb(53, 91, 177);
         if(val > 80 && val <= 90)
             return Color.rgb(63, 87, 141);
        if(val > 90 && val <= 100)
            return Color.rgb(30, 66, 106);

         return Color.WHITE;


    }


    @Override
    public void onLocationChanged(Location _location) {
        this.location = _location;
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude","disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
    }








}
