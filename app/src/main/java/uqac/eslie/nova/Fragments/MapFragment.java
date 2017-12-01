package uqac.eslie.nova.Fragments;

import android.Manifest;


import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import uqac.eslie.nova.BDD.CarPooling;
import uqac.eslie.nova.BDD.DataBaseHelper;
import uqac.eslie.nova.BDD.Marker;
import uqac.eslie.nova.Dialog.MarkerDialog;
import uqac.eslie.nova.Helper.GPSTracker;
import uqac.eslie.nova.MainActivity;
import uqac.eslie.nova.R;


public class MapFragment extends Fragment implements OnMapReadyCallback{


    private MapView mMapView;
    private GoogleMap mGoogleMap;
    private FloatingActionButton addMarker;
    private List<Marker> markers = new ArrayList<>();
    private PlaceAutocompleteFragment place;


    public MapFragment() {
        // Required empty public constructor
    }


    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_map, container, false);


        try {
            MapsInitializer.initialize(getActivity());
        } catch (Exception e) {
            Log.e("Address Map", "Could not initialize google play", e);
        }

        switch (GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity()) )
        {
            case ConnectionResult.SUCCESS:
                mMapView =  root.findViewById(R.id.mapView);
                mMapView.onCreate(savedInstanceState);
                // Gets to GoogleMap from the MapView and does initialization stuff
                if(mMapView!=null)
                {
                    mMapView.getMapAsync(this);
                    if(mGoogleMap != null) {
                        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
                        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                            mGoogleMap.setMyLocationEnabled(true);
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(43.1, -87.9), 10);
                        mGoogleMap.animateCamera(cameraUpdate);
                    }
                }

                break;
            case ConnectionResult.SERVICE_MISSING:
                Toast.makeText(getActivity(), "SERVICE MISSING", Toast.LENGTH_SHORT).show();
                break;
            case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:
                Toast.makeText(getActivity(), "UPDATE REQUIRED", Toast.LENGTH_SHORT).show();
                break;
            default: Toast.makeText(getActivity(), GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity()), Toast.LENGTH_SHORT).show();
        }
        ///com.google.android.gms.maps.MapFragment mapFragment = (com.google.android.gms.maps.MapFragment) getFragmentManager().findFragmentById(R.id.mapView);
       // mapFragment.getMapAsync(this);
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //list item click
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://nova-cac19.firebaseio.com/Marker");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Marker m =  postSnapshot.getValue(Marker.class);
                    markers.add(m);
                    mGoogleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(m.getLatitude(), m.getLongitude()))
                            .title(m.getName()));

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    @Override
    public void onMapReady(final GoogleMap map) {
        mGoogleMap = map;
            for(Marker m: markers){
                mGoogleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(m.getLatitude(), m.getLongitude()))
                        .title(m.getName()));
            }
        GPSTracker gps = new GPSTracker(getActivity().getApplicationContext());

        if(gps.canGetLocation()) {
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            Toast.makeText(
                    getActivity().getApplicationContext(),
                    "Your Location is -\nLat: " + latitude + "\nLong: "
                            + longitude, Toast.LENGTH_LONG).show();

            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 10.0f));
        }

    }
    private void OpeningDialog(){


    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }
    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }
    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState); mMapView.onSaveInstanceState(outState);
    }
}
