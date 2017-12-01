package uqac.eslie.nova.Fragments;

import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import uqac.eslie.nova.BDD.CarPooling;
import uqac.eslie.nova.BDD.DataBaseHelper;
import uqac.eslie.nova.BDD.NotificationCarPooling;
import uqac.eslie.nova.Helper.Timestamp;
import uqac.eslie.nova.LoginActivity;
import uqac.eslie.nova.MainActivity;
import uqac.eslie.nova.R;


public class CarPoolingDetailFragment extends Fragment {

    private TextView date;
    private TextView depart;
    private TextView arrivee;
    private TextView prix;
    private TextView places;
    private TextView heureDepart;
    private TextView heureRetour;
    private TextView marque;
    private TextView chauffeur;
    private Button choisir;

    public CarPoolingDetailFragment() {
        // Required empty public constructor
    }


    public static CarPoolingDetailFragment newInstance() {
        CarPoolingDetailFragment fragment = new CarPoolingDetailFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.cancelcardetail, menu);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.car_pooling_detail, container, false);
        date =  root.findViewById(R.id.date_carPooling_detail);
        depart =  root.findViewById(R.id.depart_carPooling_detail);
        arrivee =  root.findViewById(R.id.arrivee_carPooling_detail);
        prix =  root.findViewById(R.id.prix_carPooling_detail);
        places =  root.findViewById(R.id.places_carPooling_detail);
        heureDepart =  root.findViewById(R.id.heureDepart_carPooling_detail);
        heureRetour =  root.findViewById(R.id.heureRetour_carPooling_detail);
        chauffeur =  root.findViewById(R.id.chauffeur_carPooling_detail);
        marque =  root.findViewById(R.id.marque_carPooling_detail);
        choisir = root.findViewById(R.id.choisir_carPooling_Detail);
        return root;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final CarPooling carPooling = DataBaseHelper.getCurrentCarPooling();
        date.setText(carPooling.getDateText());
        depart.setText(carPooling.getDepart());
        arrivee.setText(carPooling.getDestination());
        prix.setText(String.valueOf(carPooling.getPrice()));
        places.setText(String.valueOf(carPooling.getPlaceLeft()));
        heureDepart.setText(carPooling.getHour());
        heureRetour.setText(carPooling.getReturnHour());
//        chauffeur.setText(carPooling.getUser().getDisplayName());
        marque.setText(carPooling.getMarque());
        choisir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Voulez-vous confirmer ce covoiturage ?")
                        .setCancelable(false)
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                reservation(carPooling.getUser().getUID(), carPooling);
                                Toast.makeText(getActivity(),"Covoiturage réservé ! ", Toast.LENGTH_LONG );

                            }
                        })
                        .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(getActivity(),"Covoiturage réservé ! ", Toast.LENGTH_LONG );
                                dialog.cancel();

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }


    private void reservation(String userID, CarPooling currentCarPooling){
            //Enlever une place


            //Notification à envoyer au chauffeur
           /*  NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(getActivity())
                            .setSmallIcon(R.drawable.ic_account_circle_black_24px)
                            .setContentTitle("Nouveau passager")
                            .setContentText(DataBaseHelper.getCurrentUser().getDisplayName() + " fait parti de ton covoiturage");
            // Sets an ID for the notification
            int mNotificationId = 001;
            // Gets an instance of the NotificationManager service
            NotificationManager mNotifyMgr =
                    (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
            // Builds the notification and issues it.
            mNotifyMgr.notify(mNotificationId, mBuilder.build());*/

        //Ajouter aux covoiturages de l'utilisateur
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mReference = mDatabase.getReference("CarPooling/"+currentCarPooling.getIDFirebase());
        if(currentCarPooling.getPlaceLeft() - 1 <= 0){
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            mReference.removeValue();

            try {
                NotificationCarPooling NotificationCarPooling = new NotificationCarPooling();
                NotificationCarPooling.setDate(day+"/"+month);
                NotificationCarPooling.setNewUser(DataBaseHelper.getCurrentUser());
                NotificationCarPooling.setMessage("Ton covoiturage pour le " + currentCarPooling.getDateText() + " est complet ! ");
                DatabaseReference mReferenceNotification = mDatabase.getReference("NotificationCarPooling_"+userID);
                String ID = mReferenceNotification.push().getKey();
                mReferenceNotification.child(ID).setValue(NotificationCarPooling);

            }
            catch (Exception e){

            }

        }

        else
        {
            currentCarPooling.setPlaceLeft(currentCarPooling.getPlaceLeft() - 1);
            mReference.child("placeLeft").setValue(currentCarPooling.getPlaceLeft() - 1);
        }


        NotificationCarPooling NotificationCarPooling = new NotificationCarPooling();
        long date = System.currentTimeMillis();

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        NotificationCarPooling.setDate(day+"/"+month);
        NotificationCarPooling.setNewUser(DataBaseHelper.getCurrentUser());
        NotificationCarPooling.setMessage(DataBaseHelper.getCurrentUser().getDisplayName() + " fait parti de ton covoiturage");

      // insert the new item into the database
        try {
            FirebaseDatabase mDatabase2 = FirebaseDatabase.getInstance();
            DatabaseReference mReference2 = mDatabase2.getReference("NotificationCarPooling_"+userID);
            String ID = mReference2.push().getKey();
            mReference2.child(ID).setValue(NotificationCarPooling);

        }
        catch (Exception e){

        }


    }

}
