package uqac.eslie.nova.BDD;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ESTEL on 12/11/2017.
 */

public class KP extends SugarRecord {
    private Long id;
    int now;

    Date dernierMiseAJour;
    int h_03;
    int h_36;
    int h_69;
    int h_912;
    int h_1215;
    int h_1518;
    int h_1821;
    int h_2100;

    public int getH_03() {
        return h_03;
    }

    public void setH_03(int h_03) {
        this.h_03 = h_03;
    }

    public int getH_36() {
        return h_36;
    }

    public void setH_36(int h_36) {
        this.h_36 = h_36;
    }

    public int getH_69() {
        return h_69;
    }

    public void setH_69(int h_69) {
        this.h_69 = h_69;
    }

    public int getH_912() {
        return h_912;
    }

    public void setH_912(int h_912) {
        this.h_912 = h_912;
    }

    public int getH_1215() {
        return h_1215;
    }

    public void setH_1215(int h_1215) {
        this.h_1215 = h_1215;
    }

    public int getH_1518() {
        return h_1518;
    }

    public void setH_1518(int h_1518) {
        this.h_1518 = h_1518;
    }

    public int getH_1821() {
        return h_1821;
    }

    public void setH_1821(int h_1821) {
        this.h_1821 = h_1821;
    }

    public int getH_2100() {
        return h_2100;
    }

    public void setH_2100(int h_2100) {
        this.h_2100 = h_2100;
    }

    public KP(){
        now= 0;
        dernierMiseAJour = null;
        h_03 = 0;
        h_36 = 0;
        h_69 = 0 ;
        h_912 = 0 ;
        h_1215= 0 ;
        h_1518= 0 ;
        h_1821= 0 ;
        h_2100= 0 ;
    }

    public KP(Date _date, int _now){
        now = _now;
        dernierMiseAJour = _date;
        h_03 = 0;
        h_36 = 0;
        h_69 = 0 ;
        h_912 = 0 ;
        h_1215= 0 ;
        h_1518= 0 ;
        h_1821= 0 ;
        h_2100= 0 ;

    }



    public Date getDernierMiseAJour() {
        return dernierMiseAJour;
    }

    public void setDernierMiseAJour(Date dernierMiseAJour) {
        this.dernierMiseAJour = dernierMiseAJour;
    }

    public int getNow() {

        return now;
    }

    public void setNow(int now) {
        this.now = now;
    }
}
