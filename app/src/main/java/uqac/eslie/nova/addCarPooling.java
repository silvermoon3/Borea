package uqac.eslie.nova;

import android.app.DatePickerDialog;
import android.app.Dialog;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.Calendar;

import uqac.eslie.nova.BDD.CarPooling;
import uqac.eslie.nova.Fragments.AddCarpoolingFragment;
import uqac.eslie.nova.Helper.DatePickerFragment;


public class addCarPooling extends AppCompatActivity {

    private TextView addressD;
    private TextView addressA;
    private TextView time;
    private TextView date;
    private TextView hourD;
    private TextView hourR;
    private TextView price;
    private TextView place;



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
        inflater.inflate(R.menu.carpoollingmenu, menu);
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
                addressD = findViewById(R.id.addressD_carPooling);
                addressA = findViewById(R.id.addressA_carPooling);
                price = findViewById(R.id.price_carPooling);
                date = findViewById(R.id.date_carPooling);

                if (date.getText() != null && price.getText() != null)
                {
                    //On ajoute le covoiturage

                    CarPooling carPooling = new CarPooling();
                    carPooling.setDepart(addressD.getText().toString());
                    carPooling.setDestination(addressA.getText().toString());
                    carPooling.setPrice(Double.parseDouble(price.getText().toString()));
                    carPooling.setDate(date.getText().toString());

                    // insert the new item into the database
                    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference mReference = mDatabase.getReference("CarPooling");
                    String ID = mReference.push().getKey();
                    mReference.child(ID).setValue(carPooling);

                    // add item
                    break;
                }





        }
        return true;
    }

    public void showTimePickerDialog(View v) {
        Calendar currentTime = Calendar.getInstance();
        int hour = currentTime.get(Calendar.HOUR_OF_DAY);
        int minute = currentTime.get(Calendar.MINUTE);
        TimePickerDialog newFragment = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute ) {
                time =  findViewById(R.id.hour_carPooling);
                time.setText(hour +":" +minute);
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
                date =  findViewById(R.id.date_carPooling);
                date.setText(day +"/" +month+"/"+year);
            }
        }, year, month, day);
        newFragment.show();
    }


}
