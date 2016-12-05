package net.tc.isma.graph.cewolf;

import java.util.Map;
import java.util.List;
import java.util.Vector;

import java.io.Serializable;
import org.jfree.data.DefaultPieDataset;
import org.jfree.data.DefaultIntervalCategoryDataset;
import org.jfree.chart.entity.CategoryItemEntity;
//import org.jfree.chart.tooltips.CategoryToolTipGenerator;
import de.laures.cewolf.DatasetProduceException;
import de.laures.cewolf.DatasetProducer;
import de.laures.cewolf.CategoryItemLinkGenerator;

public class GraphHtmlMapPie implements DatasetProducer,  Serializable {

    // These values would normally not be hard coded but produced by
    // some kind of data source like a database or a file
//        final String[] categories = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
//        final String[] seriesNames = {"TF", "UN"};
//        final String[] seriesUrl = {"javascript:alert('TF')", "javascript:alert('UN')"};
//        final String[] seriesToolTip = {"Test", "Test1"};
        //final String[] seriesNames = { "TF", "UN", "XX", "ZZ" };

        private DefaultPieDataset ds = null;

    public GraphHtmlMapPie(DefaultPieDataset ds) {
        this.ds = ds;
    }
    public Object produceDataset(Map params) {
        return this.ds;
    }
//    public Object produceDataset(Map params) {
//        final Integer[][] startValues = new Integer[seriesNames.length][categories.length];
//        final Integer[][] endValues = new Integer[seriesNames.length][categories.length];
//        for (int series = 0; series < seriesNames.length; series++) {
//            for (int i = 0; i < categories.length; i++) {
//                int y = (int) (Math.random() * 10 + 1);
//                startValues[series][i] = new Integer(y);
//                //startValues[series][i] = new Integer(-10);
//                if(!(series==1 && i==1)) endValues[series][i] = new Integer(y + (int) (Math.random() * 10));
//                //endValues[series][i] = new Integer(10);
//            }
//        }
//        DefaultIntervalCategoryDataset ds = new DefaultIntervalCategoryDataset(seriesNames, categories, startValues, endValues);
//
//        return ds;
//    }

    public boolean hasExpired(java.util.Map map, java.util.Date date){
        return (System.currentTimeMillis() - date.getTime())  > 5000;
    }

    public String getProducerId(){
        return "GraphHtmlMap";
    }

    public String generateToolTip(DefaultPieDataset data, int series, int category) {
        return null;
    }

}
