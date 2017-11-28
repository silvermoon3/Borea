package uqac.eslie.nova.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import uqac.eslie.nova.BDD.DataBaseHelper;
import uqac.eslie.nova.R;


public class HomeFragment extends Fragment {


    public interface clickAddCarpooling {
        void onCarPoolingClick();
    }
    public interface clickFindCarpooling {
        void onAddPhoto();
    }

    private clickAddCarpooling listener;
    private clickFindCarpooling listenerFindCarPooling;
    private TextView welcome;



    private FloatingActionButton addCarPooling;
    private FloatingActionButton fingCarPooling;

    public HomeFragment() {
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
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        addCarPooling =  root.findViewById(R.id.button_addCarPooling);
        fingCarPooling = root.findViewById(R.id.button_addPhoto);
        welcome = root.findViewById(R.id.welcome_name_home);
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addCarPooling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCarPoolingClick();
            }
        });
        fingCarPooling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenerFindCarPooling.onAddPhoto();
            }
        });
        welcome.setText(DataBaseHelper.getCurrentUser().getDisplayName());

    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try
        {

            listener = (clickAddCarpooling) context;

        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "must implement ArticleFragmentListener");
        }
        try
        {
            listenerFindCarPooling = (clickFindCarpooling) context;

        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "must implement listenerFindCarPooling");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
        listenerFindCarPooling = null;
    }

}
