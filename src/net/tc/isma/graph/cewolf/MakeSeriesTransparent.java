package net.tc.isma.graph.cewolf;

import java.util.Map;
import java.awt.Color;

import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.CategoryItemRenderer;
import org.jfree.chart.JFreeChart;
import org.jfree.data.Values2D;


public class MakeSeriesTransparent implements de.laures.cewolf.ChartPostProcessor
{

    public MakeSeriesTransparent()
    {
    }

    public void processChart(Object chart, Map params) {
        CategoryPlot cPlot = (CategoryPlot) ((JFreeChart) chart).getCategoryPlot();
        CategoryItemRenderer cIRenderer = cPlot.getRenderer();
        int sCount = ((Values2D)cPlot.getDataset()).getRowCount();
        Color oldC = null;
        for(int i=0;i<sCount;i++){
            oldC = (Color)cIRenderer.getSeriesPaint( i,0);
            cIRenderer.setSeriesPaint(i, new Color(oldC.getRed(), oldC.getGreen(), oldC.getBlue(), 100));
        }
    }


}
