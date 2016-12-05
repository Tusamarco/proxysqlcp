package net.tc.mysql.cluster.cp.listeners.handlers;

import com.mysql.cluster.mgmj.listeners.*;

import net.tc.mysql.cluster.util.TimeTools;

import com.mysql.cluster.mgmj.events.SentHeartbeat;

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
public class SentHeartbeatListener extends SentHeartbeatTypeListener {
    public SentHeartbeatListener() {
    }

    public static void main(String[] args) {
        SentHeartbeatListener sentheartbeatlistener = new SentHeartbeatListener();
    }

    @Override
    public void handleEvent(SentHeartbeat event) {

        System.out.println(TimeTools.GetCurrentTime() +
                   " - Ndb SentHeartbeat source Node: "  + event.getSourceNodeId()   +
                   " CPtr: " + event.getCPtr(event) +
                   " Node: " + event.getNode()

        );


    }

}
