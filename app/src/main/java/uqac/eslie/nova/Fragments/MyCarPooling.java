package uqac.eslie.nova.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import uqac.eslie.nova.BDD.CarPooling;
import uqac.eslie.nova.R;


public class MyCarPooling extends Fragment {


    private OnFragmentInteractionListener mListener;

    private CarFragment.CarFragmentListener listener;
    private ListView listCarPooling;

    public MyCarPooling() {
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
        View root = inflater.inflate(R.layout.fragment_my_car_pooling, container, false);

        listCarPooling = root.findViewById(R.id.myCarPooling_listView);
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://nova-cac19.firebaseio.com/CarPooling");

        final FirebaseListAdapter mAdapter = new FirebaseListAdapter<CarPooling>(getActivity(), CarPooling.class, R.layout.car_pooling_item, ref) {
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
                date.setText(model.getDate().toString());


            }

        };
        listCarPooling.setAdapter(mAdapter);
        return root;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
