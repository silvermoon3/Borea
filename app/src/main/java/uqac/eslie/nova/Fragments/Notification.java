package uqac.eslie.nova.Fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import uqac.eslie.nova.BDD.DataBaseHelper;
import uqac.eslie.nova.BDD.ImageAurore;
import uqac.eslie.nova.BDD.NotificationCarPooling;
import uqac.eslie.nova.R;

/**
 * Created by ESTEL on 26/11/2017.
 */

public class Notification extends Fragment {
    private ListView listNotification;



    public Notification() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View root = inflater.inflate(R.layout.fragment_notification, container, false);
        listNotification = root.findViewById(R.id.list_notifications);
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://nova-cac19.firebaseio.com/NotificationCarPooling_"+ DataBaseHelper.getCurrentUser().getUID());

        final FirebaseListAdapter mAdapter = new FirebaseListAdapter<NotificationCarPooling>(getActivity(), NotificationCarPooling.class, R.layout.notification_item, ref) {
            @Override
            protected void populateView(final View v, final NotificationCarPooling model, final int position) {
                // Get references to the views of message.xml
                TextView date = v.findViewById(R.id.Date_notif);
                date.setText(model.getDate());
                TextView message = v.findViewById(R.id.message_notif);
                message.setText(model.getMessage());

            }

        };

        listNotification.setAdapter(mAdapter);
        listNotification.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView, View view, final int position, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Supprimer cette notification ?")
                        .setCancelable(false)
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                DatabaseReference itemRef = mAdapter.getRef(position);
                                itemRef.removeValue();


                            }
                        })
                        .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();
                            }
                        });

                AlertDialog alert = builder.create();
                alert.show();

            }
        });




        return root;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }





    @Override
    public void onDetach() {
        super.onDetach();

    }


}
