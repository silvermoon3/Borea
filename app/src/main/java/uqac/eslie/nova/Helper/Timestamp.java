package uqac.eslie.nova.Helper;

/**
 * Created by ESTEL on 15/11/2017.
 */

public class Timestamp extends java.sql.Timestamp {

    public Timestamp(long time) {
        super(time);
    }

    public Timestamp() {
        super(System.currentTimeMillis());
    }



}