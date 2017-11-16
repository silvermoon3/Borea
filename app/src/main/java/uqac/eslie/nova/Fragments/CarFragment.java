package uqac.eslie.nova.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Collections;
import java.util.List;

import uqac.eslie.nova.Adapter.carPoolingAdapter;
import uqac.eslie.nova.BDD.CarPooling;
import uqac.eslie.nova.MainActivity;
import uqac.eslie.nova.R;

public class CarFragment extends Fragment {

    public interface CarFragmentListener {
        void onItemClick(CarPooling carPooling);
    }

    public interface CarFragmentListenerFloatingButton {
        void onButtonClick();
    }

    private CarFragmentListener listener;
    private CarFragmentListenerFloatingButton listenerFloatingButton;
    private ListView listCarPooling;
    private FloatingActionButton addCarPooling;

    public CarFragment() {
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
        View root = inflater.inflate(R.layout.fragment_car, container, false);
        addCarPooling  = root.findViewById(R.id.floatingActionButton_addCarPooling);

        listCarPooling = root.findViewById(R.id.list_carPooling);

        final DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://nova-cac19.firebaseio.com/CarPooling");

        final FirebaseListAdapter mAdapter = new FirebaseListAdapter<CarPooling>(getActivity(), CarPooling.class, R.layout.car_pooling_item, ref.orderByChild("date")) {
            @Override
            protected void populateView(final View v, final CarPooling model, final int position) {
                // Get references to the views of message.xml
                TextView depart = v.findViewById(R.id.itemCarPooling_depart);
                TextView arrivee = v.findViewById(R.id.itemCarPooling_arrivee);
                TextView prix = v.findViewById(R.id.itemCarPooling_prix);
                TextView place = v.findViewById(R.id.itemCarPooling_places);
                TextView date = v.findViewById(R.id.itemCarPooling_date);
                depart.setText(model.getDepart());
                arrivee.setText(model.getDestination());
                prix.setText(Double.toString(model.getPrice()));
                place.setText(Integer.toString(model.getPlaceTotal()));
                date.setText(model.getDateText());
            }

        };

        listCarPooling.setAdapter(mAdapter);
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //list item click
        listCarPooling.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                CarPooling item = (CarPooling) parent.getItemAtPosition(position);
                listener.onItemClick(item);
            }
        });

        addCarPooling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenerFloatingButton.onButtonClick();
            }
        });

    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CarFragmentListener) {
            listener = (CarFragmentListener) context;
        } else {
           /* throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");*/
        }

        if (context instanceof CarFragmentListenerFloatingButton) {
            listenerFloatingButton = (CarFragmentListenerFloatingButton) context;
        } else {
           /* throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");*/
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }


}
