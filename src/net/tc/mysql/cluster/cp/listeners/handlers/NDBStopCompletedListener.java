package net.tc.mysql.cluster.cp.listeners.handlers;

import com.mysql.cluster.mgmj.listeners.*;

import net.tc.mysql.cluster.util.TimeTools;

import com.mysql.cluster.mgmj.events.NdbStopCompleted;

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
public class NDBStopCompletedListener extends NdbStopCompletedTypeListener {
    public NDBStopCompletedListener() {
    }

    public static void main(String[] args) {
        NDBStopCompletedListener ndbstopcompletedlistener = new
                NDBStopCompletedListener();
    }

    @Override
    public void handleEvent(NdbStopCompleted event) {
        System.out.println(TimeTools.GetCurrentTime() +
                           " - Ndb Stop Completed(signal):" + event.getSigNum() +
                           " Node: "  + event.getSourceNodeId());


    }

}
