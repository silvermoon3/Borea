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

import uqac.eslie.nova.Helper.Timestamp;

/**
 * Created by ESTEL on 26/10/2017.
 */

public class CarPooling  implements Comparable<CarPooling> {

    private UUID itemID;
    private User user;
    private Double price;
    private String Depart;
    private String Destination;
    private String hour;
    private String returnHour;
    private int placeTotal;
    private int placeLeft;
    private Date date;
    private String dateText;
    private String marque;
    private Timestamp timestamp;
    private String IDFirebase;

    public String getIDFirebase() {
        return IDFirebase;
    }

    public void setIDFirebase(String IDFirebase) {
        this.IDFirebase = IDFirebase;
    }

    public List<User> getPassagers() {
        return passagers;
    }

    public void setPassagers(List<User> passagers) {
        this.passagers = passagers;
    }

    public void addPassager(User user){
        this.passagers.add(user);
    }

    private List<User> passagers;

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public CarPooling(){
        price = 0.0;
        Depart = "";
        Destination = "";
        hour = "";
        returnHour = "";
        placeLeft = 0;
        placeTotal = 0;
        date = null;
        marque = "";
        dateText = "";
        timestamp = null;


       // itemID = UUID.randomUUID();
        passagers = new ArrayList<>();
    }


    @Override
    public int compareTo(@NonNull CarPooling o) {
        return this.date.compareTo(o.date);
    }


 /*   public void addPassager(User passager){
        passagers.add(passager);
    }*/
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDateText() {
        return dateText;
    }

    public void setDateText(String dateText) {
        this.dateText = dateText;
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

    public UUID getItemID() {
        return itemID;
    }

    public void setItemID(UUID itemID) {
        this.itemID = itemID;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setPlaceLeft(int _placeLeft) {

        this.placeLeft = _placeLeft ;
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



    public Double getPrice() {
        return price;
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
