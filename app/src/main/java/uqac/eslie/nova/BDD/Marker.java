package uqac.eslie.nova.BDD;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by ESTEL on 29/11/2017.
 */

public class Marker {

    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    double latitude;
    double longitude;

}
