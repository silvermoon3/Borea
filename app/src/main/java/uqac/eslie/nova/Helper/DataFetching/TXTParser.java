package uqac.eslie.nova.Helper.DataFetching;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

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
                while ((inputLine = buff.readLine()) != null) {
                    n++;
                    if (n < start27)
                        continue;
                    //if dateDuJour.after(dateDeLaLigne)
                    //{
                    //  prochaine ligne
                    //else
                    result.add(inputLine.split("\\s{2,}")); // = plus de 2 espaces
                    //}
                }
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

            else if(url.toString().endsWith(".json")){
                result = jsonInterpreter();
            }

            buff.close();
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static ArrayList<String[]> jsonInterpreter(){

        return null;
    }
}
