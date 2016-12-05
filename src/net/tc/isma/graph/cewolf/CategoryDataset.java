package net.tc.isma.graph.cewolf;

import org.jfree.data.*;
import java.util.List;

public class CategoryDataset extends DefaultCategoryDataset
{

    public CategoryDataset()
    {
    }

    public void setColumValueList(int column, List valueList){
        setColumValueList(getColumnKey(column), valueList);
    }

    public void setColumValueList(Comparable columnKey, List valueList){
        int count = valueList.size();
        for(int i =0;i<count;i++){
            addValue(convertToNumber(valueList.get(i)), getRowKey(i), columnKey);
        }
    }

    public void setRowValueList(int row, List valueList){
        setRowValueList(getRowKey(row), valueList);
    }

    public void setRowValueList(Comparable rowKey, List valueList){
        int count = valueList.size();
        for(int i =0;i<count;i++){
            addValue(convertToNumber(valueList.get(i)), rowKey, getColumnKey(i));
        }
    }

    public void setValueList(List valueList){
        int countRows = getRowCount();
        int countColumns = getColumnCount();
        int k = 0;
        for(int j =0;j<countColumns;j++){
            for(int i =0;i<countRows;i++){
                addValue(convertToNumber(valueList.get(k++)), getRowKey(i), getColumnKey(j));
            }
        }
    }

    public void setValueListCumulative(List valueList){
        int countRows = getRowCount();
        int countColumns = getColumnCount();
        int k = 0;
        double rowSum = 0;
        Number rowValue;
        for(int j =0;j<countColumns;j++){
            for(int i =0;i<countRows;i++){
                rowValue = convertToNumber(valueList.get(k++));
                rowSum+= rowValue.doubleValue();
                addValue(rowSum, getRowKey(i), getColumnKey(j));
            }
        }
    }

    public void setTableData(List rows, List columns, List values){
        int count = values.size();
        String rowKey = null;
        String columnKey = null;
        int i=0;
        while(i<count){
            columnKey = (String)columns.get(i);
            while(i<count &&
            columnKey.equals((String)columns.get(i))){
                rowKey = (String)rows.get(i);
                while(i<count &&
                columnKey.equals((String)columns.get(i)) &&
                rowKey.equals((String)rows.get(i))){
                    addValue(convertToNumber(values.get(i)), rowKey, columnKey);
                    i++;
                }
            }
        }
    }

//    public void setTableDataCumulative(List rows, List columns, List values){
//        int count = values.size();
//        String rowKey = null;
//        String columnKey = null;
//        int i=0;
//        double columnSum = 0;
//        Number columnValue;
//        while(i<count){
//            columnKey = (String)columns.get(i);
//            //columnSum = 0;
//            while(i<count &&
//            columnKey.equals((String)columns.get(i))){
//                rowKey = (String)rows.get(i);
//                while(i<count &&
//                columnKey.equals((String)columns.get(i)) &&
//                rowKey.equals((String)rows.get(i))){
//                    columnValue = convertToNumber(values.get(i));
//                    columnSum += columnValue.doubleValue();
//                    addValue(columnSum, rowKey, columnKey);
//                    i++;
//                }
//            }
//        }
//    }

    public void makeDataCumulative(){
        int columnsCount = getColumnCount();
        int rowCount = getRowCount();
        double sumRow = 0;

        for(int r=0;r<rowCount;r++){
            sumRow = 0;
            for(int c=0;c<columnsCount;c++){
//                sumRow += ((Double)Util.nvl(getValue(r, c), new Double(0))).doubleValue();
                setValue(sumRow, getRowKey(r), getColumnKey(c));
            }
        }

    }


    public void setListData(List rows, List columns, List values){
        int countRows = rows.size();
        int countColumns = columns.size();
        int countValues = values.size();
        String rowKey = null;
        String columnKey = null;
        int k=0;
        for(int i=0;i<countRows;i++){
            rowKey = (String)rows.get(i);
            for(int j=0;j<countColumns;j++){
                columnKey = (String)columns.get(j);

                addValue(convertToNumber(values.get(k)), rowKey, columnKey);
                System.out.println("-------------------------------------------------");
                System.out.println(k);
                System.out.println(values.get(k));
                System.out.println(convertToNumber(values.get(k)));
                System.out.println(rowKey);
                System.out.println(columnKey);
                System.out.println("-------------------------------------------------");
                k++;
            }
        }
    }

    private Number convertToNumber(Object value) throws NumberFormatException{
        Number result = null;
        if(value instanceof Number){
            result = (Number)value;
        }else if(value instanceof String){
            result = new Double((String)value);
        }else{
            throw new NumberFormatException();
        }

        return result;
    }



}