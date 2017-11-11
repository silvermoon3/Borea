package uqac.eslie.nova.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import uqac.eslie.nova.BDD.CarPooling;
import uqac.eslie.nova.BDD.DataBaseHelper;
import uqac.eslie.nova.Helper.BitmapHelper;
import uqac.eslie.nova.Helper.FirebaseHelper;
import uqac.eslie.nova.Helper.RoundedImageView;
import uqac.eslie.nova.LoginActivity;
import uqac.eslie.nova.MainActivity;
import uqac.eslie.nova.R;


public class AccountFragment extends Fragment {

    public interface clickAccountActivity {
        void onAccountClick();
    }
    private clickAccountActivity listener;
    RoundedImageView image;

    private TextView userName;
    private RoundedImageView photo;
    private BitmapHelper bitmapHelper;

    private FirebaseAuth mAuth;
    private GoogleApiClient mGoogleApiClient;


    private OnFragmentInteractionListener mListener;

    public AccountFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.accountmenu, menu);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_account, container, false);
        userName = root.findViewById(R.id.user_name);
        photo = root.findViewById(R.id.account_photo);
        bitmapHelper = new BitmapHelper(this,DataBaseHelper.getCurrentUser().getImage());
        bitmapHelper.execute(DataBaseHelper.getCurrentUser().getImage());

        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userName.setText(DataBaseHelper.getCurrentUser().getDisplayName());



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.sign_out_button_account:
                signOut();

                break;
        }
        return true;
    }


    public void processFinish(Bitmap result){
      photo.setImageBitmap(result);
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


    private void signOut(){
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();

        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                    }
                });


    }

    @Override
    public void onStart() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .enableAutoManage(getActivity(), new GoogleApiClient.OnConnectionFailedListener(){
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
                    {

                    }

                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        super.onStart();
    }
    @Override
    public void onPause() {
        super.onPause();
        mGoogleApiClient.stopAutoManage(getActivity());
        mGoogleApiClient.disconnect();
    }

}
