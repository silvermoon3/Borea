package uqac.eslie.nova.BDD;

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
    private int placeTotal;
    private int placeLeft;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public  CarPooling() {

        itemID = UUID.randomUUID();
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


}
