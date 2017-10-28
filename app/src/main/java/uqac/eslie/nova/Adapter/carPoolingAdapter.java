package uqac.eslie.nova.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import uqac.eslie.nova.BDD.CarPooling;
import uqac.eslie.nova.R;

/**
 * Created by ESTEL on 27/10/2017.
 */


public class carPoolingAdapter extends ArrayAdapter<CarPooling> {

        public carPoolingAdapter(Context context, int resource, List<CarPooling> objects) {
            super(context, resource, objects);
}

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(this.getContext());
            View root = inflater.inflate(R.layout.car_pooling_item, null);
            CarPooling carPooling = getItem(position);
            TextView depart = root.findViewById(R.id.itemCarPooling_depart);
            TextView arrivee = root.findViewById(R.id.itemCarPooling_arrivee);
            TextView prix = root.findViewById(R.id.itemCarPooling_prix);
            TextView place = root.findViewById(R.id.itemCarPooling_places);
            depart.setText(carPooling.getDepart());
            arrivee.setText(carPooling.getDestination());
            //prix.setText(carPooling.getPrice());
            place.setText(carPooling.getPlaceLeft());

            return root;
        }



}



