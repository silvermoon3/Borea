package uqac.eslie.nova.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;

import uqac.eslie.nova.BDD.CarPooling;
import uqac.eslie.nova.BDD.DataBaseHelper;
import uqac.eslie.nova.BDD.ImageAurore;
import uqac.eslie.nova.R;


public class MyImages extends Fragment {



    private ListView listImage;


    public MyImages() {
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
        View root = inflater.inflate(R.layout.fragment_my_images, container, false);
        listImage = root.findViewById(R.id.list_myimages);
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://nova-cac19.firebaseio.com/ImageAurore_"+ DataBaseHelper.getCurrentUser().getUID());

        final FirebaseListAdapter mAdapter = new FirebaseListAdapter<ImageAurore>(getActivity(), ImageAurore.class, R.layout.my_images_item, ref) {
            @Override
            protected void populateView(final View v, final ImageAurore model, final int position) {
                // Get references to the views of message.xml
                TextView date = v.findViewById(R.id.date_image);
                TextView place = v.findViewById(R.id.place_image);
                date.setText(model.getDate());
                place.setText(model.getPlace());
                ImageView imageView =  v.findViewById(R.id.ImageAurore_image);
                imageView.setMaxWidth(400);
                imageView.setMaxHeight(200);
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageReference = storage.getReferenceFromUrl("gs://nova-cac19.appspot.com/images").child(model.getUrl());
                Glide.with(this.mContext)
                        .using(new FirebaseImageLoader())
                        .load(storageReference)
                        .into(imageView);
            }

        };

        listImage.setAdapter(mAdapter);
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
