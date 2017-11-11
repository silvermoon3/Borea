package uqac.eslie.nova.Fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import uqac.eslie.nova.BDD.DataBaseHelper;
import uqac.eslie.nova.Helper.BitmapHelper;
import uqac.eslie.nova.Helper.RoundedImageView;
import uqac.eslie.nova.R;


public class AccountFragment extends Fragment {

    public interface clickAccountActivity {
        void onAccountClick();
    }
    private clickAccountActivity listener;
    RoundedImageView image;
    private Button compte;
    private TextView userName;
    private RoundedImageView photo;
    private BitmapHelper bitmapHelper;


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
        bitmapHelper = new BitmapHelper(this,DataBaseHelper.getCurrentUser().getImage().toString());

        bitmapHelper.execute(DataBaseHelper.getCurrentUser().getImage().toString());
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userName.setText(DataBaseHelper.getCurrentUser().getDisplayName());






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


}
