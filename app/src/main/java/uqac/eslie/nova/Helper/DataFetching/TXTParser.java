package uqac.eslie.nova.Helper.DataFetching;

import android.icu.text.DateFormat;
import java.util.TimeZone;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static uqac.eslie.nova.Helper.otherUtils.removeAccent;

/**
 * Created by eliea on 02/11/2017.
 */

public class TXTParser {
    public static ArrayList<String[]> doIt(URL url){
        // lines
        int start27 = 11;
        int start3 = 14;
        int end3 = 21;

        BufferedReader buff;

        try {
            ArrayList<String[]> result = new ArrayList<String[]>();
            String inputLine;
            InputStreamReader inputReader = new InputStreamReader(url.openStream());
            buff = new BufferedReader(inputReader);
            int n = -1;

            if(url.toString().endsWith("27-day-outlook.txt")) {
                result.add(new String[]{"27"});
                Date currentTime = new Date();
                SimpleDateFormat myFormat = new SimpleDateFormat("yyyy MMM dd");
                String currentDateString = myFormat.format(currentTime);
                Date currentDate = myFormat.parse(currentDateString);


                //textFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                while ((inputLine = buff.readLine()) != null) {
                    n++;
                    if (n < start27) // comment lines in the text
                        continue;

                    String[] temp = inputLine.split("\\s{2,}");
                    String tempo = temp[0].toLowerCase();

                    if(!tempo.isEmpty()){
                        if (tempo.contains("dec")){
                            tempo = tempo.substring(0,6) + 'é' + tempo.substring(7, tempo.length());
                        }
                        Date textDate = myFormat.parse(tempo);

                        if (textDate.before(currentDate)){
                            continue;
                        }
                        else{
                            result.add(temp);
                        }
                    }
                }
                return result;
            }
            else if(url.toString().endsWith("3-day-forecast.txt")) {
                result.add(new String[]{"3"});
                while ((inputLine = buff.readLine()) != null) {
                    n++;
                    if (n < start3)
                        continue;
                    else if (n > end3)
                        break;
                    //if dateDuJour.after(dateDeLaLigne)
                    //{
                    //  prochaine ligne
                    //else
                    result.add(inputLine.split("\\s{2,}")); // = plus de 2 espaces
                    //System.out.println(inputLine);
                    //}
                }
            }

            buff.close();
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
