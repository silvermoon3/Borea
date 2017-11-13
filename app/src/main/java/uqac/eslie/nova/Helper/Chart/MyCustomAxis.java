package uqac.eslie.nova.Helper.Chart;

import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

public class MyCustomAxis implements IAxisValueFormatter
{

    protected String[] mMonths = new String[]{
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    };

    private BarLineChartBase<?> chart;

    public MyCustomAxis(BarLineChartBase<?> chart) {
        this.chart = chart;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {


       if(value == 0)
           return "0-3";
        if(value == 10)
            return "3-6";
        if(value == 20)
            return "6-9";
        if(value == 30)
            return "9-12";
        if(value == 40)
            return "12-15";
        if(value == 50)
            return "15-18";
        if(value == 60)
            return "18-21";
        if(value == 70)
            return "21-00";



       return "";
    }


}
