package uqac.eslie.nova.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import uqac.eslie.nova.R;


public class HomeFragment extends Fragment {


    public interface clickAddCarpooling {
        void onCarPoolingClick();
    }

    private clickAddCarpooling listener;

    private OnFragmentInteractionListener mListener;
    private Button addCarPooling;

    public HomeFragment() {
        // Required empty public constructor
    }
  /*  public static Fragment newInstance()
    {
        HomeFragment myFragment = new HomeFragment();
        return myFragment;
    }*/



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
        try{
            listener = (clickAddCarpooling) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "must implement ArticleFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
}
