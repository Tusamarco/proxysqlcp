package net.tc.mysql.cluster.cp.listeners.handlers;

import com.mysql.cluster.mgmj.listeners.*;

import net.tc.mysql.cluster.util.TimeTools;

import com.mysql.cluster.mgmj.events.StartReport;

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
public class StartReportListener extends StartReportTypeListener {
    public StartReportListener() {
    }

    public static void main(String[] args) {
        StartReportListener startreportlistener = new StartReportListener();
    }

    @Override
    public void handleEvent(StartReport event) {

        System.out.println(TimeTools.GetCurrentTime() +
                   " - Ndb Start Report Node: "  + event.getSourceNodeId()   +
                   " CPtr: " + event.getCPtr(event) +
                   " Bitmask Size: " + event.getBitmaskSize()+
                   " Remaining Time: " + event.getRemainingTime() +
                   " Report Type: " + event.getReportType()

        );

    }

}
