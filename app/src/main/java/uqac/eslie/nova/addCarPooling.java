package uqac.eslie.nova;

import android.app.DatePickerDialog;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



import java.util.Calendar;
import java.util.Date;

import uqac.eslie.nova.BDD.CarPooling;
import uqac.eslie.nova.BDD.DataBaseHelper;
import uqac.eslie.nova.Fragments.AddCarpoolingFragment;
import uqac.eslie.nova.Helper.Timestamp;


public class addCarPooling extends AppCompatActivity {

    private String addressD ="";
    private String addressA ="";
    private TextView dateText;
    private TextView hourD;
    private TextView hourR;
    private TextView price;
    private TextView place;
    private TextView marque;
    private Menu menu;
    private Date date;
    PlaceAutocompleteFragment autocompleteFragmentDepart;
    PlaceAutocompleteFragment autocompleteFragmentArrivee;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car_pooling);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content, new AddCarpoolingFragment()).commit();




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.validationmenu, menu);
        this.menu = menu;
        autocompleteFragmentDepart = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment_depart);
        autocompleteFragmentArrivee = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment_arrivee);
        autocompleteFragmentDepart.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                addressD = place.getName().toString();
                setDoneButton();

            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("error", "An error occurred: " + status);
            }
        });

        autocompleteFragmentArrivee.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                addressA = place.getName().toString();
                setDoneButton();

            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("error", "An error occurred: " + status);
            }
        });

        dateText = findViewById(R.id.date);

        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(view);
                setDoneButton();

            }
        });

        hourD =  findViewById(R.id.hour_depart);
        hourR =  findViewById(R.id.hour_return);

        TextView hourD = findViewById(R.id.hour_depart);
        hourD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialogHourD(view);
                setDoneButton();
            }
        });


        TextView hourR = findViewById(R.id.hour_return);
        hourR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialogHourR(view);
                setDoneButton();
            }
        });

        price = findViewById(R.id.price_carPooling);
        price.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                setDoneButton();
                return true;
            }
        });
        place = findViewById(R.id.place_carPooling);
        place.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                setDoneButton();
                return true;
            }
        });
        marque = findViewById(R.id.marque_carPooling);
        marque.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                setDoneButton();
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_item_valid:
                Toast.makeText(this, "yes", Toast.LENGTH_LONG);
                    //On ajoute le covoiturage
                    CarPooling carPooling = new CarPooling();
                    carPooling.setDate(date);
                    carPooling.setDateText(dateText.getText().toString());
                    carPooling.setHour(hourD.getText().toString());
                    carPooling.setReturnHour(hourR.getText().toString());
                    carPooling.setDepart(addressD);
                    carPooling.setDestination(addressA);
                    carPooling.setUser(DataBaseHelper.getCurrentUser());
                    carPooling.setTimestamp(new Timestamp(date.getTime()));
                    if(!price.getText().toString().equals(""))
                        carPooling.setPrice(Double.parseDouble(price.getText().toString()));
                    if(!place.getText().toString().equals("")) {
                        carPooling.setPlaceTotal(Integer.parseInt(place.getText().toString()));
                        carPooling.setPlaceLeft(Integer.parseInt(place.getText().toString()));
                    }

                    carPooling.setMarque(marque.getText().toString());
                    // insert the new item into the database
                  try {
                      FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
                      DatabaseReference mReference = mDatabase.getReference("CarPooling");
                      String ID = mReference.push().getKey();
                      mReference.child(ID).setValue(carPooling);
                      DataBaseHelper.getCurrentUser().addCarPooling(carPooling);
                      // add item
                      Toast.makeText(this,"Covoiture ajouté", Toast.LENGTH_SHORT );
                      finish();
                  }
                  catch (Exception e){

                  }
                    break;
        }
        return true;
    }

    public void showTimePickerDialogHourD(View v) {
        Calendar currentTime = Calendar.getInstance();
        int hour = currentTime.get(Calendar.HOUR_OF_DAY);
        int minute = currentTime.get(Calendar.MINUTE);
        TimePickerDialog newFragment = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute ) {
                hourD.setText(hour +":" +minute);
            }
        }, hour, minute, true);

        newFragment.show();

    }

    public void showTimePickerDialogHourR(View v) {
        Calendar currentTime = Calendar.getInstance();
        int hour = currentTime.get(Calendar.HOUR_OF_DAY);
        int minute = currentTime.get(Calendar.MINUTE);
        TimePickerDialog newFragment = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute ) {
                hourR.setText(hour +":" +minute);
            }
        }, hour, minute, true);

        newFragment.show();

    }

    public void showDatePickerDialog(View v) {
        Calendar currentDate = Calendar.getInstance();
        int year = currentDate.get(Calendar.YEAR);
        int month = currentDate.get(Calendar.MONTH);
        int day = currentDate.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog newFragment = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);
                date =  calendar.getTime();
                dateText.setText(day +"/" +month+"/"+year);
            }
        }, year, month, day);
        newFragment.show();

    }


    private boolean checkAllComplete()
    {
        if(dateText.getText() == "" || dateText.getText().length() == 0)
            return false;
        if(marque.getText() == "" || marque.getText().length() == 0)
            return false;
        if(price.getText() == "" || price.getText().length() == 0)
            return false;
        if(addressA == "")
            return false;
        if(addressD == "")
            return false;
        if(hourD.getText() == "" || hourD.getText().length() == 0)
            return false;
        if(hourR.getText() == "" || hourR.getText().length() == 0)
            return false;
        if(place.getText() =="" || place.getText().length() == 0)
            return false;
        return true;
    }


    public void setDoneButton()
    {
        if(checkAllComplete())
        {
            MenuItem  done =  menu.findItem(R.id.menu_item_valid);
            done.setEnabled(true);


        }
    }

}
