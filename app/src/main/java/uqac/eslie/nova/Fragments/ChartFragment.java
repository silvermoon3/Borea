package uqac.eslie.nova.Fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;



import uqac.eslie.nova.R;

/**
 * Created by ESTEL on 10/11/2017.
 */

public class ChartFragment extends Fragment implements SeekBar.OnSeekBarChangeListener,
        OnChartValueSelectedListener {

    protected BarChart mChart;
    protected BarChart mChart2;
    protected Typeface mTfRegular;
    protected Typeface mTfLight;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_chart, container, false);

        mChart = root.findViewById(R.id.chart);
        mChart.setOnChartValueSelectedListener(this);

        mChart2 = root.findViewById(R.id.chart2);
        mChart2.setOnChartValueSelectedListener(this);

        // mChart.setHighlightEnabled(false);


        mChart.setDrawBarShadow(false);
        mChart2.setDrawBarShadow(false);

        mChart.setDrawValueAboveBar(true);
        mChart2.setDrawValueAboveBar(true);

        mChart.getDescription().setEnabled(false);
        mChart2.getDescription().setEnabled(false);
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount(60);
        mChart2.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);
        mChart2.setPinchZoom(false);

        mChart.setDoubleTapToZoomEnabled(false);
        mChart2.setDoubleTapToZoomEnabled(false);

        // draw shadows for each bar that show the maximum value
        // mChart.setDrawBarShadow(true);

        mChart.setDrawGridBackground(false);
        mChart2.setDrawGridBackground(false);



        mChart.setDrawBarShadow(false);
        mChart.setDrawGridBackground(false);
        mChart.setGridBackgroundColor(Color.WHITE);
        mChart2.setGridBackgroundColor(Color.WHITE);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(Color.WHITE);
        mChart.getAxisLeft().setDrawGridLines(false);
        mChart.getAxisLeft().setTextColor(Color.WHITE);
        mChart.getAxisRight().setTextColor(Color.WHITE);

        /* chart 2  */
        mChart2.setDrawBarShadow(false);
        mChart2.setDrawGridBackground(false);



        XAxis xAxis2 = mChart2.getXAxis();
        xAxis2.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis2.setDrawGridLines(false);
        xAxis2.setTextColor(Color.WHITE);
        mChart2.getAxisLeft().setDrawGridLines(false);
        mChart2.getAxisLeft().setTextColor(Color.WHITE);
        mChart2.getAxisRight().setTextColor(Color.WHITE);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
        l.setTextColor(Color.WHITE);


        setData(8, 50);

        // setting data



        return root;
    }





    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {


        mChart.setFitBars(true);
        mChart.invalidate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    private void setData(int count, float range) {

        float barWidth = 9f;
        float spaceForBar = 10f;
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

       int[] KP = new int[count];
        for (int i = 0; i < count; i++) {
            int val = (int) (Math.random() * 6);
            KP[i] = val;
        }

        for (int i = 0; i < count; i++) {
            float val = (float) (Math.random() * range);
            yVals1.add(new BarEntry(i * spaceForBar, KP[i]));
        }


        ArrayList<BarEntry> yVals2 = new ArrayList<BarEntry>();

        int[] cloud = new int[count];
        for (int i = 0; i < count; i++) {
            int val = (int) (Math.random() * 100);
            cloud[i] = val;
        }

        for (int i = 0; i < count; i++) {
            float val = (float) (Math.random() * range);
            yVals2.add(new BarEntry(i * spaceForBar, cloud[i]));

        }

        BarDataSet set1;
        BarDataSet set2;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet)mChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "Coefficient KP");
            set1.setColors(Color.rgb(209, 196, 233));
            //set1.setColors(ColorTemplate.LIBERTY_COLORS);
            set1.setValueTextColor(Color.WHITE);
            set1.setDrawIcons(false);

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setValueTypeface(mTfLight);
            data.setBarWidth(barWidth);
            mChart.setData(data);
        }


        if (mChart2.getData() != null &&
                mChart2.getData().getDataSetCount() > 0) {
            set2 = (BarDataSet)mChart2.getData().getDataSetByIndex(0);
            set2.setValues(yVals2);
            mChart2.getData().notifyDataChanged();
            mChart2.notifyDataSetChanged();
        } else {
            set2 = new BarDataSet(yVals2, "Couverture nuageuse");
            set2.setColors(Color.rgb(209, 196, 233));
            set2.setValueTextColor(Color.WHITE);
            set2.setDrawIcons(false);
            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set2);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setValueTypeface(mTfLight);
            data.setBarWidth(barWidth);
            mChart2.setData(data);
        }
    }

    protected RectF mOnValueSelectedRectF = new RectF();
    @SuppressLint("NewApi")
    @Override
    public void onValueSelected(Entry e, Highlight h) {

        if (e == null)
            return;

        RectF bounds = mOnValueSelectedRectF;
        mChart.getBarBounds((BarEntry) e, bounds);

        MPPointF position = mChart.getPosition(e, mChart.getData().getDataSetByIndex(h.getDataSetIndex())
                .getAxisDependency());

        Log.i("bounds", bounds.toString());
        Log.i("position", position.toString());

        MPPointF.recycleInstance(position);
    }

    @Override
    public void onNothingSelected() {
    };


}
