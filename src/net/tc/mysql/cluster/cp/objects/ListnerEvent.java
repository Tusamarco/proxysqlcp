package net.tc.mysql.cluster.cp.objects;

import java.util.*;
import net.tc.isma.comparators.SortableByTime;

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
 *
 *
 * This class is the object representation of a single event captured by the Listner.
 *
 * It has ONLY information about the event, current value, state OK WARNING ERROR
 * and event time
 *
 * the event is written in the log as soon as it happen but it is retained in memory for a period
 * of time specified by the parameter <ndbcpeventmemoryloglife> in ndbcpconf.xml in seconds.
 *
 */
public class ListnerEvent implements SortableByTime,Checkable {
    private Date date;
    private Date time;
    private String event;
    private int status;
    private Object currentvalue;
    private long  systemTime;
    private int nodeId = 0;
    public ListnerEvent() {

    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public void setStatus(int statusIn) {
        this.status = statusIn;

    }

    public void setCurrentvalue(Object currentvalue) {
        this.currentvalue = currentvalue;
    }

    public void setSystemTime(long systemTime) {
        this.systemTime = systemTime;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public Date getDate() {
        return date;
    }

    public Date getTime() {
        return time;
    }

    public String getEvent() {
        return event;
    }

    public int getStatus() {
        return status;
    }

    public Object getCurrentvalue() {
        return currentvalue;
    }

    public long getSystemTime() {
        return systemTime;
    }

    public int getNodeId() {
        return nodeId;
    }

    public Object getChekableValue() {
        return this.getCurrentvalue();
    }

}
