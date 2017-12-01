package uqac.eslie.nova;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

import uqac.eslie.nova.BDD.DataBaseHelper;
import uqac.eslie.nova.BDD.ImageAurore;
import uqac.eslie.nova.BDD.Marker;

public class addImage extends AppCompatActivity {
    private Button add_image;
    public Bitmap toretBitmap;
    private Menu menu;
    private Uri filePath;
    PlaceAutocompleteFragment place;
    String place_image = "";
    double latitude;
    double longitude;
    private TextView date;
    private String stringDate;
    private TextView hourSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_image);
        add_image =  findViewById(R.id.selectimage);
        add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Select_OnClick();
            }
        });
        place = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete);
        ((EditText)place.getView().findViewById(R.id.place_autocomplete_search_input)).setTextColor(Color.WHITE);

        place.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                place_image = place.getName().toString();
                latitude = place.getLatLng().latitude;
                longitude = place.getLatLng().longitude;
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("error", "An error occurred: " + status);
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.validationmenu, menu);
        this.menu = menu;
        date = findViewById(R.id.date_image_select);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(view);


            }
        });
        hourSelected =  findViewById(R.id.hour_image_select);
        hourSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialogHour(view);

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
                uploadFile();

                break;
        }
        return true;
    }



    private void Select_OnClick()
    {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if(resultCode == RESULT_OK){
            filePath = data.getData();
            try {
                InputStream IS = getApplicationContext().getContentResolver().openInputStream(filePath);
                Bitmap b = BitmapFactory.decodeStream(IS);
                DisplayMetrics dm = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(dm);
                int width=dm.widthPixels;
                int height=dm.heightPixels;
                toretBitmap = getResizedBitmap(b,width,height/3);
                ImageView imageView =  findViewById(R.id.imageView2);
                imageView.setImageBitmap(toretBitmap);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }
    public void showDatePickerDialog(View v) {
        final Calendar currentDate = Calendar.getInstance();
        int year = currentDate.get(Calendar.YEAR);
        int month = currentDate.get(Calendar.MONTH);
        int day = currentDate.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog newFragment = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);
                date.setText(day +"/" +month+"/"+year);
                stringDate = day +"/" +month+"/"+year;
            }
        }, year, month, day);
        newFragment.show();

    }
    public void showTimePickerDialogHour(View v) {
        Calendar currentTime = Calendar.getInstance();
        int hour = currentTime.get(Calendar.HOUR_OF_DAY);
        int minute = currentTime.get(Calendar.MINUTE);
        TimePickerDialog newFragment = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute ) {
                hourSelected.setText(hour +":" +minute);
            }
        }, hour, minute, true);

        newFragment.show();

    }
    public void addMarker(Marker marker){
        //Add marker to firebase
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mReference = mDatabase.getReference("Marker");
        String ID = mReference.push().getKey();
        mReference.child(ID).setValue(marker);
    }
    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    private void uploadFile() {
        //if there is a file to upload
        if (filePath != null && !hourSelected.getText().toString().equals("")
                && !date.getText().toString().equals("")
                && place_image!= "") {

                //displaying a progress dialog while upload is going on
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("En cours...");
                progressDialog.show();

                FirebaseStorage mDatabase = FirebaseStorage.getInstance();
                String fileName = "pic" + filePath.toString().split("%")[1] + ".jpg";
                Marker marker = new Marker();
                marker.setLongitude(longitude);
                marker.setLatitude(latitude);
                marker.setName(place_image + ", le " + stringDate + ", à " + hourSelected.getText().toString());

                addMarker(marker);
                SaveDataFile(fileName);
                StorageReference riversRef = mDatabase.getReference("images/" + fileName);
                riversRef.putFile(filePath)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                //if the upload is successfull
                                //hiding the progress dialog
                                progressDialog.dismiss();
                                finish();

                                //and displaying a success toast
                                Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                //if the upload is not successfull
                                //hiding the progress dialog
                                progressDialog.dismiss();

                                //and displaying error message
                                Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                //calculating progress percentage
                                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                                //displaying percentage in progress dialog
                                progressDialog.setMessage("Effectué " + ((int) progress) + "%...");
                            }
                        });



        }
        //if there is not any file
        else {
            Toast toast =  Toast.makeText(this.getApplicationContext(),  "Veuillez compléter tous les champs", Toast.LENGTH_LONG);
            // toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
            toast.show();
        }
    }

    private void SaveDataFile(String fileName){

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);


        ImageAurore imageAurore = new ImageAurore();
        imageAurore.setUrl(fileName);
        imageAurore.setUser(DataBaseHelper.getCurrentUser());
        imageAurore.setDate(stringDate);
        imageAurore.setPlace(place_image);
        imageAurore.setHour(hourSelected.getText().toString());



        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mReference = mDatabase.getReference("ImageAurore");
        String ID = mReference.push().getKey();
        mReference.child(ID).setValue(imageAurore);

        DatabaseReference mReference2 = mDatabase.getReference("ImageAurore_"+ DataBaseHelper.getCurrentUser().getUID());
        String ID2 = mReference2.push().getKey();
        mReference2.child(ID2).setValue(imageAurore);


    }
}
