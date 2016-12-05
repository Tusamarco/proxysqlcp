package net.tc.mysql.cluster.cp.objects;

/**
 * <p>Title: NDBJ / API</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Marco Tusa Copyright (c) 2008</p>
 *
 * <p>Company: MySQL</p>
 *
 * @author Marco Tusa
 * @version 1.0
 */
public class RangeHandler extends ListnerEvent {
    private ListnerEvent eventStart;
    private ListnerEvent eventStop;
    private long systemTime;
//    private Object currentValue;
    public RangeHandler() {
    }

    public void setEventStart(ListnerEvent eventStart) {
        this.eventStart = eventStart;
    }

    public void setEventStop(ListnerEvent eventStop) {
        this.eventStop = eventStop;
    }

    public void setSystemTime(long systemTime) {
        this.systemTime = systemTime;
    }

//    public void setCurrentValue(Object currentValue) {
//        this.currentValue = currentValue;
//    }

    public ListnerEvent getEventStart() {
        return eventStart;
    }

    public ListnerEvent getEventStop() {
        return eventStop;
    }

    public long getSystemTime() {
        return systemTime;
    }

//    public Object getCurrentValue() {
//        return currentValue;
//    }


}
