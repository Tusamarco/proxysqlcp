package net.tc.mysql.cluster.cp.listeners.handlers;

import com.mysql.cluster.mgmj.listeners.*;

import net.tc.mysql.cluster.util.TimeTools;

import com.mysql.cluster.mgmj.events.NdbStopStarted;

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
public class NDBStopStartedListener extends NdbStopStartedTypeListener {
    public NDBStopStartedListener() {
    }

    public static void main(String[] args) {
        NDBStopStartedListener ndbstopstartedlistener = new
                NDBStopStartedListener();
    }

    @Override
    public void handleEvent(NdbStopStarted event) {
        System.out.println(TimeTools.GetCurrentTime() +
                           " - Ndb Stop Started(stop type):" + event.getStopType() +
                           " Node: "  + event.getSourceNodeId());
    }

}
