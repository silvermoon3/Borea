package uqac.eslie.nova.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;

import uqac.eslie.nova.BDD.Marker;
import uqac.eslie.nova.MainActivity;
import uqac.eslie.nova.R;

/**
 * Created by ESTEL on 16/12/2016.
 */



public class MarkerDialog extends android.app.DialogFragment {
    private MarkerListener listener;
    private PlaceAutocompleteFragment place;
    private double latitude, longitude =0;
    private String name;

    public interface MarkerListener {
        void addMarker(Marker marker);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        FragmentManager fm = ((MainActivity)(getActivity())).getSupportFragmentManager();
        Fragment fragment = (fm.findFragmentById(R.id.place_autocomplete));
        FragmentTransaction ft = fm.beginTransaction();
        ft.remove(fragment);
        ft.commit();
    }
    private DialogInterface.OnDismissListener onDismissListener;

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDismissListener != null) {
            onDismissListener.onDismiss(dialog);
        }
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View root = getActivity().getLayoutInflater().inflate(R.layout.marker_dialog, null);
        place = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete);
        place.setOnPlaceSelectedListener(new PlaceSelectionListener() {
          @Override
          public void onPlaceSelected(Place place) {
          // TODO: Get info about the selected place.
             latitude =  place.getLatLng().latitude;
             longitude =  place.getLatLng().longitude;
             name = place.getName().toString();
          }
           @Override
           public  void onError(Status s){

             }
           });

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Ajouter un spot");
        builder.setView(root);
        builder.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Marker marker = new Marker();
                marker.setLatitude(latitude);
                marker.setLongitude(longitude);
                marker.setName(name);
                listener.addMarker(marker);
            }
        });
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MarkerDialog.this.getDialog().cancel();
            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {

            listener = (MarkerListener) context;
        } catch (ClassCastException e) {

            throw new ClassCastException(context.toString()
                    + " must implement MarkerListener.");
        }

    }
}




