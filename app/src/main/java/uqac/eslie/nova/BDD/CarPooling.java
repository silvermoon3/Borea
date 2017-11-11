package uqac.eslie.nova.BDD;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.UUID;

/**
 * Created by ESTEL on 26/10/2017.
 */

public class CarPooling {

    private UUID itemID;
    private User user;
    private Double price;
    private String Depart;
    private String Destination;
    private String hour;
    private String returnHour;
    private int placeTotal;
    private int placeLeft;
    private String date;
    private String marque;
    private List<User> passagers;

    public CarPooling(){
        itemID = UUID.randomUUID();
        passagers = new ArrayList<>();
    }


    public void addPassager(User passager){
        passagers.add(passager);
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getReturnHour() {
        return returnHour;
    }

    public void setReturnHour(String returnHour) {
        this.returnHour = returnHour;
    }

    public int getPlaceTotal() {
        return placeTotal;
    }

    public void setPlaceTotal(int placeTotal) {
        this.placeTotal = placeTotal;
    }

    public int getPlaceLeft() {
        return placeLeft;
    }

    public List<User> getPassagers() {
        return passagers;
    }

    public void setPassagers(List<User> passagers) {
        this.passagers = passagers;
    }

    public void setPlaceLeft(int _placeLeft) {

        this.placeLeft = this.placeLeft - _placeLeft;
    }

    public String getDepart() {
        return Depart;
    }

    public void setDepart(String departPlace) {
        Depart = departPlace;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }



    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public UUID getID() {

        return itemID;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }
}
