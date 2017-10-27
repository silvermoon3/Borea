package uqac.eslie.nova;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import uqac.eslie.nova.BDD.CarPooling;
import uqac.eslie.nova.Fragments.AddCarpoolingFragment;
import uqac.eslie.nova.Fragments.HomeFragment;

public class addCarPooling extends AppCompatActivity {

    private TextView address;



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
                address =  findViewById(R.id.adress_carPooling);
                //On ajoute le covoiturage
                CarPooling carPooling = new CarPooling();
                carPooling.setPrice(2.0);
                carPooling.setDepart(address.toString());

                // insert the new item into the database
                FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
                DatabaseReference mReference = mDatabase.getReference("CarPooling");
                String ID = mReference.push().getKey();
                mReference.child(ID).setValue(carPooling);

                // add item
                break;





        }
        return true;
    }


}
